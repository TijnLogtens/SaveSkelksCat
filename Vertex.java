import javax.swing.*;
import java.awt.*;
public class Vertex {
  private int vertexNumber;
  private int leftX;
  private int topY;
  private int midX;
  private int midY;
  private Color vertexColor;
  private final int DIAMETER = 16;
  private final int FRAME_SIDE_LENGTH = 800; // This should be changed to correspond to the actual frame we want
  public Vertex (int number) {
    vertexNumber = number;
    leftX = (int) (Math.random()*FRAME_SIDE_LENGTH);
    topY = (int) (Math.random()*FRAME_SIDE_LENGTH);
    midX = (leftX+DIAMETER/2);
    midY = (topY+DIAMETER/2);
    vertexColor = new Color(255, 255, 255);
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

  /* SetMidX and setMidY can be used to reset the coordinates of the vertex centre,
   e.g. in order to avoid overlapping nodes or in order to keep the graph within
   the desired bounds. They automatically set the corner coordinates as well. */
  public void setMidX (int x) {
    midX = x;
    leftX = x-DIAMETER/2;
  }
  public void setMidY (int y) {
    midY = y;
    topY = y-DIAMETER/2;
  }
  public Color getColor () {
    return vertexColor;
  }
  public void setColor (Color color) {
    vertexColor = color;
  }
}
