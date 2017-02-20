package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.configs.MainConfig;
import me.schmidtchen.minivaro.utils.VaroLocation;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by Matti on 06.01.17.
 */

public class ServerListener implements Listener {

    @EventHandler
    public void onServerPing (ServerListPingEvent event) {
        event.setMaxPlayers(10);
        event.setMotd("§cVaro§2Build §6> §8Version 1.8.9\n" +
                "§1Testing & Fixing");
    }

    @EventHandler
    public void onLogin (PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (!player.isOp() && !MiniVaro.getInstance().getTeamManager().hasTeam(player)) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "§cDu bist keinem Team zugeordnet worden!\n\n" +
                    "§6Spreche bitte einen Projektleiter an!");
        }
    }

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (MiniVaro.getInstance().getTeamManager().hasTeam(player)) {
            player.setDisplayName(MiniVaro.getInstance().getChatColor(MiniVaro.getInstance().getTeamManager().getTeamByPlayer(player.getUniqueId().toString()).getColor()) + player.getName());
        }
        event.setJoinMessage(MiniVaro.getInstance().getPrefix() + "[§2+§7] " + player.getDisplayName() + " §7» §c" + MiniVaro.getInstance().getServer().getOnlinePlayers().size() + "§6/" + MiniVaro.getInstance().getServer().getMaxPlayers());

        MiniVaro.getInstance().getScoreboardManager().setScoreboard(player);

        if (player.getWorld().getName().equals("varo")) {
            player.teleport(MiniVaro.getInstance().getServer().getWorld("world").getSpawnLocation());
        }

        MainConfig mainConfig = MiniVaro.getInstance().getMainConfig();
        if (!mainConfig.getPlayers().containsKey(player.getUniqueId().toString()) || !mainConfig.getPlayers().get(player.getUniqueId().toString()).equals(player.getName())) {
            mainConfig.getPlayers().put(player.getUniqueId().toString(), player.getName());
            try {
                mainConfig.save();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(MiniVaro.getInstance().getPrefix() + "[§4-§7] " + player.getDisplayName() + " §7» §c" + (MiniVaro.getInstance().getServer().getOnlinePlayers().size() - 1) + "§6/" + MiniVaro.getInstance().getServer().getMaxPlayers());
        MiniVaro.getInstance().getMenuManager().getCurrent().remove(player);

        if (MiniVaro.getInstance().getWorldManager().getInVaro().contains(player)) {
            MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).setVaroLocation(new VaroLocation(player.getLocation()));
            if (MiniVaro.getInstance().getWorldManager().getOperators().contains(player)) {
                player.setOp(true);
                MiniVaro.getInstance().getWorldManager().getOperators().remove(player);
            }
        } else {
            MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).setBuildLocation(new VaroLocation(player.getLocation()));
        }
        try {
            MiniVaro.getInstance().getMainConfig().save();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
