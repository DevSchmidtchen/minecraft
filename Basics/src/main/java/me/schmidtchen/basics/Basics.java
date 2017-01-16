package me.schmidtchen.basics;

import lombok.Getter;
import me.schmidtchen.basics.listener.ChatListener;
import me.schmidtchen.basics.listener.PlayerListener;
import me.schmidtchen.basics.listener.ServerListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Matti on 06.11.16.
 */
@Getter
public class Basics extends JavaPlugin {

    private Basics instance;

    @Override
    public void onEnable() {
        init();

        System.out.println("[Basics] System gestartet!");
    }

    private void init() {
        getServer().getPluginManager().registerEvents(new ServerListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[Basics] System gestoppt!");
    }

}
