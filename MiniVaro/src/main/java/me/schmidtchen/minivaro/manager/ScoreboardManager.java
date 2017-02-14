package me.schmidtchen.minivaro.manager;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matti on 06.01.17.
 */
public class ScoreboardManager {

    private List<Team> teams;
    private Scoreboard scoreboard;

    public ScoreboardManager() {
        teams = new ArrayList<>();
        scoreboard = MiniVaro.getInstance().getServer().getScoreboardManager().getMainScoreboard();
        for (VaroTeam varoTeam : MiniVaro.getInstance().getTeamManager().getTeams()) {
            Team team = scoreboard.getTeam(varoTeam.getName()) != null ? scoreboard.getTeam(varoTeam.getName()) : scoreboard.registerNewTeam(varoTeam.getName());
            team.setPrefix(MiniVaro.getInstance().getChatColor(varoTeam.getColor()) + varoTeam.getName() + " §7| ");
            teams.add(team);
        }
    }

    public void setScoreboard(Player player) {
        if (MiniVaro.getInstance().getTeamManager().hasTeam(player)) {
            teams.stream().filter(team -> team.hasEntry(player.getName())).findAny().get().removeEntry(player.getName());
            scoreboard.getTeam(MiniVaro.getInstance().getTeamManager().getTeamByPlayer(MiniVaro.getInstance().getTeamManager().getVaroPlayer(player)).getName()).addEntry(player.getName());
            player.setScoreboard(scoreboard);
        }
    }
}
