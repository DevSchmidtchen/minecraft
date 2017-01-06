package me.schmidtchen.minivaro.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Created by Matti on 06.01.17.
 */
@AllArgsConstructor
@Getter
public class VaroTeam {

    private Color color;
    private String name;
    private List<UUID> members;

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

}
