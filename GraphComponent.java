import javax.swing.*;
import java.awt.*;
import java.util.*;
public class GraphComponent extends JComponent {
  private final int PANE_SIDE_LENGTH = 600;// to be changed if necessary
  private final int BOUNDARY_START = 50; // This denotes the leftmost or topmost pixel of the boundary within which we want the graph to appear
  private final int BOUNDARY_END = 450;// This denotes the rightmost or downmost pixel of the boundary within which we want the graph to appear
  private Graph graph;
  private Color color;
  private final int DIAMETER = 16;
  public GraphComponent (Graph graph) {
    this.graph = graph;
  }
  public void paintComponent (Graphics g) {

    // Check and correct vertex locations, removing overlap and enforcing bounds
    HashMap<Integer, Vertex> vertices = graph.getVertices();
    for (int i=1; i<=vertices.size(); i++) {
      Vertex vertex = vertices.get(i);
      while (vertexOverlapFound(vertex, vertices) || notWithinBounds(vertex)) {
         vertex.setMidX((int) (Math.random()*PANE_SIDE_LENGTH));
         vertex.setMidY((int) (Math.random()*PANE_SIDE_LENGTH));
       }
     }

    // Draw the edges
    ArrayList<Edge> edges = graph.getEdges();
    for (int i=0; i<edges.size(); i++) {
      Vertex startVertex = edges.get(i).getStartVertex();
      Vertex endVertex = edges.get(i).getEndVertex();
      g.setColor(Color.BLACK);
      g.drawLine(startVertex.getMidX(), startVertex.getMidY(),
                endVertex.getMidX(), endVertex.getMidY());
    }

    // Draw the vertices
    for (int i=1; i<=vertices.size(); i++) {
      Vertex vertex = vertices.get(i);
      g.setColor(vertices.get(i).getColor());
      g.fillOval(vertices.get(i).getLeftX(),
                vertices.get(i).getTopY(),
                DIAMETER, DIAMETER
                );
    }

    // Display node numbers - we can turn this off if necessary or make it optional to user
    for (int i=1; i<=vertices.size(); i++) {
      g.setColor(Color.BLACK);
      g.drawString(" " +vertices.get(i).getVertexNumber(),
                vertices.get(i).getLeftX(),
                (vertices.get(i).getTopY())-5);
    }
  }
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
  // Placeholder for public boolean notWithinBounds, which checks if the vertex is displayed within the required area
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
}
