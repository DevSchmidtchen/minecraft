package me.schmidtchen.minivaro;

import lombok.Getter;
import lombok.Setter;
import me.schmidtchen.minivaro.commands.PingCommand;
import me.schmidtchen.minivaro.commands.VaroCommand;
import me.schmidtchen.minivaro.configs.MainConfig;
import me.schmidtchen.minivaro.configs.TeamConfig;
import me.schmidtchen.minivaro.listeners.*;
import me.schmidtchen.minivaro.manager.MenuManager;
import me.schmidtchen.minivaro.manager.ScoreboardManager;
import me.schmidtchen.minivaro.manager.TeamManager;
import me.schmidtchen.minivaro.manager.WorldManager;
import me.schmidtchen.minivaro.utils.Varo;
import me.schmidtchen.minivaro.utils.VaroState;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.ChatColor;
import org.bukkit.Color;
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
    public MenuManager menuManager;
    public ScoreboardManager scoreboardManager;

    public String prefix = "§6VaroBuild §8» §7";

    public MainConfig mainConfig;
    public TeamConfig teamConfig;

    public Varo varo;

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("[VaroBuild] Enabling MiniVaro...");

        mainConfig = new MainConfig(new File(getDataFolder(), "config.yml"));
        teamConfig = new TeamConfig(new File(getDataFolder(), "teams.yml"));

        try {
            mainConfig.init();
            teamConfig.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerEvents();
        registerCommands();

        teamManager = new TeamManager();
        scoreboardManager = new ScoreboardManager();
        worldManager = new WorldManager();
        menuManager = new MenuManager();

        varo = new Varo();
        varo.setVaroState(VaroState.valueOf(mainConfig.getVaroState()));

        worldManager.loadWorlds();

        System.out.println("[VaroBuild] Plugin started!");
    }

    private void registerEvents() {
        PluginManager pluginManager = this.getServer().getPluginManager();

        pluginManager.registerEvents(new ServerListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new MenuListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new SignEditListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new PlayerDamageListener(), this);
        pluginManager.registerEvents(new BlockListener(), this);
        pluginManager.registerEvents(new EnterNetherListener(), this);
        pluginManager.registerEvents(new CraftListener(), this);
        pluginManager.registerEvents(new EntityExplodeListener(), this);
    }

    private void registerCommands() {

        this.getCommand("varo").setExecutor(new VaroCommand());
        this.getCommand("ping").setExecutor(new PingCommand());

    }

    @Override
    public void onDisable() {
        try {
            mainConfig.save();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        System.out.println("[VaroBuild] Plugin stopped!");
    }

    /* -- UTILITY -- */

    public ChatColor getChatColor(Color color) {
        if (color.equals(Color.AQUA)) return ChatColor.AQUA;
        else if (color.equals(Color.BLACK)) return ChatColor.BLACK;
        else if (color.equals(Color.BLUE)) return ChatColor.BLUE;
        else if (color.equals(Color.SILVER)) return ChatColor.GRAY;
        else if (color.equals(Color.GREEN)) return ChatColor.DARK_GREEN;
        else if (color.equals(Color.LIME)) return ChatColor.GREEN;
        else if (color.equals(Color.OLIVE)) return ChatColor.DARK_PURPLE;
        else if (color.equals(Color.ORANGE)) return ChatColor.GOLD;
        else if (color.equals(Color.PURPLE)) return ChatColor.LIGHT_PURPLE;
        else if (color.equals(Color.RED)) return ChatColor.RED;
        else if (color.equals(Color.WHITE)) return ChatColor.WHITE;
        else if (color.equals(Color.YELLOW)) return ChatColor.YELLOW;
        else if (color.equals(Color.GRAY)) return ChatColor.DARK_GRAY;
        else return null;
    }
}
