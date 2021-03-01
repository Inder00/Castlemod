package pl.inder00.rihc.castlemod.game.files;

import java.io.File;

import pl.inder00.rihc.castlemod.CastleMod;


public class FileManager {
	
	private static File mainDir = CastleMod.getInst().getDataFolder();
	private static File cfgFile = new File(mainDir, "config.yml");
	private static File users = new File(mainDir, "userdata");
	private static File arenas = new File(mainDir, "arenas");
	
	public static void check(){
		if(!mainDir.exists()) mainDir.mkdir();
		if(!users.exists()) users.mkdir();
		if(!arenas.exists()) arenas.mkdir();
		if(!cfgFile.exists()) CastleMod.getInst().saveDefaultConfig();
	}
	public static File getArenasFolder(){
		return arenas;
	}
	public static File getUsersFolder(){
		return users;
	}
	public static File getConfigFile(){
		return cfgFile;
	}

}
