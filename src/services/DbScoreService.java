package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.Difficulty;
import models.Player;

public class DbScoreService {
	private Connection con;
	
	public DbScoreService() {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:src/data/score.db");
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
	
	public void saveOrUpdate(Player player, Difficulty dif) {
		PreparedStatement insertStmt = null;
		PreparedStatement updateStmt = null;
		PreparedStatement getPointsStmt = null;
		try {
			insertStmt = con.prepareStatement("INSERT INTO scores(name,points) VALUES (?, ?, ?)");
			getPointsStmt = con.prepareStatement("SELECT points FROM scores WHERE name = ? AND difficulty = ?");
			updateStmt = con.prepareStatement("UPDATE scores SET points = ? WHERE name = ? AND difficulty = ?");
			
			getPointsStmt.setString(1, player.nombre);
			getPointsStmt.setString(2, dif.toString());
			
			if(getPointsStmt.executeQuery().next()) {
				ResultSet rs = getPointsStmt.executeQuery();
				rs.next();
				int p = rs.getInt("points");
				updateStmt.setInt(1, p);
				updateStmt.setString(2, player.nombre);
				updateStmt.setString(3, dif.toString());
				if(player.puntuacion > p) {
					updateStmt.execute();
				}
			} else {
				insertStmt.setString(1, player.nombre);
				insertStmt.setInt(2, player.puntuacion);
				insertStmt.setString(3, dif.toString());
				insertStmt.execute();
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Player[] getTop5Ranked(Difficulty dif) { //TODO work with difficulty
		Statement getTop;
		Player players[] = new Player[5];
		int c = 0;
		try {
			getTop = con.createStatement();
			ResultSet rs = getTop.executeQuery("SELECT name,points FROM scores ORDER BY points DESC LIMIT 5");
			while(rs.next())
				players[c++] = new Player(rs.getString("name"), rs.getInt("points"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return players;
	}
	
	
	
	
	
	
	
	
	
}
