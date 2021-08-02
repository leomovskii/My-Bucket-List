package ua.leomovskii.tg.mybucketlist.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ua.leomovskii.tg.mybucketlist.Logger;

public class UserHandler {

	private String db_url, db_user, db_pass;

	private ArrayList<User> user_list;

	public UserHandler() {
		db_url = System.getenv("DB_URL");
		db_user = System.getenv("DB_USERNAME");
		db_pass = System.getenv("DB_PASSWORD");

		this.user_list = getUserList();

		Logger.info(String.format("Loaded %d User records.", user_list.size()));
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(db_url, db_user, db_pass);
	}

	private ArrayList<User> getUserList() {
		String query = "SELECT * FROM `bucketlist`.`userlist`;";

		ArrayList<User> list = new ArrayList<>();

		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			ResultSet rs = s.executeQuery(query);
			while (rs.next())
				list.add(new User(rs.getLong("ID"), rs.getString("NAME"), rs.getInt("INTENT"), rs.getString("LANG"),
						rs.getInt("DATA")));

		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}

		return list;
	}

	private User getUser(long userId) {
		for (User b : user_list)
			if (b.id == userId)
				return b;
		return null;
	}

	public boolean hasUser(long userId) {
		for (User b : user_list)
			if (b.id == userId)
				return true;
		return false;
	}

	public void addUser(long userId, String name, String lang) {
		String query = "INSERT INTO `bucketlist`.`userlist` (`ID`, `NAME`, `LANG`) VALUES ('%d', '%s', '%s');";

		User user = new User(userId, name, lang);

		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			s.execute(String.format(query, user.id, user.name, user.lang));

			this.user_list.add(user);
		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}
	}

	public void setUserName(long userId, String name) {
		String query = "UPDATE `bucketlist`.`userlist` SET `NAME` = '%s' WHERE (`ID` = '%d');";

		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			s.execute(String.format(query, name, userId));

			getUser(userId).name = name;
		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}
	}

	public String getUserName(long userId) {
		return new String(getUser(userId).name);
	}

	public void setUserIntent(long userId, Intent intent) {
		String query = "UPDATE `bucketlist`.`userlist` SET `INTENT` = '%d' WHERE (`ID` = '%d');";

		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			s.execute(String.format(query, Intent.toInt(intent), userId));

			getUser(userId).intent = Intent.toInt(intent);
		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}
	}

	public Intent getUserIntent(long userId) {
		return Intent.fromInt(getUser(userId).intent);
	}

	public void setUserLang(long userId, String lang) {
		String query = "UPDATE `bucketlist`.`userlist` SET `LANG` = '%s' WHERE (`ID` = '%d');";

		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			s.execute(String.format(query, lang, userId));

			getUser(userId).lang = lang;
		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}
	}

	public String getUserLang(long userId) {
		return new String(getUser(userId).lang);
	}

	public void setUserData(long userId, int data) {
		String query = "UPDATE `bucketlist`.`userlist` SET `DATA` = '%d' WHERE (`ID` = '%d');";

		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			s.execute(String.format(query, data, userId));

			getUser(userId).data = data;
		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}
	}

	public int getUserData(long userId) {
		return getUser(userId).data;
	}

	public void removeUser(long userId) {
		String query = "DELETE FROM `bucketlist`.`userlist` WHERE (`ID` = '%d');";

		User user = getUser(userId);

		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			s.execute(String.format(query, userId));

			this.user_list.remove(user);
		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}
	}

	private class User {
		long id;
		String name, lang;
		int intent;
		int data;

		User(long id, String name, int intent, String lang, int data) {
			this.id = id;
			this.name = name;
			this.intent = intent;
			this.lang = lang;
			this.data = data;
		}

		User(long id, String name, String lang) {
			this(id, name, 0, lang, 0);
		}

	}

}