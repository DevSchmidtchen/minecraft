package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * Created by Matti on 24.01.17.
 */
public class RestartItem extends MenuItem {

    public RestartItem() {
        super(Material.STAINED_CLAY);
        super.setDisplayName("§8>> §4Varo neustarten")
                .setData((short) 4)
                .setLore("Hiermit kannst du", "Varo neustarten!", "", "Benutze es nur,", "wenn es wichtig ist!");
    }

    @Override
    public void onClick(Player player) {
        MiniVaro.getInstance().getMenuManager().waitForConfirmation(player, new Consumer<Player>() {
            @Override
            public void accept(Player player) {
                player.closeInventory();
                player.sendMessage(MiniVaro.getInstance().getPrefix() + "Varo wird zurückgesetzt...");
                MiniVaro.getInstance().getVaro().restart();
            }
        });
    }

}
