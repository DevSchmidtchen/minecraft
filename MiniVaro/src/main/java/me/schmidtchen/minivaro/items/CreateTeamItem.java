package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Matti on 24.01.17.
 */
public class CreateTeamItem extends MenuItem {

    public CreateTeamItem() {
        super(Material.NAME_TAG);
        super.setDisplayName("§8>> §3Team erstellen");
    }

    @Override
    public void onClick(Player player) {
        MiniVaro.getInstance().getMenuManager().createNewTeam(player);
    }

}
