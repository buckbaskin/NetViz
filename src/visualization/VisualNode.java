package visualization;

import java.awt.Color;

import network_representation.Person;

public class VisualNode 
{
	Person p;
	public int x = -1; //coordinates of center
	public int y = -1; //coordinates of center
	int r = -1;
	Color c;
	
	boolean ready = false;
	
	public VisualNode(Person p)
	{
		this.p = p;
		p.vn = this;
	}
	
	public void assignLocation(int x_center, int y_center)
	{
		x = x_center;
		y = y_center;
	}
	public void assignVisual(Color c, int radius)
	{
		this.c = c;
		r = radius;
		if(x!= -1 && y!= -1 && c!=null && r!= -1)
		{
			ready = true;
		}
		else
		{
			ready = false;
		}
	}
	public boolean isPaintable()
	{
		return ready;
	}
}
