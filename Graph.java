/* Receives an integer array as input, creates a HashMap of vertices and an ArrayList of edges.
Vertices and Edges can be called by methods getVertices and getEdges.
Once the graph is created, the HashMap can be used to draw circles and the ArrayList to draw lines as appropriate.
The information required for graphical display include the vertex coordinates and identities.
Those should be stored in the Vertex object.
*/
import java.awt.Color;
import java.util.*;
public class Graph {

  private HashMap<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
  private ArrayList<Edge> edges = new ArrayList<Edge>();
  private final int PANE_SIDE_LENGTH = 600;// to be changed if necessary
  private final int BOUNDARY_START = 50; // This denotes the leftmost or topmost pixel of the area within which we want the graph to appear
  private final int BOUNDARY_END = 450;// This denotes the rightmost or downmost pixel of the area within which we want the graph to appear
  private final int DIAMETER = 16;
  public Graph (int[][] ColEdge) {
     //Saving all the nodes into vertices at the HashMap location corresponding to the node number
    for (int i=0; i<ColEdge.length; i++) {
    	for(int j=0; j<2; j++) {
	      if (!vertices.containsKey(ColEdge[i][j])) { // If no Vertex with the same number already exists, a new Vertex is added (can use index location to indicate vertex number)
	        vertices.put((ColEdge[i][j]), new Vertex(ColEdge[i][j]));
	      }
    	}
    }

    // Check and correct vertex locations, removing overlap and enforcing bounds
    for (int i=1; i<=vertices.size(); i++) {
      Vertex vertex = vertices.get(i);
      while (vertexOverlapFound(vertex, vertices) || notWithinBounds(vertex)) {
         vertex.setMidX((int) (Math.random()*PANE_SIDE_LENGTH));
         vertex.setMidY((int) (Math.random()*PANE_SIDE_LENGTH));
       }
     }

    // Saving all the edges
    for (int i=0; i<ColEdge.length; i++) {
      edges.add(new Edge(
        vertices.get(ColEdge[i][1]),
        vertices.get(ColEdge[i][0])
      ));
    }
  }

// Method for getting the list of vertices as a HashMap
  public HashMap<Integer, Vertex> getVertices () {
    return vertices;
  }

  // Method for getting the list of edges as an ArrayList
  public ArrayList<Edge> getEdges () {
    return edges;
  }
  // Checks that vertices are not drawn on top of each other
  public boolean vertexOverlapFound (Vertex vertex, HashMap<Integer, Vertex> vertices) {
    boolean overlapFound = false;
    for (int i=1; i<=vertices.size(); i++) {
      if (!vertex.equals(vertices.get(i)) &&
          vertex.getMidX()>(vertices.get(i).getMidX()-DIAMETER) &&
          vertex.getMidX()<(vertices.get(i).getMidX()+DIAMETER) &&
          vertex.getMidY()>(vertices.get(i).getMidY()-DIAMETER) &&
          vertex.getMidY()<(vertices.get(i).getMidY()+DIAMETER)) {
            overlapFound = true;
          }
    }
    return overlapFound;
  }
  // Checks if the vertex is displayed within the required area
  public boolean notWithinBounds(Vertex vertex) {
    boolean notWithinBounds = false;
    if (vertex.getMidX()<BOUNDARY_START ||
        vertex.getMidX()>BOUNDARY_END ||
        vertex.getMidY()<BOUNDARY_START ||
        vertex.getMidY()>BOUNDARY_END) {
          notWithinBounds = true;
        }
    return notWithinBounds;
  }
  
  //checks for invalid moves
  public boolean check(){
		for(int i=0; i <edges.size() ; i++){
				if(!(edges.get(i).getStartVertex().getColor().equals(Color.BLACK))
						&& !(edges.get(i).getEndVertex().getColor().equals(Color.BLACK)) 
						&& edges.get(i).getStartVertex().getColor().equals(edges.get(i).getEndVertex().getColor())) {
					return false;
				}
		}
		return true;
	}
}
