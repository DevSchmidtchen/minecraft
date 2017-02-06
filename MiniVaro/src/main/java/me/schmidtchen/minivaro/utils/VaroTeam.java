package me.schmidtchen.minivaro.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.schmidtchen.minivaro.MiniVaro;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
    public List<VaroPlayer> members = new ArrayList<>();
    public Location teamChest;

    public VaroTeam(Color color, String name, List<VaroPlayer> members) {
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
                    setName(event.getName());
                    System.out.println("[VaroBuild] Name: " + event.getName());
                    MiniVaro.getInstance().getMenuManager().openMenu(player, Menu.COLOR);
                } else {
                    event.setWillClose(false);
                    event.setWillDestroy(false);
                }
            }
        });
        anvilGUI.open();
    }

    public void requestMember(Player player) {
        if (this.getMembers().isEmpty() || this.getMembers().size() == 1) {
            AnvilGUI anvilGUI = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                    if (event.getSlot().equals(AnvilGUI.AnvilSlot.OUTPUT)) {
                        event.setWillClose(true);
                        event.setWillDestroy(true);
                        addMember(event.getName());
                        System.out.println("[VaroBuild] Spielername: " + event.getName());
                        if (getMembers().size() == 2) {
                            player.sendMessage(MiniVaro.getInstance().getPrefix() + "Team hinzugefügt!");
                            getTeam().addToConfig();
                        } else {
                            requestMember(player);
                        }
                    } else {
                        event.setWillClose(false);
                        event.setWillDestroy(false);
                    }
                }
            });
            anvilGUI.open();
        }
    }

    public void setColor (Color color) {
        this.color = color;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void addMember (String name) {
        if (members.size() < 2) {
            UUIDFetcher.getUUID(name, new Consumer<UUID>() {
                @Override
                public void accept(UUID uuid) {
                    members.add(new VaroPlayer(uuid));
                }
            });
        }
    }

    public void removeMember (Player player) {
        for (VaroPlayer varoPlayer : members) {
            if (varoPlayer.getUuid() == player.getUniqueId()) {
                members.remove(varoPlayer);
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

    public void setTeamchest(Location location) {
        this.teamChest = location;
    }

    public void removeTeamchest() {
        teamChest = null;
    }

    public VaroTeam getTeam() {
        return this;
    }

}
