package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.TitleAPI;
import me.schmidtchen.minivaro.utils.VaroState;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.meta.FireworkMeta;

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
            MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).setVaroLocation(null);
            player.sendMessage(MiniVaro.getInstance().getPrefix() + "§cDu bist aus Varo ausgeschieden!");
            MiniVaro.getInstance().getServer().broadcastMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §cist aus Varo ausgeschieden!");
            MiniVaro.getInstance().getScoreboardManager().updateScoreboard();
            if (player.getKiller() != null) {
                Player killer = player.getKiller();
                event.setDeathMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7wurde von " + killer.getDisplayName() + " §7ausgelöscht!");
                killer.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 10, 10);
                MiniVaro.getInstance().getTeamManager().getVaroPlayer(killer).addKill();
            } else {
                event.setDeathMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7ist gestorben!");
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 10, 10);
            }
            checkEnd();
        } else {
            event.setKeepInventory(true);
            event.setNewLevel(Math.round(player.getLevel()/2));
            event.setDroppedExp(0);
            if (player.getKiller() != null) {
                Player killer = player.getKiller();
                event.setDeathMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7wurde von " + killer.getDisplayName() + " §7getötet!");
            } else {
                event.setDeathMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7ist gestorben!");
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (MiniVaro.getInstance().getWorldManager().getInVaro().contains(player)) {
            event.setRespawnLocation((MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).getBuildLocation() == null) ? MiniVaro.getInstance().getServer().getWorld("world").getSpawnLocation() : MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).getBuildLocation().toBukkitLocation());
        } else {
            event.setRespawnLocation(MiniVaro.getInstance().getServer().getWorld("world").getSpawnLocation());
        }

        MiniVaro.getInstance().getWorldManager().getInVaro().remove(player);
        if (MiniVaro.getInstance().getWorldManager().getOperators().contains(player)) {
            MiniVaro.getInstance().getWorldManager().getOperators().remove(player);
            player.setOp(true);
        }


    }

    public void checkEnd() {
        if (MiniVaro.getInstance().getTeamManager().getLivingTeams().size() <= 1) {
            Optional<VaroTeam> winner = MiniVaro.getInstance().getTeamManager().getLivingTeams().stream().findFirst();
            if (winner.isPresent()) {
                for (Player player : MiniVaro.getInstance().getServer().getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 10F, 10F);
                    if (winner.get().getMembers().contains(player.getUniqueId().toString())) {
                        TitleAPI.sendTitle(player, 10, 60, 15, "§2Herzlichen Glückwunsch!", null);
                        Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
                        FireworkMeta fireworkMeta = firework.getFireworkMeta();
                        fireworkMeta.addEffect(FireworkEffect.builder()
                            .flicker(true)
                            .trail(true)
                            .with(FireworkEffect.Type.STAR)
                            .withColor(winner.get().getColor())
                            .withFade(winner.get().getColor())
                            .build());
                        fireworkMeta.setPower(2);
                        firework.setFireworkMeta(fireworkMeta);
                    }
                }
                MiniVaro.getInstance().getVaro().setVaroState(VaroState.END);
                MiniVaro.getInstance().getServer().broadcastMessage(MiniVaro.getInstance().getPrefix() + MiniVaro.getInstance().getChatColor(winner.get().getColor()) + winner.get().getName() + " §2hat Varo gewonnen!");

            }
        }
    }

}
