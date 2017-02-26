package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Matti on 26.02.17.
 */
public class CraftListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getView().getPlayer() instanceof Player) {
            Player player = (Player) event.getView().getPlayer();
            ItemStack result = event.getRecipe().getResult();

            if ((result.isSimilar(new ItemStack(Material.GOLDEN_APPLE, result.getAmount(), (short) 1)) || result.isSimilar(new ItemStack(Material.FISHING_ROD, result.getAmount()))) && MiniVaro.getInstance().getWorldManager().getInVaro().contains(player)) {
                player.sendMessage(MiniVaro.getInstance().getPrefix() + "§cDieses Item kannst du nicht craften!");
                event.setCancelled(true);
            }
        }
    }

}
