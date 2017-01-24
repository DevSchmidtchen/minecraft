package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.ItemBuilder;
import me.schmidtchen.minivaro.utils.Menu;
import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Matti on 24.01.17.
 */
public class SettingsItem extends MenuItem {

    public SettingsItem() {
        super(Material.REDSTONE_COMPARATOR);
        super.setDisplayName("§8>> §6Hauptmenü")
                .setLore("Hier kannst du", "wichtige Einstellungen vornehmen!");
    }

    @Override
    public void onClick(Player player) {
        MiniVaro.getInstance().getMenuManager().openMenu(player, Menu.MAIN);
    }

}
