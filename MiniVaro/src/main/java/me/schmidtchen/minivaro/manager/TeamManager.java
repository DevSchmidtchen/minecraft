package me.schmidtchen.minivaro.manager;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroPlayer;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Matti on 06.01.17.
 */
public class TeamManager {

    public boolean addTeam(Color color, String name, List<String> members) {
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

    public VaroTeam getTeamByPlayer(String uuid) {
        for (VaroTeam varoTeam : getTeams()) {
            if (varoTeam.getMembers().contains(uuid)) {
                return varoTeam;
            }
        }
        return null;
    }

    public VaroPlayer getVaroPlayer(Player player) {
        for (VaroPlayer varoPlayer : MiniVaro.getInstance().getMainConfig().getVaroPlayer()) {
            if (varoPlayer.getUuid().equals(player.getUniqueId().toString())) {
                return varoPlayer;
            }
        }
        return null;
    }

    public VaroPlayer getVaroPlayer(String uuid) {
        for (VaroPlayer varoPlayer : MiniVaro.getInstance().getMainConfig().getVaroPlayer()) {
            if (varoPlayer.getUuid().equals(uuid)) {
                return varoPlayer;
            }
        }
        return null;
    }

    public boolean hasTeam(Player player) {
        VaroPlayer varoPlayer = getVaroPlayer(player);
        if (varoPlayer == null) {
            return false;
        }
        for (VaroTeam varoTeam : getTeams()) {
            if (varoTeam.getMembers().contains(varoPlayer.getUuid())) {
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
            if (varoTeam.getMembers().stream().filter(uuid -> !getVaroPlayer(uuid).isDead()).count() > 0) {
                livingTeams.add(varoTeam);
            }
        }
        return livingTeams;
    }

    public void removeTeam(String name) {
        VaroTeam varoTeam = getTeamByName(name);
        varoTeam.removeFromConfig();
        MiniVaro.getInstance().getScoreboardManager().updateScoreboard();
        for (String uuid : varoTeam.getMembers()) {
            Player player = MiniVaro.getInstance().getServer().getPlayer(UUID.fromString(uuid));
            if (player != null) {
                player.setDisplayName(player.getName());
            }
        }
    }
}
