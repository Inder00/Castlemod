package pl.inder00.rihc.castlemod.modes.manager;

import java.util.ArrayList;
import java.util.List;

public class PVP {
	
	public static List<PVP> users = new ArrayList<PVP>();
	
	private String player;
	private int antylogout;
	
	public PVP(String player){
		this.player = player;
		this.antylogout = 0;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public int getAntylogout() {
		return antylogout;
	}

	public void setAntylogout(int antylogout) {
		this.antylogout = antylogout;
	}
	public void delete(){
		users.remove(this);
	}
	
	public static PVP get(String name){
		for(PVP user : users){
			if(user.getPlayer().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}

}
