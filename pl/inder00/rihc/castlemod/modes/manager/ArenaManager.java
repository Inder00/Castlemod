package pl.inder00.rihc.castlemod.modes.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_7_R4.NBTTagList;
import pl.inder00.rihc.castlemod.CastleMod;
import pl.inder00.rihc.castlemod.game.Human;
import pl.inder00.rihc.castlemod.game.User;
import pl.inder00.rihc.castlemod.game.files.Config;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;

public class ArenaManager {
	

	public static void joinGame(Player p, String a){
		User u = User.get(p.getName());
		if(u == null){
			Arena arena = Arena.get(a);
			if(arena != null){
				if(arena.getStatus() == ArenaStatus.RESTARTING || arena.getStatus() == ArenaStatus.PLAYING){
					p.sendMessage("§c[CastleMod] Gra juz sie rozpoczela");
					return;
				}
				((CraftPlayer)p).getHandle().inventory.b(new NBTTagList());
				p.updateInventory();
				new User(p.getName(),arena,0);
				arena.setNumPlayers(arena.getNumPlayers()+1);
				arena.addUsers(p);
				for(Player pall : arena.getUsers()){
					pall.sendMessage("§6[CastleMod] §7Gracz §6"+p.getName()+" §7dolaczyl do areny (§6"+arena.getNumPlayers()+"§7/§624§7)");
				}
				p.sendMessage("§6[CastleMod] §7Mapa §6"+arena.getName()+" §7zostala stworzona przez §6"+arena.getCreator());
				//TODO tp, add item
				ItemStack blue = new ItemStack(Material.WOOL,1,(short)11);
				ItemMeta iblue = blue.getItemMeta();
				iblue.setDisplayName("§1Dolacz do broniacych");
				blue.setItemMeta(iblue);
				
				ItemStack red = new ItemStack(Material.WOOL,1,(short)14);
				ItemMeta ired = red.getItemMeta();
				ired.setDisplayName("§4Dolacz do atakujacych");
				red.setItemMeta(ired);
				
				p.getInventory().addItem(blue);
				p.getInventory().addItem(red);
				p.sendMessage("§6[CastleMod] §7Dolaczyles do areny §6"+arena.getName());
				p.teleport(CastleMod.lobby);
				return;
			} else {
				p.sendMessage("§c[CastleMod] Podana arena nie istnieje!");
				return;
			}
			
		} else {
			p.sendMessage("§c[CastleMod] Aktualnie grasz na arenie, jesli uwazasz, ze to blad wejdz ponownie na serwer");
			return;
		}
	}
	
