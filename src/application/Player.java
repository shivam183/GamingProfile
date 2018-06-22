package application;

import java.util.ArrayList;

public class Player {
	private int id = 0;
	private String firstName;
	private String lastName;
	private String address;
	private String postalCode;
	private String province;
	private String phoneNumber;
	private ArrayList<PlayerGame> playedGames;


	public Player(String firstName, String lastName, String address, String postalCode, String province,
			String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postalCode = postalCode;
		this.province = province;
		this.phoneNumber = phoneNumber;
		this.playedGames = new ArrayList<PlayerGame>();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<PlayerGame> getPlayedGames() {
		return playedGames;
	}

	public void setPlayedGames(ArrayList<PlayerGame> playedGames) {
		this.playedGames = playedGames;
	}

	public String toString(){
		return String.format("%s %s", getFirstName(), getLastName());
	}

}
