package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.Menu;
import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Matti on 23.01.17.
 */
public class BackItem extends MenuItem {

    public BackItem() {
        super(Material.REDSTONE);
        super.setDisplayName("§8>> §cZurück")
                .setLore("Hiermit gelangst du", "zum Menü davor!");
    }

    @Override
    public void onClick(Player player) {
        if (MiniVaro.getInstance().getMenuManager().getCurrent().get(player) == Menu.SWITCH) {
            player.closeInventory();
            MiniVaro.getInstance().getMenuManager().getCurrent().remove(player);
        } else if (MiniVaro.getInstance().getMenuManager().getCurrent().get(player) == Menu.MAIN){
            MiniVaro.getInstance().getMenuManager().openMenu(player, Menu.SWITCH);
        } else {
            MiniVaro.getInstance().getMenuManager().openMenu(player, Menu.MAIN);
        }
    }
}
