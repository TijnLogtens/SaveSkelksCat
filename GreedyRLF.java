import java.io.*;
import java.util.*;
public class GreedyRLF {
  private boolean DEBUG = false;
  public GreedyRLF (ColEdge[] edges) {
    // Each location in colors list represents a new color, and the list contained in the locations has the vertices colored with that color
    ArrayList<ArrayList<Integer>> colors = new ArrayList<ArrayList<Integer>>(); // S

    // Get the list and the number of vertices in the graph:
    ArrayList<Integer> vertices = new ArrayList<Integer>(); // V
    for (int i=0; i<edges.length; i++) {
      if (!vertices.contains(edges[i].u)) {
        vertices.add(edges[i].u);
      }
      if (!vertices.contains(edges[i].v)) {
        vertices.add(edges[i].v);
      }
    }
    if (DEBUG) {System.out.println("List of all vertices in the graph: " + vertices);}
    int numberOfVertices = vertices.size();

    // AdjacentVertices will hold at each iteration the nodes which are adjacent to the one being colored (and cannot therefore have the same color)
    ArrayList<Integer> adjacentVertices = new ArrayList<Integer>(); // Y
    ArrayList<Integer> uncoloredVertices = new ArrayList<Integer>(); // X
    for (int i=0; i<vertices.size(); i++) {
      uncoloredVertices.add(vertices.get(i));
    }
    if (DEBUG) {System.out.println("List of uncolored vertices in the graph: " + uncoloredVertices);}

    // RLF algorithm:
    int i = 0;
    while (!uncoloredVertices.isEmpty()) { // continue coloring until no more uncolored vertices
      i++;
      if (DEBUG) {System.out.println("Round number " + i);}
      colors.add(new ArrayList<Integer>());
      while (!uncoloredVertices.isEmpty()) {
        int vertexToColor = uncoloredVertices.get(0);
        if (DEBUG) {System.out.println("Now coloring vertex " + vertexToColor);}
        colors.get(i-1).add(vertexToColor);
        if (DEBUG) {System.out.println("The lists of colored vertices are: " + colors);}
        for (int j=0; j<edges.length; j++) {
          if (DEBUG) {System.out.println("Check for adjacent vertices: edge no " + j);}
          if ((edges[j].u == vertexToColor) && !adjacentVertices.contains(edges[j].v)) {
            // check if adjacent vertex already colored: if yes, cannot be added to adjacentVertices
            boolean alreadyColored = false;
            for (int k=0; k<colors.size(); k++) {
              if (colors.get(k).contains(edges[j].v)) {
                alreadyColored = true;
              }
            }
            // If not already colored, the adjacent vertex cannot be colored in this round.
            if (!alreadyColored) {
              adjacentVertices.add(edges[j].v);
              if (DEBUG) {System.out.println("Vertex " + edges[j].v + " is adjacent to vertex " + vertexToColor);}
            }
          }

          if ((edges[j].v == vertexToColor) && !adjacentVertices.contains(edges[j].u)) {
            // check if adjacent vertex already colored: if yes, cannot be added to adjacentVertices
            boolean alreadyColored = false;
            for (int k=0; k<colors.size(); k++) {
              if (colors.get(k).contains(edges[j].u)) {
                alreadyColored = true;
              }
            }
            // If not already colored, the adjacent vertex cannot be colored in this round.
            if (!alreadyColored) {
              adjacentVertices.add(edges[j].u);
              if (DEBUG) {System.out.println("Vertex " + edges[j].u + "is adjacent to vertex " + vertexToColor);}
            }
          }

        }
        uncoloredVertices.remove(Integer.valueOf(vertexToColor));
        for (int j=0; j<adjacentVertices.size(); j++) {
          uncoloredVertices.remove(Integer.valueOf(adjacentVertices.get(j)));
        }
      }
      if (DEBUG) {System.out.println("After coloring round number " + i + " X contains " + uncoloredVertices.size() + " vertices.");}
      for (int k=0; k<adjacentVertices.size(); k++) {
        uncoloredVertices.add(adjacentVertices.get(k));
      }
      adjacentVertices.clear();
    }
    System.out.println("The upper bound is " + colors.size());
    if (DEBUG) {System.out.println("There were " + i + " coloring rounds.");}

  }
}
