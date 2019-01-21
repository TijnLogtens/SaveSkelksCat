import java.io.*;
import java.util.*;
public class BKCompetition {
  private long bkStartTime = ChromaticNumbers.bkStartTime;
  private int lowerBound;
  private int counter = 0;
  public BKCompetition(ColEdge[] originalEdges) throws Exception {
    int[][] edges = new int[originalEdges.length][2];
    for (int i=0; i<originalEdges.length; i++) {
      edges[i][0] = originalEdges[i].u;
      edges[i][1] = originalEdges[i].v;
    }

    // BK algorithm section
    ArrayList<ArrayList<Integer>> cliques = new ArrayList<ArrayList<Integer>>(); // Make an ArrayList to store the arrays with the nodes of the maximal cliques.
    ArrayList<Integer> P = new ArrayList<Integer>(); // P contains all the vertices in the graph
    for (int i=0; i<edges.length; i++) { // checking 1st column of edges[][] and adding non-duplicate nodes to P
      boolean found = false;
      for (int j=0; j<P.size(); j++) {
        if (P.get(j) == edges[i][0]) {
          found = true;
        }
      }
      if (!found) {
        P.add(edges[i][0]);
      }
    }
    ArrayList<Integer> R = new ArrayList<Integer>(); // R is now empty but will contain the vertices of potential cliques
    ArrayList<Integer> X = new ArrayList<Integer>(); // X is now empty but will contain vertices already in some cliques or processed
    HashMap<Integer, ArrayList> neighbours = getNeighbours(originalEdges); // Stores the neighbours of each vertex

    bronkerbosch(P, R, X, cliques, neighbours);
    int largestCliqueSize = 0;
    for (int i=0; i<cliques.size(); i++) {
      if (cliques.get(i).size()>largestCliqueSize) {
        largestCliqueSize = cliques.get(i).size();
      }
    }
    lowerBound = largestCliqueSize;
    //System.out.println("The lower bound of the graph is " + largestCliqueSize);

  }
  public void bronkerbosch (
    ArrayList<Integer> P,
    ArrayList<Integer> R,
    ArrayList<Integer> X,
    ArrayList<ArrayList<Integer>> cliques,
    HashMap<Integer, ArrayList> neighbours) throws Exception {
    counter++;
    long currentTime = System.currentTimeMillis();
    //System.out.println("Round " + counter);
    long runningTime = currentTime-bkStartTime;
    // if ((runningTime)>=30000) {
    //
    //   System.out.println("Running time: " + runningTime/1000 + " s.");
    //   throw new Exception();
    // }
    if (P.size() == 0 && X.size() == 0) {
      cliques.add(R);
    }
    int pivotVertex = 0;
    if (P.size() != 0) {
      pivotVertex = P.get(0);
    }
    for (int i=0; i<P.size(); i++) {
      int vertexV = P.get(i);
      // The contents will be carried out only if i is not a neighbour of the pivot vertex
      if (!isNeighbour(vertexV, pivotVertex, neighbours)) {
        bronkerbosch(intersectOf(vertexV, P, neighbours), unionOf(vertexV, R), intersectOf(vertexV, X, neighbours), cliques, neighbours);
        currentTime = System.currentTimeMillis();
        P.remove(Integer.valueOf(vertexV));
        X = unionOf(vertexV, X);
      }
    }
  }
  public HashMap<Integer, ArrayList> getNeighbours(ColEdge[] originalEdges) {
    HashMap<Integer, ArrayList> neighbours = new HashMap<Integer, ArrayList>(); // Stores the neighbours of each vertex
    for (int i=0; i<originalEdges.length; i++) {
      int vertex1 = originalEdges[i].u;
      int vertex2 = originalEdges[i].v;
      if (!neighbours.containsKey(vertex1)) {
        ArrayList<Integer> neighbourList = new ArrayList<Integer>();
        for (int j=0; j<originalEdges.length; j++) {
          if (originalEdges[j].u == vertex1) {
            neighbourList.add(originalEdges[j].v);
          }
          if (originalEdges[j].v == vertex1) {
            neighbourList.add(originalEdges[j].u);
          }
        }
        neighbours.put(vertex1, neighbourList);
      }
      if (!neighbours.containsKey(vertex2)) {
        ArrayList<Integer> neighbourList = new ArrayList<Integer>();
        for (int j=0; j<originalEdges.length; j++) {
          if (originalEdges[j].u == vertex2) {
            neighbourList.add(originalEdges[j].v);
          }
          if (originalEdges[j].v == vertex2) {
            neighbourList.add(originalEdges[j].u);
          }
        }
        neighbours.put(vertex2, neighbourList);
      }
    }
    return neighbours;
  }
  public ArrayList<Integer> intersectOf(int v, ArrayList<Integer> oldList, HashMap<Integer, ArrayList> neighbours) {
    ArrayList<Integer> newList = new ArrayList<Integer>();
    ArrayList<Integer> neighbourList = neighbours.get(v);
    for (int i=0; i<oldList.size(); i++) {
      if (neighbourList.contains(oldList.get(i))) {
        newList.add(oldList.get(i));
      }
    }
    return newList;
  }
  public ArrayList<Integer> unionOf(int v, ArrayList<Integer> oldList)	{
    ArrayList<Integer> newList = new ArrayList<Integer>(oldList);
    if(!newList.contains(v)) {
      newList.add(v);
    }
    return newList;
  }
  public boolean isNeighbour(int i, int pivotVertex, HashMap<Integer, ArrayList> neighbours) {
    return neighbours.get(pivotVertex).contains(i);
  }
  public int getLowerBound () {
    return lowerBound;
  }
}
