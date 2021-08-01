package ua.leomovskii.tg.mybucketlist.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private String db_url, db_user, db_pass;

	public Database() {
		db_url = System.getenv("DB_URL");
		db_user = System.getenv("DB_USERNAME");
		db_pass = System.getenv("DB_PASSWORD");

	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(db_url, db_user, db_pass);
	}

	// TODO Database -> get user

	// TODO Database -> add user

	// TODO Database -> edit user

	// TODO Database -> delete user

	// TODO Database -> get wish

	// TODO Database -> wish list of

	// TODO Database -> edit wish

	// TODO Database -> delete wish

}