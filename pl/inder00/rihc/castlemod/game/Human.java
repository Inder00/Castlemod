package pl.inder00.rihc.castlemod.game;

import java.util.ArrayList;
import java.util.List;

public class Human {
	
	public static List<Human> users = new ArrayList<Human>();
	
	private String name;
	private int coins;
	private int kills;
	private int deaths;
	private int wins;
	private int lose;
	private int pkt;
	
	public Human(String name, int coins, int kills, int deaths, int wins, int lose, int pkt){
		this.name = name;
		this.coins = coins;
		this.kills = kills;
		this.deaths = deaths;
		this.wins = wins;
		this.lose = lose;
		this.pkt = pkt;
		users.add(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}
	public static Human get(String name){
		for(Human e : users){
			if(e.getName().equalsIgnoreCase(name)){
				return e;
			}
		}
		return null;
	}

	public int getPkt() {
		return pkt;
	}

	public void setPkt(int pkt) {
		this.pkt = pkt;
	}

}
