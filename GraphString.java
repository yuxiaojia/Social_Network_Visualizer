
package application;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Filename: Graph.java Project: p4 Authors: Jarvis Jia
 * 
 * Directed and unweighted graph implementation
 */

public class GraphString implements GraphStringADT {

	private int vertexNumber; // the vertex number in the graph
	private int edgeNumber;// the edge numebr
	private HashMap<String, ArrayList<String>> adjList; // main body of graph. array to record vertex, hashmap to record
														// edge
	private ArrayList<String> vertexList;// set the vertex
	/*
	 * Default no-argument constructor
	 */

	public GraphString() {
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
	public void addVertex(String vertex) {
		if (vertex == null || adjList.containsKey(vertex)) { // vertex is null or aleady in graph
			return;
		} else {
			ArrayList<String> edges = new ArrayList<String>();// the vertex's edges
			adjList.put(vertex, edges); // add the edges list to the vertex
			vertexList.add(vertex);// add vertex
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
	public void removeVertex(String vertex) {
		if (vertex == null || !adjList.containsKey(vertex)) { // vertex should be non null and in the graph
			return;
		} else {

			for (int i = 0; i < adjList.get(vertex).size(); i++) {
				adjList.get(vertex).remove(i);// delete the vertex's dependences
				edgeNumber--;// delete edge number
			}
			for (int j = 0; j < vertexList.size(); j++) {// find if any vertex has dependency to this vertex
				if (adjList.get(vertexList.get(j)) == null) {

				} else if (adjList.get(vertexList.get(j)).contains(vertex)) {
					// if any vertex has dependency to this vertex, delete the edge of them
					//my xteammates help find the errors that I did not delete all of the edges 
					adjList.get(vertexList.get(j)).remove(vertex);
					edgeNumber--;
				}
			}

			adjList.remove(vertex); // remove edges
			vertexList.remove(vertex);// remove vertex
			vertexNumber--;// delete vertex number

		}
	}

	/**
	 * Add the edge from vertex1 to vertex2 to this graph. (edge is directed and
	 * unweighted) If either vertex does not exist, add vertex, and add edge, no
	 * exception is thrown. If the edge exists in the graph, no edge is added and no
	 * exception is thrown.
	 * 
	 * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
	 * the graph 3. the edge is not in the graph
	 */
	public void addEdge(String vertex1, String vertex2) {
		if (vertex1 == null || vertex2 == null) {// two vertex cannot be null

			return;
		} else {
			if (adjList.containsKey(vertex1)) {// if vertex1 alreay exist
				if (adjList.get(vertex1).contains(vertex2)) { // vertex2 already in the edge
					return;
				} else {
					if (adjList.containsKey(vertex2)) {

					} else {
						addVertex(vertex2);// add the vertex2 as vertex first
					}
					adjList.get(vertex1).add(vertex2);// add vertex2 to edge
					edgeNumber++;// add the edge number
				}
			} else {
				addVertex(vertex1);// add the vertex1 if vertex1 is not in the graph
				addEdge(vertex1, vertex2);// recursive call to add the edge
			}

		}
	}

	/**
	 * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed
	 * and unweighted) If either vertex does not exist, or if an edge from vertex1
	 * to vertex2 does not exist, no edge is removed and no exception is thrown.
	 * 
	 * Valid argument conditions: 1. neither vertex is null 2. both vertices are in
	 * the graph 3. the edge from vertex1 to vertex2 is in the graph
	 */
	public void removeEdge(String vertex1, String vertex2) {
		if (vertex1 == null || vertex2 == null) {
			return;
		} else {
			if (adjList.containsKey(vertex1)) {
				if (adjList.get(vertex1).contains(vertex2)) {// if the vertex2 is in the edge
					adjList.get(vertex1).remove(vertex2);// remove the vertex2 from the edge
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
	public List<String> getAdjacentVerticesOf(String vertex) {
		List<String> list = new ArrayList<String>();
		if (vertexList.contains(vertex)) {// if vertex is in graph
			for (int i = 0; i < adjList.get(vertex).size(); i++) {
				list.add(adjList.get(vertex).get(i));// get every edge of the vertex
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
}
