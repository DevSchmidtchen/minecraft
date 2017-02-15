package me.schmidtchen.minivaro.manager;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroPlayer;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matti on 06.01.17.
 */
public class TeamManager {

    public boolean addTeam(Color color, String name, List<VaroPlayer> members) {
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

    public VaroTeam getTeamByPlayer(VaroPlayer varoPlayer) {
        for (VaroTeam varoTeam : getTeams()) {
            if (varoTeam.getMembers().contains(varoPlayer)) {
                return varoTeam;
            }
        }
        return null;
    }

    public VaroPlayer getVaroPlayer(Player player) {
        for (VaroTeam varoTeam : getTeams()) {
            return varoTeam.getMembers().stream().filter((varoPlayer) -> varoPlayer.getUuid().equals(player.getUniqueId().toString())).findFirst().orElse(null);
        }
        return null;
    }

    public boolean hasTeam(Player player) {
        VaroPlayer varoPlayer = getVaroPlayer(player);
        for (VaroTeam varoTeam : getTeams()) {
            if (varoTeam.getMembers().contains(varoPlayer)) {
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

    public List<VaroTeam> getLivingTeams() {
        List<VaroTeam> livingTeams = new ArrayList<>();
        for (VaroTeam varoTeam : getTeams()) {
            if (varoTeam.getMembers().stream().filter(varoPlayer -> !varoPlayer.isDead()).count() > 0) {
                livingTeams.add(varoTeam);
            }
        }
        return livingTeams;
    }

    public void removeTeam(String name) {
        VaroTeam varoTeam = getTeamByName(name);
        varoTeam.removeFromConfig();
        MiniVaro.getInstance().getScoreboardManager().updateScoreboard();
        for (VaroPlayer varoPlayer : varoTeam.getMembers()) {
            Player player = MiniVaro.getInstance().getServer().getPlayer(varoPlayer.getUuid());
            if (player != null) {
                player.setDisplayName(player.getName());
            }
        }
    }
}
