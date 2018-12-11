import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ColorListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			Point p = new Point(e.getX(), e.getY());
			for(int i=0; i < GraphGame.graphComponent.getColorGrid().getRectSize(); i++) {
  			if(GraphGame.graphComponent.getColorGrid().getRect()[i].contains(p)) {
  				VertexClickListener.setColor(GraphGame.graphComponent.getColorGrid().getColor()[i]);
  			}
			}
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}

}
