package ua.leomovskii.tg.mybucketlist.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import ua.leomovskii.tg.mybucketlist.Logger;

public class WishHandler {

	private String db_url, db_user, db_pass;

	private ArrayList<Wish> wish_list;

	public WishHandler() {
		db_url = System.getenv("DB_URL");
		db_user = System.getenv("DB_USERNAME");
		db_pass = System.getenv("DB_PASSWORD");

		this.wish_list = getWishList();

		Logger.info(String.format("Loaded %d Wish records.", wish_list.size()));
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(db_url, db_user, db_pass);
	}

	public ArrayList<Wish> getWishList() {
		String query = "SELECT * FROM `bucketlist`.`wishlist`;";

		ArrayList<Wish> list = new ArrayList<>();

		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			ResultSet rs = s.executeQuery(query);
			while (rs.next())
				list.add(new Wish(rs.getInt("INDEX"), rs.getLong("OWNER_ID"), rs.getString("TITLE"),
						rs.getInt("CHECKED") == 1));

		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}
		return list;
	}

	private Wish getWish(int index) {
		for (Wish w : wish_list)
			if (w.index == index)
				return w;
		return null;
	}

	public void addWish(long ownerId, String title) {
		String query1 = "INSERT INTO `bucketlist`.`wishlist` (`OWNER`, `TITLE`) VALUES ('%d', '%s');";
		String query2 = "SELECT MAX(`ID`) FROM `bucketlist`.`wishlist`;";

		try (Connection c = getConnection();
				Statement s = c.createStatement();
				PreparedStatement ps = getConnection().prepareStatement(query2)) {

			s.execute(String.format(query1, ownerId, title, 0));

			ResultSet rs = ps.executeQuery();
			rs.next();

			this.wish_list.add(new Wish(rs.getInt(1), ownerId, title));

		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}
	}

	public void setWishTitle(int index, String title) {
		String query = "UPDATE `bucketlist`.`wishlist` SET `TITLE` = '%s' WHERE (`INDEX` = '%d');";

		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			s.execute(String.format(query, title, index));

			getWish(index).title = title;
		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}
	}

	public String getWishTitle(int index) {
		return new String(getWish(index).title);
	}

	public void setWishChecked(int index, boolean checked) {
		String query = "UPDATE `bucketlist`.`wishlist` SET `CHECKED` = '%d' WHERE (`INDEX` = '%d');";

		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			s.execute(String.format(query, checked ? 1 : 0, index));

			getWish(index).checked = checked;
		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}
	}

	public boolean isWishChecked(int index) {
		return getWish(index).checked;
	}

	public void removeWish(int index) {
		Wish wish = getWish(index);

		try (Connection c = getConnection(); Statement s = c.createStatement()) {
			String query = "DELETE FROM `bucketlist`.`wishlist` WHERE (`INDEX` = '%d');";

			s.execute(String.format(query, index));

			this.wish_list.remove(wish);
		} catch (SQLException e) {
			Logger.error(e.getLocalizedMessage());
		}
	}

	public HashMap<Integer, String> getWishesOf(long userId) {
		HashMap<Integer, String> map = new HashMap<>();

		for (Wish w : wish_list)
			if (w.ownerId == userId)
				map.put(w.index, w.title);

		return map;
	}

	private class Wish {

		int index;
		long ownerId;
		String title;
		boolean checked;

		Wish(int index, long ownerId, String title, boolean checked) {
			this.index = index;
			this.ownerId = ownerId;
			this.title = title;
			this.checked = checked;
		}

		Wish(int index, long ownerId, String title) {
			this(index, ownerId, title, false);
		}

	}

}