import java.util.ArrayList;
import java.util.HashMap;



public class DSatur {
	private ColEdge[] edge;
	private HashMap <Integer, Integer> saturation = new HashMap<>();
	private HashMap <Integer, Integer> numEdges = new HashMap<>();
	private int numNodes;
	private int upperBound;
	private int nextColour;
	private int countColors = 0;
	private int counter = 0;
	private ArrayList<Integer> colouredNodes = new ArrayList<Integer>();
	private HashMap <Integer, Integer> colours = new HashMap<>();
	private final boolean DEBUG = true;
	
	public DSatur(ColEdge[] edge, int numNodes ){
		this.edge = edge;
		this.numNodes = numNodes;
		
		initiateSaturation(saturation, numNodes);
		assignNumEdges(numEdges,edge);
		upperBound= compute(edge, saturation	, colours);
		if(DEBUG) {
		System.out.println(numEdges);
		System.out.println();
		System.out.println(saturation);
		System.out.println();
		System.out.println(colours);
		System.out.println();
		System.out.println(upperBound);
		}
	}
	
	//method that computes the upperBound
	public int compute(ColEdge[] edge, HashMap <Integer, Integer> saturation, HashMap <Integer, Integer> colours ) {
		while(true) {//it runs untill it colours every node
    
		if(counter == saturation.size()) {//every node it's been coloured
			return count(colours);
		}
		else {
			counter++;
			int nextNode = pick(saturation, numEdges);
			nextColour = chooseNextColour(nextNode, edge, colours);
			updateSaturation(nextNode, saturation, edge);
			colourNode(nextNode, colours, nextColour);
		}
		}
	}
	
	//method for assigning the number of edges to each node
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
	
	//initiate saturation to 0
	public void initiateSaturation(HashMap <Integer, Integer> saturation, int numNodes) {
		for(int i=1; i<=numNodes; i++) {
			saturation.put(i, 0);
			numEdges.put(i, 0);
		}
	}
	
	//method that choose the next node that needs to be coloured
	public int pick(HashMap <Integer, Integer> saturation, HashMap <Integer, Integer> numEdges) {
		int nextNode = 0;
		int maxSat = 0;
		for(int i=1; i<saturation.size(); i++) {
			if(!colouredNodes.contains(i)) {
				nextNode = i;
				maxSat = saturation.get(nextNode);
				break;
			}
		}
		
		for(int i=2; i<=saturation.size(); i++) {
			if(!colouredNodes.contains(i)) {
				int sat = saturation.get(i);
				if(sat>maxSat) {
					nextNode = i;
					maxSat = sat;
				}
				else if(sat == maxSat) {
					if(numEdges.get(i)>numEdges.get(nextNode)) {
						nextNode = i;
					}
				}
			}
		}
		return nextNode;
	}
	
	//method that colours the node
	public void colourNode(int node, HashMap <Integer, Integer> colours, int nextColour ) {
		colours.put(node, nextColour);
		colouredNodes.add(node);
	}
	
	//method that updates saturation every time a new node is coloured
	public void updateSaturation(int node, HashMap <Integer, Integer> saturation, ColEdge[] edge){
		for(int i=0; i<edge.length; i++) {
			if(edge[i].u == node) {
				int newSat = saturation.get(edge[i].v)+1;
				saturation.replace(edge[i].v, newSat);
			}
			else if(edge[i].v == node) {
				int newSat = saturation.get(edge[i].u)+1;
				saturation.replace(edge[i].u, newSat);
			}
		}
	}
	
	
	// method that chooses the colour for colouring the next node
	public int chooseNextColour(int node, ColEdge[] edge,HashMap <Integer, Integer> colours) {
		ArrayList<Integer> illegalColors = new ArrayList<Integer>();
		for(int i=0; i<edge.length; i++) {
			if(edge[i].u == node) {
				if(!(colours.get(edge[i].v) == null))
					illegalColors.add(colours.get(edge[i].v));
			}
			else if(edge[i].v == node) {
				if(!(colours.get(edge[i].u) == null))
					illegalColors.add(colours.get(edge[i].u));
			}
		}
		for(int i=0; i<countColors; i++) {
			if(!illegalColors.contains(i)) {
				return i;
			}
		}
		countColors++;
		return countColors;
	}
	
	//method for counting the number of colours used (return the upperBound)
	public int count(HashMap <Integer, Integer> colours) {
		int max = 0;
		for(int i=1; i<=numNodes; i++) {
			int num = colours.get(i);
			if(num > max)
				max = num;
		}
		return ++max;
	}
	
	//return the upperBound
	public int getupperBound() {
		return upperBound;
	}
	
	//return a feasible solution for colouring the graph
	public HashMap<Integer, Integer> getPossibleSolution(){
		return colours;
	}
}
