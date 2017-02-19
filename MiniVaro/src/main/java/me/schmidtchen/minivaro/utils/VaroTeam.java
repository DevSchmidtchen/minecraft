package me.schmidtchen.minivaro.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.schmidtchen.minivaro.MiniVaro;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by Matti on 06.01.17.
 */
@Getter
@NoArgsConstructor
public class VaroTeam {

    public Color color;
    public String name;
    public List<String> members = new ArrayList<>();
    public List<VaroLocation> teamChest;

    public VaroTeam(Color color, String name, List<String> members) {
        this.color = color;
        this.name = name;
        this.members = members;
    }

    public void requestName(Player player) {
        AnvilGUI anvilGUI = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
            public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                if (event.getSlot().equals(AnvilGUI.AnvilSlot.OUTPUT)) {
                    event.setWillClose(true);
                    event.setWillDestroy(true);
                    if (MiniVaro.getInstance().getTeamManager().getTeams().stream().anyMatch(varoTeam -> varoTeam.getName().equalsIgnoreCase(event.getName()))) {
                        player.sendMessage(MiniVaro.getInstance().getPrefix() + "Dieses Team existiert schon!");
                        return;
                    }
                    setName(event.getName());
                    System.out.println("[VaroBuild] Name: " + event.getName());
                    MiniVaro.getInstance().getMenuManager().openMenu(player, Menu.COLOR);
                } else {
                    event.setWillClose(false);
                    event.setWillDestroy(false);
                }
            }
        });

        ItemStack itemStack = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Teamname");
        itemStack.setItemMeta(itemMeta);

        anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, itemStack);
        anvilGUI.open();
    }

    public void requestMembers(Player player) {
        if (this.getMembers().size() < 2) {
            AnvilGUI anvilGUI = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                    if (event.getSlot().equals(AnvilGUI.AnvilSlot.OUTPUT)) {
                        event.setWillClose(true);
                        event.setWillDestroy(true);
                        addMember(event.getName(), name -> {
                            if (name == null) {
                                player.sendMessage(MiniVaro.getInstance().getPrefix() + "§cDieser Spieler existiert nicht!");
                                requestMembers(player);
                            } else {
                                System.out.println("[VaroBuild] Spielername: " + name);
                                if (getMembers().size() == 2) {
                                    player.sendMessage(MiniVaro.getInstance().getPrefix() + "Team hinzugefügt!");
                                    getTeam().addToConfig();
                                    MiniVaro.getInstance().getScoreboardManager().updateScoreboard();
                                } else {
                                    requestMembers(player);
                                }
                            }
                        });
                    } else {
                        event.setWillClose(false);
                        event.setWillDestroy(false);
                    }
                }
            });

            ItemStack itemStack = new ItemStack(Material.NAME_TAG);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("Spielername");
            itemStack.setItemMeta(itemMeta);

            anvilGUI.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, itemStack);
            anvilGUI.open();
        }
    }

    public void setColor (Color color) {
        this.color = color;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void addMember (String name, Consumer<String> callback) {
        if (members.size() < 2) {
            if (Bukkit.getPlayer(name) != null) {
                Player player = Bukkit.getPlayer(name);
                members.add(player.getUniqueId().toString());
                MiniVaro.getInstance().getMainConfig().getVaroPlayer().add(new VaroPlayer(player.getUniqueId().toString()));
                try {
                    MiniVaro.getInstance().getMainConfig().save();
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                }
                player.setDisplayName(MiniVaro.getInstance().getChatColor(color) + player.getName());
                MiniVaro.getInstance().getServer().getScheduler().runTaskAsynchronously(MiniVaro.getInstance(), () -> callback.accept(name));
            } else {
                UUIDFetcher.getUUID(name, new Consumer<UUID>() {
                    @Override
                    public void accept(UUID uuid) {
                        if (uuid != null) {
                            members.add(uuid.toString());
                            MiniVaro.getInstance().getMainConfig().getVaroPlayer().add(new VaroPlayer(uuid.toString()));
                            callback.accept(name);
                            try {
                                MiniVaro.getInstance().getMainConfig().save();
                            } catch (InvalidConfigurationException e) {
                                e.printStackTrace();
                            }
                        } else {
                            callback.accept(null);
                        }
                    }
                });
            }
        }
    }

    public void removeMember (Player player) {
        for (String uuid : members) {
            if (uuid.equals(player.getUniqueId().toString())) {
                members.remove(uuid);
            }
        }
    }

    public void addToConfig() {
        MiniVaro.getInstance().getTeamConfig().getTeams().add(this);
        try {
            MiniVaro.getInstance().getTeamConfig().save();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void removeFromConfig() {
        MiniVaro.getInstance().getTeamConfig().getTeams().remove(this);
        try {
            MiniVaro.getInstance().getTeamConfig().save();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        members.stream().filter(uuid -> Bukkit.getPlayer(UUID.fromString(uuid)) != null).forEach(uuid -> Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(MiniVaro.getInstance().getPrefix() + message));
    }

    public void setTeamchest(List<VaroLocation> locations) {
        this.teamChest = locations;
    }

    public void removeTeamchest() {
        teamChest = null;
    }

    public VaroTeam getTeam() {
        return this;
    }

}
