package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Matti on 24.01.17.
 */
public class ConfirmationItem extends MenuItem {

    public ConfirmationItem() {
        super(Material.STAINED_GLASS_PANE);
        super.setDisplayName("§8>> §2Ja")
                .setData((short) 5);
    }

    @Override
    public void onClick(Player player) {
        MiniVaro.getInstance().getMenuManager().getWaiters().get(player);
    }

}
