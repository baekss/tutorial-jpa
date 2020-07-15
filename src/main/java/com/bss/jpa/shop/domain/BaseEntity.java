package com.bss.jpa.shop.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass //Entity 간에 공통으로 사용할 속성을 묶어 super 클래스로 제공할 수 있다. 이 자체가 Entity는 아니라 영속객체 및 테이블과도 관계없다.
public abstract class BaseEntity {
	private String city; 
	private String street;
	private String zipcode;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
}
