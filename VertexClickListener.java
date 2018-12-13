import java.awt.event.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.colorchooser.*;
import java.util.HashMap;
import java.util.HashSet;
import java.awt.Color;
import java.util.ArrayList;

//VertexClickListener checks whether the user clicked on a vertex, and updates the color of that vertex.
public class VertexClickListener extends MouseAdapter {
	private Graph graph;
	private GraphComponent graphComponent;
	private static Color color = Color.BLACK;
	final int DIAMETER = 16;
	private int counter=0;
	private static boolean won;
	private static ArrayList<Color> colourCounter = new ArrayList<Color>();

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
						 if(checkDuplicateColour(colourCounter)){
							 colourCounter.add(color);
						 }
		         counter++;
		         graphComponent.repaint();
		        if(!graph.check()) {//case mistake
							won = false;
			    	}

			    	if(counter==vertices.size()) {
			    		counter=0;
			    		if(graph.check()) {//case won
								won = true;
			    		}
			    	}
	        return;
				}
	  	}
		}
	}

	//Have someone fix this so it removes unused colours
	public static boolean checkDuplicateColour(ArrayList lit){
		HashSet set = new HashSet();
		for(int w = 0; w < lit.size(); w++){
			boolean there = set.add(lit.get(w));
			if(there == false){
				return there;
			}
		}
		return true;
	}

	public static boolean getBoold(){
		return won;
	}

	public static int getNumberColours(){
		System.out.println(colourCounter.size());
		return colourCounter.size();
	}

	public static void setColor(Color col) {
		  color = col;
	}
}
