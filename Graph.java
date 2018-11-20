/* Receives ColEdge array as input, creates a HashMap of vertices and an ArrayList of edges.
Vertices and Edges can be called by methods getVertices and getEdges.
Once the graph is created, the HashMap can be used to draw circles and the ArrayList to draw lines as appropriate.
The information required for graphical display include the vertex coordinates and identities.
Those should be stored in the Vertex object. (I.e. also the check for overlap must be done by the Vertex object.)
*/
import java.util.*;
public class Graph {

  private HashMap<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
  private ArrayList<Edge> edges = new ArrayList<Edge>();

  public Graph (int[][] ColEdge) {
     //Saving all the nodes into vertices at the location corresponding to the node number
    for (int i=0; i<ColEdge.length; i++) {
      if (!vertices.containsKey(ColEdge[i][0])) { // If no Vertex with the same number already exists, a new Vertex is added (can use index location to indicate vertex number)
        vertices.put((ColEdge[i][0]), new Vertex(ColEdge[i][0]));
      }
    }

    // Saving all the edges
    for (int i=0; i<ColEdge.length; i++) {
      edges.add(new Edge(
        vertices.get(ColEdge[i][0]),
        vertices.get(ColEdge[i][1])
      ));
    }
  }

  public HashMap<Integer, Vertex> getVertices () {
    return vertices;
  }
  public ArrayList<Edge> getEdges () {
    return edges;
  }
}
