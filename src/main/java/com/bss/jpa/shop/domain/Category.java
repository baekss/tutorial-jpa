package com.bss.jpa.shop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Category {

	@Id @GeneratedValue
	@Column(name="CATEGORY_ID")
	private Long id;
	
	private String name; 
	
	@ManyToOne
	@JoinColumn(name="PARENT_ID")
	private Category parent;
	
	//cascade=CascadeType.ALL 해당객체에 연관된 모든객체가 같이 영속화 되면서 저장 수정 삭제가 함께 DB반영 이루어짐
	//orphanRemoval=true 해당객체가 가지고 있는 어떠한 객체 삭제시 DB에도 반영
	@OneToMany(mappedBy="parent", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Category> child = new ArrayList<Category>();
	
	@ManyToMany
	@JoinTable(name="CATEGORY_ITEM",
			joinColumns=@JoinColumn(name="CATEGORY_ID"),
			inverseJoinColumns=@JoinColumn(name="ITEM_ID")
	)
	private List<Item> items = new ArrayList<Item>();

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
		parent.getChild().add(this);
	}

	public List<Category> getChild() {
		return child;
	}
	
}
