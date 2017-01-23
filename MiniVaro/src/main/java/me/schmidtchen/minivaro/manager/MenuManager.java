package me.schmidtchen.minivaro.manager;

import me.schmidtchen.minivaro.utils.Menu;
import me.schmidtchen.minivaro.utils.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matti on 22.01.17.
 */
public class MenuManager {

    public Map<Player, Menu> current;
    public String inventoryPrefix;
    public List<MenuItem> menuItems;

    public MenuManager() {
        current = new HashMap<>();
        inventoryPrefix = "§8>> §6VaroBuild §7- §e";
    }

    public void openVaroInventory(Player player) {
        openMenu(player, Menu.SWITCH);
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

    public void confirm() {

    }

    public void openMenu(Player player, Menu menu) {
        current.put(player, menu);
        Inventory inventory = Bukkit.getServer().createInventory(null, menu.getSize(), inventoryPrefix + menu.getName());
    }

}
