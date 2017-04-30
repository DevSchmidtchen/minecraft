package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroState;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

/**
 * Created by Matti on 16.02.17.
 */
public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getWorld().getName().equals("varo")) {
            if (MiniVaro.getInstance().getVaro().getVaroState() == VaroState.COUNTDOWN || (MiniVaro.getInstance().getVaro().getVaroState() == VaroState.STARTING && !(event.getPlayer().getGameMode() == GameMode.CREATIVE))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!MiniVaro.getInstance().getWorldManager().getInVaro().contains(player)) {
            Block block = event.getBlockPlaced();
            if (block.getType() == Material.TNT || block.getType() == Material.LAVA || block.getType() == Material.LAVA_BUCKET || block.getType() == Material.STATIONARY_LAVA) {
                System.out.println("[VaroBuild] BlockPlace: " + player.getName() + " - " + block.getType().name() + " - " + block.getLocation().getX() + ":" + block.getLocation().getY() + ":" + block.getLocation().getZ());
            }
        } else if ((MiniVaro.getInstance().getVaro().getVaroState() == VaroState.STARTING && !(player.getGameMode() == GameMode.CREATIVE)) || MiniVaro.getInstance().getVaro().getVaroState() == VaroState.COUNTDOWN) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketEmpty (PlayerBucketEmptyEvent event) {
        if (event.getBucket() == Material.LAVA_BUCKET) {
            System.out.println("[VaroBuild] EmptyBucket: " + event.getPlayer().getName() + " - " + event.getBucket().name() + " - " + event.getBlockClicked().getLocation().getX() + ":" + event.getBlockClicked().getLocation().getY() + ":" + event.getBlockClicked().getLocation().getZ());
        }
    }

}
