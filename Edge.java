public class Edge {
  private Vertex startVertex;
  private Vertex endVertex;

  public Edge (Vertex start, Vertex end) {
    startVertex = start;
    endVertex = end;

  }

  public Vertex getStartVertex () {
    return startVertex;
  }

  public Vertex getEndVertex () {
    return endVertex;
  }
}
