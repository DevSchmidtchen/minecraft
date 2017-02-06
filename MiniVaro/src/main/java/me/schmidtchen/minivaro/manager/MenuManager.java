package me.schmidtchen.minivaro.manager;

import lombok.Getter;
import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.Menu;
import me.schmidtchen.minivaro.utils.MenuItem;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Matti on 22.01.17.
 */
@Getter
public class MenuManager {

    public Map<Player, Menu> current;
    public String inventoryPrefix;
    public List<MenuItem> menuItems;

    public Map<Player, Consumer<Player>> waiters;

    public Map<Player, VaroTeam> cachedVaroTeams;

    public MenuManager() {
        cachedVaroTeams = new HashMap<>();
        current = new HashMap<>();
        waiters = new HashMap<>();
        inventoryPrefix = "§8>> §6VaroBuild §7- §e";
    }

    public void openVaroInventory(Player player) {
        openMenu(player, Menu.SWITCH);
    }

    public void createNewTeam(Player player) {
        VaroTeam varoTeam = new VaroTeam();
        cachedVaroTeams.put(player, varoTeam);
        varoTeam.requestName(player);
    }

    public void listTeams(Player player) {
        openMenu(player, Menu.LIST);
    }

    public void removeTeam(String name) {
        MiniVaro.getInstance().getTeamManager().removeTeam(name);
    }

    public void restartVaro() {
        MiniVaro.getInstance().getVaro().restart();
    }

    public void switchGametype(Player player) {

    }

    public void waitForConfirmation(Player player, Consumer<Player> callback) {
        openMenu(player, Menu.CONFIRM);
        waiters.put(player, callback);
    }

    public void openMenu(Player player, Menu menu) {
        current.put(player, menu);
        Inventory inventory = Bukkit.getServer().createInventory(null, menu.getSize(), inventoryPrefix + menu.getName());
        for (Map.Entry<Integer, ItemStack> entry : menu.getContent(player).entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
        player.openInventory(inventory);
    }

}
