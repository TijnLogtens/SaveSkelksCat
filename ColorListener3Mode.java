import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ColorListener3Mode implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			Point p = new Point(e.getX(), e.getY());
			for(int i=0; i<ColorGrid3Mode.getRect().length; i++) {
			if(ColorGrid3Mode.getRect()[i].contains(p)) {
				VertexClickListener3Mode.setColor(ColorGrid3Mode.getColor()[i]);
			}
			}

		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
}
