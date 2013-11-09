package com.puongra.neotest.Entity;

import java.util.ArrayList;
import java.util.List;

public class Team {

	private String name;
	private List players;

	public Team(String name) {
		this.name = name;
		players = new ArrayList();
	}
	
	public String toString(){
		return name+" - Players:"+players.size();
	}
	public void addPlayer(Player play){
		players.add(play);
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public List getPlayers(){
		return players;
	}
	
	public void setPlayers(List players){
		this.players = players;
	}
}