package visualization;

public class Link 
{
	int x1;
	int y1;
	int x2;
	int y2;
	
	VisualNode vn1;
	VisualNode vn2;
	
	public Link(VisualNode one, VisualNode two)
	{
		x1 = one.x;
		x2 = two.x;
		y1 = one.y;
		y2 = two.y;
		vn1 = one;
		vn2 = two;
	}
	public Link(int x1,int y1,int x2,int y2)
	{
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	public String toString()
	{
		return "Link ["+x1+" "+y1+"  "+x2+" "+y2+"]";
	}
}
