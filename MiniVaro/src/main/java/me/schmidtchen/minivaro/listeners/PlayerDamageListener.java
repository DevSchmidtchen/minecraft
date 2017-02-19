package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Matti on 16.02.17.
 */
public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player victim = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            if (MiniVaro.getInstance().getWorldManager().getInVaro().contains(victim)) {
                VaroTeam victimTeam = MiniVaro.getInstance().getTeamManager().getTeamByPlayer(victim.getUniqueId().toString());
                VaroTeam damagerTeam = MiniVaro.getInstance().getTeamManager().getTeamByPlayer(damager.getUniqueId().toString());

                event.setCancelled(victimTeam.getName().equals(damagerTeam.getName()));
            }
        }
    }

}
