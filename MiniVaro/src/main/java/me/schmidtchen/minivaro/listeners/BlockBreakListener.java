package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Matti on 16.02.17.
 */
public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getWorld().getName().equals("varo")) {
            event.setCancelled(MiniVaro.getInstance().getVaro().getVaroState().equals(VaroState.COUNTDOWN));
        }
    }

}
