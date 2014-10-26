package network_analysis;

import network_representation.*;

public class NetworkRanker {

	public static Person cluster_coefficient(Network n)
	{
		Person max_cluster = new Person("a","b",false);
		max_cluster.cluster_ratio = -1;
		for(Person p: n.nodes)
		{
			p.cluster_ratio = local_cluster_eval(p);
			if(p.cluster_ratio > .5)
				//	System.out.println("High Cluster: "+p.name()+" rating: "+p.cluster_ratio);
				if(p.cluster_ratio > .99)
					p.highlight = true;
			if(p.cluster_ratio > max_cluster.cluster_ratio)
			{
				max_cluster = p;
			}
		}
		//System.out.println("MAX CLUSTER: "+max_cluster.name()+" rating: "+max_cluster.cluster_ratio);
		return max_cluster;
	}
	private static double local_cluster_eval(Person p) {

		double max_connections = p.friends.size()*(p.friends.size()-1);
		double connection_sum = 0.0;
		for(Person friendo : p.friends)
		{
			for(Person fof : friendo.friends)
			{
				if(p.friends.contains(fof))
				{
					connection_sum++;
				}
			}
		}
		//System.out.println("connection_sum: "+connection_sum+" / max_connections: "+max_connections);
		return connection_sum/max_connections;
	}

	//high level ranking methods
	public static int page_rank(Network n)
	{
		page_rank_setup(n);
		for(int i = 0; i < 10; i++)
		{
			page_rank_step(n);
		}
		return 1;
	}
	private static void page_rank_setup(Network n)
	{
		for(Person p: n.nodes)
		{
			p.new_page_rank = 1.0;
			p.old_page_rank = 1.0;
		}
	}
	private static void page_rank_step(Network n)
	{
		double max_rating = 0;
		double sum = 0;
		double count = 0;
		for(int i = 1; i < n.nodes.size(); i++)
		{
			count++;
			Person p = n.nodes.get(i);
			p.new_page_rank = 0;
			for (Person friend : p.friends)
			{
				p.new_page_rank += p.old_page_rank;
			}
			if(p.new_page_rank > max_rating)
			{
				max_rating = p.new_page_rank; count+= p.new_page_rank;
			}
		}
		System.out.println("max rating: "+max_rating);
		for(Person p:n.nodes)
		{
			p.old_page_rank = p.new_page_rank/max_rating;
		}
	}
	public void flow_rank(Network n)
	{
		//rate based on minimum distance paths passing through the node
		//connecting all other nodes
		//TODO
	}

}