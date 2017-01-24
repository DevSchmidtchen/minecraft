package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * Created by Matti on 23.01.17.
 */
public class SwitchItem extends MenuItem {

    public SwitchItem() {
        super(Material.STAINED_GLASS_PANE);
        super.setData((short) 5)
                .setDisplayName("§8>> §aSpielmodus wechseln")
                .setLore("Hiermit kannst du", "in die Varo- bzw.", "die Buildwelt wechseln!")
                .build();
    }

    @Override
    public void onClick(Player player) {
        MiniVaro.getInstance().getMenuManager().waitForConfirmation(player, new Consumer<Player>() {
            @Override
            public void accept(Player confirmedPlayer) {
                System.out.println("onClick: " + player.getName());
                System.out.println("accept: " + confirmedPlayer.getName());
                if (player.getUniqueId() == confirmedPlayer.getUniqueId()) {
                    MiniVaro.getInstance().getMenuManager().switchGametype(confirmedPlayer);
                }

            }
        });
    }

}
