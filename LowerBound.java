import java.io.*;
import java.util.*;

		class ColEdge
			{
			int u;
			int v;
			}
		
public class LowerBound
		{
		
		public final static boolean DEBUG = true;
		
		public final static String COMMENT = "//";
		
		public static void main( String args[] )
			{
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
			

			//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
			//! e[1] will be the second edge...
			//! (and so on)
			//! e[m-1] will be the last edge
			//! 
			//! there will be n vertices in the graph, numbered 1 to n

			//! INSERT YOUR CODE HERE!
			
			// Copying the edges of the graph to a 2d array
			int[][] edges = new int[m][2];
			for (int i=0; i<m; i++) {
				edges[i][0] = e[i].u;
			}
			for (int i=0; i<m; i++) {
				edges[i][1] = e[i].v;
			}
			
			
			
			ArrayList<ArrayList<Integer>> cliques = new ArrayList<ArrayList<Integer>>(); // Make an ArrayList to store the arrays with the nodes of the maximal cliques.
			/*for (int i=1; i<=10; i++) { // this for-loop is for testing
				ArrayList<Integer> newClique = new ArrayList<Integer>();
				cliques.add(newClique);
			}
			System.out.print(cliques.size());*/
			
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
			
			
			
			// ArrayList<Integer> intersect = intersectOf(1, P, edges);
			//for (int i=0; i<intersect.size(); i++) {
				//System.out.print(intersect.get(i) + " ");
			//} 
			//ArrayList<Integer> union = unionOf(8, P);
			//for (int i=0; i<union.size(); i++) {
				//System.out.print(union.get(i) + " ");
			//}
			
			
			/* 
				The Bron-Kerbosch algorithm is used to find and store all the 
				maximal cliques in the graph (i.e. complete graphs that are
				not subgraphs of another complete graph).
				The upper bound will be the number of nodes in the largest clique.
			*/
			
			
		}
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
			
			
			
}

