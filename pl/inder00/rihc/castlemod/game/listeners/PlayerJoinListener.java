package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import pl.inder00.rihc.castlemod.game.Human;
import pl.inder00.rihc.castlemod.modes.manager.PVP;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(Human.get(p.getName()) == null){
			
			new Human(p.getName(), 0, 0, 0, 0, 0, 1000);
			
		}
		new PVP(p.getName());
	}

}
