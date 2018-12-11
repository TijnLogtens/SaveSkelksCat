import java.util.ArrayList;
public class HintFunction {
    private ColEdge[] newGraph;
    private ArrayList<Integer> nodes;
    private int numberOfNodes;
    /* HintFunction class currently does the following:
    * 1. calculates the chromatic number of the graph passed as parameter, so it can be used in the hint.
    * (see method getChromaticNumber)
    * 2. creates an arraylist containing all the distinct nodes in the graph.
    * 3. calculates the number of distinct nodes in the graph. (see method getNumberOfNodes)
    */
    public HintFunction (ColEdge[] newGraph) {
      this.newGraph = newGraph; // This graph is made for test purposes. Needs to be updated to use the same graph as the game panel.

      // ArrayList nodes is used to store all the distinct vertices of the graph. The number of nodes is calculated by using the list size.
      this.nodes = new ArrayList<Integer>();
      for (int i=0; i<newGraph.length; i++) {
        System.out.println("Edge no " + i + ": " + newGraph[i].u + " " + newGraph[i].v);
        if(!nodes.contains(newGraph[i].u)){
          nodes.add(newGraph[i].u);
          System.out.println("Node " + newGraph[i].u + " is new and will be stored in the list.");
        }
        if(!nodes.contains(newGraph[i].v)){
          nodes.add(newGraph[i].v);
          System.out.println("Node " + newGraph[i].v + " is new and will be stored in the list.");
        }
      }
      this.numberOfNodes = nodes.size();
      System.out.println("The number of nodes is " + numberOfNodes);

      // The chromatic number should be used for the hint function.
      int chromaticNumber = getChromaticNumber();

    }
    public int getChromaticNumber () {
      // Using BruteForce class to calculate chromatic number.
      //Parameters passed are: Number of nodes, empty arrayList for colors, starting chromatic number 1, and newGraph
      ArrayList<Integer> colors = new ArrayList<Integer>();
      ColEdge[] graph = getGraph();
      int numberOfNodes = getNumberOfNodes();
      int startingChromaticNumber = 1;
      BruteForce bf = new BruteForce();
      int chromaticNumber = bf.search(numberOfNodes, colors, startingChromaticNumber, graph);
      System.out.println("The chromatic number of this graph is: " + chromaticNumber);
      return chromaticNumber;
    }
    public ArrayList<Integer> getNodes() {
      return nodes;
    }
    public int getNumberOfNodes () {
      return numberOfNodes;
    }
    public ColEdge[] getGraph () {
      return newGraph;
    }
}
