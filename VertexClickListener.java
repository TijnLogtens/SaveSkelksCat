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
private int counter=0;
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
	   if(!color.equals(Color.BLACK)) {
         vertex.setColor(color);
         counter++;
         graphComponent.repaint();
         if(!graph.check()) {//code in case of defeat
	    		System.out.println("you loose");
	    	}
        
	    	if(counter==vertices.size()) {
	    		counter=0;
	    		if(graph.check()) {//code in case of victory
	    			System.out.println("you win");
	    		}
	    	}
         
         return;
       }
   }
 }
}
public static void setColor(Color col) {
	  color = col;
}
}

