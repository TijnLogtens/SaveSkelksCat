import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ColorListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			Point p = new Point(e.getX(), e.getY());
			for(int i=0; i<ColorGrid.getRect().length; i++) {
			if(ColorGrid.getRect()[i].contains(p)) {
				VertexClickListener.setColor(ColorGrid.getColor()[i]);
			}
			}
			
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}

	}
