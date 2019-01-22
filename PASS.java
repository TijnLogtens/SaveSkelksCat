/** The plan:
* Initial values:
* Graph (G) = (V, E); The set of uncoloured vertexes (U) := V;
* Partial colouring (P) = empty set; The size of the minimal colouring (Cmax) := 0;
* A threshold (TH) := 3;
*
* Step 1:
* Select a vertex from U with maximum saturation degree.
* Let T be the set of tied vertices, defined as the the vertices with max saturation degree
* If a certain parameter (m) < TH; Use PASS for tiebreaking; else use DSATURh
*
* Step 2:
* If U = empty set, Cmax = c(P), return chromatic number
*
* Step 3:
* Select c(v) with the lowest possible colour used in P.
* If none are available assign c(v):= c(P) + 1
*
* Step 4:
* If c(v) >= Cmax; return
*
* Step 5:
* PASS (P + (v,c(v)), U - {v}, Cmax, TH)
**/
import java.util.ArrayList;
import java.util.HashMap;

public class PASS {
	private ColEdge[] edge;
	private HashMap <Integer, Integer> saturation = new HashMap<>();
	private HashMap <Integer, Integer> numEdges = new HashMap<>();
	private HashMap <Integer, Integer> T = new HashMap<>();
	private int numNodes;
	private int UB;
	private int nextColour;
	private int countColors = -1;
	private final int TH = 3;
	private int Vs = 0;
	private int Vi = 0;
	private int maxSat = 0;
	private int counter = 0;
	private ArrayList<Integer> colouredNodes = new ArrayList<Integer>();
	private HashMap<Integer, Integer> colours = new HashMap<>();
	private int[] sequence;
	private final boolean DEBUG = true;

	public PASS(ColEdge[] edge, int numNodes ){
		this.sequence = new int[numNodes];
		this.edge = edge;
		this.numNodes = numNodes;

		initiateSaturation(saturation, numNodes);
		assignNumEdges(numEdges, edge);
		UB = compute(edge, saturation, colours, T);
		if(DEBUG) {
		System.out.println(numEdges);
		System.out.println();
		System.out.println(saturation);
		System.out.println();
		System.out.println(colours);
		System.out.println();
		System.out.println("UB = " + UB);
		}
	}

	//Return chromatic number
	public int compute(ColEdge[] edge, HashMap <Integer, Integer> saturation, HashMap <Integer, Integer> colours, HashMap<Integer, Integer> T) {
		while(true) {//it runs untill it colours every node
			int vertex = 0;
			//Step1
			generateT(saturation, numEdges);
			if(T.size()==1) {
			//if there is no tie.
			 vertex = T.get(0);
     		}
			else {//Step 2
				if(colouredNodes.size() == saturation.size()){//every node is coloured
					return count(colours);
				}
				if(m()&&countColors!=-1){ //PASSh
					vertex = PASSSelection(T, colours, edge);
				}
				else{ //DSATURh
					vertex = DSATURSelection(saturation, numEdges);
				}
			}
			sequence[counter] = vertex;
			counter++;
			//Step 3
			updateSaturation(vertex, saturation, edge);
			nextColour = chooseNextColour(vertex, edge, colours);
			colourNode(vertex, colours, nextColour);
		}
	}

	/*Finds the next vertex to colour by maximum the number of common available colours
		in the uncoloured neighbourhoud of maxSat vertices*/
	private int PASSSelection(HashMap<Integer, Integer> T, HashMap<Integer, Integer> colours, ColEdge[] edge){
		HashMap<Integer, Integer> bestBoiStorage = new HashMap<>();
		HashMap<Integer, Integer> bestBoiFinder = new HashMap<>();
		for(int s = 0; s < T.size(); s++){
			int coloursPos = 0;
			Vs = T.get(s);
			for(int i = 0; i < T.size(); i++){
				if(i != s){
					Vi = T.get(i);
					coloursPos += same(edge, colours, Vs, Vi);
				}
			else{
					break;
				}
			}
			bestBoiStorage.put(s, Vs);
			bestBoiFinder.put(s, coloursPos);
		}
		int mostColPos = 0;
		int mostColKey = 0;
		for(int b = 0; b < bestBoiFinder.size(); b++){
			if(mostColPos == 0 || bestBoiFinder.get(b).compareTo(mostColPos) > 0){
				mostColPos = bestBoiFinder.get(b);
				mostColKey = b;
			}
		}
		return bestBoiStorage.get(mostColKey);
	}

