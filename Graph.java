/* Receives an integer array as input, creates a HashMap of vertices and an ArrayList of edges.
Vertices and Edges can be called by methods getVertices and getEdges.
Once the graph is created, the HashMap can be used to draw circles and the ArrayList to draw lines as appropriate.
The information required for graphical display include the vertex coordinates and identities.
Those should be stored in the Vertex object.
*/
import java.util.*;
public class Graph {

  private HashMap<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
  private ArrayList<Edge> edges = new ArrayList<Edge>();
  private final int PANE_SIDE_LENGTH = 600;// to be changed if necessary
  private final int BOUNDARY_START = 50; // This denotes the leftmost or topmost pixel of the area within which we want the graph to appear
  private final int BOUNDARY_END = 850;// This denotes the rightmost or downmost pixel of the area within which we want the graph to appear
  private final int DIAMETER = 8;
  public Graph (int[][] ColEdge) {
     //Saving all the nodes into vertices at the HashMap location corresponding to the node number
     System.out.println("New graph is generated.");
    for (int i=0; i<ColEdge.length; i++) {
      if (!vertices.containsKey(ColEdge[i][0])) { // If no Vertex with the same number already exists, a new Vertex is added (can use index location to indicate vertex number)
        vertices.put((ColEdge[i][0]), new Vertex(ColEdge[i][0]));
        //System.out.println("Location " + ColEdge[i][0]);
      }
      if (!vertices.containsKey(ColEdge[i][1])) { // If no Vertex with the same number already exists, a new Vertex is added (can use index location to indicate vertex number)
        vertices.put((ColEdge[i][1]), new Vertex(ColEdge[i][1]));
        //System.out.println("Location " + ColEdge[i][1]);
      }
    }

    for (int i=1; i<vertices.size(); i++) {
      if (vertices.get(i) != null) {System.out.println("Location " + i + ": " + vertices.get(i).toString()); }
      else {System.out.println("Location " + i + " points to null.");}
    }

    // Check and correct vertex locations, removing overlap and enforcing bounds
    for (int i=1; i<=vertices.size(); i++) {
      Vertex vertex = vertices.get(i);
      while (vertices.get(i) != null && (vertexOverlapFound(vertex, vertices) || notWithinBounds(vertex))) {
         vertex.setMidX((int) (Math.random()*PANE_SIDE_LENGTH));
         vertex.setMidY((int) (Math.random()*PANE_SIDE_LENGTH));
       }
     }



    // Saving all the edges
    for (int i=0; i<ColEdge.length; i++) {
      edges.add(new Edge(
        vertices.get(ColEdge[i][0]),
        vertices.get(ColEdge[i][1])
      ));
    }

    // Swap vertex locations as long as it reduces the length of some edge
    // While there exist two vertices that can be swapped so that there exists
    // an edge connected to either those two vertices which will be shorter as a result of the swap,
    // the vertices must be swapped.
    System.out.println("Next: swap vertex locations");
    swapVertexLocations();
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
      if (vertices.get(i) != null && !vertex.equals(vertices.get(i)) &&
          vertex.getMidX()>(vertices.get(i).getMidX()-(DIAMETER+4)) &&
          vertex.getMidX()<(vertices.get(i).getMidX()+(DIAMETER+4)) &&
          vertex.getMidY()>(vertices.get(i).getMidY()-(DIAMETER+4)) &&
          vertex.getMidY()<(vertices.get(i).getMidY()+(DIAMETER+4))) {
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
  public double getEdgeLength (Vertex startVertex, Vertex endVertex) {
    if (startVertex.equals(endVertex)) {
      return 0;
    }
    int diffY = startVertex.getMidY() - endVertex.getMidY();
    int diffX = startVertex.getMidX() - endVertex.getMidX();
    // System.out.println("Measuring the distance between vertices " + startVertex.getVertexNumber() + " and " + endVertex.getVertexNumber());
    // System.out.println("Distance of x-values is: " + diffX + " and distance of Y-values is " + diffY);
    double edgeLength = Math.sqrt(diffY*diffY + diffX*diffX); // TBC if this casting causes any issues.
    return edgeLength;
  }
  public double getTotalEdgeLength () {
    double totalEdgeLength = 0;
    //System.out.println("Edges.size: " + edges.size());
    for (int i=0; i<edges.size(); i++) {
      //System.out.println("Hello world");
      Vertex startV = edges.get(i).getStartVertex();
      Vertex endV = edges.get(i).getEndVertex();
      //System.out.println("Start vertex " + startV.getVertexNumber() + " End vertex " + endV.getVertexNumber());
      double edgeLength = getEdgeLength(startV, endV);

      //System.out.println("Edge length is " + edgeLength);
      totalEdgeLength = totalEdgeLength + edgeLength;
    }
    //System.out.println("Total edge length is " + totalEdgeLength);
    return totalEdgeLength;
  }
  public void swapVertexLocations () {
    boolean hasReductionPotential = true;
    int counter = 0;
    int max = 1000000;
    while (hasReductionPotential && counter<max) {

      double totalEdgeLength = getTotalEdgeLength();
      //System.out.println("Initial total length is " + totalEdgeLength);
      boolean swapsDone = false;
      for (int i=1; i<=vertices.size()&&counter<max; i++) {
        for (int j=1; j<=vertices.size()&&counter<max; j++) {
          counter++;
          Vertex v1 = vertices.get(i);
          Vertex v2 = vertices.get(j);
          if (v1 != null && v2 != null && !v1.equals(v2)) {
            int tempX = v1.getMidX();
            int tempY = v1.getMidY();
            v1.setMidX(v2.getMidX());
            v1.setMidY(v2.getMidY());
            v2.setMidX(tempX);
            v2.setMidY(tempY);
          }
          double newTotalEdgeLength = getTotalEdgeLength();
          //System.out.println("New total edge length: " + newTotalEdgeLength);
          if (newTotalEdgeLength >= totalEdgeLength && v1 != null && v2 != null) { // if the swap does not reduce total edge length, it will be reversed
            int tempX = v1.getMidX();
            int tempY = v1.getMidY();
            v1.setMidX(v2.getMidX());
            v1.setMidY(v2.getMidY());
            v2.setMidX(tempX);
            v2.setMidY(tempY);
          } else{
            //System.out.println("New length: " + newTotalEdgeLength + " Old length: " + totalEdgeLength);
            totalEdgeLength = newTotalEdgeLength;
            swapsDone = true; // if the swap is not reversed, the while loop will run once more
          }
        }
      }
      if (!swapsDone) {
        hasReductionPotential = false;
      }
    }

  }

}
