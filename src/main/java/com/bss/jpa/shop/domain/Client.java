package com.bss.jpa.shop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;

@Entity
public class Client extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CLIENT_ID")
	private Long id;
	
	@Column(length=10)
	private String name;
	
	@BatchSize(size=100) //fetch join 시 1에 대한 N을 조회할 쿼리가 날라갈 때 IN 절로 일괄 조회를 할 수 있도록 한다.
	@OneToMany(mappedBy="client") //매핑 관계의 주인인 Order 객체가 Client 객체와 join을 맺을 때 사용한 필드가 mappedBy 연관관계를 나타낸다. 읽기전용 속성으로 jpa의해 쓰기, 수정 등이 관리되지 않는다.
	private List<Order> orders = new ArrayList<Order>();
	
	/*
	1:N 관계일때만 가능한 옵션이다.
	ORDERS 테이블의 FK_CLIENT_ID 를 Client 영속객체가 DB에 반영될 때 update 쿼리로 관리함
	Order 객체에서는 client필드를 갖지 않게 한다.(다만, Order 객체에서 mappedBy 처럼 client를 가지고 싶을 땐 insertable=false, updatable=false를 적용해 읽기전용으로 만듬)
	@OneToMany
	@JoinColumn(name="FK_CLIENT_ID")
	private List<Order> orders = new ArrayList<Order>();
	*/
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Order> getOrders() {
		return orders;
	}
}
