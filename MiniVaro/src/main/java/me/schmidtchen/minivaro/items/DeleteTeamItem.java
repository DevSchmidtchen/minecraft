package me.schmidtchen.minivaro.items;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.MenuItem;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * Created by Matti on 26.02.17.
 */
public class DeleteTeamItem extends MenuItem {

    private VaroTeam varoTeam;

    public DeleteTeamItem(VaroTeam varoTeam) {
        super(Material.BARRIER);
        super.setDisplayName("§8>> " + MiniVaro.getInstance().getChatColor(varoTeam.getColor()) + varoTeam.getName() + " §4löschen");

        this.varoTeam = varoTeam;
    }

    @Override
    public void onClick(Player player) {
        MiniVaro.getInstance().getMenuManager().waitForConfirmation(player, new Consumer<Player>() {
            @Override
            public void accept(Player player) {
                MiniVaro.getInstance().getMenuManager().removeTeam(varoTeam.getName());
                player.sendMessage(MiniVaro.getInstance().getPrefix() + "Team entfernt!");
            }
        });
    }

}
