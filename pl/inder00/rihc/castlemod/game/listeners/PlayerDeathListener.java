package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EnumClientCommand;
import net.minecraft.server.v1_7_R4.NBTTagList;
import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand;
import pl.inder00.rihc.castlemod.CastleMod;
import pl.inder00.rihc.castlemod.game.Human;
import pl.inder00.rihc.castlemod.game.User;
import pl.inder00.rihc.castlemod.game.files.Config;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;
import pl.inder00.rihc.castlemod.modes.manager.PVP;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		User u = User.get(p.getName());
		if(u != null){
			Arena a = u.getArena();
			if(a != null && a.getStatus() == ArenaStatus.PLAYING){
				Config cfg = Config.getInst();
				Human b = Human.get(p.getName());
				b.setDeaths(b.getDeaths()+1);
				b.setPkt(b.getPkt()-cfg.rankingDeath);
				e.setDeathMessage(null);
				
				if(u.getLastDamaged() == null || Bukkit.getServer().getPlayerExact(u.getLastDamaged().getName()) == null){
					for(Player plls : a.getUsers()){
						plls.sendMessage("§6[CastleMod] §7Gracz §6"+p.getName()+" §7znigal");
					}
				} else {
					Human c = Human.get(u.getLastDamaged().getName());
					c.setKills(c.getKills()+1);
					c.setCoins(c.getCoins()+cfg.coinsKill);
					c.setPkt(c.getPkt()+cfg.rankingDeath);
					for(Player plls : a.getUsers()){
						plls.sendMessage("§6[CastleMod] §7Gracz §6"+p.getName()+" §7zostal zabity przez §6"+u.getLastDamaged().getName()+" §7(§6+"+cfg.rankingDeath+"§7)");
					}
				}
				a.remUsers(p);
				if(u.getTeam() == 1){
					a.remTeam1(u);
				} else {
					a.remTeam2(u);
				}
				((CraftPlayer)p).getHandle().inventory.b(new NBTTagList());
				p.updateInventory();
				u.delete();
				p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
				p.setHealth(20.0D);
				p.setFoodLevel(20);
				p.setFallDistance(0L);
				p.setFireTicks(0);
				
				Location death = p.getLocation();
				World w = p.getWorld();
				for(ItemStack is : e.getDrops()){
					w.dropItem(death, is);
				}
				e.getDrops().clear();
				
				PacketPlayInClientCommand in = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
				EntityPlayer cPlayer = ((CraftPlayer)p).getHandle();
				cPlayer.playerConnection.a(in);
				
				p.teleport(CastleMod.lobby);
				return;
			}
		}
	}

}
