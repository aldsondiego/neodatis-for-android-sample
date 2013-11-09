package com.puongra.neotest.Entity;

import java.util.Date;

public class Player {
	
	public String name;
	public Date birthDate;
	public Sport favoriteSport;
	
	public Player(String name, Date birthDate, Sport favoriteSport) {
		this.name = name;
		this.birthDate = birthDate;
		this.favoriteSport = favoriteSport;
	}
	
	public String toString(){
		return this.name + " - "+this.birthDate +" - "+this.favoriteSport.name;
	}
}
