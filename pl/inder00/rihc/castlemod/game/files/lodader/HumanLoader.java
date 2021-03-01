package pl.inder00.rihc.castlemod.game.files.lodader;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import pl.inder00.rihc.castlemod.game.Human;
import pl.inder00.rihc.castlemod.game.files.FileManager;
import pl.inder00.rihc.castlemod.modes.Arena;


public class HumanLoader {
	
	public static void loadPlayers(){
		for(File f : FileManager.getUsersFolder().listFiles()){
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			String name = yml.getString("player.name");
			int kills = yml.getInt("player.kills");
			int deaths = yml.getInt("player.deaths");
			int coins = yml.getInt("player.coins");
			int wins = yml.getInt("player.wins");
			int lose = yml.getInt("player.lose");
			int pkt = yml.getInt("player.pkt");
			
			new Human(name,coins,kills,deaths,wins,lose,pkt);
		}
	}
	
	public static void savePlayers(){
		for(Human a : Human.users){
				File f = new File(FileManager.getUsersFolder(), a.getName() + ".yml");
				if(!f.exists()){
					try {
						f.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
				yml.set("player.name", a.getName());
				yml.set("player.coins", a.getCoins());
				yml.set("player.kills", a.getKills());
				yml.set("player.deaths", a.getDeaths());
				yml.set("player.wins", a.getWins());
				yml.set("player.lose", a.getLose());
				yml.set("player.pkt", a.getPkt());
				try {
					yml.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}

}
