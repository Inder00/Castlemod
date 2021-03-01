package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import pl.inder00.rihc.castlemod.game.User;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;
import pl.inder00.rihc.castlemod.modes.Util;

public class BlockRegeneration implements Listener {
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
    public void onPlace(BlockPlaceEvent e) {
		if(e.isCancelled()){
			return;
		}
		Player p = e.getPlayer();
		User u = User.get(p.getName());
		if(u != null){
			if(u.getArena() == null){
				return;
			}
			Arena  a = Arena.get(u.getArena().getName());
			if(a.getStatus() == ArenaStatus.PLAYING){
				if(u.getArena() == a && u.getTeam() == 2 && a.atArea(e.getBlock().getLocation(), a)){
					e.setCancelled(true);
					p.sendMessage("§cNie mozesz niszczyc/stawiac blokow na terenie zamku");
					return;
				}
				if(u.getArena() == a && !a.atMap(e.getBlock().getLocation(), a)){
					e.setCancelled(true);
					p.sendMessage("§cNie mozesz niszczyc/stawiac blokow poza arena");
					return;
				}
				return;
			} else {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
    public void onBreak(BlockBreakEvent e) {
		if(e.isCancelled()){
			return;
		}
		Player p = e.getPlayer();
		User u = User.get(p.getName());
		if(u != null){
			if(u.getArena() == null){
				return;
			}
			Arena  a = Arena.get(u.getArena().getName());
			if(a.getStatus() == ArenaStatus.PLAYING){
				if(u.getArena() == a && u.getTeam() == 2 && a.atArea(e.getBlock().getLocation(), a)){
					e.setCancelled(true);
					p.sendMessage("§cNie mozesz niszczyc/stawiac blokow na terenie zamku");
					return;
				}
				if(u.getArena() == a && !a.atMap(e.getBlock().getLocation(), a)){
					e.setCancelled(true);
					p.sendMessage("§cNie mozesz niszczyc/stawiac blokow poza arena");
					return;
				}
				return;
			} else {
				e.setCancelled(true);
				return;
			}
		}
	}

}
