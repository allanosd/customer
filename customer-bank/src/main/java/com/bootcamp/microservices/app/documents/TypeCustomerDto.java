package com.bootcamp.microservices.app.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeCustomerDto {
	
	private String id;
	
    private EnumTypeCustomer value;
    
    private SubTypeDto subType;
    
    public enum EnumTypeCustomer {
        EMPRESARIAL, PERSONAL
    }
}
