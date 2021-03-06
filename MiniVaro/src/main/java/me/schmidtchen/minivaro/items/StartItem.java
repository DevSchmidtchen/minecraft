package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Matti on 14.02.17.
 */
public class StartItem extends MenuItem {

    public StartItem() {
        super(Material.STAINED_CLAY);
        super.setDisplayName("§8>> §2Varo starten")
                .setData((short) 5)
                .setLore("Hiermit kannst du", "Varo starten!", "", "Benutze es nur,", "wenn du dir sicher bist!");
    }

    @Override
    public void onClick(Player player) {
        MiniVaro.getInstance().getMenuManager().waitForConfirmation(player, callbackPlayer -> {
            callbackPlayer.closeInventory();
            if (MiniVaro.getInstance().getMainConfig().getVaroCenter() != null) {
                MiniVaro.getInstance().getVaro().start();
            } else {
                callbackPlayer.sendMessage(MiniVaro.getInstance().getPrefix() + "Du musst zuerst die Mitte der Welt setzen!");
            }
        });
    }
}
