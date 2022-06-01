package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * This class defines the operation of a social network
 * 
 * @author ATEAM 120
 */

public class SocialNetwork implements SocialNetworkADT {

  ArrayList<String> users;
  private Graph graph;
  private List<String> tempStore;
  private ArrayList<Person> personList;
  private String centralUser;
  private Queue<Person> queue;
  private int numFriendship;

  /**
   * Constructor that creates the graph instance and complete initialization of the variables
   * defined above.
   * 
   */
  public SocialNetwork() {

    graph = new Graph();
    recordOperations = new ArrayList<String>();
    personList = new ArrayList<Person>();
    users = new ArrayList<String>();
    centralUser = null;
    numFriendship = graph.size();

  }

  // record every operation made by user
  public ArrayList<String> recordOperations;

  /**
   * This method return a string containing the status of a social network
   * 
   * @return info, info of a social network containing the number of users, number of friendships,
   *         and number of groups.
   * 
   */
  public String updateInfo() {
    String info = "User: " + graph.order() + ", Friendship: " + graph.size()
        + ", Connected Components: " + getConnectedComponents();
    return info;
  }

  /**
   * Returns a boolean value indicating if friendship is added successfully
   * 
   * @param person1 person2, names of users to be added
   * @return true if friendship is added successfully, false otherwise
   */
  @Override
  public boolean addFriends(String person1, String person2) {
    // first check if person1 and person2 are null, they should not
    // be null
    if (person1 == null || person2 == null) {
      return false;
    }
    // if person1 is not in the user arrayList, then add it
    if (!users.contains(person1)) {
      addUser(person1);
    }
    // same as above
    if (!users.contains(person2)) {
      addUser(person2);
    }
    // create new person objects
    Person personone = new Person();
    Person persontwo = new Person();

    // assigning names for the new person objects
    for (int i = 0; i < personList.size(); i++) {
      if (personList.get(i).getName().equals(person1)) {
        personone = personList.get(i);
      }
      if (personList.get(i).getName().equals(person2)) {
        persontwo = personList.get(i);
      }
    }
    personone.addFriendShip(person2, persontwo);
    persontwo.addFriendShip(person1, personone);

    // add the edge between the two persons
    graph.addEdge(person1, person2);
    // record the operation in the loog
    recordOperations.add("a " + person1 + " " + person2);
    return true;
  }

  /**
   * Returns a boolean value indicating if friendship is removed successfully
   * 
   * @param person1 person2, name of the users to be removed
   * @return true if friendship is removed successfully, false otherwise
   */
  @Override
  public boolean removeFriends(String person1, String person2) {
    // if person2 is not a friend of person1, removal failed
    if (!graph.getAdjacentVerticesOf(person1).contains(person2)) {
      return false;
    }
    // otherwise, remove edge between these two persons
    graph.removeEdge(person1, person2);
    recordOperations.add("r " + person1 + " " + person2);
    return true;
  }

  /**
   * Returns a boolean value indicating if user(vertex) is added successfully to the graph
   * 
   * @param person, name of user to be added
   * @return true if user is added successfully, false otherwise
   */
  @Override
  public boolean addUser(String person) {
    // person to be added should not be null
    if (person == null) {
      return false;
    }
    // is the user is already in the user list, return false
    if (users.contains(person)) {
      return false;
    }
    // create new person instance
    Person user = new Person(person);
    // add new user to the graph
    personList.add(user);
    users.add(person);
    graph.addVertex(person);
    recordOperations.add("a " + person);
    return true;
  }

  /**
   * Returns a boolean value indicating if user(vertex) is removed successfully from the graph
   * 
   * @param person, user to be removed from the graph
   * @return true if user is removed successfully, false otherwise
   */
  @Override
  public boolean removeUser(String person) {
    // if the user is not in the graph, return falsde
    if (!graph.getAllVertices().contains(person)) {
      return false;
    }
    // remove the user
    for(int i = 0; i < users.size(); i++) {
    	if(users.get(i).equals(person)) {
    		
    	personList.remove(i);
    	}
    }
    users.remove(person);
    graph.removeVertex(person);
    recordOperations.add("r " + person);
    return true;
  }

  /**
   * Returns a set containing all the friends of a certain user
   * 
   * @param person, user whom we want to get all friends from
   * @return a set containing Person type
   */
  @Override
  public Set<Person> getFriends(String person) {
    // store all the person's friends
    List<String> friendsList = graph.getAdjacentVerticesOf(person);
    // declare a new set
    Set<Person> friendSet = new HashSet<Person>();
    //
    for (String friend : friendsList) {
      // friendSet.add(friend);
    }
    return friendSet;
  }

