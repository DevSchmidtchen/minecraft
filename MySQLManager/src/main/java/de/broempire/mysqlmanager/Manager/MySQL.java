/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.broempire.mysqlmanager.Manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Matti
 */
public class MySQL {
    
    private static String host;
    private static String username;
    private static String database;
    private static String password;
    
    public static Connection con;
    
    public MySQL(String host, String database, String username, String password) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        
        connect();
    }
    
    private static void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true", username, password);
            System.out.println("[SQLManager] Verbindung zu MySQL hergestellt!");
        } catch (SQLException e) {
            System.out.println("[SQLManager] Verbindung zu MySQL konnte nicht hergestellt werden! Fehler: " + e.getMessage());
        }
    }
    private void close() {
        try {
            if(con != null) {
                con.close();
                System.out.println("[MySQLManager] Verbindung zu MySQL beendet!");
            }
        } catch (SQLException e) {
            System.out.println("[MySQLManager] Fehler beim Beenden der Verbindung zur MySQL! Fehler: " + e.getMessage());
        }
    }
    public static boolean isConnected() {
        if (con != null) {
            return true;
        }
        return false;
    }
    public static void update(String qry) {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(qry);
            statement.close();
        } catch (SQLException e) {}
    }
    public static ResultSet query(String qry) {
        ResultSet rs = null;
        try {
            Statement statement = con.createStatement();
            statement.executeQuery(qry);
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
        return rs;
               
    }
    
}
