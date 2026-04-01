package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Player;

public class DbScoreService {
	private Connection con;
	
	public DbScoreService() {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:/src/data/scores.db");
		} catch (SQLException e) {
			e.printStackTrace(); //No deberia poder llegar a aca
		}
		
	}
	
	public Player getPLayer(String name) {
	String playerName = "";
	int points = 0;
		try {
			PreparedStatement prpStmnt = con.prepareStatement("SELECT name, points FROM scores WHERE name = ?");
			prpStmnt.setString(1, name);
			ResultSet rs = prpStmnt.executeQuery();
			if(!rs.next()) return null;
			playerName = rs.getString("name");
			points = rs.getInt("points");
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Player(playerName, points);
	}
	
	
	
}
