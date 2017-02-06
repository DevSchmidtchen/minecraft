package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.MenuItem;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Created by Matti on 06.02.17.
 */
public class ColorItem extends MenuItem {

    private Color color;

    public ColorItem(Color color) {
        super(Material.LEATHER_CHESTPLATE);
        super.setDisplayName("§8>> " + color + color.toString());

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) super.build().getItemMeta();
        leatherArmorMeta.setColor(color);
        super.setItemMeta(leatherArmorMeta);
        this.color = color;
    }

    @Override
    public void onClick(Player player) {
        VaroTeam varoTeam = MiniVaro.getInstance().getMenuManager().getCachedVaroTeams().get(player);
        varoTeam.setColor(color);
        varoTeam.requestMember(player);
    }
}
