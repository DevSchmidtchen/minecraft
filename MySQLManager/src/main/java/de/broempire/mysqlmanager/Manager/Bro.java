/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.broempire.mysqlmanager.Manager;

import de.broempire.mysqlmanager.main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Matti
 */
public class Bro {
    
    private static MySQL mySQL;
    
    private final Plugin plugin;
    private final String uuid;
    
    public Bro(Plugin plugin, String uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
        
        
    }
    
    public static boolean UUIDexists (String uuid) {
        try{
            ResultSet resultSet = mySQL.query("SELECT * FROM Players WHERE UUID= '" + uuid + "'");
            
            if(resultSet.next()){
                return resultSet.getString("UUID") != null;
            }
		resultSet.close();
                return false;
        
        }catch(SQLException e){
            e.printStackTrace();
	}
                return false;
    }
    public static boolean NameExists (String name) {
        try{
            ResultSet resultSet = mySQL.query("SELECT * FROM Players WHERE Last-Name= '" + name + "'");
            
            if(resultSet.next()){
                return resultSet.getString("Last-Name") != null;
            }
		resultSet.close();
                return false;
        
        }catch(SQLException e){
            e.printStackTrace();
	}
                return false;
    }
    
    public Bro getPlayer(main plugin, String uuid) {
        return new Bro(plugin, uuid);
    }
    public Bro getPlayerByName(main plugin, String name) {
        String uuid = null;
        
        if (NameExists(name)) {
            try {
                
                ResultSet resultSet = mySQL.query("SELECT * FROM Players WHERE Last-Name = '" + name + "'");
                while (resultSet.next()) {
                    uuid = resultSet.getString("UUID");
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            
        }
        return new Bro(plugin, uuid); 
    }
    public void create() {
        Long firstonline = System.currentTimeMillis();
        Long lastonline = System.currentTimeMillis();
        
        if (!UUIDexists(uuid)) {
            mySQL.update("INSERT INTO Players (UUID, Last-Name, Group, Coins, First-Online, Last-Online, Ban-Points) VALUES ('" + uuid + "', '" + Bukkit.getPlayer(UUID.fromString(uuid)) + "', 'PLAYER', '100', '" + firstonline + "', '" + lastonline + "', '0'");
        }
    }
    public String getGroup() {
        String group = null;
        if (UUIDexists(uuid)) {
            try {
                ResultSet resultSet = mySQL.query("SELECT * FROM Players WHERE UUID= '" + uuid + "'");
                while (resultSet.next()) {
                    group = resultSet.getString("Group");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            create();
            getGroup();
        }
        return group;
    }
    public void setGroup(String Group){
        if (UUIDexists(uuid)) {
                mySQL.update("UPDATE Players SET Group='" + Group + "' WHERE UUID='" + uuid + "'");
        } else {
            create();
            setGroup(Group);
        }
            
    }
    public int getCoins() {
        int coins = 0;
        if (UUIDexists(uuid)) {
            try {
                ResultSet resultSet = mySQL.query("SELECT * FROM Players WHERE UUID='" + uuid + "'");
                while (resultSet.next()) {
                    coins = resultSet.getInt("Coins");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            create();
            getCoins();
        }
        return coins;
    }
    public void setCoins(int coins) {
        if (UUIDexists(uuid)) {
            mySQL.update("UPDATE Players SET Coins '" + coins + "' WHERE UUID='" + uuid + "'");
        } else {
            create();
            setCoins(coins);
        }
    }
    public int getBanPoints() {
        int banpoints = 0;
        if (UUIDexists(uuid)) {
            try {
                ResultSet resultSet = mySQL.query("SELECT * FROM Players WHERE UUID='" + uuid + "'");
                
                while(resultSet.next()) {
                    banpoints = resultSet.getInt("Ban-Points");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            create();
            getCoins();
        }
        return banpoints;
    }
    public void setBanPoints(int banpoints) {
        if (UUIDexists(uuid)) {
            mySQL.update("UPDATE Players SET Ban-Points '" + banpoints + "' WHERE UUID='" + uuid + "'");
        } else {
            create();
            setBanPoints(banpoints);
        }
    }
    public Long getLastOnline() {
        Long lastlogin = null;
        if (UUIDexists(uuid)) {
            try {
                ResultSet resultSet = mySQL.query("SELECT * FROM Players WHERE UUID='" + uuid + "'");
                
                while (resultSet.next()) {
                    lastlogin = resultSet.getLong("Last-Login");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            create();
            getLastOnline();
        }
        return lastlogin;
    }
    public void setLastOnline(Long time) {
        if (UUIDexists(uuid)) {
            mySQL.update("UPDATE Players SET Last-Login '" + time + "' WHERE UUID='" + uuid + "'");
        } else {
            create();
            setLastOnline(time);
        }
    }
    public Long getFirstOnline() {
        Long firstonline = null;
        if (UUIDexists(uuid)) {
            try {
                ResultSet resultSet = mySQL.query("SELECT * FROM Players WHERE UUID='" + uuid + "'");
                while (resultSet.next()) {
                    firstonline = resultSet.getLong("First-Online");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            create();
            getFirstOnline();
        }
        return firstonline;
    }
    
}
