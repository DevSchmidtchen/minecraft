package me.schmidtchen.minivaro.manager;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Created by Matti on 06.01.17.
 */
public class TeamManager {

    public boolean addTeam (Color color, String name, List<UUID> members) {
        if (!checkExistence(name)) {
            VaroTeam team = new VaroTeam(color, name, members);
            team.addToConfig();
            return true;
        }
        return false;
    }

    public List<VaroTeam> getTeams() {
        return MiniVaro.getInstance().getTeamConfig().getTeams();
    }

    public VaroTeam getTeamByName(String name) {
        for (VaroTeam varoTeam : getTeams()) {
            if (name.equals(varoTeam.getName())) {
                return varoTeam;
            }
        }
        return null;
    }

    public boolean hasTeam(Player player) {
        for (VaroTeam varoTeam : getTeams()) {
            if (varoTeam.getMembers().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkExistence(String name) {
        for (VaroTeam varoTeam : getTeams()) {
            if (name.equals(varoTeam.getName())) {
                return true;
            }
        }
        return false;
    }

}