	public static void leaveGame(Player p){
		User u = User.get(p.getName());
		if(u != null){
			if(u.getArena() == null){
				p.sendMessage("§c[CastleMod] Nie grasz na zadnej arenie");
				return;
			}
			Arena arena = u.getArena();
			if(arena != null){
				arena.setNumPlayers(arena.getNumPlayers()-1);
				arena.remUsers(p);
				
				if(u.getLastDamaged() == null || Bukkit.getServer().getPlayerExact(u.getLastDamaged().getName()) == null){
					for(Player plls : arena.getUsers()){
						plls.sendMessage("§6[CastleMod] §7Gracz §6"+p.getName()+" §7znigal");
					}
				} else {
					int points = Config.getInst().rankingDeath;
					Human c = Human.get(u.getLastDamaged().getName());
					c.setKills(c.getKills()+1);
					c.setPkt(c.getPkt()+points);
					for(Player plls : arena.getUsers()){
						plls.sendMessage("§6[CastleMod] §7Gracz §6"+p.getName()+" §7zostal zabity przez §6"+u.getLastDamaged().getName()+" §7(§6"+points+"§7)");
					}
				}
				
				if(arena.getStatus() == ArenaStatus.WAITING || arena.getStatus() == ArenaStatus.STARTING){
					for(Player pall : arena.getUsers()){
						pall.sendMessage("§6[CastleMod] §7Gracz §6"+p.getName()+" §7opuscil gre (§6"+arena.getNumPlayers()+"§7/§624§7)");
					}	
				} else {
					Arena a = u.getArena();
					if(u.getTeam() == 1){
						a.remTeam1(u);
					} else {
						a.remTeam2(u);
					}
					p.sendMessage("§6[CastleMod] §7Gracz §6"+p.getName()+" §7opuscil gre (§6"+arena.getNumPlayers()+"§7/§624§7)");
				}
				((CraftPlayer)p).getHandle().inventory.b(new NBTTagList());
				p.updateInventory();
				u.delete();
				p.teleport(CastleMod.lobby);
				p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
				p.setHealth(20.0D);
				p.setFoodLevel(20);
				p.setFallDistance(0L);
				p.setFireTicks(0);
				return;
			} else {
				p.sendMessage("§c[CastleMod] Podana arena nie istnieje!");
				return;
			}
			
		} else {
			p.sendMessage("§c[CastleMod] Nie grasz na zadnej arenie");
			return;
		}
	}
	public static void items(Player p, int i){
		//Set
		ItemStack helm = new ItemStack(Material.DIAMOND_HELMET, 1);
		ItemMeta helmIM = helm.getItemMeta();
		helmIM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		helmIM.addEnchant(Enchantment.DURABILITY, 3, true);
		helm.setItemMeta(helmIM);
		
		ItemStack klata = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
		ItemMeta klataIM = klata.getItemMeta();
		klataIM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		klataIM.addEnchant(Enchantment.DURABILITY, 3, true);
		klata.setItemMeta(klataIM);
		
		ItemStack spodnie = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
		ItemMeta spodnieIM = spodnie.getItemMeta();
		spodnieIM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		spodnieIM.addEnchant(Enchantment.DURABILITY, 3, true);
		spodnie.setItemMeta(spodnieIM);
		
		ItemStack buty = new ItemStack(Material.DIAMOND_BOOTS, 1);
		ItemMeta butyIM = buty.getItemMeta();
		butyIM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		butyIM.addEnchant(Enchantment.DURABILITY, 3, true);
		buty.setItemMeta(butyIM);
		
		p.getInventory().setHelmet(helm);
		p.getInventory().setChestplate(klata);
		p.getInventory().setLeggings(spodnie);
		p.getInventory().setBoots(buty);
		
		//Podstawowe inventory
		ItemStack miecz = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta mieczIM = miecz.getItemMeta();
		mieczIM.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		mieczIM.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
		miecz.setItemMeta(mieczIM);
		
		ItemStack kilof = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta kilofIM = kilof.getItemMeta();
		kilofIM.addEnchant(Enchantment.DURABILITY, 3, true);
		kilofIM.addEnchant(Enchantment.DIG_SPEED, 5, true);
		kilof.setItemMeta(kilofIM);
		
		ItemStack luk = new ItemStack(Material.BOW);
		ItemMeta lukIM = luk.getItemMeta();
		lukIM.addEnchant(Enchantment.ARROW_FIRE, 1, true);
		lukIM.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		lukIM.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, true);
		lukIM.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
		luk.setItemMeta(lukIM);
		
		ItemStack koxy = new ItemStack(Material.GOLDEN_APPLE, 2, (short) 1);
		ItemStack refy = new ItemStack(Material.GOLDEN_APPLE, 5);
		ItemStack steki = new ItemStack(Material.COOKED_BEEF, 16);
		
		p.getInventory().addItem(miecz);
		p.getInventory().addItem(luk);
		p.getInventory().addItem(koxy);
		p.getInventory().addItem(refy);
		p.getInventory().addItem(steki);
		p.getInventory().addItem(kilof);
		if(i == 1){
			p.getInventory().addItem(new ItemStack(Material.TNT, 64));	
		}
		p.getInventory().addItem(new ItemStack(Material.COBBLESTONE, 64));
		p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
		p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET, 1));
		p.getInventory().addItem(new ItemStack(Material.ARROW, 1));
		
		//Aktualizacja inv
		p.updateInventory();
	}

}
