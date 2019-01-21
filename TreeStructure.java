
public class TreeStructure {
	private int numNodes;
	private ColEdge[] edge;
	private boolean isTree;
	private final boolean DEBUG = false;

	public TreeStructure(ColEdge[] e, int n) {
		numNodes = n;
		edge = e;
		// to be a tree it needs to have n number of nodes and n-1 edges
		if(e.length == n-1)
			isTree = detectTree(edge, numNodes);
		else
			isTree = false;
		if(DEBUG){
		System.out.println(isTree);
		}
	}

	//method that returns if it detected a tree or not
	public boolean detectTree(ColEdge[] edge, int n) {
		//keep trak of which nodes are visited
		 boolean visited[] = new boolean[n+1];
	        for (int i=0; i <n; i++) {
	            visited[i] = false;//initialize it to false
	        }

	        if (detectCycle(1, visited, 0))
	            return false;

	        for (int u = 1; u < n; u++) {
	            if (!visited[u])
	                return false;
	        }

	        return true;
	}

	//method for detecting cycle in subgrah
	public boolean detectCycle(int node, boolean visited[], int parent) {
		visited[node] = true;//check this node as visited

		//if it finds a vertice connected to the node then
		//it calls the method recursivly with that node
		for(int i=0; i<edge.length; i++) {
			if(edge[i].u==node) {
				if(!visited[edge[i].v]) {
					if(detectCycle(edge[i].v, visited, node)) {
						return true;
					}
				}
				else if(edge[i].v != parent)
					return true;
			}
			else if(edge[i].v == node) {
				if(!visited[edge[i].u]) {
					if(detectCycle(edge[i].u, visited, node)) {
						return true;
					}
				}
				else if(edge[i].u != parent)
					return true;
			}
		}
		return false;

	}

	public boolean isTree() {
		return isTree;
	}
}
// inspired by https://www.geeksforgeeks.org/check-given-graph-tree/
