package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Created by Matti on 24.01.17.
 */
public class TeamItem extends MenuItem {

    public TeamItem(Color color, String name) {
        super(Material.LEATHER_HELMET);
        super.setDisplayName("§8>> " + color + name + " löschen");

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) super.build().getItemMeta();
        leatherArmorMeta.setColor(color);
        super.setItemMeta(leatherArmorMeta);

    }

    @Override
    public void onClick(Player player) {

    }

}
