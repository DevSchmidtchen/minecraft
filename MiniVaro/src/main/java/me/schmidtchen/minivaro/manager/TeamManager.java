package me.schmidtchen.minivaro.manager;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.Color;

import java.util.List;
import java.util.UUID;

/**
 * Created by Matti on 06.01.17.
 */
public class TeamManager {

    public void addTeam (Color color, String name, List<UUID> members) {

    }

    public List<VaroTeam> getTeams() {
        return MiniVaro.getInstance().getTeamConfig().getTeams();
    }

}
