package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import pl.inder00.rihc.castlemod.game.User;

public class PlayerCommandPreprocessListener implements Listener {
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onCommand(PlayerCommandPreprocessEvent e){
		if(e.isCancelled()){
			return;
		}
		User u = User.get(e.getPlayer().getName());
		if(u == null){
			return;
		}
		if(u.getArena() == null){
			return;
		}
		if(!e.getMessage().toLowerCase().contains("/castlemod") && !e.getMessage().toLowerCase().contains("/cmod") && !e.getMessage().toLowerCase().contains("/cm") && !e.getPlayer().isOp()){
			e.getPlayer().sendMessage("§cPodana komenda jest zablokowana na castlemod! Zezwolone komendy: /castlemod");
			e.setCancelled(true);
		}
	}

}
