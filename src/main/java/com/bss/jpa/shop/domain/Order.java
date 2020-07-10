package com.bss.jpa.shop.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ORDERS")
public class Order {
	@Id
	@GeneratedValue
	@Column(name="ORDER_ID")
	private Long id;
	
	//@Column(name="CLIENT_ID")
	//private Long clientId; //Order에서 clientId값 보다 Client 객체를 갖는 것이 더 자연스럽다.(객체지향적이지도 않고 객체그래프도 사용 못함)
	
	@ManyToOne
	//ORDERS 테이블에 명시한 FK 컬럼명을 쓰는 것이다. (CLIENT 테이블에 FK로 참조시킨 컬럼명(ex. primary key of CLIENT TABLE) 아님)
	//Auto DDL로 만들었으면 ALTER TABLE ORDERS RENAME COLUMN CLIENT_ID TO FK_CLIENT_ID_MAPPED_PK_CLIENT_ID_COLUMN_OF_CLIENT_TABLE 로 바꾸고 해보면 이해가 쉽다.
	@JoinColumn(name="FK_CLIENT_ID_MAPPED_PK_CLIENT_ID_COLUMN_OF_CLIENT_TABLE")
	private Client client; //하나의 주문은 하나의 인원에게 귀속된다. (인원 1명 - 주문 N개)
	
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public Long getId() {
		return id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
		client.getOrders().add(this);
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
}
