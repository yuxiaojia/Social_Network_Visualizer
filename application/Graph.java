/**
 * Filename: Graph.java 
 * Project: Social Network 
 * Authors: ateam120
 * 
 * Directed and unweighted graph implementation
 */

package application;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph implements GraphADT {

    private int vertexNumber; // the vertex number in the graph
    private int edgeNumber;// the edge numebr
    private HashMap<String, ArrayList<String>> adjList; // main body of graph. array to record vertex, hashmap to record
                                                        // edge
    private ArrayList<String> vertexList;// set the vertex
    /*
     * Default no-argument constructor
     */

    public Graph() {
        edgeNumber = 0;// initiate edge number
        vertexNumber = 0;// initiate vertex number
        adjList = new HashMap<String, ArrayList<String>>(vertexNumber);
        vertexList = new ArrayList<String>();
    }

    /**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists, method ends without adding a vertex or
     * throwing an exception.
     * 
     * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in
     * the graph
     */
    public void addVertex(String person) {
        if (person == null || adjList.containsKey(person)) { // vertex is null or aleady in graph
            return;
        } else {
            ArrayList<String> edges = new ArrayList<String>();// the vertex's edges
            adjList.put(person, edges); // add the edges list to the vertex
            vertexList.add(person);// add vertex
            vertexNumber++;// vertex number increase
        }
    }

    /**
     * Remove a vertex and all associated edges from the graph.
     * 
     * If vertex is null or does not exist, method ends without removing a vertex,
     * edges, or throwing an exception.
     * 
     * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in
     * the graph
     */
    public void removeVertex(String person) {
        if (person == null || !adjList.containsKey(person)) { // vertex should be non null and in the graph
            return;
        } else {
            // useless
            for (int i = 0; i < adjList.get(person).size(); i++) {
                adjList.get(person).remove(i);// delete the vertex's relation
                edgeNumber--;// delete edge number
            }
            for (int j = 0; j < vertexList.size(); j++) {// find if any vertex has relation to this vertex
                if (adjList.get(vertexList.get(j)) == null) {

                } else if (adjList.get(vertexList.get(j)).contains(person)) {
                    // if any vertex has relationship to this vertex, delete the edge of them
                    // my xteammates help find the errors that I did not delete all of the edges
                    adjList.get(vertexList.get(j)).remove(person);
                    edgeNumber--;
                }
            }
            adjList.remove(person); // remove edges
            vertexList.remove(person);// remove vertex
            vertexNumber--;// delete vertex number
        }
    }

    /**
     * Add the edge from vertex1 to vertex2 to this graph. (edge is undirected and
     * unweighted) If either vertex does not exist, add vertex, and add edge, no
     * exception is thrown. If the edge exists in the graph, no edge is added and no
     * exception is thrown.
     * 
     * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
     * the graph 3. the edge is not in the graph
     */
    public void addEdge(String person1, String person2) {
        if (person1 == null || person2 == null) {// two vertex cannot be null

            return;
        } else {
            if (adjList.containsKey(person1)) {// if vertex1 alreay exist
                if (adjList.get(person1).contains(person2)) { // vertex2 already in the edge
                    return;
                } else {
                    if (adjList.containsKey(person2)) {

                    } else {
                        addVertex(person2);// add the vertex2 as vertex first
                    }
                    adjList.get(person1).add(person2);// add vertex2 to edge
                    adjList.get(person2).add(person1);// add vertex2 to edge
                    edgeNumber++;// add the edge number
                }
            } else {
                addVertex(person1);// add the vertex1 if vertex1 is not in the graph
                addEdge(person1, person2);// recursive call to add the edge
            }
        }
    }

    /**
     * Remove the edge from vertex1 to vertex2 from this graph. (edge is undirected
     * and unweighted)
     * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
     * the graph 3. the edge from vertex1 to vertex2 is in the graph
     */
    public void removeEdge(String person1, String person2) {
        if (person1 == null || person2 == null) {
            return;
        } else {
            if (adjList.containsKey(person1)) {
                if (adjList.get(person1).contains(person2)) {// if the vertex2 is in the edge
                    adjList.get(person1).remove(person2);// remove the vertex2 from the edge
                    adjList.get(person2).remove(person1);
                    edgeNumber--;
                } else {
                    // if no vertex2, no change
                }
            }

        }
    }

    /**
     * Returns a Set that contains all the vertices
     * 
     */
    public Set<String> getAllVertices() {
        Set<String> set = new HashSet<>();
        if (vertexList == null) {// if there is nothing in the graph
            return null;
        } else {
            for (int i = 0; i < vertexNumber; i++) {
                set.add(vertexList.get(i));// add all the verteices to the set
            }
            return set;
        }
    }

    /**
     * Get all the neighbor (adjacent) vertices of a vertex
     *
     */
    public List<String> getAdjacentVerticesOf(String person) {
        List<String> list = new ArrayList<String>();
        if (vertexList.contains(person)) {// if vertex is in graph
            for (int i = 0; i < adjList.get(person).size(); i++) {
                list.add(adjList.get(person).get(i));// get every edge of the vertex
            }
        } else {
            return null;
        }
        return list;
    }

    /**
     * Returns the number of edges in this graph.
     */
    public int size() {
        return edgeNumber; // the edges number in the graph
    }

    /**
     * Returns the number of vertices in this graph.
     */
    public int order() {
        return vertexNumber;// the total vertice number in the graph
    }

    public List<String> getVertexList() {
        return vertexList;
    }
}
