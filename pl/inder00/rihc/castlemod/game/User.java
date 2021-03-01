package pl.inder00.rihc.castlemod.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import pl.inder00.rihc.castlemod.modes.Arena;

public class User {
	
	public static List<User> players = new ArrayList<User>();
	
	private String name;
	private Arena arena;
	private int team;
	private Player lastDamaged;
	
	public User(String name, Arena arena, int team){
		this.name = name;
		this.arena = arena;
		this.team = team;
		players.add(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Arena getArena() {
		return arena;
	}

	public void setArena(Arena arena) {
		this.arena = arena;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}
	public void delete(){
		players.remove(this);
	}
	
	public static User get(String name){
		for(User p : players){
			if(p.getName().equalsIgnoreCase(name)){
				return p;
			}
		}
		return null;
	}

	public Player getLastDamaged() {
		return lastDamaged;
	}

	public void setLastDamaged(Player lastDamaged) {
		this.lastDamaged = lastDamaged;
	}

}
