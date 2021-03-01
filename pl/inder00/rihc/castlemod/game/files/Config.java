package pl.inder00.rihc.castlemod.game.files;

import org.bukkit.configuration.file.FileConfiguration;

import pl.inder00.rihc.castlemod.CastleMod;

public class Config {
	
	//=========================================================================
	//Variables
	private static Config inst;
	private FileConfiguration cfg = CastleMod.getInst().getConfig();
	
	//Plugin
	public int coinsKill;
	public int coinsWin;
	
	public int startGame;
	
	public int lobbyX;
	public int lobbyY;
	public int lobbyZ;
	public String lobbyWorld;
	public int rankingDeath;
	//=========================================================================
	
	//=========================================================================
	//Reload
	public void reload(){
		CastleMod.getInst().reloadConfig();
		this.cfg = CastleMod.getInst().getConfig();
		load();
	}
	//=========================================================================
	
	//=========================================================================
	//Load
	public void load(){
		this.coinsKill = cfg.getInt("config.coins.kill");
		this.coinsWin = cfg.getInt("config.coins.win");
		this.startGame = cfg.getInt("config.game.start");
		
		this.lobbyX = cfg.getInt("config.lobby.x");
		this.lobbyY = cfg.getInt("config.lobby.y");
		this.lobbyZ = cfg.getInt("config.lobby.z");
		this.lobbyWorld = cfg.getString("config.lobby.world");
		
		this.rankingDeath = cfg.getInt("config.ranking.death");
	}
	//=========================================================================
	
	//=========================================================================
	//Instance
	public static Config getInst(){
		if(inst == null) return new Config();
		return inst;
	}
	public Config(){
		inst = this;
	}
	//=========================================================================

}
