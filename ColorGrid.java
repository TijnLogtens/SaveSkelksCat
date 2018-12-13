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
	private static Color[] color = { new Color(1,0,103), new Color(213,255,0), new Color(255,0,86), new Color(158,0,142),
			new Color(14,76,161), new Color(255,229,2), new Color(0,95,57), new Color(0,255,0), new Color(149,0,58), new Color(255,147,126),
			new Color(164,36,0), new Color(0,21,68), new Color(145,208,203), new Color(98,14,0),new Color(107,104,130),
			new Color(0,0,255), new Color(0,125,181), new Color(106,130,108), new Color(0,174,126), new Color(194,140,159), new Color(190,153,112), new Color(0,143,156), new Color(95,173,78), new Color(255,0,0),
			new Color(255,0,246), new Color(255,2,157), new Color(104,61,59), new Color(255,116,163), new Color(150,138,232), new Color(152,255,82),new Color(167,87,64), new Color(1,255,254), new Color(255,238,232), new Color(254,137,0),
			new Color(189,198,255), new Color(1,208,255), new Color(187,136,0), new Color(117,68,177), new Color(165,255,210), new Color(255,166,254),new Color(119,77,0), new Color(122,71,130), new Color(38,52,0), new Color(0,71,84),
			new Color(67,0,44), new Color(181,0,255), new Color(255,177,103), new Color(255,219,102), new Color(144,251,146), new Color(126,45,210)};
	final private int posX = 25;
	final private int posY = 620;
	final private int rectSide = 20;
	static Rectangle2D.Double[] rect = new Rectangle2D.Double[color.length];
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
				rect[i] = new Rectangle2D.Double(posX+((i-25)*posX),posY+posX,rectSide, rectSide);
				drawRect(i);
			}
		}
	}

	public Graphics2D getGraphics2D() {
		return g2;
	}

	public static Color[] getColor() {
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

	public static Rectangle2D.Double[] getRect(){
		return rect;
	}

}
