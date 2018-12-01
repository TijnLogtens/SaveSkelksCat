import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class LostMessage extends JComponent{

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.RED);
		g2.setFont(new Font("myFont", Font.BOLD, 20));
		g2.drawString("YOU LOST", 550, 250);
		
	}
	
	
}
