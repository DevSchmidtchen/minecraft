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
        teams.clear();
    }

    public void setScoreboard(Player player) {
        Optional<Team> scoreboardTeam = teams.stream().filter(team -> team.hasEntry(player.getName())).findAny();
        scoreboardTeam.ifPresent(team -> team.removeEntry(player.getName()));
        if (MiniVaro.getInstance().getTeamManager().hasTeam(player)) {
            Team team = scoreboard.getTeam(player.getName()) != null ? scoreboard.getTeam(player.getName()) : scoreboard.registerNewTeam(player.getName());
            VaroTeam varoTeam = MiniVaro.getInstance().getTeamManager().getTeamByPlayer(player.getUniqueId().toString());
            team.setPrefix(MiniVaro.getInstance().getChatColor(varoTeam.getColor()) + varoTeam.getName() + " §7| ");
            if (MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).isDead()) {
                team.setSuffix(" §8[§c†§8]");
            } else {
                team.setSuffix(" §8[§2✔§8]");
            }
            team.addEntry(player.getName());
            teams.add(team);
        }
        player.setScoreboard(scoreboard);
    }

    public void updateScoreboard() {
        for (Player player : MiniVaro.getInstance().getServer().getOnlinePlayers()) {
            if (MiniVaro.getInstance().getTeamManager().hasTeam(player)) {
                Team team = scoreboard.getTeam(player.getName()) != null ? scoreboard.getTeam(player.getName()) : scoreboard.registerNewTeam(player.getName());
                VaroTeam varoTeam = MiniVaro.getInstance().getTeamManager().getTeamByPlayer(player.getUniqueId().toString());
                team.setPrefix(MiniVaro.getInstance().getChatColor(varoTeam.getColor()) + varoTeam.getName() + " §7| ");
                if (MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).isDead()) {
                    team.setSuffix(" §8[§c†§8]");
                } else {
                    team.setSuffix(" §8[§2✔§8]");
                }
                team.addEntry(player.getName());
            } else {
                teams.stream().filter(team -> team.hasEntry(player.getName())).findAny().ifPresent(team -> team.removeEntry(player.getName()));
            }
        }
    }
}
