package com.bss.jpa.goods;

import javax.persistence.Entity;

@Entity
public class Movie extends Goods {

	private String director;
	private String actor;
	
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	
}
