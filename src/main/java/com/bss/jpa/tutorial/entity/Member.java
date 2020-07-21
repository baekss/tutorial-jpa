package com.bss.jpa.tutorial.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {
	
	@Id
	private Long id;
	private String name;
	
	@Embedded //관계성이 있는 필드를 객체로 관리 (생성되는 테이블은 하나의 테이블로 생성됨)
	private Period period;
	
	@Embedded
	private Address address;
	
	@Embedded //동일한 Address(동일한 컬럼)를 다른 컨셉으로 사용하고 싶을 때 값을 오버라이드하여 테이블에 해당 컬럼을 추가한다.
	@AttributeOverrides({
		@AttributeOverride(name="city",column=@Column(name="workCity")),
		@AttributeOverride(name="street",column=@Column(name="workStreet")),
		@AttributeOverride(name="zipcode",column=@Column(name="workZipcode")),
	})
	private Address workAddress;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Period getPeriod() {
		return period;
	}
	public void setPeriod(Period period) {
		this.period = period;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
}
