package com.bss.jpa.tutorial.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="MEMBER")
@SequenceGenerator(
name = "MEMBER_SEQ_GENERATOR"
, sequenceName = "MEMBER_SEQUENCE" //시퀀스 테이블명
, initialValue = 1, allocationSize = 10) //allocationSize 는 해당 값의 시퀀스를 미리 로딩시켜놓고 메모리에서 가져다 쓴다.(그때그때 시퀀스 채번 쿼리 안 날림.)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR") //시퀀스 테이블 사용 방법
	private Long id;
	
	@Column(name="name", nullable=false)
	private String userName;
	
	private Integer age;
	
	@Enumerated(EnumType.STRING)//EnumType.ORDINAL 은 Enum의 순서로 값을 넣기 때문에 Enum 요소의 순서 바뀌면 기존 데이터와 뒤죽박죽 되기 때문에 사용 금지
	private RoleType roleType;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	//java.time 패키지에 있는 것을 쓰면 @Temporal 어노테이션 없어도 하이버네이트가 적절한 타입으로 알아서 인식한다.(DDL ex. testLocalDate date, testLocalDateTime timestamp)
	private LocalDate testLocalDate;
	private LocalDateTime testLocalDateTime;
	
	@Lob
	private String description;
	
	@Transient
	private String temp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public LocalDate getTestLocalDate() {
		return testLocalDate;
	}

	public void setTestLocalDate(LocalDate testLocalDate) {
		this.testLocalDate = testLocalDate;
	}

	public LocalDateTime getTestLocalDateTime() {
		return testLocalDateTime;
	}

	public void setTestLocalDateTime(LocalDateTime testLocalDateTime) {
		this.testLocalDateTime = testLocalDateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
	
}
