package visualization;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class NetworkPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9001413302201931259L;

	VisualController vc;
	
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		vc.paint(g2);
	}
	
}
