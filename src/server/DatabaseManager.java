package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {


	private static String url = "jdbc:sqlite:yahtzee.db";

	private static DatabaseManager databaseManager;
	private Connection connection = null;

	public static DatabaseManager getInstance() {
		if (databaseManager == null) {
			databaseManager = new DatabaseManager();
		}
		return databaseManager;
	}

	private DatabaseManager() {
	}

	public Connection connect() {
		// SQLite connection string
		try {

			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(url);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate(
					"create table if not exists game (ID integer primary key, saveName string, date string, data string)");
			statement.close();
			System.out.println("Database connected");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);

		}
		return connection;
	}

	public void disconnect() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public int saveGame(String saveName, String date, String data) {
		try {
			System.out.println("saveGame  " + saveName + " " + date + " " + data);
			Statement statement = connection.createStatement();
			String sql = "INSERT INTO game (saveName,date,data) " + "VALUES ('" + saveName + "', '" + date + "','"
					+ data + "');";
			statement.executeUpdate(sql);
			statement.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		return 1;
	}

	public ArrayList<Record> getGameInformation(String saveName) {
		ArrayList<Record> list = new ArrayList();
		String sql = "SELECT * FROM game WHERE saveName=";
		sql = sql + "'" + saveName + "'";

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			ResultSetMetaData md = resultSet.getMetaData();
			int columnCount = md.getColumnCount();
			while (resultSet.next()) {
				Record record = new Record(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
				list.add(record);
			}
			statement.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		return list;
	}

	public String test() {
		String sql = "SELECT * FROM game";
		;

		StringBuilder sb = new StringBuilder();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			ResultSetMetaData md = resultSet.getMetaData();
			int columnCount = md.getColumnCount();
			for (int i = 1; i <= columnCount; i++)
				System.out.println(md.getColumnName(i));
			while (resultSet.next()) {

				for (int i = 1; i <= columnCount; i++) {
					System.out.println(resultSet.getString(i));
				}
				sb.append("\n");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		return sb.toString();
	}

	public int XsaveGame(String saveName, String date, String data) {
		String sql = "INSERT INTO game('saveName','date','data') values(?,?,?)";
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, saveName);
			ps.setString(2, date);
			ps.setString(3, data);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

	public static void main(String[] args) {
		DatabaseManager manager = getInstance();
		manager.connect();
		manager.test();
		// String data = "ss";
		// manager.saveGame("tom", "2020-12-12", data);
		// ArrayList<Record> list = manager.getGameInformation("tom");
		// System.out.println(list);
		manager.disconnect();
	}
}
