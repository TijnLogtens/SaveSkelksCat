import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.colorchooser.*;
import javax.swing.event.MouseInputAdapter;

class ColEdge {		//this is linked to the BruteForce class.
	int u;
	int v;
}

public class GraphGame extends JPanel implements Runnable{

	//Window size
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;

	//Render
	private Graphics2D g2d;
	private BufferedImage image;

	//Run
	private Thread thread;
	private long tTime;
	private boolean alive;

	//Graph reader related
	public final static boolean DEBUG = false;
	public final static String COMMENT = "//";
	private File file;

	//Colouring algorithms related
	public static boolean tooLarge = true; //set to true will compute the bruteforce, set to false will compute the upper and lower bounds
	public static GraphComponent graphComponent;

	public GraphGame(){
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify(){
		super.addNotify();
		thread = new Thread(this);
		thread.start();
	}

	private void setFPS(int FPS){
		tTime = 1000 / FPS;
	}

	public void run(){
		if(alive){
			return;
		}
		init();
		requestRender();

		long sTime;
		long pTime;
		long delay;

		while(alive){
			sTime = System.nanoTime();

			update();
			requestRender();

			pTime = System.nanoTime() - sTime;
			delay = tTime - pTime / 1000000;
			if(delay > 0){
				try{
					Thread.sleep(delay);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	private void init(){
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g2d = image.createGraphics();
		alive = true;
		setUp();
	}

	private void update(){
		//Put all continuously changing things here
	}

	private void setUp(){
		//Button that allows opening a file from a directory
		JButton graphSelect = new JButton("Select Graph");
		graphSelect.setLocation(50, 50);
		graphSelect.setSize(120, 30);
		graphSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				fileChooser();
			}
		});
		add(graphSelect);

		//Button that opens game mode 1
		JButton mode1opener = new JButton("Play game mode 1");
		mode1opener.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				gameMode1();
			}
		});
		add(mode1opener);
	}

	private void requestRender(){
		render(g2d);
		Graphics graphics = getGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
	}

	private void fileChooser(){
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int val = fileChooser.showOpenDialog(null);
		if(val == JFileChooser.APPROVE_OPTION){
			file = fileChooser.getSelectedFile();
			readGraph(file);
		}
	}

	public static int getWindowSize(){
		return WIDTH;
	}

	private void gameMode1(){
    EventQueue.invokeLater(new Runnable(){
    	@Override
        public void run(){
          JFrame gamemode1 = new JFrame("Game Mode 1");
	        gamemode1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	          JPanel panG1 = new JPanel();
						panG1.setPreferredSize(new Dimension(WIDTH, HEIGHT));

						//Set up for layout
						Border b = getBorder();
						Border m = new EmptyBorder(10, 10, 10, 10);
						panG1.setBorder(new CompoundBorder(b, m));

						GridBagLayout layout = new GridBagLayout();
						layout.columnWidths = new int[] {WIDTH-200, 20, 0};
						layout.rowHeights = new int[] {HEIGHT-200, 20, 0};
						layout.columnWeights = new double[] {1.0, 0.1, Double.MIN_VALUE};
						layout.rowWeights = new double[] {1.0, 0.0, 0.0, Double.MIN_VALUE};

						panG1.setLayout(layout);
						panG1.setFocusable(true);
						panG1.requestFocus();
	          panG1.setOpaque(true);

						//Attempt at implementation of the graph display components
							ColEdge[] colEdge1 = GenerateGraph.randomGraph();
							int[][] colEdge = new int[colEdge1.length][2];

							for(int i=0; i<colEdge.length; i++) {
					 			colEdge[i][0] = colEdge1[i].u;
					 			colEdge[i][1] = colEdge1[i].v;
					 		}

				      Graph graph = new Graph(colEdge, layout.columnWidths[0], 0, layout.rowHeights[0]);
							GraphComponent graphComponent = new GraphComponent(graph);
				      VertexClickListener clickListener = new VertexClickListener(graph, graphComponent);
				      graphComponent.addMouseListener(clickListener);
				      VertexMovementListener movementListener = new VertexMovementListener(graph, graphComponent);
				      graphComponent.addMouseMotionListener(movementListener);
							ColorListener listener = new ColorListener();
 							graphComponent.addMouseListener(listener);

						GridBagConstraints testCon = new GridBagConstraints();
						testCon.fill = GridBagConstraints.BOTH;
						testCon.insets = new Insets(0, 0, 5, 5);
						testCon.gridx = 0;
						testCon.gridy = 0;
						panG1.add(graphComponent, testCon);

          gamemode1.setContentPane(panG1);
          gamemode1.setResizable(false);
          gamemode1.pack();
          gamemode1.setPreferredSize(new Dimension(WIDTH, HEIGHT));
          gamemode1.setLocationRelativeTo(null);
          gamemode1.setVisible(true);
      }
    });
  }

	public void readGraph(File file){

		boolean seen[] = null;

		//! n is the number of vertices in the graph
		int n = -1;

		//! m is the number of edges in the graph
		int m = -1;

		//! e will contain the edges of the graph
		ColEdge e[] = null;

		try {
		    FileReader fr = new FileReader(file); //was inputfile
		    BufferedReader br = new BufferedReader(fr);

		    String record = new String();

			//! THe first few lines of the file are allowed to be comments, starting with a // symbol.
			//! These comments are only allowed at the top of the file.

			//! -----------------------------------------
		    while ((record = br.readLine()) != null){
				if( record.startsWith("//") ) continue;
				break; // Saw a line that did not start with a comment -- time to start reading the data in!
			}

			if( record.startsWith("VERTICES = ") ){
				n = Integer.parseInt( record.substring(11) );
				if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
			}

			seen = new boolean[n+1];

			record = br.readLine();

			if( record.startsWith("EDGES = ") ){
				m = Integer.parseInt( record.substring(8) );
				if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
			}

			e = new ColEdge[m];

			for( int d=0; d<m; d++){
				if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
					record = br.readLine();
					String data[] = record.split(" ");
					if( data.length != 2 ){
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
				if( surplus != null ){
					if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");
				}

		} catch (IOException ex){
	    // catch possible io errors from readLine()
		    System.out.println("Error! Problem reading file "+ file);
			System.exit(0);
		}

		for( int x=1; x<=n; x++ ){
			if( seen[x] == false ){
				if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
			}
		}

		//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
		//! e[1] will be the second edge...
		//! (and so on)
		//! e[m-1] will be the last edge
		//!
		//! there will be n vertices in the graph, numbered 1 to n

	//Brute Force section
		//create an empty array of colours.
		ArrayList<Integer> colours = new ArrayList<Integer>();
		BruteForce a = new BruteForce();
		if (tooLarge == false){
			a.search(n, colours, 2, e);
		}

//Upperbound section
		if(tooLarge == true){
		int[][] edges = new int[m][2];
		for (int i=0; i < m; i++) {
			edges[i][0] = e[i].u;
		}
		for (int i=0; i<m; i++) {
			edges[i][1] = e[i].v;
		}

		// The method greedy is called to compute the maximum vertex degree of the graph.
		int upperBound = greedy(edges, n) + 1;
		System.out.println("The upper bound of the graph is " + upperBound);
		//Lowerbound section
				ArrayList<ArrayList<Integer>> cliques = new ArrayList<ArrayList<Integer>>(); // Make an ArrayList to store the arrays with the nodes of the maximal cliques.
				ArrayList<Integer> P = new ArrayList<Integer>(); // P contains all the vertices in the graph
					for (int i=0; i<edges.length; i++) { // checking 1st column of edges[][] and adding non-duplicate nodes to P
						boolean found = false;
						for (int j=0; j<P.size(); j++) {
							if (P.get(j) == edges[i][0]) {
								found = true;
							}
						}
						if (!found) {
							P.add(edges[i][0]);
					}
				}
				ArrayList<Integer> R = new ArrayList<Integer>(); // R is now empty but will contain the vertices of potential cliques
				ArrayList<Integer> X = new ArrayList<Integer>(); // X is now empty but will contain vertices already in some cliques or processed

				bronkerbosch(P, R, X, edges, cliques);
				int largestCliqueSize = 0;
					for (int i=0; i<cliques.size(); i++) {
						if (cliques.get(i).size()>largestCliqueSize) {
							largestCliqueSize = cliques.get(i).size();
						}
					}
				System.out.println("The lower bound of the graph is " + largestCliqueSize);
		}
	}

	//Method for checking Upperbound
	public static int greedy(int[][] edges, int n) {
		int[] edgesPerNode = new int[n];
		int counter = 0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<edges.length; j++) {
				if (edges[j][0] == i) {
					counter++;
				}
				if (edges[j][1] == i) {
					counter++;
				}
				edgesPerNode[i] = counter;
			}
			counter = 0;
		}
		int maxVertexDegree = 0;
		for (int i=0; i<n; i++) {
			if (edgesPerNode[i]>maxVertexDegree) {
				maxVertexDegree = edgesPerNode[i];
			}
		}
		return maxVertexDegree;
	}

