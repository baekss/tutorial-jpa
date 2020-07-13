package com.bss.jpa.shop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem {
	
	@Id
	@GeneratedValue
	@Column(name="ORDER_ITEM_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="FK_ORDER_ID")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name="FK_ITEM_ID")
	private Item item;
	
	private int orderPrice;
	
	private int count;
	
}
