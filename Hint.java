import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Hint{
private static final int FRAME_WIDTH = 400;
private static final int FRAME_HEIGHT = 100;


public static void main(String[] args){
 JFrame frame = new JFrame();
 JButton button = new JButton("Get Hint");
 JLabel label = new JLabel();
 JPanel panel = new JPanel();
 panel.add(label);
 panel.add(button);
 frame.add(panel);

 class AddListener implements ActionListener {
 public void actionPerformed(ActionEvent event) {
 label.setText("You should not use more color than:");
 }
 }
 ActionListener listener = new AddListener();
 button.addActionListener(listener);

 frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 frame.setVisible(true);
 }
 }