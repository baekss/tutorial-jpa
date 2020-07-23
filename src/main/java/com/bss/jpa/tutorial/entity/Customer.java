package com.bss.jpa.tutorial.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //자동증가 사용 방법
	@Column(name="CUSTOMER_ID")
	private Long id;
	
	@Column(name="name", nullable=false)
	private String customerName;
	
	private Integer age;
	
	@ElementCollection //컬렉션 타입의 값을 저장할 컬럼 타입이 존재하지 않으므로, 1:N 구조로 만든다.
	@CollectionTable(name="FAVORITE_FOODS", joinColumns=@JoinColumn(name="CUSTOMER_ID"))
	@Column(name="FOOD_NAME") //컬렉션 안 요소가 String 하나 뿐 이므로 auto DDL시 varchar 계열의 컬럼 하나 생긴다.
	private Set<String> favoriteFoods = new HashSet<>();
	
	@ElementCollection
	@CollectionTable(name="HISTORY", joinColumns=@JoinColumn(name="CUSTOMER_ID"))
	//컬렉션 안 요소의 객체는 Embeddable 이여야 함
	private List<History> histories = new ArrayList<>();
	
	public Long getId() {
		return id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Set<String> getFavoriteFoods() {
		return favoriteFoods;
	}

	public List<History> getHistories() {
		return histories;
	}
	
}
