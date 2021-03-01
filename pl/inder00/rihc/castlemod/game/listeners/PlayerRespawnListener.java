package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import pl.inder00.rihc.castlemod.CastleMod;

public class PlayerRespawnListener implements Listener {
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onRespawn(PlayerRespawnEvent e){
		if(e.isBedSpawn()){
			return;
		}
		e.setRespawnLocation(CastleMod.lobby);
	}

}
