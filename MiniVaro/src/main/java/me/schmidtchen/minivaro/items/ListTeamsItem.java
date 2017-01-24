package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Matti on 24.01.17.
 */
public class ListTeamsItem extends MenuItem {

    public ListTeamsItem() {
        super(Material.BOOK);
        super.setDisplayName("§8>> §3Teamliste");
    }

    @Override
    public void onClick(Player player) {
        MiniVaro.getInstance().getMenuManager().listTeams(player);
    }

}
