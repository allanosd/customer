package com.bootcamp.microservices.app.documents;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document(collection="customer")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private String id;
    
    private DocumentType documentType; //ruc/dni
    
    private String documentNumber; //numero
    
    private String names; //nombres o razon social
    
    private String gender; //masculino / feminino
    
    private String numberphone; //telefono
    
    private String address;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateBirth;
    
    private TypeCustomerDto typeCustomer;
    
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date createAc;

    public enum DocumentType {
        DNI,
        RUC
    }
}
