package visualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import network_representation.Network;

public class NetworkFrame extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1945986348750483807L;
	NetworkPanel draw_pane;
	VisualController v;
	
	public NetworkFrame(String title, Network n, int reduction_level)
	{
		super(title);
		this.setBackground(Color.BLACK);
		Dimension d= new Dimension();
		d.height = 700;
		d.width = 1000;
		this.setSize(d);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		draw_pane = new NetworkPanel();
		this.add(draw_pane);
		v = new VisualController(draw_pane, n);
		draw_pane.vc = v;
		update(reduction_level);
		
		setVisible(true);
		repaint();
	}
	public void update(int reduction_level)
	{
		v.update_network();
		v.update_visual(reduction_level);
	}
}
