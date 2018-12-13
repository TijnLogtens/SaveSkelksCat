import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.Color;

public class GUIBasic {

	public static void main(String[] args) {
		JFrame window = new JFrame("Graph Game");
		window.setVisible(true);
		window.setContentPane(new GraphGame());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setPreferredSize(new Dimension(GraphGame.WIDTH, GraphGame.HEIGHT));
		window.setLocationRelativeTo(null);
	}
}
