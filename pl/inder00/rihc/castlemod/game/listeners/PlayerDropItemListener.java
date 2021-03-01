package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import pl.inder00.rihc.castlemod.game.User;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;

public class PlayerDropItemListener implements Listener {
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerDropItemEvent(PlayerDropItemEvent e){
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
			if(u.getArena() == a){
				if(a.getStatus() == ArenaStatus.WAITING || a.getStatus() == ArenaStatus.STARTING){
					e.setCancelled(true);
					return;	
				}
			}
		}
	}
}
