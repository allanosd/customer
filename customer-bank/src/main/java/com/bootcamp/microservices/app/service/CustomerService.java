package com.bootcamp.microservices.app.service;

import com.bootcamp.microservices.app.documents.Customer;
import com.bootcamp.microservices.app.documents.TypeCustomerDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
	
	public Mono<TypeCustomerDto> findTypeCustomer(String id);
    public Flux<Customer> findAll();
    public Mono<Customer> findById(String id);
    public Mono<Customer> findByDocumentNumber(String documentNumber);
    public Mono<Customer> create(Customer customer);
    public Mono<Customer> update(Customer customer);
    public Mono<Boolean> delete(String id);

}
