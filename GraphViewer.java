import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.colorchooser.*;

class VertexClickListener extends MouseAdapter {
  private Graph graph;
  private GraphComponent graphComponent;
  final int DIAMETER = 16;
  public VertexClickListener (Graph graph, GraphComponent graphComponent) {
    this.graph = graph;
    this.graphComponent = graphComponent;
  }
  public void mouseClicked(MouseEvent e) {
    HashMap<Integer, Vertex> vertices = graph.getVertices();
    for (int i=1; i<=vertices.size(); i++) {
      Vertex vertex = vertices.get(i);
      if (e.getX()>(vertex.getMidX()-DIAMETER/2) &&
          e.getX()<(vertex.getMidX()+DIAMETER/2) &&
          e.getY()>(vertex.getMidY()-DIAMETER/2) &&
          e.getY()<(vertex.getMidY()+DIAMETER/2)) {
            vertex.setColor(Color.GREEN);
            graphComponent.repaint();
            return;
          }
    }
  }
}

public class GraphViewer {

  public static void main(String[] args) {
    int[][] ColEdge = {{1,2},{2,3},{3,4},{4,1},{2,4},{5,4},{6,4},{7,4},{8,4},{9,4},{10,5},{10,6},{10,7},{11,5},{12,5},{13,5},{14,5}};
    Graph graph = new Graph(ColEdge);
    System.out.println(graph.getVertices());

    JFrame window = new JFrame();
    Container contentPane = window.getContentPane();
    contentPane.setPreferredSize(new Dimension(800, 800));
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GraphComponent graphComponent = new GraphComponent(graph);
    VertexClickListener clickListener = new VertexClickListener(graph, graphComponent);
    graphComponent.addMouseListener(clickListener);
    window.add(graphComponent);
    window.pack();
    window.setVisible(true);

  }
}
