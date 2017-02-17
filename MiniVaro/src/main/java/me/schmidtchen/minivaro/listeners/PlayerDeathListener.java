package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroState;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Optional;

/**
 * Created by Matti on 06.01.17.
 */
public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (MiniVaro.getInstance().getWorldManager().getInVaro().contains(player)) {
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
            MiniVaro.getInstance().getWorldManager().switchWorld(player);
            checkEnd();
        } else {
            event.setKeepInventory(true);
            if (player.getKiller() != null) {
                Player killer = player.getKiller();
                event.setDeathMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7wurde von " + killer.getDisplayName() + " §7getötet!");
            } else {
                event.setDeathMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7ist gestorben!");
            }
        }
    }

    public void checkEnd() {
        if (MiniVaro.getInstance().getTeamManager().getLivingTeams().size() <= 1) {
            Optional<VaroTeam> winner = MiniVaro.getInstance().getTeamManager().getLivingTeams().stream().findFirst();
            if (winner.isPresent()) {
                for (Player player : MiniVaro.getInstance().getServer().getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 10F, 10F);
                }
                MiniVaro.getInstance().getVaro().setVaroState(VaroState.END);
                MiniVaro.getInstance().getServer().broadcastMessage(MiniVaro.getInstance().getPrefix() + MiniVaro.getInstance().getChatColor(winner.get().getColor()) + winner.get().getName() + " §2hat Varo gewonnen!");
            }

        }
    }

}
