package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import pl.inder00.rihc.castlemod.game.User;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;

public class PlayerTeleportListener implements Listener {
	
    @EventHandler(priority=EventPriority.MONITOR)
    public void noEnderPearl(PlayerTeleportEvent e){
        if (e.getCause().equals(TeleportCause.ENDER_PEARL)){
            Player p = e.getPlayer();
            Location to = e.getTo();
            User a = User.get(p.getName());
            if(a != null && a.getArena().getStatus() == ArenaStatus.PLAYING){
            	if(!a.getArena().atMap(to, a.getArena())){
            		p.sendMessage("§cNie mozesz wyrzucac perly za arene!");
            		e.setTo(e.getFrom());
            		e.setCancelled(true);
            		return;
            	}
            }
        }
    }

}
