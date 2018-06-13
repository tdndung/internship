package com.axonactive.training.sniffer;

import lombok.Getter;

@Getter
public enum Status{
	INACTIVE("1"),
	OPERATING("2"),
	STRIKED("3"),
	UNKNOWN("4");
	
	/**
	 * show the number of status
	 */
	private String number;
	
	private Status(String number) {
		this.number = number;
	}
	

}
