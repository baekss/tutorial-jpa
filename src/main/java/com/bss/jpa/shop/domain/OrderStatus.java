package com.bss.jpa.shop.domain;

public enum OrderStatus {
	ORDER("주문"), CANCEL("취소");
	
	private String status;

	private OrderStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
	
}
