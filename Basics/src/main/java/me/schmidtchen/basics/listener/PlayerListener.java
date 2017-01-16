package me.schmidtchen.basics.listener;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

/**
 * Created by Matti on 06.11.16.
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setDisplayName(ChatColor.DARK_PURPLE + player.getName() + ChatColor.WHITE);
        event.setJoinMessage(player.getDisplayName() + " §7hat den Server §2betreten§7!");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 3F, 3F);
        player.sendMessage("§aViel Spaß beim Spielen!");
        player.setPlayerListName(player.getDisplayName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(player.getDisplayName() + " §7hat den Server §4verlassen§7!");
    }

}
