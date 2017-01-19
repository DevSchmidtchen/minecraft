package me.schmidtchen.minivaro;

import lombok.Getter;
import lombok.Setter;
import me.schmidtchen.minivaro.commands.VaroCommand;
import me.schmidtchen.minivaro.configs.MainConfig;
import me.schmidtchen.minivaro.configs.TeamConfig;
import me.schmidtchen.minivaro.listeners.PlayerDeathListener;
import me.schmidtchen.minivaro.listeners.ServerListener;
import me.schmidtchen.minivaro.manager.TeamManager;
import me.schmidtchen.minivaro.manager.WorldManager;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Matti on 06.01.17.
 */
@Getter
@Setter
public class MiniVaro extends JavaPlugin {

    @Getter
    private static MiniVaro instance;

    public WorldManager worldManager;
    public TeamManager teamManager;

    public String prefix = "§6VaroBuild §8» §7";

    public MainConfig mainConfig;
    public TeamConfig teamConfig;

    @Override
    public void onEnable() {
        instance = this;

        mainConfig = new MainConfig(new File(getDataFolder(), "config.yml"));
        teamConfig = new TeamConfig(new File(getDataFolder(), "teams.yml"));

        try {
            mainConfig.init();
            teamConfig.init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        registerEvents();
        registerCommands();

        worldManager = new WorldManager();
        teamManager = new TeamManager();

        System.out.println("[VaroBuild] Plugin started!");
    }

    private void registerEvents() {
        PluginManager pluginManager= this.getServer().getPluginManager();

        pluginManager.registerEvents(new ServerListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
    }

    private void registerCommands() {

        this.getCommand("varo").setExecutor(new VaroCommand());

    }

    @Override
    public void onDisable() {
        System.out.println("[VaroBuild] Plugin stopped!");
    }
}
