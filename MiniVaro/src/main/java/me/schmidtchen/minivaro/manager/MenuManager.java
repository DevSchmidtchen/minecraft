package me.schmidtchen.minivaro.manager;

import lombok.Getter;
import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.items.BackItem;
import me.schmidtchen.minivaro.items.DeleteTeamItem;
import me.schmidtchen.minivaro.utils.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.*;
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
        menuItems = new ArrayList<>();
        current = new HashMap<>();
        waiters = new HashMap<>();
        inventoryPrefix = "§6Varo §7- §e";
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

    public void showTeamInfo(VaroTeam varoTeam, Player player) {
        Inventory inventory = MiniVaro.getInstance().getServer().createInventory(null, 27, inventoryPrefix + MiniVaro.getInstance().getChatColor(varoTeam.getColor()) + varoTeam.getName());
        int inventoryPos = 10;
        for (String uuid : varoTeam.getMembers()) {
            VaroPlayer varoPlayer = MiniVaro.getInstance().getTeamManager().getVaroPlayer(uuid);
            if (varoPlayer != null) {
                ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                if (MiniVaro.getInstance().getMainConfig().getPlayers().containsKey(uuid)) {
                    skullMeta.setOwner(MiniVaro.getInstance().getMainConfig().getPlayers().get(uuid));
                    skullMeta.setDisplayName(MiniVaro.getInstance().getChatColor(varoTeam.getColor()) + MiniVaro.getInstance().getMainConfig().getPlayers().get(uuid));
                } else {
                    UUIDFetcher.getName(UUID.fromString(uuid), new Consumer<String>() {
                        @Override
                        public void accept(String name) {
                            skullMeta.setOwner(name);
                            skullMeta.setDisplayName(MiniVaro.getInstance().getChatColor(varoTeam.getColor()) + name);
                        }
                    });
                }
                List<String> lore = new ArrayList<>();
                lore.add("§6Status: " + (varoPlayer.isDead() ? "§causgeschieden" : "§2lebendig"));
                lore.add("§6Kills: §a" + varoPlayer.getKills());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.M.yyyy HH:mm", Locale.GERMANY);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));

                if (varoPlayer.getLastVaroSession() != -1) {
                    lore.add("§6letzter Varo-Beitritt: " + simpleDateFormat.format(new Date(varoPlayer.getLastVaroSession())));
                } else {
                    lore.add("§6letzter Varo-Beitritt: §cNie");
                }

                skullMeta.setLore(lore);
                itemStack.setItemMeta(skullMeta);

                inventory.setItem(inventoryPos, itemStack);
                inventoryPos++;
            }
        }
        inventory.setItem(14, new DeleteTeamItem(varoTeam).build());
        inventory.setItem(16, new BackItem().build());

        player.openInventory(inventory);
        current.put(player, Menu.INFO);
    }

    public void removeTeam(String name) {
        MiniVaro.getInstance().getTeamManager().removeTeam(name);
    }

    public void switchGametype(Player player) {
        if (MiniVaro.getInstance().getTeamManager().hasTeam(player)) {
            MiniVaro.getInstance().getWorldManager().switchWorld(player);
            if (MiniVaro.getInstance().getMenuManager().getCurrent().containsKey(player)) {
                MiniVaro.getInstance().getMenuManager().getCurrent().remove(player);
            }
        } else {
            player.sendMessage(MiniVaro.getInstance().getPrefix() + "§cDu bist keinem Team zugeordnet!");
        }
    }

    public void waitForConfirmation(Player player, Consumer<Player> callback) {
        openMenu(player, Menu.CONFIRM);
        waiters.put(player, callback);
    }

    public void openMenu(Player player, Menu menu) {
        Inventory inventory = MiniVaro.getInstance().getServer().createInventory(null, menu.getSize(), inventoryPrefix + menu.getName());
        for (Map.Entry<Integer, ItemStack> entry : menu.getContent(player).entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
        MiniVaro.getInstance().getServer().getScheduler().runTask(MiniVaro.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.openInventory(inventory);
                current.put(player, menu);
            }
        });
    }

}
