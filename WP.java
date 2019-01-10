import java.util.ArrayList;
import java.util.HashMap;

public class WP {
	private ColEdge[] edge;
	private HashMap <Integer, Integer> numEdges = new HashMap<>();
	private ArrayList<Integer> sortedNodes = new ArrayList<Integer>();
	private int numNodes;
	private int upperBound;
	private ArrayList<Integer> colourSet = new ArrayList<Integer>();
	private HashMap <Integer, Integer> colours = new HashMap<>();
	private int nextColour;
	private final boolean DEBUG = true;
	
	
	//constructor
	public WP(ColEdge[] edge, int numNodes) {
		this.edge = edge;
		this.numNodes = numNodes;
		initiateColours(colourSet, numNodes);
		sortedNodes = sortNodes(edge, numEdges);
		compute(edge, colourSet, sortedNodes, colours);
		upperBound = count(colours);
		
		//debug infos:
		if(DEBUG) {
			System.out.println(colours);
			System.out.println();
			System.out.println(upperBound);
		}
	}
	
	
	//.....methods.....................................//
	
	
	//method that computes the upperBound
	public void compute(ColEdge[] edge,  ArrayList<Integer> colourSet, ArrayList<Integer> sortedNodes, HashMap <Integer, Integer> colours) {
		while(colours.size()!=numNodes) {//run until every node is coloured
			colourNextNode(colourSet, sortedNodes,colours);
			colourOtherNodes(colourSet, sortedNodes,colours);
		}
	}
	
	
	
	//method that colour the first node of a new colour
	public void colourNextNode(ArrayList<Integer> colourSet, ArrayList<Integer> sortedNodes, HashMap <Integer, Integer> colours) {
		int node = sortedNodes.get(0);
		nextColour = colourSet.get(0);
		colours.put(node, nextColour);
		sortedNodes.remove(0);
	}
	
	
	
	//method that colours the rest of the nodes with the same colour of the first node
	public void colourOtherNodes(ArrayList<Integer> colourSet, ArrayList<Integer> sortedNodes, HashMap <Integer, Integer> colours) {
		for(int i=1; i<= numNodes; i++) {
			if(!colours.containsKey(i) && isColourable(i, edge, colours)) {
				colours.put(i, nextColour);
			}
		}
		colourSet.remove(0);
	}
	
	
	
	//method that loops throught the nodes and decides which node is cololourable with the same colour
	public boolean isColourable(int node, ColEdge[] edge, HashMap <Integer, Integer> colours ) {
		boolean colourable = true;
		for(int i=0; i<edge.length; i++) {
			int newNode1 = edge[i].u;
			int newNode2 = edge[i].v;
			if(newNode1==node) {
				if(colours.containsKey(newNode2) && colours.get(newNode2)==nextColour) {
					colourable = false;
				}
			}
			else if(newNode2==node) {
				if(colours.containsKey(newNode1) && colours.get(newNode1)==nextColour) {
					colourable = false;
				}
			}
		}
		return colourable;
	}
	
	
	//method for creating a set of colours
	public void initiateColours(ArrayList<Integer> colours, int numNodes) {
		for(int i=0; i<numNodes; i++) 
			colours.add(i);
	}
	
	
	
	//method that sorts nodes from highest degree to lowest
	public ArrayList<Integer> sortNodes(ColEdge[] edge, HashMap <Integer, Integer> numEdges){
		ArrayList<Integer> sortedNodes = new ArrayList<Integer>();
		assignNumEdges(numEdges, edge);
		int max = 0;
		int counter = 0;
		for(int j=0; j<numEdges.size(); j++) {
			
			for(int i=1; i<=numEdges.size(); i++) {
				
					int val = numEdges.get(i);
					if(max == 0 || val > numEdges.get(max)) {
						if(!sortedNodes.contains(i)) 
						max = i;
					}
			}
				sortedNodes.add(max);
				max = 0;
		}
		return sortedNodes;
	}
	
	
	
	//method that counts how many colours have been used at the end of the algorithm (returns upperBound)
	public int count(HashMap <Integer, Integer> colours) {
		int max = 0;
		for(int i=1; i<=numNodes; i++) {
			int num = colours.get(i);
			if(num > max)
				max = num;
		}
		return ++max;
	}

		
	
	//method for assigning to each node it's number of edges
	public void assignNumEdges(HashMap <Integer, Integer> numEdges, ColEdge[] edge) {
		for(int i=1; i<=numNodes; i++) {
			numEdges.put(i, 0);
		}
		for(int i=0; i<edge.length; i++) {
			int  node1 = edge[i].u;
			int node2 = edge[i].v;
			int newNum1 = numEdges.get(node1)+1;
			int newNum2 = numEdges.get(node2)+1;
			numEdges.replace(node1, newNum1);
			numEdges.replace(node2, newNum2);
		}
	}
	
	
	//method for retrieving the upperBound
	public int getUpperBound() {
		return upperBound;
	}
	
	
	//return a feasible solution for colouring the graph
		public HashMap<Integer, Integer> getPossibleSolution(){
			return colours;
		}
	
	
}