//Method for Lower Bound
	public static void bronkerbosch (
		ArrayList<Integer> P,
		ArrayList<Integer> R,
		ArrayList<Integer> X,
		int[][] edges,
		ArrayList<ArrayList<Integer>> cliques) {
		if (P.size() == 0 && X.size() == 0) {
			cliques.add(R);
		}
		for (int i=0; i<P.size(); i++) {
			bronkerbosch(intersectOf(P.get(i), P, edges), unionOf(P.get(i), R), intersectOf(P.get(i), X, edges), edges, cliques);
			P.remove(new Integer(i));
			X = unionOf(i, X);
		}
	}

	public static ArrayList<Integer> intersectOf(int v, ArrayList<Integer> oldList, int[][]edges) {
		ArrayList<Integer> newList = new ArrayList<Integer>();
		for (int i=0; i<oldList.size(); i++) {
			for (int j=0; j<edges.length; j++) {
				if ((edges[j][0] == v) && (edges[j][1] == oldList.get(i))) {
					newList.add(edges[j][1]);
				}
				if ((edges[j][1] == v) && (edges[j][0] == oldList.get(i))) {
					newList.add(oldList.get(i));
				}
			}
		}
		return newList;
	}

	public static ArrayList<Integer> unionOf(int v, ArrayList<Integer> oldList)	{
		ArrayList<Integer> newList = new ArrayList<Integer>(oldList);
		boolean found = false;
		for (int i=0; i<oldList.size(); i++) {
			if (oldList.get(i) == v) {
				found = true;
			}
		}
		if (!found) {
				newList.add(v);
		}
		return newList;
	}

	public static void printArrayList(ArrayList<ArrayList<Integer>> cliques) {
		System.out.println(cliques);
	}

	public void render(Graphics2D g2d){

	}
}
