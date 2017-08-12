package pl.inder00.rihc.castlemod;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import me.confuser.barapi.BarAPI;
import pl.inder00.rihc.castlemod.game.Human;
import pl.inder00.rihc.castlemod.game.User;
import pl.inder00.rihc.castlemod.game.commands.CastleModCommand;
import pl.inder00.rihc.castlemod.game.files.Config;
import pl.inder00.rihc.castlemod.game.files.FileManager;
import pl.inder00.rihc.castlemod.game.files.lodader.ArenaLoader;
import pl.inder00.rihc.castlemod.game.files.lodader.HumanLoader;
import pl.inder00.rihc.castlemod.game.listeners.BlockPlaceListener;
import pl.inder00.rihc.castlemod.game.listeners.EntityDamageByEntityListener;
import pl.inder00.rihc.castlemod.game.listeners.InventoryClickListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerCommandPreprocessListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerDeathListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerDropItemListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerInteractListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerJoinListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerMoveListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerQuitListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerRespawnListener;
import pl.inder00.rihc.castlemod.game.listeners.PlayerTeleportListener;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;
import pl.inder00.rihc.castlemod.modes.manager.PVP;
import pl.inder00.rihc.castlemod.modes.manager.ScoreBoardManager;

public class CastleMod extends JavaPlugin {

	//Instance
	private static CastleMod inst;
	public static WorldEditPlugin wep;
	public static WorldEdit we;
	public static CastleMod getInst(){
		if(inst == null) return new CastleMod();
		return inst;
	}
	public CastleMod(){
		inst = this;
	}
	//===============
	//LOBBY
	public static Location lobby;
	//===============
	
