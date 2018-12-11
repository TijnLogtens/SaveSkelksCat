import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class HintButtonViewer {
  public static void main(String[] args){
    int FRAME_WIDTH = 400;
    int FRAME_HEIGHT = 100;
    JFrame frame = new JFrame();
    JButton button = new JButton("Get a hint");
    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    panel.add(button);
    frame.add(panel);
    panel.add(label);

    ColEdge[] newGraph = GenerateGraph.randomGraph();

    // The hintlistener below needs to be attached to the hint button in the main game display
    class HintListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
        HintFunction hint = new HintFunction(newGraph);

        // The upper limit given to user is chromatic number + a number between 1 and 5.
        int colorLimit = hint.getChromaticNumber() + ((int)(Math.random()*5+1));
        label.setText("You should not use more than " + colorLimit + " colours.");
      }
    }

    HintListener listener = new HintListener();
    button.addActionListener(listener);

    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
