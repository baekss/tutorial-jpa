package com.bss.jpa.shop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Client extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CLIENT_ID")
	private Long id;
	
	@Column(length=10)
	private String name;
	
	@OneToMany(mappedBy="client") //매핑 관계의 주인인 Order 객체가 Client 객체와 join을 맺을 때 사용한 필드가 mappedBy 연관관계를 나타낸다. 읽기전용 속성으로 jpa의해 쓰기, 수정 등이 관리되지 않는다.
	private List<Order> orders = new ArrayList<Order>();
	
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
