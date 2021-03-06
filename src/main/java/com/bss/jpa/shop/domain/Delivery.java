package com.bss.jpa.shop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Delivery extends BaseEntity {

	@Id @GeneratedValue
	@Column(name="DELIVERY_ID")
	private Long id;
	
	private DeliveryStatus status;
	
	@OneToOne(mappedBy="delivery")
	private Order order;
}
