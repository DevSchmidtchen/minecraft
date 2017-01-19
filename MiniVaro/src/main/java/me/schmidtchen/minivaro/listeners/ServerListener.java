package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.configs.MainConfig;
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

        event.setJoinMessage(MiniVaro.getInstance().getPrefix() + "[§2+§7] " + player.getDisplayName() + " §7» §c" + MiniVaro.getInstance().getServer().getOnlinePlayers().size() + "§6/" + MiniVaro.getInstance().getServer().getMaxPlayers());

        MainConfig mainConfig = MiniVaro.getInstance().getMainConfig();
        if (!mainConfig.getPlayers().containsKey(player.getUniqueId()) || !mainConfig.getPlayers().get(player.getUniqueId()).equals(player.getName())) {
            mainConfig.getPlayers().put(player.getUniqueId(), player.getName());
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

        event.setQuitMessage(MiniVaro.getInstance().getPrefix() + "[§4-§7] " + player.getDisplayName() + " §7» §c" + MiniVaro.getInstance().getServer().getOnlinePlayers().size() + "§6/" + MiniVaro.getInstance().getServer().getMaxPlayers());
    }

}
