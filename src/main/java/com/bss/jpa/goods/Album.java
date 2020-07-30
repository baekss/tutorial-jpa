package com.bss.jpa.goods;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
		name="Album.findByArtist",
		query="SELECT a FROM Album a WHERE a.artist = :artist"
)
public class Album extends Goods {

	private String artist;

	protected Album() {
		super();
	}

	protected Album(String artist) {
		super();
		this.artist = artist;
	}
	
	public String getArtist() {
		return artist;
	}

}
