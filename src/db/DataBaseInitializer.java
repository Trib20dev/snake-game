package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseInitializer {
	public static void init() {
		try {
			Connection con = DriverManager.getConnection("jdbc:sqlite:score.db");
			Statement stmt = con.createStatement();
			stmt.executeQuery("CREATE IF NOT EXISTS TABLE(id INTEGER, name VARCHAR(15), point INTEGER, PRIMARY KRY (id AUTONUMERIC))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
