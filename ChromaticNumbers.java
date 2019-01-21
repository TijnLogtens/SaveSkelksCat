import java.io.*;
import java.util.*;
public class ChromaticNumbers {
  public static long generalStartTime;
  public static long bkStartTime;
  public static long ubStartTime;
  public static ColEdge[] edges;
  public static int numVertices;
  public static int numEdges;
  public static int bound;
  public static void main(String[] args) {
    generalStartTime = System.currentTimeMillis();

    // Read the graph
    ReadGraphFinal newGraph = new ReadGraphFinal(args);
    edges = newGraph.getEdges();
    numVertices = newGraph.getNumberOfVertices();
    numEdges = newGraph.getNumberOfEdges();

    // Structure detection section. Time limit: TBD
    TreeStructure graphStructure = new TreeStructure(edges, numVertices);
    if (graphStructure.isTree()) {
      System.out.println("CHROMATIC NUMBER = 2");
      System.exit(0);
    }

    // Upper bound section. Time limit: 50 seconds from ubStartTime, applies to all except GreedyRLF.
    ubStartTime = System.currentTimeMillis();
    try {
      long ubCurrentTime;
      GreedyRLF grlf = new GreedyRLF(edges);
      bound = grlf.getUpperBound();
      printUpper(bound);

      if (numEdges<100001) {
        RLFHeuristicOrdering rlf = new RLFHeuristicOrdering(edges);
        bound = rlf.getUpperBound();
        printUpper(bound);
      }

      ubCurrentTime = System.currentTimeMillis();
      checkTime(ubCurrentTime);

      if (numEdges<500001) {
        DSatur ds = new DSatur(edges, numVertices);
        bound = ds.getUpperBound();
        printUpper(bound);
      }

      ubCurrentTime = System.currentTimeMillis();
      checkTime(ubCurrentTime);

      if (numEdges<500001) {
        WP wp = new WP(edges, numVertices);
        bound = wp.getUpperBound();
        printUpper(bound);
      }

    } catch (Exception ex) {}

    // Lower bound section. Time limit: TBD
    bkStartTime = System.currentTimeMillis();
    try {
      BKCompetition bk = new BKCompetition(edges);
      int lowerBound = bk.getLowerBound();
      System.out.println("LOWER BOUND = " + lowerBound);
    } catch (Exception ex) {}

    // Exact algorithm section.

  }
  public static void printUpper (int bound) {
    if (bound>2 || edges.length<1) {
      System.out.println("UPPER BOUND = " + bound);
    } else {
      System.out.println("CHROMATIC NUMBER = " + bound);
      System.exit(0);
    }
  }
  public static void checkTime (long ubCurrentTime) throws Exception {
    long runningTime = ubCurrentTime-ubStartTime;
    if (runningTime>50000) {
      throw new Exception();
    }
  }
}
