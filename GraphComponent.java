import javax.swing.*;
import java.awt.*;
import java.util.*;
public class GraphComponent extends JComponent {
  private Graph graph;
  public GraphComponent (Graph graph) {
    this.graph = graph;
  }
  public void paintComponent (Graphics g) {
    final int FRAME_SIDE_LENGTH = 800;
    int counter = 0;
    final int DIAMETER = 16;

    // Drawing the vertices
    HashMap<Integer, Vertex> vertices = graph.getVertices();
    for (int i=1; i<=vertices.size(); i++) {
      g.fillOval(vertices.get(i).getLeftX(),
                vertices.get(i).getTopY(),
                DIAMETER, DIAMETER
                );
    }

    // Drawing the edges
    ArrayList<Edge> edges = graph.getEdges();
    for (int i=0; i<edges.size(); i++) {
      Vertex startVertex = edges.get(i).getStartVertex();
      Vertex endVertex = edges.get(i).getEndVertex();
      g.drawLine(startVertex.getMidX(), startVertex.getMidY(),
                endVertex.getMidX(), endVertex.getMidY());
    }
  }
}
