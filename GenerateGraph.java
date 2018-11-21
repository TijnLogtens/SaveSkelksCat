import java.util.Random;

public class GenerateGraph {
	private static int maxNodes = 30;
	private static int minNodes = 4;
	private static int maxEdges = 60; //needs to be less then number of nodes^2. 
	private static int minEdges = 0;//needs to be less then maxNodes (if 0 then the actual minimum will be the num of edges because every node needs at least an edge)
    private static Random rand = new Random();
    private static int nodes;
    
	public static ColEdge[] randomGraph() {
		nodes = minNodes + rand.nextInt(maxNodes-minNodes);
		minEdges += nodes;  //it's not possible to have less edges then nodes
		int edges = minEdges + rand.nextInt(maxEdges); //random number between minEdge and maxEdge
		ColEdge[] e = new ColEdge[edges];
			 
			 for(int i=0; i<nodes; i++) { //this loop assign to each node an edge connected to another random node
				 e[i] = new ColEdge();
				 e[i].v = i+1;
				 e[i].u = 1 + rand.nextInt(nodes);
				 while(e[i].v == e[i].u)//you cannot have an edge that connects the same node it would make the colouring impossible.
					 e[i].u = 1 + rand.nextInt(nodes);
			}
			 //if the numb of edges is grater then the number of nodes then this loop will complete all edges
			 for(int i=nodes; i<edges; i++) {
				 e[i] = new ColEdge();
				 e[i].v = 1 + rand.nextInt(nodes);
				 e[i].u = 1 + rand.nextInt(nodes);
				 while(e[i].v == e[i].u)
					 e[i].u = 1+ rand.nextInt(nodes);
			}
	return e;
	}
	public static int getNodes() {//this method is important because when you call the search method you need to know the number of nodes
		return nodes;
	}
	
}
