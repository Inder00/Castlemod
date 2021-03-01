package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import pl.inder00.rihc.castlemod.game.User;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;
import pl.inder00.rihc.castlemod.modes.manager.PVP;

public class PlayerMoveListener implements Listener {
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onMove(PlayerMoveEvent e){
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
				Location from = e.getFrom();
				Location to = e.getTo();
				if(!a.atMap(to, a)){
					p.teleport(from);
					e.setCancelled(true);
					return;
				}
				if(a.inAttackArea(to, a)){
					if(!a.isAttackingCastle() && PVP.get(p.getName()).getAntylogout() == 0){
						a.setAttackCastle(0);
						a.setAttackerCastle(p);
						a.setAttackingCastle(true);
					}
				}
			}
		}
	}

}
