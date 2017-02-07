package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroState;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Matti on 06.01.17.
 */
public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (MiniVaro.getInstance().getWorldManager().isInVaro(player)) {
            MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).setDead(true);
            player.sendMessage(MiniVaro.getInstance().getPrefix() + "§cDu bist aus Varo ausgeschieden!");
            if (player.getKiller() != null) {
                Player killer = player.getKiller();
                MiniVaro.getInstance().getTeamManager().getVaroPlayer(killer).addKill();
                event.setDeathMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7wurde von " + killer.getDisplayName() + " §7ausgelöscht!");
                killer.getWorld().playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 10, 10);
            } else {
                event.setDeathMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7ist gestorben!");
                player.getWorld().playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 10, 10);
            }
        } else {
            event.setKeepInventory(true);
            if (player.getKiller() != null) {
                Player killer = player.getKiller();
                event.setDeathMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7wurde von " + killer.getDisplayName() + " §7getötet!");
            } else {
                event.setDeathMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7ist gestorben!");
            }
        }
        checkEnd();
    }

    public void checkEnd() {
        if (MiniVaro.getInstance().getTeamManager().getLivingTeams().size() <= 1) {
            VaroTeam winner = MiniVaro.getInstance().getTeamManager().getLivingTeams().stream().findFirst().get();
            MiniVaro.getInstance().getVaro().setVaroState(VaroState.END);
            MiniVaro.getInstance().getServer().broadcastMessage(MiniVaro.getInstance().getPrefix() + winner.getColor() + winner.getName() + " §2hat Varo gewonnen!");
        }
    }

}
