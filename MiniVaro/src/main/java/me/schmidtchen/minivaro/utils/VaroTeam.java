package me.schmidtchen.minivaro.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.schmidtchen.minivaro.MiniVaro;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Matti on 06.01.17.
 */
@Getter
public class VaroTeam {

    public VaroTeam(Color color, String name, List<UUID> members) {
        this.color = color;
        this.name = name;
        this.members = members;
    }

    public Color color;
    public String name;
    public List<UUID> members;
    public Location teamChest;

    public void changeColor (Color color) {
        this.color = color;
    }

    public void changeName (String name) {
        this.name = name;
    }

    public void addMember (Player player) {
        if (members.size() <= 2) {
            members.add(player.getUniqueId());
        }
    }

    public void removeMember (Player player) {
        if (members.contains(player.getUniqueId())) {
            members.remove(player.getUniqueId());
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

    public void setTeamchest(Location location) {
        this.teamChest = location;
    }

    public void removeTeamchest() {
        teamChest = null;
    }

}
