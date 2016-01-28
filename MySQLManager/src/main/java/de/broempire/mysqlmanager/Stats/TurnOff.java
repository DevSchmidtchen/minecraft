/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.broempire.mysqlmanager.Stats;

import de.broempire.mysqlmanager.Manager.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Matti
 */
public class TurnOff {
    
    private static MySQL mySQL;
    
    public static boolean playerExists(String uuid){
		
	try{
		ResultSet rs = mySQL.query("SELECT * FROM TurnOffStats WHERE UUID= '" + uuid + "'");
		
		if(rs.next()){
			return rs.getString("UUID") != null;
		}
		rs.close();
		  return false;
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public static void createPlayer(String uuid){
		
		if(!(playerExists(uuid))){
			MySQL.update("INSERT INTO TurnOffStats(UUID, Played, Wins, Points, Kills, Deaths, disabledLevers) VALUES ('" + uuid + "', '0', '0', '100', '0', '0', '0');");
		}
	}
	

	
	public static Integer getWins(String uuid){
		Integer i = 0;
		
		if(playerExists(uuid)){
			
			
			
			try{
				ResultSet rs = MySQL.query("SELECT * FROM TurnOffStats WHERE UUID= '" + uuid + "'");
	
				if((!rs.next()) || (Integer.valueOf(rs.getInt("Wins")) == null));
				
				i = rs.getInt("Wins");
				
				}catch(SQLException e){
					e.printStackTrace();
				}
				
		}else{
			createPlayer(uuid);
			getWins(uuid);
		}
	
	return i;
	
	}
	
	public static Integer getDisabled(String uuid){
		Integer i = 0;
		
		if(playerExists(uuid)){
			
			
			
			try{
				ResultSet rs = mySQL.query("SELECT * FROM TurnOffStats WHERE UUID= '" + uuid + "'");
	
				if((!rs.next()) || (Integer.valueOf(rs.getInt("disabledLevers")) == null));
				
				i = rs.getInt("disabledLevers");
				
				}catch(SQLException e){
					e.printStackTrace();
				}
				
		}else{
			createPlayer(uuid);
			getWins(uuid);
		}
	
	return i;
	
	}
	
	public static Integer getKills(String uuid){
		Integer i = 0;
		
		if(playerExists(uuid)){
			
			
			
			try{
				ResultSet rs = mySQL.query("SELECT * FROM TurnOffStats WHERE UUID= '" + uuid + "'");
	
				if((!rs.next()) || (Integer.valueOf(rs.getInt("Kills")) == null));
				
				i = rs.getInt("Kills");
				
				}catch(SQLException e){
					e.printStackTrace();
				}
				
		}else{
			createPlayer(uuid);
			getWins(uuid);
		}
	
	return i;
	
	}
	
	public static Integer getDeaths(String uuid){
		Integer i = 0;
		
		if(playerExists(uuid)){
			
			
			
			try{
				ResultSet rs = mySQL.query("SELECT * FROM TurnOffStats WHERE UUID= '" + uuid + "'");
	
				if((!rs.next()) || (Integer.valueOf(rs.getInt("Deaths")) == null));
				
				i = rs.getInt("Deaths");
				
				}catch(SQLException e){
					e.printStackTrace();
				}
				
		}else{
			createPlayer(uuid);
			getWins(uuid);
		}
	
	return i;
	
	}
	
	public static void setWins(String uuid, Integer kills){
		if(playerExists(uuid)){
			mySQL.update("UPDATE TurnOffStats SET Wins= '" + kills + "' WHERE UUID= '" + uuid + "';");
		}else{
			createPlayer(uuid);
			setWins(uuid, kills);
		}
	}
	

	public static void addWins(String uuid, Integer kills){
		if(playerExists(uuid)){
			setWins(uuid, Integer.valueOf(getWins(uuid).intValue() + kills.intValue()));
		}else{
			createPlayer(uuid);
			addWins(uuid, kills);
		}
	}
	
	public static Integer getPlayed(String uuid){
		Integer i = 0;
		
		if(playerExists(uuid)){
			
			
			
			try{
				ResultSet rs = mySQL.query("SELECT * FROM TurnOffStats WHERE UUID= '" + uuid + "'");
	
				if((!rs.next()) || (Integer.valueOf(rs.getInt("Played")) == null));
				
				i = rs.getInt("Played");
				
				}catch(SQLException e){
					e.printStackTrace();
				}
				
		}else{
			createPlayer(uuid);
			getWins(uuid);
		}
	
	return i;
	
	}
	
	public static void setPlayed(String uuid, Integer kills){
		if(playerExists(uuid)){
			mySQL.update("UPDATE TurnOffStats SET Played= '" + kills + "' WHERE UUID= '" + uuid + "';");
		}else{
			createPlayer(uuid);
			setPlayed(uuid, kills);
		}
	}
	

	public static void addPlayed(String uuid, Integer kills){
		if(playerExists(uuid)){
			setPlayed(uuid, Integer.valueOf(getPlayed(uuid).intValue() + kills.intValue()));
		}else{
			createPlayer(uuid);
			addPlayed(uuid, kills);
		}
	}
	
	public static Integer getPoints(String uuid){
		Integer i = 0;
		
		if(playerExists(uuid)){
			
			
			
			try{
				ResultSet rs = mySQL.query("SELECT * FROM TurnOffStats WHERE UUID= '" + uuid + "'");
	
				if((!rs.next()) || (Integer.valueOf(rs.getInt("Points")) == null));
				
				i = rs.getInt("Points");
				
				}catch(SQLException e){
					e.printStackTrace();
				}
				
		}else{
			createPlayer(uuid);
			getWins(uuid);
		}
	
	return i;
	
	}
	
	public static void setPoints(String uuid, Integer kills){
		if(playerExists(uuid)){
			mySQL.update("UPDATE TurnOffStats SET Points= '" + kills + "' WHERE UUID= '" + uuid + "';");
		}else{
			createPlayer(uuid);
			setPoints(uuid, kills);
		}
	}
	

	public static void addPoints(String uuid, Integer kills){
		if(playerExists(uuid)){
			setPoints(uuid, Integer.valueOf(getPoints(uuid).intValue() + kills.intValue()));
		}else{
			createPlayer(uuid);
			addPoints(uuid, kills);
		}
	}
	
	
	
	public static void setKills(String uuid, Integer kills){
		if(playerExists(uuid)){
			mySQL.update("UPDATE TurnOffStats SET Kills= '" + kills + "' WHERE UUID= '" + uuid + "';");
		}else{
			createPlayer(uuid);
			setKills(uuid, kills);
		}
	}
	

	public static void addKills(String uuid, Integer kills){
		if(playerExists(uuid)){
			setKills(uuid, Integer.valueOf(getKills(uuid).intValue() + kills.intValue()));
		}else{
			createPlayer(uuid);
			addKills(uuid, kills);
		}
	}
	
	public static void setDeaths(String uuid, Integer kills){
		if(playerExists(uuid)){
			mySQL.update("UPDATE TurnOffStats SET Deaths= '" + kills + "' WHERE UUID= '" + uuid + "';");
		}else{
			createPlayer(uuid);
			setDeaths(uuid, kills);
		}
	}
	

	public static void addDeaths(String uuid, Integer kills){
		if(playerExists(uuid)){
			setDeaths(uuid, Integer.valueOf(getDeaths(uuid).intValue() + kills.intValue()));
		}else{
			createPlayer(uuid);
			addDeaths(uuid, kills);
		}
	}
	
	public static void setDisabled(String uuid, Integer kills){
		if(playerExists(uuid)){
			mySQL.update("UPDATE TurnOffStats SET disabledLevers= '" + kills + "' WHERE UUID= '" + uuid + "';");
		}else{
			createPlayer(uuid);
			setDisabled(uuid, kills);
		}
	}
	

	public static void addZerstört(String uuid, Integer kills){
		if(playerExists(uuid)){
			setDisabled(uuid, Integer.valueOf(getDisabled(uuid).intValue() + kills.intValue()));
		}else{
			createPlayer(uuid);
			addZerstört(uuid, kills);
		}
	}
    
}
