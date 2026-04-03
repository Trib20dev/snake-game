package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseInitializer {
	public static void init() {
		try {
			File dir = new File("data");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			Connection con = DriverManager.getConnection("jdbc:sqlite:data/score.db");
			Statement stmt = con.createStatement();
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS scores(id INTEGER, name VARCHAR(15), points INTEGER, difficulty TEXT, PRIMARY KEY (id AUTOINCREMENT))");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
