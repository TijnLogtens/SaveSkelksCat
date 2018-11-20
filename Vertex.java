public class Vertex {
  private int vertexNumber;
  private int leftX;
  private int topY;
  private int midX;
  private int midY;
  private final int DIAMETER = 16;
  private final int FRAME_SIDE_LENGTH = 800; // This should be changed if the frame side changes
  public Vertex (int number) {
    vertexNumber = number;
    leftX = (int) (Math.random()*FRAME_SIDE_LENGTH);
    topY = (int) (Math.random()*FRAME_SIDE_LENGTH);
    midX = (leftX+DIAMETER/2);
    midY = (topY+DIAMETER/2);
  }
  public int getVertexNumber () {
    return vertexNumber;
  }
  public int getMidX () {
    return midX;
  }
  public int getMidY () {
    return midY;
  }
  public int getLeftX () {
    return leftX;
  }
  public int getTopY () {
    return topY;
  }
  public String toString() {
    return "Vertex " + vertexNumber;
  }
  public void setMidX (int x) {
    midX = x;
    leftX = x-DIAMETER/2;
  }
  public void setMidY (int y) {
    midY = y;
    topY = y-DIAMETER/2;
  }
}
