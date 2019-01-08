import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.colorchooser.*;
import javax.swing.event.MouseInputAdapter;
class ColEdge
  {
  int u;
  int v;
  }
public class GraphViewer  {
  public final static boolean DEBUG = false;
  public final static String COMMENT = "//";
  public static void main(String[] args) {

      ColEdge[] e = readGraph(args);
      int[][] preGraph = colEdgeToPreGraph(e);// TBFinished
      Graph graph = new Graph(preGraph);
      System.out.println(graph.getVertices());

      JFrame window = new JFrame();
      Container contentPane = window.getContentPane();
      contentPane.setPreferredSize(new Dimension(1000, 1000));
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
    public static int[][] colEdgeToPreGraph(ColEdge[] e) {
      int[][] preGraph = new int[e.length][2];
      for (int i=0; i<preGraph.length; i++) {
        preGraph[i][0] = e[i].u;
        preGraph[i][1] = e[i].v;
      }
      return preGraph;
    }
    public static ColEdge[] readGraph (String[] args) {
      if( args.length < 1 )
				{
				System.out.println("Error! No filename specified.");
				System.exit(0);
				}


			String inputfile = args[0];

			boolean seen[] = null;

			//! n is the number of vertices in the graph
			int n = -1;

			//! m is the number of edges in the graph
			int m = -1;

			//! e will contain the edges of the graph
			ColEdge e[] = null;

			try 	{
			    	FileReader fr = new FileReader(inputfile);
			        BufferedReader br = new BufferedReader(fr);

			        String record = new String();

					//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
					//! These comments are only allowed at the top of the file.

					//! -----------------------------------------
			        while ((record = br.readLine()) != null)
						{
						if( record.startsWith("//") ) continue;
						break; // Saw a line that did not start with a comment -- time to start reading the data in!
						}

					if( record.startsWith("VERTICES = ") )
						{
						n = Integer.parseInt( record.substring(11) );
						if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
						}

					seen = new boolean[n+1];

					record = br.readLine();

					if( record.startsWith("EDGES = ") )
						{
						m = Integer.parseInt( record.substring(8) );
						if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
						}

					e = new ColEdge[m];

					for( int d=0; d<m; d++)
						{
						if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
						record = br.readLine();
						String data[] = record.split(" ");
						if( data.length != 2 )
								{
								System.out.println("Error! Malformed edge line: "+record);
								System.exit(0);
								}
						e[d] = new ColEdge();

						e[d].u = Integer.parseInt(data[0]);
						e[d].v = Integer.parseInt(data[1]);

						seen[ e[d].u ] = true;
						seen[ e[d].v ] = true;

						if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);

						}

					String surplus = br.readLine();
					if( surplus != null )
						{
						if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");
						}

					}
			catch (IOException ex)
				{
		        // catch possible io errors from readLine()
			    System.out.println("Error! Problem reading file "+inputfile);
				System.exit(0);
				}

			for( int x=1; x<=n; x++ )
				{
				if( seen[x] == false )
					{
					if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
					}
				}
      return e;
    }
}


// VertexClickListener checks whether the user clicked on a vertex, and updates the color of that vertex.
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
              if (GraphViewer.DEBUG) {System.out.println("Danger! Node collision with vertex " + vertex.getVertexNumber());}
              collision = true;
            }
          }
    }
    return collision;
  }
}
