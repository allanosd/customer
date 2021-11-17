package com.bootcamp.microservices.app.documents;

import lombok.Data;

@Data
public class SubTypeDto {

	private String id;
	
	private EnumSubType value;

	public enum EnumSubType {
		NORMAL, VIP, PYME
	}

}
