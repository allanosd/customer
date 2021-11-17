package com.bootcamp.microservices.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.microservices.app.dao.CustomerDao;
import com.bootcamp.microservices.app.documents.Customer;
import com.bootcamp.microservices.app.documents.TypeCustomerDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
	
    private final WebClient webClient;
    
    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    @Value("${config.base.typecustomer}")
    private String url;

    public CustomerServiceImpl(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.webClient = WebClient.builder().baseUrl(this.url).build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("typeCustomer");
    }

    @Autowired
    private CustomerDao dao;

    @Override
    public Mono<TypeCustomerDto> findTypeCustomer(String id) {
        log.info("Buscando typeCustomer");
        return reactiveCircuitBreaker.run(webClient.get().uri(this.url,id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(TypeCustomerDto.class),
                throwable -> {
                    return this.getDefaultTypeCustomer();
                });
    }

    /**
     *
     * @return null if it does not find a Customer
     */
    public Mono<TypeCustomerDto> getDefaultTypeCustomer() {
        log.info("la peticion no respondio");
        Mono<TypeCustomerDto> tcustomer = Mono.just(new TypeCustomerDto("0", null, null));
        return tcustomer;
    }

    @Override
    public Flux<Customer> findAll() {
        return dao.findAll();
    }

    @Override
    public Mono<Customer> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public Mono<Customer> findByDocumentNumber(String documentNumber) {
        return dao.findByDocumentNumber(documentNumber).next();
    }

    @Override
    public Mono<Customer> create(Customer customer) {
        //return dao.save(customer);
        return dao.findByDocumentNumber(customer.getDocumentNumber()).collectList().flatMap( c -> {
            if(c.isEmpty()){
                log.info("customer recibido");
                customer.setNames(customer.getNames().toUpperCase());
                customer.setGender(customer.getGender().toUpperCase());
                customer.setAddress(customer.getAddress().toUpperCase());
                return dao.save(customer);
            }else{
                return Mono.just(new Customer());
            }
        });
    }

    @Override
    public Mono<Customer> update(Customer customer) {
        return dao.findById(customer.getId()).flatMap(c -> {
            return dao.save(customer);
        });
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return dao.findById(id)
                .flatMap(dc -> dao.delete(dc).then(Mono.just(Boolean.TRUE)))
                .defaultIfEmpty(Boolean.FALSE);
    }

}
