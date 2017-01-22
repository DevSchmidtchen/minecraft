package me.schmidtchen.minivaro.manager;

import me.schmidtchen.minivaro.utils.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matti on 22.01.17.
 */
public class MenuManager {

    public Map<Player, Menu> current;
    public String inventoryPrefix;

    public MenuManager() {
        current = new HashMap<>();
        inventoryPrefix = "§8>> §6VaroBuild §7- §e";
    }

    public void openVaroInventory(Player player) {
        Bukkit.getServer().createInventory(null, player.isOp() ? 45 : 27, inventoryPrefix + Menu.MAIN.name());
    }

    public void createNewTeam() {

    }

    public void listTeams() {

    }

    public void removeTeam() {

    }

    public void restartVaro() {

    }

    public void switchGametype() {

    }

    public void openMenu(Player p, Menu menu) {

    }

}
