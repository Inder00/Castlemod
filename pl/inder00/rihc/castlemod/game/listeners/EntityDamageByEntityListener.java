package pl.inder00.rihc.castlemod.game.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import pl.inder00.rihc.castlemod.game.User;
import pl.inder00.rihc.castlemod.modes.ArenaStatus;
import pl.inder00.rihc.castlemod.modes.manager.PVP;

import org.bukkit.Location;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class EntityDamageByEntityListener implements Listener {
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onDamage(EntityDamageByEntityEvent e) {
		final Entity attacker = e.getDamager();
		final Entity victim = e.getEntity();
		if (attacker instanceof Player) {
			if (victim instanceof Player) {
				
				User a = User.get(((Player) attacker).getName());
				User b = User.get(((Player) victim).getName());
				if(a != null & b != null){
					if(a.getArena().getName().equalsIgnoreCase(b.getArena().getName())){
						
						if(a.getTeam() == b.getTeam()){
							e.setCancelled(true);
							return;
						} else {
							b.setLastDamaged(((Player) attacker));
							return;
						}
						
					}
				}
				
			}
		}
	}
    @EventHandler(priority=EventPriority.MONITOR)
    public void onProjectileHit(EntityDamageByEntityEvent e) {
    	Projectile projectile = (Projectile) e.getDamager();
        Player attacker = (Player) projectile.getShooter();
        Player victim = (Player) e.getEntity();
        if(attacker != victim){
			User a = User.get(((Player) attacker).getName());
			User b = User.get(((Player) victim).getName());
		    PVP ap = PVP.get(attacker.getName());
		    PVP bp = PVP.get(victim.getName()); 
			if(a != null & b != null){
				if(a.getArena().getName().equalsIgnoreCase(b.getArena().getName())){
					
					if(a.getTeam() == b.getTeam()){
						e.setCancelled(true);
						return;
					} else {
						b.setLastDamaged(((Player) attacker));
						if(ap == null){
							new PVP(attacker.getName());
						}
						if(bp == null){
							new PVP(victim.getName());
						}
						ap.setAntylogout(20);
						bp.setAntylogout(20);
						return;
					}
					
				}
			}
       }
    }

}
