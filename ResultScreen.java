import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.geom.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

public class ResultScreen {

	public static JPanel panel1;
	public static JPanel panel2;
	public static JPanel pane;
	public static JLabel label;
	public static JFrame frame;
	public static JLabel timeLabel1;
	public static JLabel timeLabel2;
	public static JLabel modeLabel1;
	public static JLabel modeLabel2;
	public static JLabel scoreLabel1;
	public static JLabel scoreLabel2;

	public static void ResultScreen(){
		//Frame
		frame = new JFrame();
			frame.setSize(GraphGame.getWindowSize(), GraphGame.getWindowSize2());
			frame.setTitle("Game Results");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Panel(s)
		panel1 = new JPanel();
		pane = new JPanel(new FlowLayout());

		//Labels
		label = new JLabel("Game Complete!");
			label.setFont(label.getFont().deriveFont(32f));
			label.setHorizontalTextPosition(JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.CENTER);
			label.setForeground(Color.RED);
			Border border = BorderFactory.createLineBorder(Color.BLACK);
			label.setBorder(border);

		timeLabel1 = new JLabel(" -Time: ");
			timeLabel1.setBounds(83, 185, 100, 50);
			timeLabel1.setFont(label.getFont().deriveFont(20f));

		//Timer getter
		timeLabel2 = new JLabel(GraphGame.getTime());
		  timeLabel2.setBounds(200, 185, 100, 50);
		  timeLabel2.setFont(label.getFont().deriveFont(20f));

		modeLabel1 = new JLabel(" -Game Mode: ");
			modeLabel1.setBounds(15, 250, 200, 100);
			modeLabel1.setFont(label.getFont().deriveFont(20f));

		// Game mode getter
		modeLabel2 = new JLabel(Integer.toString(GraphGame.getGameMode()));
			modeLabel2.setBounds(198, 250, 200, 100);
			modeLabel2.setFont(label.getFont().deriveFont(20f));

		panel1.add(label);
		frame.add(timeLabel1);
		frame.add(timeLabel2);
		frame.add(modeLabel1);
		frame.add(modeLabel2);
		frame.add(panel1);
		frame.setVisible(true);
	}

    /*	public ResultScreen(double x, double y) {
    		xLeft = x;
    		yTop = y;
    	}

    	public void draw(Graphics2D g2) {
  		 	//Drawing an outline for the table
    		Rectangle2D.Double outlineBox = new Rectangle2D.Double(xLeft, yTop + 10, 500, 500);
    			g2.draw(outlineBox);
    	}

    	private double xLeft;
    	private double yTop;
    */
}
