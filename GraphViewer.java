import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.MouseInputAdapter;

public class GraphViewer  {

		
	public static boolean DEBUG = false;
	static Graph graph = null;
 	static GraphComponent graphComponent = null;
 	static ColEdge[] ed = null;
 	
 	public static void main(String[] args) {
 		ed = GenerateGraph.randomGraph();
 		int[][] ColEdge = new int[ed.length][2];
 		
 		//from ColEdge[] to int[][]
 		for(int i=0; i<ColEdge.length; i++) {
 			ColEdge[i][0] = ed[i].u;
 			ColEdge[i][1] = ed[i].v;
 		}
 		
		graph = new Graph(ColEdge);
 		graphComponent = new GraphComponent(graph);
 		JFrame window = new JFrame();
 		window.setPreferredSize(new Dimension(700, 700));
 		//I added set location and setResizable(false) because it looks nicer
 		window.setLocation(250, 50);
 		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		window.pack(); 
		VertexClickListener clickListener = new VertexClickListener(graph, graphComponent);
 		graphComponent.addMouseListener(clickListener);
 		VertexMovementListener movementListener = new VertexMovementListener(graph, graphComponent);
 		graphComponent.addMouseMotionListener(movementListener);
 		ColorListener listener = new ColorListener();
 		graphComponent.addMouseListener(listener);
		window.add(graphComponent);
		window.setVisible(true);
    }
}



/////////////////////////////////////end__of__main__method///////////////////////////////////////////////////////////////////




// VertexClickListener checks whether the user clicked on a vertex, and updates the color of that vertex.
class VertexClickListener extends MouseAdapter {
	private Graph graph;
	private static Color color = Color.BLACK;
	private GraphComponent graphComponent;
	final int DIAMETER = 16;
	int counter=0;
	private ArrayList<Integer> sequence = null;
  
	public VertexClickListener (Graph graph, GraphComponent graphComponent) {
		this.graph = graph;
		this.graphComponent = graphComponent;
		sequence = randomSequence(graph.getVertices().size());
		graph.getVertices().get(sequence.get(0)).setBorderColor(Color.RED);
	}
  
	public void mouseClicked(MouseEvent e) {
		if(!color.equals(Color.BLACK)) {//you can't colour a node in black you have to choose a colour			
			HashMap<Integer, Vertex> vertices = graph.getVertices();
		    Vertex nextVertex = null;
		    Vertex vertex = vertices.get(sequence.get(counter));
			vertex.setBorderColor(Color.RED);
			
			if(counter<(sequence.size()-1))
				nextVertex = vertices.get(sequence.get(counter+1));
			
		    if	(e.getX()>(vertex.getMidX()-DIAMETER/2) &&
		    	e.getX()<(vertex.getMidX()+DIAMETER/2) &&
		    	e.getY()>(vertex.getMidY()-DIAMETER/2) &&
		    	e.getY()<(vertex.getMidY()+DIAMETER/2)
		    										) { 
		    	vertex.setBorderColor(Color.BLACK);
		    	if(nextVertex!=null)
		    	nextVertex.setBorderColor(Color.RED);
		    	counter++;
		    	vertex.setColor(color);
		    	 
		    	if(!graph.check()) {//code in case of defeat
		    		System.out.println("you win");
		    	}
	           
		    	if(counter==vertices.size()) {
		    		counter=0;
		    		if(graph.check()) {//code in case of victory
		    			System.out.println("you win");
		    		}
		    	}
		    	graphComponent.repaint();
	            return;
		    }
		}
	}

	public static void setColor(Color col) {
		  color = col;
	  }
	
	//this method generate a random sequence which is the order of colouring the nodes
	private ArrayList<Integer> randomSequence(int a) {
		ArrayList<Integer> sortedSequence = new ArrayList<Integer>();
		ArrayList<Integer> randomSequence = new ArrayList<Integer>();
		
		for(int i=0; i<=a; i++) {
			sortedSequence.add(i);
		}
		
		for(int i=0; i<=a; i++) {
			int index =(int)(Math.random()*sortedSequence.size());
			randomSequence.add(sortedSequence.get(index));
			sortedSequence.remove(index);
		}
		for(int i=0; i<=randomSequence.size(); i++) {
			if(randomSequence.get(i)==0) {
				randomSequence.remove(i);
				break;
			}	
		}
		
		return randomSequence;
			
			
		}
	
	}

// VertexMovementListener updates the location of the vertex based on mouse-dragging.
class VertexMovementListener extends MouseInputAdapter {
  private Graph graph;
  private static GraphComponent graphComponent;
  final int DIAMETER = 16;
  public VertexMovementListener(Graph graph, GraphComponent graphComponent) {
    this.graph = graph;
    this.graphComponent = graphComponent;
  }
  public void mouseMoved (MouseEvent e) {
    if (GraphViewer.DEBUG) {System.out.println("Mouse moved at X= " + e.getX() + " Y= " + e.getY());}
  }
  public void mouseDragged (MouseEvent e) {

    if (GraphViewer.DEBUG) {System.out.println("Mouse dragged at X= " + e.getX() + " Y= " + e.getY());}
    checkAndUpdateVertexLocation(e);
  }
  public void mouseReleased(MouseEvent e) {
    if (GraphViewer.DEBUG) {System.out.println("Mouse released at X= " + e.getX() + " Y= " + e.getY());}
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
              if (GraphViewer.DEBUG) {System.out.println("Danger! Node collision with vertex " + vertex.getVertexNumber());}
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

	
