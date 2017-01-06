package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(MiniVaro.getInstance().getPrefix() + "[§2+§7] " + player.getDisplayName() + " §7» §c" + MiniVaro.getInstance().getServer().getOnlinePlayers().size() + "§6/" + MiniVaro.getInstance().getServer().getMaxPlayers());
    }

}