	//Checks for the amount of common possible colours
	private int same(ColEdge[] edge, HashMap<Integer, Integer> colours, int Vs, int Vi){
		HashMap<Integer, Integer> cloneCol = (HashMap) colours.clone();
		for(int j = 0; j < edge.length; j++){
			if((edge[j].v == Vs || edge[j].v == Vi) && (colours.containsKey(edge[j].u))){
				cloneCol.remove(edge[j].u);
			}else if((edge[j].u == Vs || edge[j].u == Vi) &&(colours.containsKey(edge[j].v))){
				cloneCol.remove(edge[j].v);
			}
		}
		return cloneCol.size();
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

	//Generates a set of uncoloured nodes with maximum saturation (T)
	public void generateT(HashMap <Integer, Integer> saturation, HashMap <Integer, Integer> numEdges) {
		int nextNode = 0;
		int key = 0;
		int amountUncoloured = numNodes - colouredNodes.size();
		T.clear();

    //Starts by assuming maxSat = sat first uncoloured vertex
   // for(int j = 0; j < amountUncoloured; j++){
  		for(int i = 1; i < saturation.size(); i++){
  			if(!colouredNodes.contains(i)){
  				nextNode = i;
  				maxSat = saturation.get(nextNode);
  				break;
  			}
  		}
      //Starts updating maxSat for uncoloured vertices
  		for(int i = 2; i <= saturation.size(); i++){
  			if(!colouredNodes.contains(i)){
  				int sat = saturation.get(i);
  				if(sat > maxSat){
  					nextNode = i;
  					maxSat = sat;
  				}
  			}
  		}
  //  }
		//Adds vertices with maxSat to a set T
		for(int a = 1; a <= saturation.size(); a++){
			if(saturation.get(a) == maxSat && !colouredNodes.contains(a)){
			nextNode = a;
			T.put(key, nextNode);
			key++;
			}
		}
	}

  //Boolean for determining which tiebreaking method to use
  private boolean m(){
    int c = calculateC(colours, T);
    int m = (countColors+1) - c;
    if (m <= TH){
      return true;
    }else{
      return false;
    }
  }
  
  private int calculateC(HashMap <Integer, Integer> colours, HashMap<Integer, Integer> T) {
	  ArrayList<Integer>notAvaiable = new ArrayList<Integer>();
	  for(int i=0; i<colours.size(); i++) {
		  boolean[] tf = new boolean[T.size()];
		  int colour = i;
		  for(int a=0; a<T.size(); a++) {
			  int node = T.get(a);
			  tf[a] = false;
			  for(int j=0; j<edge.length; j++) {
			  if(edge[j].u == node) {
					if(colours.get(edge[j].v)!=null && colours.get(edge[j].v) == colour){
						tf[a]= true;
						break;
					}
			   }
			   else if(edge[j].v == node) {
				   if(colours.get(edge[j].u)!=null &&  colours.get(edge[j].u) == colour) {
						tf[a] = true;
						break;
					}
			   }	
			}
		  }
		  boolean tf2 = true;
		  for(int f=0; f<tf.length; f++) {
			  if(tf[f]==false) 
				  tf2 = false;
		  }
		  if(tf2)
			  notAvaiable.add(colour);
	 }
	  return notAvaiable.size();
   }

	//method that picks next vertex based on DSATUR tiebreaking
	public int DSATURSelection(HashMap <Integer, Integer> saturation, HashMap <Integer, Integer> numEdges) {
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


	//method that chooses the colour for colouring the next node, the least used legal colour
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
		for(int i=0; i<=countColors; i++) {
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
	public int getUB() {
		return UB;
	}
	
	//return sequence
	public int[] getSequence() {
		return sequence;
	}
	public HashMap<Integer, Integer> getColours(){
		return colours;
	}
}
