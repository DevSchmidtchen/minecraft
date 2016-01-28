/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.broempire.mysqlmanager;

import de.broempire.mysqlmanager.Manager.Bro;
import de.broempire.mysqlmanager.Manager.MySQL;
import de.broempire.mysqlmanager.Stats.TurnOff;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Matti
 */
public class main extends JavaPlugin{
    MySQL mySQL;
    Bro bro;
    TurnOff turnOff;

    @Override
    public void onEnable() {
        
        init();
        
        System.out.println("[" + this.getName() + "]" + " Plugin wurde aktiviert");
    }
    
    private void init() {
        
        loadConfig();
       
        mySQL = new MySQL(getConfig().getString("MySQL.Hostname"), getConfig().getString("MySQL.Database"), getConfig().getString("MySQL.Username"), getConfig().getString("MySQL.Password"));
        mySQL.update("CREATE TABLE IF NOT EXISTS Players (UUID VARCHAR(64), Last-Name VARCHAR(16), Group VARCHAR(16), Coins INT, First-Online VARCHAR(100), Last-Online VARCHAR(100), Ban-Points INT)");
        
        
        
    }
    public void loadConfig() {
        FileConfiguration cfg = this.getConfig();
        
        cfg.options().copyDefaults(true);
        
        cfg.addDefault("MySQL.Hostname", "Hostname");
        cfg.addDefault("MySQL.Username", "Username");
        cfg.addDefault("MySQL.Database", "Database");
        cfg.addDefault("MySQL.Password", "Password");
        
        saveConfig();
    }
    
    @Override
    public void onDisable() {
        System.out.println("[" + this.getName() + "]" + " Plugin wurde deaktiviert");
    }

    public Bro getBro() {
        return bro;
    }

    public TurnOff getTurnOff() {
        return turnOff;
    }
    
    
    
    
    
    
    
}
