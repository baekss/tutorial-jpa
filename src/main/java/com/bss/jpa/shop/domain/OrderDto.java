package com.bss.jpa.shop.domain;

public class OrderDto {
	private Long id;
	private String status;
	
	public OrderDto() {
		super();
	}
	
	public OrderDto(Long id, OrderStatus status) {
		super();
		this.id = id;
		this.status = status.getStatus();
	}
	
	public Long getId() {
		return id;
	}
	public String getStatus() {
		return status;
	}
}
