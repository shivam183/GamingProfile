package application;

import java.sql.Date;

public class PlayerGame {
	private int id;
	private int playerId;
	private int gameId;
	private String gameTitle;
	private Date playingDate;
	private double score;

	public PlayerGame(int playerId, int gameId, String gameTitle, Date playingDate, double score) {
		this.playerId = playerId;
		this.gameId = gameId;
		this.gameTitle = gameTitle;
		this.playingDate = playingDate;
		this.score = score;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getGameId() {
		return gameId;
	}

	public String getGameTitle() {
		return gameTitle;
	}

	public Date getPlayingDate() {
		return playingDate;
	}

	public double getScore() {
		return score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
