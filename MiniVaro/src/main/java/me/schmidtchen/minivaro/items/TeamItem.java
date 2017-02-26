package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Created by Matti on 24.01.17.
 */
public class TeamItem extends MenuItem {

    private String name;

    public TeamItem(Color color, String name) {
        super(Material.LEATHER_HELMET);
        super.setDisplayName("§8>> " + MiniVaro.getInstance().getChatColor(color) + name);

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) super.build().getItemMeta();
        leatherArmorMeta.setColor(color);
        super.setItemMeta(leatherArmorMeta);
        this.name = name;
    }

    @Override
    public void onClick(Player player) {
        MiniVaro.getInstance().getMenuManager().showTeamInfo(MiniVaro.getInstance().getTeamManager().getTeamByName(name), player);
    }

}
