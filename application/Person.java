/**
 * Filename: Person.java 
 * Project: Social Network 
 * Authors: ateam120
 * 
 */
package application;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;    
    private ArrayList<String> FriendShip;
    boolean visited;
    Person predecessor;
    int weight;
    int totalWeight;
    List<Person> list_of_user_friends; 
    
    /**
     * Initializes a person with given name
     * 
     * @param name person's name
     */
    public Person(String name) {
        this.name = name;
        this.weight = 1; // each person has a weight of 1
        this.totalWeight = 100; // each has a total weight of 100 to start
        this.predecessor = null;
        FriendShip = new ArrayList<String>();
        list_of_user_friends = new ArrayList<Person>();
    }
    
    /*
     * Default no-argument constructor
     */
    public Person() {
	   
    }
    /**
     * Initializes a person with given name and friendship
     * 
     * @param name person's name
     * @param FriendShip person's friendship
     */
    public Person(String name, ArrayList<String> FriendShip) {
        this.name = name;
        this.FriendShip = FriendShip;
        list_of_user_friends = new ArrayList<Person>();
    }
    
    /**
     * Returns the name of the person
     * 
     * @return the name of the person
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the person's friendship
     * 
     * @return person's friendship
     */
    public ArrayList<String> getFriendShip() {
        return this.FriendShip;
    }
    
    /**
     * Sets the person's name
     * 
     * @param name name of the person
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Adds a new friendship for given person
     * 
     * @param friend name of a friend
     * @param person5 person to add new friend
     */
    public void addFriendShip(String friend, Person person5) {
         FriendShip.add(friend);
         list_of_user_friends.add(person5);
    }  
    
    /**
     * Returns the list of person's friends
     * 
     * @return the list of person's friends
     */
    public List<Person> getListOfUsersFriends() {
        return this.list_of_user_friends;
    }
}
