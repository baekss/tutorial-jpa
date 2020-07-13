package com.bss.jpa.shop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	@JoinColumn(name="FK_CLIENT_ID")
	private Client client; //하나의 주문은 하나의 인원에게 귀속된다. (인원 1명 - 주문 N개)
	
	@OneToOne
	@JoinColumn(name="DELIVERY_ID")
	private Delivery delivery;
	
	@OneToMany(mappedBy="order")//OrderItem 객체의 order 필드와 연관관계를 가진다.
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	//ORDERS 테이블과 ITEM 테이블을 N:M 관계를 하고 싶을 땐 중간에 조인 테이블 (ORDERS_ITEM) 둬 가능하지만, 오직 두 테이블의 관계에만 필요한 컬럼(ORDER_ID, ITEM_ID) 밖에 가질 수 없으므로 실무에선 사용하지 말자. 
	//실무에선 1:N - 일반테이블 - N:1 관계로 풀어서 사용함
	/*
	@ManyToMany
	@JoinTable(name="ORDERS_ITEM")
	private List<Item> items = new ArrayList<Item>();
	*/

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
