import javax.swing.*;
import java.awt.*;

public class GraphViewer {
  public static void main(String[] args) {
    int[][] ColEdge = {{1,2},{2,3},{3,4},{4,1},{2,4}};
    Graph graph = new Graph(ColEdge);
    System.out.println(graph.getVertices());

    JFrame window = new JFrame();
    window.setSize(800, 800);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JComponent graphComponent = new GraphComponent(graph);
    window.add(graphComponent);
    window.setVisible(true);
  }
}
