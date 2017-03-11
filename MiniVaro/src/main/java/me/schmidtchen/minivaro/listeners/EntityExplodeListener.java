package me.schmidtchen.minivaro.listeners;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * Created by Matti on 02.03.17.
 */
public class EntityExplodeListener implements Listener {

    @EventHandler
    public void onExplode (EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed && event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            event.blockList().clear();
        }
    }

}
