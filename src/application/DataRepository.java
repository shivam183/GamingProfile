package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataRepository {
	private String dbUrl = "jdbc:oracle:thin:COMP228_W18_el_2/password@199.212.26.208:1521:SQLD";

	public DataRepository() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (Exception e) {

		}
	}

	public ArrayList<Game> SelectGame(Integer filterId) throws SQLException {
		Connection conn = null;
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<Game> list = new ArrayList<Game>();

		if (filterId == null || filterId <= 0)
			return list;

		try {
			conn = DriverManager.getConnection(dbUrl);

			String sql = "SELECT GAME_ID, GAME_TITLE FROM JAVA_GAME WHERE GAME_ID = ?";

			statement = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			statement.setInt(1, filterId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Game obj = new Game(resultSet.getString(2));
				obj.setId(resultSet.getInt(1));
				list.add(obj);
			}

		} finally {
			if (conn != null)
				conn.close();
		}

		return list;
	}

	public ArrayList<Game> SelectGame() throws SQLException {
		Connection conn = null;
		Statement statement;
		ResultSet resultSet;
		ArrayList<Game> list = new ArrayList<Game>();

		try {
			conn = DriverManager.getConnection(dbUrl);

			String sql = "SELECT GAME_ID, GAME_TITLE FROM JAVA_GAME";

			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				Game obj = new Game(resultSet.getString(2));
				obj.setId(resultSet.getInt(1));
				list.add(obj);
			}

		} finally {
			if (conn != null)
				conn.close();
		}

		return list;
	}

	public void InsertGame(Game gObj) throws SQLException {
		// If the game object has an assigned Id, it means that it is already
		// on the database
		if (gObj.getId() > 0)
			return;

		Connection conn = null;
		PreparedStatement statement;

		try {
			conn = DriverManager.getConnection(dbUrl);

			String sql = "INSERT INTO JAVA_GAME (GAME_TITLE) VALUES (?)";

			statement = conn.prepareStatement(sql, new String[] { "GAME_ID" });

			statement.setString(1, gObj.getTitle());

			statement.executeUpdate();

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					gObj.setId(generatedKeys.getInt(1));
				}
			}
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public Player SelectPlayer(Integer filterId) throws SQLException {
		Connection conn = null;
		PreparedStatement statement;
		ResultSet pSet;

		if (filterId == null || filterId <= 0)
			return null;

		try {
			conn = DriverManager.getConnection(dbUrl);

			String sql = "SELECT PLAYER_ID, FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER "
					+ "FROM JAVA_PLAYER WHERE PLAYER_ID = ?";

			statement = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			statement.setInt(1,filterId);

			pSet = statement.executeQuery();

			while (pSet.next()) {
				Player pObj = new Player(pSet.getString(2), pSet.getString(3), pSet.getString(4), pSet.getString(5),
						pSet.getString(6), pSet.getString(7));
				pObj.setId(pSet.getInt(1));

				String pgSql = "SELECT PG.PLAYER_GAME_ID, PG.GAME_ID, G.GAME_TITLE, PG.PLAYING_DATE, PG.SCORE "
						+ "FROM JAVA_PLAYER_GAME PG JOIN JAVA_GAME G ON PG.GAME_ID = G.GAME_ID WHERE PG.PLAYER_ID = ?";
				PreparedStatement pgStatement = conn.prepareStatement(pgSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				pgStatement.setInt(1, filterId);

				ResultSet pgSet = pgStatement.executeQuery();
				ArrayList<PlayerGame> pgList = new ArrayList<PlayerGame>();

				while (pgSet.next()) {
					PlayerGame pgObj = new PlayerGame(pObj.getId(), pgSet.getInt(2), pgSet.getString(3),
							pgSet.getDate(4), pgSet.getDouble(5));
					pgObj.setId(pgSet.getInt(1));
					pgList.add(pgObj);
				}

				pObj.setPlayedGames(pgList);

				return pObj;
			}

		} finally {
			if (conn != null)
				conn.close();
		}

		return null;
	}

	public ArrayList<Player> SelectPlayer() throws SQLException {

		Connection conn = null;
		Statement statement;
		ArrayList<Player> list = new ArrayList<Player>();

		try {
			conn = DriverManager.getConnection(dbUrl);

			String sql = "SELECT PLAYER_ID, FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER "
					+ "FROM JAVA_PLAYER";

			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet pSet = statement.executeQuery(sql);

			while (pSet.next()) {
				Player pObj = new Player(pSet.getString(2), pSet.getString(3), pSet.getString(4), pSet.getString(5),
						pSet.getString(6), pSet.getString(7));
				pObj.setId(pSet.getInt(1));

				String pgSql = "SELECT PG.PLAYER_GAME_ID, PG.GAME_ID, G.GAME_TITLE, PG.PLAYING_DATE, PG.SCORE "
						+ "FROM JAVA_PLAYER_GAME PG JOIN JAVA_GAME G ON PG.GAME_ID = G.GAME_ID WHERE PG.PLAYER_ID = ?";
				PreparedStatement pgStatement = conn.prepareStatement(pgSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				pgStatement.setInt(1, pObj.getId());

				ResultSet pgSet = pgStatement.executeQuery();
				ArrayList<PlayerGame> pgList = new ArrayList<PlayerGame>();

				while (pgSet.next()) {
					PlayerGame pgObj = new PlayerGame(pObj.getId(), pgSet.getInt(2), pgSet.getString(3),
							pgSet.getDate(4), pgSet.getDouble(5));
					pgObj.setId(pgSet.getInt(1));
					pgList.add(pgObj);
				}

				pObj.setPlayedGames(pgList);

				list.add(pObj);
			}

		} finally {
			if (conn != null)
				conn.close();
		}

		return list;
	}

	public void InsertPlayer(Player p) throws SQLException {
		// If the player object has an assigned Id, it means that it is already
		// on the database
		if (p.getId() > 0)
			return;

		Connection conn = null;
		PreparedStatement statement;

		try {
			conn = DriverManager.getConnection(dbUrl);

			String sql = "INSERT INTO JAVA_PLAYER (FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER)"
					+ " VALUES (?, ?, ?, ?, ?, ?)";

			statement = conn.prepareStatement(sql, new String[] { "PLAYER_ID" });

			statement.setString(1, p.getFirstName());
			statement.setString(2, p.getLastName());
			statement.setString(3, p.getAddress());
			statement.setString(4, p.getPostalCode());
			statement.setString(5, p.getProvince());
			statement.setString(6, p.getPhoneNumber());

			statement.executeUpdate();

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					p.setId(generatedKeys.getInt(1));
				}
			}

			if (p.getId() > 0) {

				statement = conn.prepareStatement(
						"INSERT INTO JAVA_PLAYER_GAME (GAME_ID, PLAYER_ID, PLAYING_DATE, SCORE) VALUES (?, ?, ?, ?)");

				for (PlayerGame pgObj : p.getPlayedGames()) {
					statement.setInt(1, pgObj.getGameId());
					statement.setInt(2, p.getId());
					statement.setDate(3, pgObj.getPlayingDate());
					statement.setDouble(4, pgObj.getScore());
					statement.addBatch();
				}

				statement.executeBatch();
			}

		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void UpdatePlayer(Player p, boolean updatePlayedGames) throws SQLException {
		// If the player object has not an assigned Id, it means that it is not
		// on the database
		if (p.getId() <= 0)
			return;

		Connection conn = null;
		PreparedStatement statement;

		try {
			conn = DriverManager.getConnection(dbUrl);

			String sql = "UPDATE JAVA_PLAYER SET "
					+ "FIRST_NAME = ?, LAST_NAME = ?, ADDRESS = ?, POSTAL_CODE =?, PROVINCE = ?, PHONE_NUMBER = ?"
					+ " WHERE PLAYER_ID = ?";

			statement = conn.prepareStatement(sql);

			statement.setString(1, p.getFirstName());
			statement.setString(2, p.getLastName());
			statement.setString(3, p.getAddress());
			statement.setString(4, p.getPostalCode());
			statement.setString(5, p.getProvince());
			statement.setString(6, p.getPhoneNumber());
			statement.setInt(7, p.getId());

			int affectedRows = statement.executeUpdate();

			if (affectedRows <= 0) {
				throw new SQLException("Could not update player information.");
			}

			if (updatePlayedGames) {
				statement = conn.prepareStatement("DELETE FROM JAVA_PLAYER_GAME WHERE PLAYER_ID = ?");
				statement.setInt(1, p.getId());
				statement.executeUpdate();

				statement = conn.prepareStatement(
						"INSERT INTO JAVA_PLAYER_GAME (GAME_ID, PLAYER_ID, PLAYING_DATE, SCORE) VALUES (?, ?, ?, ?)");

				for (PlayerGame pgObj : p.getPlayedGames()) {
					pgObj.setPlayerId(p.getId());
					statement.setInt(1, pgObj.getGameId());
					statement.setInt(2, p.getId());
					statement.setDate(3, pgObj.getPlayingDate());
					statement.setDouble(4, pgObj.getScore());
					statement.addBatch();
				}

				statement.executeBatch();
			}
		} finally

		{
			if (conn != null)
				conn.close();
		}
	}

	public void DeletePlayerGame(PlayerGame pgObj) throws SQLException {
		// If the object has not an assigned Id, it means that it is not
		// on the database
		if (pgObj.getId() <= 0)
			return;

		Connection conn = null;
		PreparedStatement statement;

		try {
			conn = DriverManager.getConnection(dbUrl);

			String sql = "DELETE FROM JAVA_PLAYER_GAME WHERE PLAYER_GAME_ID = ?";

			statement = conn.prepareStatement(sql);

			statement.setInt(1, pgObj.getId());

			int affectedRows = statement.executeUpdate();

			if (affectedRows <= 0) {
				throw new SQLException("Could not delete PlayerGame information.");
			}
		} finally

		{
			if (conn != null)
				conn.close();
		}
	}

}
