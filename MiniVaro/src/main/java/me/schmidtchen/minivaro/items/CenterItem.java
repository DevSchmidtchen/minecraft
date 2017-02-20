package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.MenuItem;
import me.schmidtchen.minivaro.utils.VaroLocation;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * Created by Matti on 19.02.17.
 */
public class CenterItem extends MenuItem {

    public CenterItem() {
        super(Material.EYE_OF_ENDER);
        super.setDisplayName("§8>> §3Mitte setzen")
                .setLore("Hiermit kannst du die", "Mitte von Varo setzen,", "damit die Border richtig", "gesetzt wird!");
    }

    @Override
    public void onClick(Player player) {
        MiniVaro.getInstance().getMenuManager().waitForConfirmation(player, new Consumer<Player>() {
            @Override
            public void accept(Player player) {
                MiniVaro.getInstance().getMainConfig().setVaroCenter(new VaroLocation(player.getLocation().getBlock().getLocation()));
                MiniVaro.getInstance().getWorldManager().loadWorlds();
                try {
                    MiniVaro.getInstance().getMainConfig().save();
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                }
                player.sendMessage(MiniVaro.getInstance().getPrefix() + "Du hast die Mitte von Varo gesetzt!");
            }
        });
    }
}
