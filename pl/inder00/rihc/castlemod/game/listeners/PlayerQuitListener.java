package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import pl.inder00.rihc.castlemod.game.User;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;
import pl.inder00.rihc.castlemod.modes.manager.ArenaManager;
import pl.inder00.rihc.castlemod.modes.manager.PVP;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		User  u = User.get(e.getPlayer().getName());
		if(u != null && u.getArena() != null){
			ArenaManager.leaveGame(e.getPlayer());
		}
		PVP pvp = PVP.get(e.getPlayer().getName());
		if(pvp != null){
			pvp.delete();
		}
	}

}
