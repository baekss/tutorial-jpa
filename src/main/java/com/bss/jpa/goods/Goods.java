package com.bss.jpa.goods;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)//자식 객체와 join형식으로 테이블 DDL 생성
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE) //부모와 모든 자식 객체가 같이 공존한 형태의 테이블 DDL 생성. 자식 객체 식별을 위해 DTYPE 컬럼 무조건 생긴다. 또한 자식 객체 필드에 해당하는 모든 컬럼은 경우에 따라 컬럼 값이 다르게 채워질테니 null 허용해야 함.
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) //부모 객체에 해당하는 테이블이 따로 생성되지 않고 부모+자식 객체에 해당하는 테이블만 각각 생긴다. 자식 객체 식별 할 일이 없으므로 DTYPE 컬럼은 생기지 않는다.
@DiscriminatorColumn //PK와 FK로도 부모 자식 테이블 구분 가능하지만 DTYPE 으로 자식테이블 구분 값을 준다.
public abstract class Goods {
	
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	private int price;
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
