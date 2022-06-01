package application;

public class Person {
	private String name;	
	private String[] FriendShip;
	
	public Person() {
		
	}
	
	public Person(String name, String[] FriendShip) {
		this.name = name;
		this.FriendShip = FriendShip;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String[] getFriendShip() {
		return this.FriendShip;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setFriendShip(String[] FriendShip) {
		this.FriendShip = FriendShip;
	}
}
