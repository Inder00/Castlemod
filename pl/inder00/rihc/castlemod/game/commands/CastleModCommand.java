package pl.inder00.rihc.castlemod.game.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import pl.inder00.rihc.castlemod.CastleMod;
import pl.inder00.rihc.castlemod.game.User;
import pl.inder00.rihc.castlemod.game.files.Config;
import pl.inder00.rihc.castlemod.game.files.FileManager;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;
import pl.inder00.rihc.castlemod.modes.manager.ArenaManager;

public class CastleModCommand implements CommandExecutor {

	public CastleModCommand(CastleMod cm){
		cm.getCommand("castlemod").setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if(!(sender instanceof Player)){
			return false;
		}
		Player p = (Player) sender;
		if(args.length == 0){
			p.sendMessage("§8============ §6CastleMod §8============");
			p.sendMessage("§6/Castlemod dolacz <arena> §8- §7Dolacza do wybranej areny");
			p.sendMessage("§6/Castlemod opusc §8- §7Opuszczanie areny");
			if(p.hasPermission("cm.admin")){
				p.sendMessage("§6/Castlemod stworz <arena> §8- §7Tworzy arene");
				p.sendMessage("§6/Castlemod usun <arena> §8- §7Usuwa arene");
				p.sendMessage("§6/Castlemod start <arena> §8- §7Wymusza start gry");
				p.sendMessage("§6/Castlemod setspawn <arena> <1/2> §8- §7Ustawia spawn");
				p.sendMessage("§6/Castlemod setcastle <arena> <1/2> §8- §7Ustawia zamek po przekatnej (nie zwraca uwagi na wysokosc)");
				p.sendMessage("§6/Castlemod setattackarea <arena> <1/2> §8- §7Ustawia strefe po przejmowania zamku (nie zwraca uwagi na wysokosc)");
				p.sendMessage("§6/Castlemod setmap <arena> <1/2> §8- §7Ustawia przekatne mapy");
				p.sendMessage("§6/Castlemod setlobby §8- §7Ustawia lobby dla wszystkich aren");
				p.sendMessage("§6/Castlemod setcreator <arena> <nick> §8- §7Ustawia autora mapy");
				p.sendMessage("§6/Castlemod stop <arena> §8- §7Stopuje dana arene");
				p.sendMessage("§6/Castlemod setlobby §8- §7Ustawia lobby dla wszystkich gier");
			}
			p.sendMessage("§8============ §6CastleMod §8============");
			return false;
		} else if(args.length > 0){
			if(args[0].equalsIgnoreCase("dolacz")){
				if(args.length <= 1){
					p.sendMessage("§cPodaj nazwe areny, do ktorej chcesz dolaczyc");
					return false;
				}
				String name = args[1].toString();
				Arena a = Arena.get(name);
				if(a == null){
					p.sendMessage("§cPodana arena nie istnieje");
					return false;
				}
				if(
						a.getX1() == 0 
						|| a.getX2() == 0 
						|| a.getZ2() == 0 
						|| a.getZ1() == 0 
						|| a.getAttack_1_x() == 0
						|| a.getAttack_1_y() == 0
						|| a.getAttack_1_z() == 0
						|| a.getAttack_2_x() == 0
						|| a.getAttack_2_y() == 0
						|| a.getAttack_2_z() == 0
						|| a.getMap_1_x() == 0
						|| a.getMap_1_y() == 0
						|| a.getMap_1_z() == 0
						|| a.getMap_2_x() == 0
						|| a.getMap_2_y() == 0
						|| a.getMap_2_z() == 0
				){
					p.sendMessage("§cArena nie zostala skonfigurowana, jesli uwazasz ze to blad, zglos go administratorowi!");
					return false;
				}
				if(a.getStatus() == ArenaStatus.STARTING || a.getStatus() == ArenaStatus.WAITING){
					if(a.getNumPlayers() >= 20){
						if(p.hasPermission("cm.vip")){
							ArenaManager.joinGame(p, name);
							return false;
						} else {
							p.sendMessage("&cVIP: Slot jest zarezerowany dla rangi VIP!");
							return false;
						}
					} else {
						ArenaManager.joinGame(p, name);
						return false;
					}
				} else {
					p.sendMessage("§c[CastleMod] Arena jest restartowana lub trwa na niej juz rozgrywka");
					return false;
				}
			} else if(args[0].equalsIgnoreCase("opusc")){
				User u = User.get(p.getName());
				if(u == null){
					p.sendMessage("§c[CastleMod] Nie znajdujesz sie na zadnej arenie!");
					return false;
				} else {
					ArenaManager.leaveGame(p);
					return false;
				}
			} else if(args[0].equalsIgnoreCase("stworz")){
				if(!p.hasPermission("castlemod.admin")){
					p.sendMessage("§c[CastleMod] Nie masz uprawnien do uzycia tej komendy");
					return false;
				}
				if(args.length <= 1){
					p.sendMessage("§cPodaj nazwe areny");
					return false;
				}
				Arena a = Arena.get(args[1]);
				if(a == null){
					new Arena(args[1], "brak", p.getLocation().getWorld().getName(), 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, null,0,0,0,null,0,0,0,null,0,0,0,0,0,0);
					p.sendMessage("§6[CastleMod] §7Stworzyles pomylsnie arene §6"+args[1]);
					return false;
				} else {
					p.sendMessage("§cPodana arena istnieje!");
					return false;
				}
		} else if(args[0].equalsIgnoreCase("usun")){
			if(!p.hasPermission("castlemod.admin")){
				p.sendMessage("§c[CastleMod] Nie masz uprawnien do uzycia tej komendy");
				return false;
			}
			if(args.length <= 1){
				p.sendMessage("§cPodaj nazwe areny");
				return false;
			}
			String name = args[1].toString();
			Arena a = Arena.get(name);
			if(a == null){
				p.sendMessage("§cPodana arena nie istnieje!");
				return false;
			}
			File f = new File(FileManager.getArenasFolder(), a.getName() + ".yml");
			f.delete();
			a.stop();
			a.delete();
			p.sendMessage("§6[CastleMod] §7Stworzyles usunieto arene §6"+a.getName());
			return false;
		} else if(args[0].equalsIgnoreCase("setspawn")){
			if(!p.hasPermission("castlemod.admin")){
				p.sendMessage("§c[CastleMod] Nie masz uprawnien do uzycia tej komendy");
				return false;
			}
			if(args.length <= 1){
				p.sendMessage("§cPodaj nazwe areny");
				return false;
			}
			if(args.length < 3){
				p.sendMessage("§cPodaj spawn (1/2)");
				return false;
			}
			String name = args[1].toString();
			Arena a = Arena.get(name);
			if(a == null){
				p.sendMessage("§cPodana arena nie istnieje!");
				return false;
			}
			if(args[2].equalsIgnoreCase("1") || args[2].equalsIgnoreCase("2")){
				Location loc = p.getLocation();
				if(args[2].equalsIgnoreCase("1")){
					a.setSpawn_1_x(loc.getBlockX());
					a.setSpawn_1_y(loc.getBlockY());
					a.setSpawn_1_z(loc.getBlockZ());
					a.setSpawn_1_world(loc.getWorld().getName());
					p.sendMessage("§6[CastleMod] §7Ustawiono §61 spawn §7na koordynatach §6x: "+loc.getBlockX()+" y: "+loc.getBlockY()+" z: "+loc.getBlockZ());
					return false;
				} else {
					a.setSpawn_2_x(loc.getBlockX());
					a.setSpawn_2_y(loc.getBlockY());
					a.setSpawn_2_z(loc.getBlockZ());
					a.setSpawn_2_world(loc.getWorld().getName());
					p.sendMessage("§6[CastleMod] §7Ustawiono §62 spawn §7na koordynatach §6x: "+loc.getBlockX()+" y: "+loc.getBlockY()+" z: "+loc.getBlockZ());
					return false;
				}
			} else {
				p.sendMessage("§cNiepoprawny nr spawnu. §cPodaj spawn (1/2)");
				return false;
			}
			
		} else if(args[0].equalsIgnoreCase("setcastle")){
			if(!p.hasPermission("castlemod.admin")){
				p.sendMessage("§c[CastleMod] Nie masz uprawnien do uzycia tej komendy");
				return false;
			}
			if(args.length <= 1){
				p.sendMessage("§cPodaj nazwe areny");
				return false;
			}
			if(args.length < 3){
				p.sendMessage("§cPodaj wierzcholek (1/2)");
				return false;
			}
			String name = args[1].toString();
			Arena a = Arena.get(name);
			if(a == null){
				p.sendMessage("§cPodana arena nie istnieje!");
				return false;
			}
			if(args[2].equalsIgnoreCase("1") || args[2].equalsIgnoreCase("2")){
				Location loc = p.getLocation();
				if(args[2].equalsIgnoreCase("1")){
					a.setX1(loc.getBlockX());
					a.setZ1(loc.getBlockZ());
					p.sendMessage("§6[CastleMod] §7Ustawiono §61 wierzcholek §7na koordynatach §6x: "+loc.getBlockX()+" z: "+loc.getBlockZ());
					return false;
				} else {
					a.setX2(loc.getBlockX());
					a.setZ2(loc.getBlockZ());
					p.sendMessage("§6[CastleMod] §7Ustawiono §62 wierzcholek §7na koordynatach §6x: "+loc.getBlockX()+" z: "+loc.getBlockZ());
					return false;
				}
			} else {
				p.sendMessage("§cNiepoprawny wierzcholek. §cPodaj wierzcholek (1/2)");
				return false;
			}
			
		} else if(args[0].equalsIgnoreCase("setattackarea")){
			if(!p.hasPermission("castlemod.admin")){
				p.sendMessage("§c[CastleMod] Nie masz uprawnien do uzycia tej komendy");
				return false;
			}
			if(args.length <= 1){
				p.sendMessage("§cPodaj nazwe areny");
				return false;
			}
			if(args.length < 3){
				p.sendMessage("§cPodaj wierzcholek (1/2)");
				return false;
			}
			String name = args[1].toString();
			Arena a = Arena.get(name);
			if(a == null){
				p.sendMessage("§cPodana arena nie istnieje!");
				return false;
			}
			if(args[2].equalsIgnoreCase("1") || args[2].equalsIgnoreCase("2")){
				Location loc = p.getLocation();
				if(args[2].equalsIgnoreCase("1")){
					a.setAttack_1_x(loc.getBlockX());
					a.setAttack_1_z(loc.getBlockZ());
					p.sendMessage("§6[CastleMod] §7Ustawiono §61 wierzcholek §7na koordynatach §6x: "+loc.getBlockX()+" z: "+loc.getBlockZ());
					return false;
				} else {
					a.setAttack_2_x(loc.getBlockX());
					a.setAttack_2_z(loc.getBlockZ());
					p.sendMessage("§6[CastleMod] §7Ustawiono §62 wierzcholek §7na koordynatach §6x: "+loc.getBlockX()+" z: "+loc.getBlockZ());
					return false;
				}
			} else {
				p.sendMessage("§cNiepoprawny wierzcholek. §cPodaj wierzcholek (1/2)");
				return false;
			}
			
		} else if(args[0].equalsIgnoreCase("stop")){
			if(!p.hasPermission("castlemod.admin")){
				p.sendMessage("§c[CastleMod] Nie masz uprawnien do uzycia tej komendy");
				return false;
			}
			if(args.length <= 1){
				p.sendMessage("§cPodaj nazwe areny");
				return false;
			}
			String name = args[1].toString();
			Arena a = Arena.get(name);
			if(a == null){
				p.sendMessage("§cPodana arena nie istnieje!");
				return false;
			}
			if(a.getStatus() != ArenaStatus.PLAYING){
				p.sendMessage("§c[CastleMod] Gra na podanej arenie sie nie rozpoczela");
				return false;
			}
			a.stop();
			p.sendMessage("§6[CastleMod] §7Pomylsnie zatrzymano arene §6"+a.getName());
			return false;
			
			
		} else if(args[0].equalsIgnoreCase("setcreator")){
			if(!p.hasPermission("castlemod.admin")){
				p.sendMessage("§c[CastleMod] Nie masz uprawnien do uzycia tej komendy");
				return false;
			}
			if(args.length <= 1){
				p.sendMessage("§cPodaj nazwe areny");
				return false;
			}
			if(args.length < 3){
				p.sendMessage("§cPodaj nick gracza, ktory stworzyl ta mape");
				return false;
			}
			String name = args[1].toString();
			Arena a = Arena.get(name);
			if(a == null){
				p.sendMessage("§cPodana arena nie istnieje!");
				return false;
			}
			a.setCreator(args[2]);
			p.sendMessage("§6[CastleMod] §7Pomylsnie zmieniono nick autora mapy na §6"+args[2]);
			return false;
			
		} else if(args[0].equalsIgnoreCase("start")){
			if(!p.hasPermission("castlemod.admin")){
				p.sendMessage("§c[CastleMod] Nie masz uprawnien do uzycia tej komendy");
				return false;
			}
			if(args.length <= 1){
				p.sendMessage("§cPodaj nazwe areny");
				return false;
			}
			String name = args[1].toString();
			Arena a = Arena.get(name);
			if(a == null){
				p.sendMessage("§cPodana arena nie istnieje!");
				return false;
			}
			if(a.getStatus() != ArenaStatus.WAITING){
				p.sendMessage("§c[CastleMod] Nie mozna wystartowac areny. Mozliwe, ze trwa juz na niej gra, staruje lub sie restartuje");
				return false;
			}
			if(a.getNumPlayers() < Config.getInst().startGame){
				p.sendMessage("§c[CastleMod] Nie ma wystarczajacej ilosci osob do wystartowania mapy");
				return false;
			}
			a.start();
			p.sendMessage("§6[CastleMod] §7Pomylsnie wystartowano arene §6"+a.getName());
			return false;
			
		} else if(args[0].equalsIgnoreCase("setlobby")){
			if(!p.hasPermission("castlemod.admin")){
				p.sendMessage("§c[CastleMod] Nie masz uprawnien do uzycia tej komendy");
				return false;
			}
			Location loc = p.getLocation();
			File f = FileManager.getConfigFile();
			if(!f.exists()){
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
			yml.set("config.lobby.x", loc.getX());
			yml.set("config.lobby.y", loc.getY());
			yml.set("config.lobby.z", loc.getZ());
			yml.set("config.lobby.world", loc.getWorld().getName());
			try {
				yml.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Config.getInst().reload();
			CastleMod.lobby = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
			p.sendMessage("§6[CastleMod] §7Ustawiono §6lobby §7na koordynatach §6x: "+loc.getBlockX()+" y: "+loc.getBlockY()+" z: "+loc.getBlockZ());
			return false;
		} else if(args[0].equalsIgnoreCase("setmap")){
			if(!p.hasPermission("castlemod.admin")){
				p.sendMessage("§c[CastleMod] Nie masz uprawnien do uzycia tej komendy");
				return false;
			}
			if(args.length <= 1){
				p.sendMessage("§cPodaj nazwe areny");
				return false;
			}
			if(args.length < 3){
				p.sendMessage("§cPodaj wierzcholek (1/2)");
				return false;
			}
			String name = args[1].toString();
			Arena a = Arena.get(name);
			if(a == null){
				p.sendMessage("§cPodana arena nie istnieje!");
				return false;
			}
			if(args[2].equalsIgnoreCase("1") || args[2].equalsIgnoreCase("2")){
				Location loc = p.getLocation();
				if(args[2].equalsIgnoreCase("1")){
					a.setMap_1_x(loc.getBlockX());
					a.setMap_1_y(loc.getBlockY());
					a.setMap_1_z(loc.getBlockZ());
					a.setMap_1_world(loc.getWorld().getName());
					p.sendMessage("§6[CastleMod] §7Ustawiono §61 wierzcholek areny §7na koordynatach §6x: "+loc.getBlockX()+" y: "+loc.getBlockY()+" z: "+loc.getBlockZ());
					return false;
				} else {
					a.setMap_2_x(loc.getBlockX());
					a.setMap_2_y(loc.getBlockY());
					a.setMap_2_z(loc.getBlockZ());
					a.setMap_2_world(loc.getWorld().getName());
					p.sendMessage("§6[CastleMod] §7Ustawiono §62 wierzcholek areny §7na koordynatach §6x: "+loc.getBlockX()+" y: "+loc.getBlockY()+" z: "+loc.getBlockZ());
					return false;
				}
			} else {
				p.sendMessage("§cNiepoprawny wierzcholek. §cPodaj wierzcholek (1/2)");
				return false;
			}
			
		} else {
			p.sendMessage("§8============ §6CastleMod §8============");
			p.sendMessage("§6/Castlemod dolacz <arena> §8- §7Dolacza do wybranej areny");
			p.sendMessage("§6/Castlemod opusc §8- §7Opuszczanie areny");
			if(p.hasPermission("cm.admin")){
				p.sendMessage("§6/Castlemod stworz <arena> §8- §7Tworzy arene");
				p.sendMessage("§6/Castlemod usun <arena> §8- §7Usuwa arene");
				p.sendMessage("§6/Castlemod start <arena> §8- §7Wymusza start gry");
				p.sendMessage("§6/Castlemod setspawn <arena> <1/2> §8- §7Ustawia spawn");
				p.sendMessage("§6/Castlemod setcastle <arena> <1/2> §8- §7Ustawia zamek po przekatnej (nie zwraca uwagi na wysokosc)");
				p.sendMessage("§6/Castlemod setmap <arena> <1/2> §8- §7Ustawia przekatne mapy");
				p.sendMessage("§6/Castlemod setlobby §8- §7Ustawia lobby dla wszystkich aren");
				p.sendMessage("§6/Castlemod setcreator <arena> <nick> §8- §7Ustawia autora mapy");
				p.sendMessage("§6/Castlemod stop <arena> §8- §7Stopuje dana arene");
				p.sendMessage("§6/Castlemod setlobby §8- §7Ustawia lobby dla wszystkich gier");
			}
			p.sendMessage("§8============ §6CastleMod §8============");
			return false;
		}
		}
		return false;
	}

}
