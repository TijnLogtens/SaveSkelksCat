
import java.util.HashMap;

public class BackTrack {
	private int[] sequence;
	private int chromaticNum;
	private ColEdge[] edge;
	private int numNodes;
	private int counter = 0;
	private final boolean DEBUG = true;
	private HashMap<Integer, Integer> colours = new HashMap<>();
	
	public BackTrack(ColEdge[] edge, int[] sequence, int numNodes, int UB) {
		this.edge = edge;
		this.sequence = sequence;
		this.numNodes = numNodes;
		int k = UB-1;
		chromaticNum = k;
		
		chromaticNum = compute(edge, sequence, colours, k);
		if(DEBUG) {
			System.out.println("chromatic number = " + chromaticNum);
		}
	}
	
	//recursive method for the search
	public int compute(ColEdge[] edge, int[] sequence, HashMap<Integer, Integer> colours, int k) {
		if(colours.size()!=numNodes) {
			for(int i=0; i<k; i++) {
				if(counter<0)
					return chromaticNum;
				int nextNode = sequence[counter];
				colourNode(nextNode, colours, i);
				if(check(colours, edge)) {
					counter++;
					compute(edge, sequence, colours, k);
				}
			}
			if(counter<0)
				return chromaticNum;
			colours.remove(sequence[counter]);
			counter--;
			
		}
		else if(check(colours, edge)) {//there is a solution for this k
			if(DEBUG)System.out.println("new upper bound: "+k);
			chromaticNum = k;
			colours.clear();
			counter =0;
			compute(edge, sequence, colours, k-1);//re-search with k-1
		}
		return chromaticNum;
		
			
		
	}
	
	public void colourNode(int nextNode, HashMap<Integer, Integer> colours, int colour) {
		colours.put(nextNode, colour);
	}
	
	//method for checking legality of neighbours nodes
	public boolean check(HashMap<Integer, Integer> colours, ColEdge[] edge) {
		for(int i=0; i<edge.length; i++) {
			int node1 = edge[i].u;
			int node2 = edge[i].v;
			if(colours.containsKey(node1) && colours.containsKey(node2) && colours.get(node1)==colours.get(node2))
				return false;
		}
		return true;
	}

}
