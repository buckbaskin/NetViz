package visualization;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import network_representation.*;

public class VisualController 
{
	ArrayList<VisualNode> draw_objs;
	ArrayList<Link> connections;
	JPanel draw_here;
	Network to_draw;
	Color default_color;
	Color nice_blue;
	Color my_green;
	int default_r;

	public ReductionEngine re;

	public VisualController(JPanel paint_surface, Network n)
	{
		re = new ReductionEngine();
		draw_objs = new ArrayList<VisualNode>();
		connections = new ArrayList<Link>();
		draw_here = paint_surface;
		to_draw = n;
		default_color = new Color(255,255,255);
		nice_blue = new Color(0,179,255);
		my_green = new Color(0,255,154);
		default_r = 5;
	}

	public void update_network()
	{
		//reassign pairings between the network representation and visual representation
		draw_objs.clear();
		for(Person p: to_draw.output_nodes())
		{
			draw_objs.add(new VisualNode(p));
		}
	}
	public void update_visual(int reduction_level)
	{
		//SET INITIAL LOCATIONS
		//int counter = 0;
		//reassign location sizing and color to existing visual elements
		int x_c = 500;
		int y_c = 350;
		int r_draw = 250;
		int variation = 50;
		draw_objs.get(0).assignLocation(x_c, y_c);
		draw_objs.get(0).assignVisual(my_green, default_r+5);
		///*
		int size = draw_objs.size();
		if (size%2==1) size++;

		double step = (Math.PI*2)/((double)size-1);


		for(int i = 1; i < draw_objs.size(); i++)
		{
			double radial_location = (i-1)*step;
			double distance = r_draw-variation*Math.random();
			//counter++;
			//System.out.println("counter => "+counter);
			draw_objs.get(i).assignLocation((int)(x_c+distance*Math.cos(radial_location)),
					(int)(y_c+distance*Math.sin(radial_location)));
			draw_objs.get(i).assignVisual(default_color, default_r);
			//System.out.println("radial location: r:"+distance+" theta:"+radial_location);

			//System.out.println("last connection: "+connections.get(connections.size()-1));
			//System.out.print("");
		}

		//*/
		//ITERATE TO BETTER LOCATIONS
		for(int i = 0; i < reduction_level; i++)
		{
			re.reduce_one(to_draw);
		}
		//ADD CONNECTIONS
		int count = 0;
		for(int i = 0; i < draw_objs.size(); i++)
		{
			for( Person other : draw_objs.get(i).p.friends)
			{
				count++;
				if(draw_objs.get(i).x != -1 && draw_objs.get(i).y !=-1 &&
						other.vn.x !=-1 && other.vn.y != -1)
				{
					connections.add(new Link(draw_objs.get(i), other.vn));
					//System.out.println(count+" Connecting: "+draw_objs.get(i).p+" , "+other);
				}
			}
		}
	}

	public void add(VisualNode vn)
	{
		draw_objs.add(vn);
	}
	public void remove(VisualNode vn)
	{
		draw_objs.remove(vn);
	}

	public void paint(Graphics2D g)
	{
		for(int i = 0; i < connections.size(); i++)
		{
			//g.drawString("2-"+i, connections.get(i).x2, connections.get(i).y2);
			g.setColor(nice_blue);
			if(i != 0) g.drawLine(connections.get(i).x1, connections.get(i).y1, connections.get(i).x2, connections.get(i).y2);
		}
		///*
		g.setColor(Color.RED);
		//g.drawOval(300-200, 300-200, 400, 400);
		g.drawOval(500-150, 350-150, 300, 300);
		//g.drawOval(300-100, 300-100, 200, 200);
		for(int i = 0; i < draw_objs.size(); i++)
		{
			if(draw_objs.get(i).isPaintable())
			{
				VisualNode n = draw_objs.get(i);
				if(n.p.highlight && false)
				{
					g.setColor(new Color(120,255,120));
					g.fillOval(n.x-(n.r+2), n.y-(n.r+2), 2*(n.r+2), 2*(n.r+2));
				}
				Color clusterf = new Color((int) (Math.min(255,255*n.p.cluster_ratio)),0,0);
				g.setColor(clusterf);
				g.fillOval(n.x-n.r, n.y-n.r, 2*n.r, 2*n.r);
			}
		}
		//*/
		//Write names
		for (int i = 0; i < connections.size(); i++) 
		{
			g.setColor(Color.ORANGE);
			if(Math.random()<.05) g.drawString(""+connections.get(i).vn1.p.name(), connections.get(i).x1, connections.get(i).y1);
		}
	}
}