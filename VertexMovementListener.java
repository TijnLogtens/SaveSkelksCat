import java.awt.event.*;
import javax.swing.event.MouseInputAdapter;
import java.util.HashMap;

public class VertexMovementListener extends MouseInputAdapter {
  private Graph graph;
  private static GraphComponent graphComponent;
  final int DIAMETER = 16;

  public VertexMovementListener(Graph graph, GraphComponent graphComponent) {
    this.graph = graph;
    this.graphComponent = graphComponent;
  }

  public void mouseMoved (MouseEvent e) {
    //if (GraphViewer.DEBUG) {System.out.println("Mouse moved at X= " + e.getX() + " Y= " + e.getY());}
  }

  public void mouseDragged (MouseEvent e) {
    //if (GraphViewer.DEBUG) {System.out.println("Mouse dragged at X= " + e.getX() + " Y= " + e.getY());}
    checkAndUpdateVertexLocation(e);
  }

  public void mouseReleased(MouseEvent e) {
    //if (GraphViewer.DEBUG) {System.out.println("Mouse released at X= " + e.getX() + " Y= " + e.getY());}
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

  // This method prevents the checkAndUpdateVertexLocation method from moving vertices on top of each other
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
              //if (GraphViewer.DEBUG) {System.out.println("Danger! Node collision with vertex " + vertex.getVertexNumber());}
              collision = true;
            }
          }
    }
    return collision;
  }

  public static GraphComponent getGraphComponent() {
	  return graphComponent;
  }
}
