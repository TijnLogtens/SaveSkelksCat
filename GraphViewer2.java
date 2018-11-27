import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.colorchooser.*;

class VertexClickListener extends MouseAdapter {
  private Graph graph;
  private GraphComponent graphComponent;
  ArrayList<Color> colors = new ArrayList<Color>();
  private int counter =0;
  final int DIAMETER = 16;
  public VertexClickListener (Graph graph, GraphComponent graphComponent) {
	colors.add(Color.BLACK);
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
    	  Color newcol = new Color((int)(Math.random()*255), (int)(Math.random()*255),(int)(Math.random()*255));
    	  colors.add(newcol);
    	  for(int j=0; j<colors.size(); j++) {
    	  if(vertex.getColor().equals(colors.get(j))) {
    		  vertex.setColor(colors.get(j+1));
    		  break;
    	  }
    	  }
    		
            counter++;
    	 
            graphComponent.repaint();
            return;
          }
    }
  }
}

public class GraphViewer {

  public static void main(String[] args) {
    
    ColEdge[] colEdge1 = GenerateGraph.randomGraph();
    int[][] colEdge = new int[colEdge1.length][2];
    for(int i=0; i<colEdge1.length; i++) {
    	colEdge[i][0]=colEdge1[i].u;
    	colEdge[i][1]=colEdge1[i].v;
    	System.out.print("["+colEdge[i][0]+","+colEdge[i][1]+"] ");
    }
    		Graph graph = new Graph(colEdge);
    System.out.println(graph.getVertices());

    JFrame window = new JFrame();
    Container contentPane = window.getContentPane();
    contentPane.setPreferredSize(new Dimension(800, 800));
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GraphComponent graphComponent = new GraphComponent(graph);
    VertexClickListener clickListener = new VertexClickListener(graph, graphComponent);
    graphComponent.addMouseListener(clickListener);
    window.add(graphComponent);
    window.pack();
    window.setVisible(true);

  }
}
