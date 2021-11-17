package com.bootcamp.microservices.app.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp.microservices.app.documents.Customer;

import reactor.core.publisher.Flux;

public interface CustomerDao extends ReactiveMongoRepository<Customer,String> {
	
    Flux<Customer> findByDocumentNumber(String documentNumber);

}
