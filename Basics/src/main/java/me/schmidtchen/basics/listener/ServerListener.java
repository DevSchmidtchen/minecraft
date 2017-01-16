package me.schmidtchen.basics.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

/**
 * Created by Matti on 06.11.16.
 */
public class ServerListener implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        event.setMotd("§4§k!-§r §2§lHerzlich willkommen! §4§k-!");
        event.setMaxPlayers(2);
    }

}
