package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Matti on 07.02.17.
 */
public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (MiniVaro.getInstance().getWorldManager().getInVaro().contains(player) && event.hasBlock() && event.getClickedBlock().getType().equals(Material.CHEST)) {
            Block chest = event.getClickedBlock();
            for (VaroTeam varoTeam : MiniVaro.getInstance().getTeamManager().getLivingTeams()) {
                if (varoTeam.getTeamChest() != null && !varoTeam.getTeamChest().isEmpty()) {
                    if (varoTeam.getTeamChest().stream().anyMatch(location -> location.toBukkitLocation().distance(chest.getLocation()) == 0)) {
                        if (!varoTeam.getMembers().contains(player.getUniqueId().toString())) {
                            player.sendMessage(MiniVaro.getInstance().getPrefix() + "Das ist die Teamchest von Team " + MiniVaro.getInstance().getChatColor(varoTeam.getColor()) + varoTeam.getName() + "§7!");
                            event.setCancelled(true);
                            break;
                        }
                    }
                }
            }
        }
    }

}
