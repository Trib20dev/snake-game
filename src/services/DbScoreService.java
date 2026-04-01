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
	
	public int getPoints(String name){
		try {
			PreparedStatement prpStmnt = con.prepareStatement("SELECT name, points FROM scores WHERE name = ?");
			prpStmnt.setString(1, name);
			ResultSet rs = prpStmnt.executeQuery();
			if(!rs.next()) return 0;
			return rs.getInt("points");
		
		} catch (SQLException e) {
			e.printStackTrace();;
		}
	}
	
	public void saveOrUpdate() {
		
	}
	
	
	
	
	
	
	
}
