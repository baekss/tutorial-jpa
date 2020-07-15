package com.bss.jpa.shop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Item {
	@Id 
	@GeneratedValue
	@Column(name="ITEM_ID")
	private Long id;
	
	private String name;
	
	private int price;
	
	private int stockQuantity = 100;

	@ManyToMany(mappedBy="items")
	private List<Category> categories = new ArrayList<Category>();
	
	/*
	@ManyToMany(mappedBy="items")
	private List<Order> orders = new ArrayList<Order>();
	*/
}
