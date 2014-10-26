package network_representation;

public class ReductionEngine 
{
	public ReductionEngine()
	{
		
	}
	public void reduce_one(Network n)
	{
		for(Person p : n.nodes)
		{
			if(!p.name().equals(n.source.name()))
			{
				int[] moves = eval_function(p,n);
				p.dx = moves[0];
				p.dy = moves[1];
			}
		}
		for(Person p : n.nodes)
		{
			if(!p.name().equals(n.source.name()))
			{
				p.vn.x += p.dx;
				p.vn.y += p.dy;
			}
		}
	}
	public int[] eval_function(Person p, Network n)
	{
		int[] spreading = counter_radial(p, n);
		int[] collapsing = centroid(p, n);
		int[] result = new int[2];
		result[0] = spreading[0]+collapsing[0];
		result[1] = spreading[1]+collapsing[1];
		
		result[0] = result[0] + (int)(Math.round(Math.random()*2)-1);
		result[1] = result[1] + (int)(Math.round(Math.random()*2)-1);
		
		return result;
	}
	private int[] centroid(Person p, Network n) {
		int[] results = new int[2];
		int x_sum = 0;
		int y_sum = 0;
		int count = 0;
		for(Person friend: p.friends)
		{
			x_sum += friend.vn.x;
			y_sum += friend.vn.y;
			count++;
		}
		int desired_x = (int)((double)(x_sum)/(double)(count));
		int desired_y = (int)((double)(y_sum)/(double)(count));
		double theta = Math.atan2(desired_y-p.vn.y, desired_x-p.vn.x);

		double distance = Math.sqrt((desired_x-x_sum)^2 + (desired_y-y_sum)^2);

		double alpha = 5.0*(1-Math.pow(Math.E, -1*distance));
		double dx = alpha*Math.cos(theta);
		double dy = alpha*Math.sin(theta);

		results[0] = (int)(dx);
		results[1] = (int)(dy);

		return results;
	}
	private int[] counter_radial(Person p, Network n) {
		int[] results = new int[2];

		int px = p.vn.x;
		int py = p.vn.y;
		//System.out.println("n = "+n+" source = "+n.nodes.get(0)+"\n vn = "+n.nodes.get(0).vn+" vn.x = "+n.nodes.get(0).vn.x);
		int nx = n.nodes.get(0).vn.x;
		int ny = n.nodes.get(0).vn.y;

		double theta = Math.atan2(ny-py, nx-px);
		double distance = Math.sqrt((px-nx)^2 + (py-ny)^2);
		double alpha = 4.95*(1-Math.pow(Math.E, -1*distance));

		double dx = -alpha*Math.cos(theta);
		double dy = -alpha*Math.sin(theta);

		results[0] = (int)(dx);
		results[1] = (int)(dy);

		return results;
	}
}
