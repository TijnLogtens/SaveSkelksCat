import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

// VertexClickListener checks whether the user clicked on a vertex, and updates the color of that vertex.
class VertexClickListener3Mode extends MouseAdapter {
	private Graph graph;
	private static Color color = Color.BLACK;
	private GraphComponent3Mode graphComponent;
	final int DIAMETER = 16;
	int counter=0;
	private ArrayList<Integer> sequence = null;

	public VertexClickListener3Mode (Graph graph, GraphComponent3Mode graphComponent) {
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
		    		System.out.println("you loose");
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
