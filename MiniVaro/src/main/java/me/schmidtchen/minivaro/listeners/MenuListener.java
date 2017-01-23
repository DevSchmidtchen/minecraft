package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Matti on 22.01.17.
 */
public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        if (MiniVaro.getInstance().getMenuManager().current.containsKey(player)) {
            for (MenuItem menuItem : MiniVaro.getInstance().getMenuManager().menuItems) {
                if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && menuItem.getDisplayName().equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                    menuItem.onClick(player);
                    return;
                }
            }
        }
    }

}
