package com.bss.jpa.goods;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "GOODS_A") //DTYPE 으로 들어갈 값 지정 (기본은 Entity명)
public class Book extends Goods {

	private String author;
	private String isbn;
}
