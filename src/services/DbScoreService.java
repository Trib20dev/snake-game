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
		return 0;
	}
	
	public void saveOrUpdate(Player player) {
		PreparedStatement insertStmt = null;
		PreparedStatement updateStmt = null;
		PreparedStatement getPointsStmt = null;
		try {
			insertStmt = con.prepareStatement("INSERT INTO scores VALUES (null, ?, ?)");
			getPointsStmt = con.prepareStatement("SELECT points FROM scores WHERE name = ?");
			updateStmt = con.prepareStatement("UPDATE scores SET points = ? WHERE name = ? ");
			
			getPointsStmt.setString(1, player.nombre);
			
			if(getPointsStmt.executeQuery().next()) {
				ResultSet rs = getPointsStmt.executeQuery();
				rs.next();
				int p = rs.getInt("points");
				updateStmt.setInt(1, p);
				updateStmt.setString(2, player.nombre);
				if(player.puntuacion > p) {
					updateStmt.execute();
				}
			} else {
				insertStmt.setString(1, player.nombre);
				insertStmt.setInt(1, player.puntuacion);
				insertStmt.execute();
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
}
