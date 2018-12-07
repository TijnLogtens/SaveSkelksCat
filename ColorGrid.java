import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;


public class ColorGrid extends JComponent{
	private Color[] color = {Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN,
			  Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW,
			  new Color(57,20,100), new Color(100,20,0),new Color(12,200,10), new Color(224,181,255),
			  new Color(200,150,71), new Color(1,61,26), new Color(255,119,1), new Color(110,119,21), 
			  new Color(79,132,142), new Color(117,91,91), new Color(236,249,224), new Color(255,251,196),
			  new Color(26,44,45)};
	final private int posX = 50;
	final private int posY = 500;
	final private int rectSide = 30;
	Rectangle2D.Double[] rect = new Rectangle2D.Double[24];
	Graphics2D g2;
	
	public void PaintComponent(Graphics g) {
		g2 = (Graphics2D)g;
		
		for(int i=0; i<rect.length; i++) {
			if(i<rect.length/2) {
				rect[i] = new Rectangle2D.Double(posX+(i*posX),posY,rectSide, rectSide);
				g2.setColor(color[i]);
				g2.fill(rect[i]);
			}
			else{
				rect[i] = new Rectangle2D.Double(posX+((i-12)*posX),posY+posX,rectSide, rectSide);
				drawRect(i);
			}
		}
		
		
	}
	public Graphics2D getGraphics2D() {
		return g2;
	}
	public Color[] getColor() {
		return color;
	}
	public void brighter(int index) {
		getGraphics2D().setColor(Color.BLACK);
		getGraphics2D().fill(rect[index]);
	}
	public void drawRect(int index) {
		getGraphics2D().setColor(color[index]);
		getGraphics2D().fill(rect[index]);
	}
	public Rectangle2D.Double[] getRect() {
		return rect;
	}	
	
	
}
