package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import pl.inder00.rihc.castlemod.game.User;
import pl.inder00.rihc.castlemod.modes.Arena;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;

public class PlayerInteractListener implements Listener {
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent e) {
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
			/*if(a.getStatus() == ArenaStatus.WAITING || a.getStatus() == ArenaStatus.STARTING){
				if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK) ||e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					if(p.getItemInHand().getType().equals(Material.WOOL) && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§1Dolacz do broniacych")) {
						if(u.getTeam() != 0){
							if(u.getTeam() == 1){
								return;
							} 
							if(u.getTeam() == 2){
								a.remTeam2(u);
							}
						}
						p.sendMessage("§6[CastleMod] §7Dolaczyles/as do druzyny §6broniacych§7!");
						a.addTeam1(u);
						u.setTeam(1);
						e.setCancelled(true);
						return;
					} else if(p.getItemInHand().getType().equals(Material.WOOL) && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§4Dolacz do atakujacych")) {
						if(u.getTeam() != 0){
							if(u.getTeam() == 2){
								return;
							}
							if(u.getTeam() == 1){
								a.remTeam1(u);
							} 
						}
						p.sendMessage("§6[CastleMod] §7Dolaczyles/as do druzyny §6atakujacych§7!");
						a.addTeam2(u);
						u.setTeam(2);
						e.setCancelled(true);
						return;
					}
				}
			}*/
		}
	}

}
