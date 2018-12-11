import java.awt.event.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.colorchooser.*;
import java.util.HashMap;
import java.awt.Color;
import java.util.ArrayList;

//VertexClickListener checks whether the user clicked on a vertex, and updates the color of that vertex.
class VertexClickListener extends MouseAdapter {
private Graph graph;
private GraphComponent graphComponent;
private static Color color = Color.BLACK;
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
         vertex.setColor(color);
         graphComponent.repaint();
         return;
       }
 }
}
public static void setColor(Color col) {
	  color = col;
}
}
