package me.schmidtchen.minivaro.utils;

import me.schmidtchen.minivaro.MiniVaro;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Matti on 23.01.17.
 */
public abstract class MenuItem extends ItemBuilder {

    public MenuItem(Material material) {
        super(material);
        MiniVaro.getInstance().getMenuManager().getMenuItems().add(this);
    }

    public abstract void onClick(Player player);

}