	@Override
	public void onDisable() {
		ArenaLoader.saveArenas();
		HumanLoader.savePlayers();
		for(Arena a : Arena.arenas){
			if(a.getStatus() == ArenaStatus.PLAYING){
				a.stop();	
			}	
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
        if (plugin == null || !(plugin instanceof WorldEditPlugin)){
        	Bukkit.getConsoleSender().sendMessage("§4[CastleMod] Zainstaluj plugin WorldEdit, aby castlemod dzialal w pelni!");
        	Bukkit.getPluginManager().disablePlugin(this);
        	return;	
        }
        wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        we = wep.getWorldEdit();
		
		FileManager.check();
		new CastleModCommand(this);
		Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
		
		Config.getInst().load();
		Config cfg = Config.getInst();
		
		ArenaLoader.loadArenas();
		HumanLoader.loadPlayers();
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(Human.get(p.getName()) == null){
				new Human(p.getName(), 0, 0, 0, 0, 0, 1000, false, false, false, false, false, false, false);
			}
			if(PVP.get(p.getPlayer().getName()) == null){
				new PVP(p.getPlayer().getName());
			}
		}
		
		lobby = new Location(Bukkit.getWorld(cfg.lobbyWorld), cfg.lobbyX, cfg.lobbyY, cfg.lobbyZ);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this , new Runnable(){
			@Override
			public void run() {
				for(PVP p : PVP.users){
					User u = User.get(p.getPlayer());
					if(p.getAntylogout() > 0){
						p.setAntylogout(p.getAntylogout()-1);
					} else if(u != null && u.getLastDamaged() != null && p.getAntylogout() == 0) {
						u.setLastDamaged(null);
					}
				}
				for(Arena a : Arena.arenas){
					a.checkGame();
					if(a.getStatus() == ArenaStatus.PLAYING || a.getStatus() == ArenaStatus.WAITING) {
						ScoreBoardManager.checkPlayers(a);
					}
					if(a.getStatus() == ArenaStatus.PLAYING){
						if(a.getTntExplode() > 0) {
							a.setTntExplode(a.getTntExplode()-1);
						}
						if(a.getTimeGame() == 900) {
							for(Player p : a.getUsers()) {
								p.sendMessage("§6[CastleMod] §7Przedmioty stale zostana dodane za §62:00");
							}
						}
						if(a.getTimeGame() == 840) {
							for(Player p : a.getUsers()) {
								p.sendMessage("§6[CastleMod] §7Przedmioty stale zostana dodane za §61:00");
							}
						}
						if(a.getTimeGame() == 810) {
							for(Player p : a.getUsers()) {
								p.sendMessage("§6[CastleMod] §7Przedmioty stale zostana dodane za §60:30");
							}
						}
						if(a.getTimeGame() == 780) {
							for(Player p : a.getUsers()) {
								p.sendMessage("§6[CastleMod] §7Przedmioty stale zostaly §6dodane§7!");
								Human e = Human.get(p.getName());
								if(e != null) {
									if(e.isPerla()) p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL,1));
									if(e.isKoks()) p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE,1,(short)1));
									if(e.isReffil()) p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE,1));
									if(e.isSila3min()) p.getInventory().addItem(new ItemStack(Material.POTION,1,(short)8201));
									if(e.isOgien6min()) p.getInventory().addItem(new ItemStack(Material.POTION,1,(short)16451));
									if(e.isSpeed1_2min15sec()) p.getInventory().addItem(new ItemStack(Material.POTION,1,(short)16386));
									if(e.isInstantheal()) p.getInventory().addItem(new ItemStack(Material.POTION,1,(short)16453));
								}
							}
						}
						a.setTimeGame(a.getTimeGame()-1);
						if(a.getTimeGame() == 600) {
							for(Player p : a.getUsers()) {
								p.sendMessage("§6[CastleMod] §7Do konca gry pozostalo §610:00");
							}
						}
						if(a.getTimeGame() == 300) {
							for(Player p : a.getUsers()) {
								p.sendMessage("§6[CastleMod] §7Do konca gry pozostalo §65:00");
							}
						}
						if(a.getTimeGame() == 60) {
							for(Player p : a.getUsers()) {
								p.sendMessage("§6[CastleMod] §7Do konca gry pozostalo §61:00");
							}
						}
						if(a.isAttackingCastle()){
							if(Bukkit.getServer().getPlayerExact(a.getAttackerCastle().getName()) == null){
								a.setAttackingCastle(false);
							} else {
								PVP p = PVP.get(a.getAttackerCastle().getName());
								if(p != null) {
									if(p.getAntylogout() != 0){
										a.setAttackingCastle(false);
										a.getAttackerCastle().sendMessage("§cPrzejmowanie anulowane, zostales/as zaatakowany/a!");
										for(Player player : a.getUsers()){
											if(BarAPI.hasBar(player)){
												BarAPI.removeBar(player);
												BarAPI.setMessage(player, "§8-------- §7Przejmowanie anulowane §8--------", 3);
											}
										}
									} else {
										Location to = a.getAttackerCastle().getLocation();
										if(a.inAttackArea(to, a)){
											a.setAttackCastle(a.getAttackCastle()+1);
											for(Player player : a.getUsers()){
												BarAPI.setMessage(player, "§8-------- §7Przejmowanie §6"+a.getAttackCastle()+" §8--------", 100F);
											}
											if(a.getAttackCastle() == 75 && a.isCastleCuboid()) {
												for(Player player : a.getUsers()) {
													player.sendMessage("§c[CastleMod] Cuboid zamku zostal usuniety!");
													player.sendMessage("§c[CastleMod] Cuboid zamku zostal usuniety!");
													player.sendMessage("§c[CastleMod] Cuboid zamku zostal usuniety!");
												}
												a.setCastleCuboid(false);
											}
											if(a.getAttackCastle() == 100){
												for(User t2 : a.getTeam2()){
													Human.get(t2.getName()).setCoins(+cfg.coinsWin);
												}
												Bukkit.broadcastMessage("§6[CastleMod] §7Druzyna §6atakujacych §7wygrala na arenie §6"+a.getName());
												
												a.restore();
											}
										} else {
											a.setAttackingCastle(false);
											a.getAttackerCastle().sendMessage("§cPrzejmowanie anulowane, ruszyles/as sie!");
											for(Player player : a.getUsers()){
												if(BarAPI.hasBar(player)){
													BarAPI.removeBar(player);
													BarAPI.setMessage(player, "§8-------- §7Przejmowanie anulowane §8--------", 3);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}, 0L, 20L);
		
	}
	
	

}