  /**
   * Returns a set containing the mutual friend between two users
   * 
   * @param person1 person2, persons whom we need to find common friends
   * @return a set containing String type
   */
  @Override
  public Set<String> getMutualFriends(String person1, String person2) {
    // store all the friends of person1 and person2 into list
    // and compare the two lists to find common vertices
    List<String> friend1 = graph.getAdjacentVerticesOf(person1);
    System.out.println(friend1);
    List<String> friend2 = graph.getAdjacentVerticesOf(person2);
    System.out.println(friend2);
    // two lists should not be null
    if (friend1 == null || friend2 == null) {
      return null;
    }
    // declare a new set
    Set<String> mFriend = new HashSet<String>();
    // put common friends to the set
    for (int i = 0; i < friend1.size(); i++) {
      for (int j = 0; j < friend2.size(); j++) {
        if (friend1.get(i).equals(friend2.get(j))) {
          mFriend.add(friend1.get(i));
        }
      }
    }
    return mFriend;
  }

  /**
   * Returns a list containing the shortest path from person1 to person2 in the graph
   * 
   * @param person1 person2, find the shortest path between these two persons
   * @return a List containing the vertices visited on the shortest path
   */
  @Override
  public List<String> getShortestPath(String person1, String person2) {
	  tempStore = new ArrayList<String>();
    if (users.contains(person1) && users.contains(person2)) {
      // if the userList doesn't contain two users, the return null
    } else {
      return null;
    }
    // mark each vertices as unvisited first
    for (int i = 0; i < personList.size(); i++) {
      personList.get(i).visited = false;
    }
    // declare new person objects
    Person Person1 = personList.get(users.indexOf(person1));
    Person Person2 = personList.get(users.indexOf(person2));
    // use Dijkstra's algo to find the shortest path
    try {
      tempStore = findPathAlgorithm(Person1, Person2);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tempStore;
  }

  // private Person GetPerson(String person) {
  //
  // return graph.getPerson(person);
  // }

  /**
   * the Dijkstra's Algorithm to find shortest path
   * 
   * @param person1
   * @param person2
   * @return shortest path
   * @throws Exception
   */
  public List<String> findPathAlgorithm(Person person1, Person person2) throws Exception {

    if (person1 == null || person2 == null) {
      throw new NullPointerException("no input,please input person name");
    } else {

      tempStore = new ArrayList<String>(); // initializes list to store shortest path
      Person node = person1; // stores person1 to start
      person1.totalWeight = 0; // sets start total weight to 0

      queue = new LinkedList<Person>(); // initializes queue
      queue.add(person1); // adds person1 to queue

      // checks that person2 exists in graph, otherwise throws exception
      List<String> vertexList = graph.getVertexList();
      if (!vertexList.contains(person2.getName()))
        throw new Exception(person2 + " not exist");

      while (!queue.isEmpty()) {
        node = queue.remove(); // remove first item from queue
        node.visited = true; // item has now been visited

        // if the person2 has been reached break from loop
        if (node.getName().equals(person2.getName())) {
          break;
        }
        dhelper(node); // call helper
      }

      node = person2;

      // copies predecessors of node into list
      while (node.predecessor != null) {
        tempStore.add(node.getName());
        node = node.predecessor;
      }
      tempStore.add(person1.getName()); // adds person1 to store
      Collections.reverse(tempStore); // reverses list so it appears starting at person1, ending at
                                      // person2

      // if store doesn't contain person2 throw exception
      if (!tempStore.contains(person2.getName())) {
        throw new Exception("Can not find path.");
      }

      return tempStore;
    }
  }

  /**
   * Helper method for dijkstra's algorithm
   * 
   * @param person1
   */
  private void dhelper(Person person1) {

    Person node = null;
    int edgeDistance = -1;
    int newDistance = -1;

    // iterates through list of friends for given person
    for (int i = 0; i < person1.getListOfUsersFriends().size(); i++) {
      node = person1.getListOfUsersFriends().get(i);

      // if person has not been visited yet, check if total weight can be reduced
      if (!node.visited) {
        edgeDistance = node.weight;
        newDistance = person1.totalWeight + edgeDistance;

        // if weight can be reduced do it and add person to queue
        if (newDistance < node.totalWeight) {
          node.totalWeight = newDistance;
          node.predecessor = person1;
          queue.add(node);
        }
      }
    }
  }

  /**
   * Returns the number of un-connected groups in the network
   * 
   * @return a int of number of un-connected group
   */
  @Override
  public int getConnectedComponents() {
    int groupNum = 0;
    // declare a arraylist of type string
    ArrayList<String> visitedVertices = new ArrayList<String>();
    // get all users of the graph and store them in a List
    List<String> allNetWorkUsers = getAllUsers();
    if (graph.order() > 0) {
      // for each user in the graph
      for (String user : graph.getAllVertices()) {
        // if the arrayList doesn't contain user
        if (!visitedVertices.contains(user)) {
          // then use DFS algo to find the number of groups 
          connectedCalculator(user, visitedVertices);
          groupNum++;
        }
      }
    }
    return groupNum;
  }

  /**
   * DFS algo to find the number of groups 
   * 
   * @param user, user's name. visitedVertices, a arrayList
   */
  private void connectedCalculator(String user, ArrayList<String> visitedVertices) {
    // add the user to the visited arrayList 
    visitedVertices.add(user);
    // get all friends of user and for each friend
    for (String userName : graph.getAdjacentVerticesOf(user)) {
      // if the visited arrayList doesn't contain the friend
      if (!visitedVertices.contains(userName)) {
        // recursive call DFS 
        this.connectedCalculator(userName, visitedVertices);
      }
    }

  }


  /**
   * Helper method to get all users of tbe graph 
   * 
   * @return a list of all user as type string
   */
  private List<String> getAllUsers() {
    
    Set<String> allUserSet = graph.getAllVertices();
    ArrayList<String> usersList = new ArrayList<String>();

    for (String eachUser : allUserSet) {
      usersList.add(eachUser);
    }
    return usersList;
  }

  /**
   * Return the central user 
   * 
   * @return a string as the central user 
   * 
   */
  @Override
  public String getCentralUser() {
    return centralUser;
  }

  /**
   * setting the central user 
   * 
   * @param centralUser, the string in which we wanted to set as central user
   */
  @Override
  public boolean setCentral(String setUser) {
    if (!users.contains(setUser)) {
      System.out.println("User not exist");
      return false;
    }
    centralUser = setUser;
    System.out.println(centralUser);
    recordOperations.add("s " + setUser);
    return true;
  }

  public ArrayList<String> getCenterFriend(String center) {
    ArrayList<String> friends = new ArrayList<String>();
    friends = (ArrayList<String>) graph.getAdjacentVerticesOf(center);
    return friends;

  }

  /**
   * Update the current social network with the information in the file to be loaded
   * 
   * @param file, the name of the file to be loaded
   */
  @Override
  public void loadFromFile(File file) {
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] args = line.split(" ");
        if (args.length == 2) {
          switch (args[0]) {
            case "a":
              addUser(args[1]);
              System.out.println("added a user " + args[1]);
              break;
            case "r":
              removeUser(args[1]);
              System.out.println("removed a user " + args[1]);
              break;
            case "s":
              setCentral(args[1]);
              System.out.println("Set central: " + args[1]);
              break;
          }
        } else if (args.length == 3) {
          switch (args[0]) {
            case "a":
              addFriends(args[1], args[2]);
              System.out.println("added a friendship between " + args[1] + " and " + args[2]);
              break;
            case "r":
              removeFriends(args[1], args[2]);
              System.out.println("added a friendship between " + args[1] + " and " + args[2]);
              break;
          }
        } else {
          // TODO exception
          System.out.println("This is not a valid command: " + line);
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Saving the log data (recordOperation) to a the specified file 
   * 
   * @param file, the name of file in which we want to save the log to 
   */
 @Override
    public void saveToFile(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            System.out.println(recordOperations.size());
            for (int i = 0; i < recordOperations.size(); i++) {
                String log = recordOperations.get(i);
                // System.out.println(log);
                fileWriter.write(log);
                fileWriter.write("\n");
            }
            fileWriter.close();
        }

        catch (Exception e) {
            System.out.println("Caution: IOException!");
        }

    }

  /**
   * removing all the vertices and edges from the graph 
   * 
   */
  public void removeAll() {
    // remove all the edges 
    for (String user : graph.getAllVertices()) {
      for (String userFriend : graph.getAdjacentVerticesOf(user)) {
        graph.removeEdge(user, userFriend);
      }
    }
    // remove all the vertices
    for (String userVertex : graph.getAllVertices()) {
      graph.removeVertex(userVertex);
    }
    // clear all the storage DS
    users.clear();
    personList.clear();
    recordOperations.clear();
    // Default central User 
    centralUser = "None";
  }
}
