package me.schmidtchen.minivaro.manager;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Matti on 06.01.17.
 */
public class ScoreboardManager {

    private List<Team> teams;
    private Scoreboard scoreboard;

    public ScoreboardManager() {
        teams = new ArrayList<>();
        scoreboard = MiniVaro.getInstance().getServer().getScoreboardManager().getMainScoreboard();
        scoreboard.getTeams().forEach(Team::unregister);
        updateTeams();
    }

    public void updateTeams() {
        List<VaroTeam> varoTeams = MiniVaro.getInstance().getTeamManager().getTeams();
        teams.clear();
        for (VaroTeam varoTeam : varoTeams) {
            Team team = scoreboard.getTeam(varoTeam.getName()) != null ? scoreboard.getTeam(varoTeam.getName()) : scoreboard.registerNewTeam(varoTeam.getName());
            team.setPrefix(MiniVaro.getInstance().getChatColor(varoTeam.getColor()) + varoTeam.getName() + " §7| ");
            teams.add(team);
        }
    }

    public void setScoreboard(Player player) {
        Optional<Team> scoreboardTeam = teams.stream().filter(team -> team.hasEntry(player.getName())).findAny();
        scoreboardTeam.ifPresent(team -> team.removeEntry(player.getName()));
        if (MiniVaro.getInstance().getTeamManager().hasTeam(player)) {
            scoreboard.getTeam(MiniVaro.getInstance().getTeamManager().getTeamByPlayer(player.getUniqueId().toString()).getName()).addEntry(player.getName());
        }
        player.setScoreboard(scoreboard);
    }

    public void updateScoreboard() {
        updateTeams();
        for (Player player : MiniVaro.getInstance().getServer().getOnlinePlayers()) {
            teams.stream().filter(team -> team.hasEntry(player.getName())).findAny().ifPresent(team -> team.removeEntry(player.getName()));
            if (MiniVaro.getInstance().getTeamManager().hasTeam(player)) {
                scoreboard.getTeam(MiniVaro.getInstance().getTeamManager().getTeamByPlayer(player.getUniqueId().toString()).getName()).addEntry(player.getName());
            }
        }
    }
}
