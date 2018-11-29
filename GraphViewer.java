import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.colorchooser.*;
import javax.swing.event.MouseInputAdapter;

// VertexClickListener updates the color of the vertex which the user has clicked on.
class VertexClickListener extends MouseAdapter {
  private Graph graph;
  private GraphComponent graphComponent;
  final int DIAMETER = 16;
  public VertexClickListener (Graph graph, GraphComponent graphComponent) {
    this.graph = graph;
    this.graphComponent = graphComponent;
  }
  public void mouseClicked(MouseEvent e) {
    HashMap<Integer, Vertex> vertices = graph.getVertices();
    for (int i=1; i<=vertices.size(); i++) {
      Vertex vertex = vertices.get(i);
      if (e.getX()>(vertex.getMidX()-DIAMETER/2) &&
          e.getX()<(vertex.getMidX()+DIAMETER/2) &&
          e.getY()>(vertex.getMidY()-DIAMETER/2) &&
          e.getY()<(vertex.getMidY()+DIAMETER/2)) {
            vertex.setColor(Color.GREEN);
            graphComponent.repaint();
            return;
          }
    }
  }
}

// VertexMovementListener updates the location of the vertex based on mouse-dragging.
class VertexMovementListener extends MouseInputAdapter {
  private Graph graph;
  private GraphComponent graphComponent;
  final int DIAMETER = 16;
  public VertexMovementListener(Graph graph, GraphComponent graphComponent) {
    this.graph = graph;
    this.graphComponent = graphComponent;
  }
  public void mouseMoved (MouseEvent e) {
    System.out.println("Mouse moved at X= " + e.getX() + " Y= " + e.getY());
  }
  public void mouseDragged (MouseEvent e) {
    System.out.println("Mouse dragged at X= " + e.getX() + " Y= " + e.getY());
    checkAndUpdateVertexLocation(e);
  }
  public void mouseReleased(MouseEvent e) {
    System.out.println("Mouse released at X= " + e.getX() + " Y= " + e.getY());
    checkAndUpdateVertexLocation(e);
  }
  // The following method checks if click was on vertex area and updates location to the mouse coordinates
  public void checkAndUpdateVertexLocation(MouseEvent e) {
    HashMap<Integer, Vertex> vertices = graph.getVertices();
    for (int i=1; i<=vertices.size(); i++) {
      Vertex vertex = vertices.get(i);
      if (e.getX()>(vertex.getMidX()-DIAMETER/2) &&
          e.getX()<(vertex.getMidX()+DIAMETER/2) &&
          e.getY()>(vertex.getMidY()-DIAMETER/2) &&
          e.getY()<(vertex.getMidY()+DIAMETER/2) &&
          !dangerOfCollision(vertices, e.getX(), e.getY())) {
            vertex.setMidX(e.getX());
            vertex.setMidY(e.getY());
            graphComponent.repaint();
      }
    }
  }
  public boolean dangerOfCollision (HashMap<Integer, Vertex> vertices, int mouseX, int mouseY) {
    boolean collision = false;
    int collisionCounter = 0;
    for (int i=1; i<=vertices.size(); i++) {
      Vertex vertex = vertices.get(i);
      if (
          mouseX>(vertex.getMidX()-(DIAMETER+2)) &&
          mouseX<(vertex.getMidX()+(DIAMETER+2)) &&
          mouseY>(vertex.getMidY()-(DIAMETER+2)) &&
          mouseY<(vertex.getMidY()+(DIAMETER+2))) {
            collisionCounter++;
            if (collisionCounter>=2) {
              System.out.println("Danger! Node collision with vertex " + vertex.getVertexNumber());
              collision = true;
            }
          }
    }
    return collision;
  }
}

public class GraphViewer  {
  public static void main(String[] args) {
      int[][] ColEdge = {{1,2},{2,3},{3,4},{4,1},{2,4},{5,4},{6,4},{7,4},{8,4},{9,4},{10,5},{10,6},{10,7},{11,5},{12,5},{13,5},{14,5}};
      Graph graph = new Graph(ColEdge);
      System.out.println(graph.getVertices());

      JFrame window = new JFrame();
      Container contentPane = window.getContentPane();
      contentPane.setPreferredSize(new Dimension(800, 800));
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      GraphComponent graphComponent = new GraphComponent(graph);
      VertexClickListener clickListener = new VertexClickListener(graph, graphComponent);
      graphComponent.addMouseListener(clickListener);
      VertexMovementListener movementListener = new VertexMovementListener(graph, graphComponent);
      graphComponent.addMouseMotionListener(movementListener);
      window.add(graphComponent);
      window.pack();
      window.setVisible(true);
    }
}
