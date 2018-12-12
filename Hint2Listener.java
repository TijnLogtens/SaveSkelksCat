import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
   // The hintlistener below needs to be attached to the hint button in the main game display
class Hint2Listener implements ActionListener {
	private GraphComponent graphComponent;
	private Graph graph;
	private Color color;
	//constructor
	public Hint2Listener(GraphComponent graphComponent) {
		this.graph = graphComponent.getGraph();
		this.graphComponent = graphComponent;

	}
 
	public void actionPerformed(ActionEvent event) {
    	HashMap<Integer, Vertex> vertices = graph.getVertices();
    	
    	for (int i=1; i<=vertices.size(); i++) {
    		Vertex vertex = vertices.get(i);
    		
    		if(vertex.getColor().equals(Color.BLACK)) {//if the next node isn't already coloured
    			
    			//decides which colour the next node should be
    			color = decideColor(graphComponent, vertex);
    			//set the color after it's been decided and repaint
    			vertex.setColor(color);
    			graphComponent.repaint();
    			
    			return;
    		}
    	}
	}
	
	
	//method for deciding the colour of the next node
	public Color decideColor(GraphComponent graphComponent, Vertex vertex) {
		HashMap<Integer, Vertex> vertices = graph.getVertices();
		Graph graph = graphComponent.getGraph();
		for(int i=1; i<vertices.size(); i++) {
			Color vertexColor = vertices.get(i).getColor();
			if(!vertexColor.equals(Color.BLACK)) {
				vertex.setColor(vertexColor);
				//in case the you can color the next node with a colour that you already used.
				if(graph.check() && !vertexColor.equals(Color.BLACK)) {
					return vertexColor;
				}
			}
		}
		// in case you need a new color not already used
		Color newColor = newColor(graphComponent);
		return newColor;
	}
	
	
	//method for picking a new random color from the colorGrid
	public Color newColor(GraphComponent graphComponent) {
		Graph graph = graphComponent.getGraph();
		HashMap<Integer, Vertex> vertices = graph.getVertices();
		
		//get random Color from the grid
		Color newColor = ColorGrid.getColor()[(int)(Math.random()*ColorGrid.getColor().length)];
		ArrayList<Color> colors = new ArrayList<Color>();
		
		for(int i=1; i<vertices.size(); i++) {
			colors.add(vertices.get(i).getColor());
		}
		
		//pick a random color untill that color is new.
		while(colors.contains(newColor)) {
			newColor = ColorGrid.getColor()[(int)(Math.random()*ColorGrid.getColor().length)];
		}
		
		return newColor;
		
	}
  
}
