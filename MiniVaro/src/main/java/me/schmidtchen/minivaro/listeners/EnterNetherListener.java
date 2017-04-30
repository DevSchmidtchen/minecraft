package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import org.bukkit.Location;
import org.bukkit.TravelAgent;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created by Matti on 18.02.17.
 */
public class EnterNetherListener implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            return;
        }

        TravelAgent travelAgent = event.getPortalTravelAgent();

        String oldWorld = event.getFrom().getWorld().getName();
        String newWorld;
        if (oldWorld.indexOf('_') >= 0) {
            newWorld = oldWorld.split("_")[0];
        } else {
            newWorld = oldWorld + "_nether";
        }
        World world = MiniVaro.getInstance().getServer().getWorld(newWorld);
        Location location = event.getFrom();
        location.setWorld(world);

        event.useTravelAgent(true);

        travelAgent.setCanCreatePortal(true);
        travelAgent.setSearchRadius(50);
        Location newLocation = travelAgent.findOrCreate(location);

        event.setTo(newLocation);
    }

}
