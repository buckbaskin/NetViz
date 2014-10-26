package network_representation;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import visualization.VisualNode;

public class Person
{
	/*
	 * string name
  				|- string id?
  				|- long id?
  				|- List friends
  				|- List groups
  				|- func double net_eval() - returns an aggregated network score based on all metrics
  				|- func double me_eval() - returns an aggregated score based on connections to me
  				|- network statistics
  					|- friends_count
  					|- friends of friends count
  					|- friends of friends of friends count (maybe this is calculated on a need to know basis)
  					|- centrality stats
  						|- people within x levels (see above)
  						|- distance to me
  						|- pagerank?
  						|- betweeness centrality (most shortest paths)
  						|- bridging (uniqueness of connections between groups)
  				|- on the addition or update of a node, call update for connected people
	 */
	Network origin;
	
	boolean friend;
	//identification (both program and human readable)
	public String first_name;
	public String last_name;
	public String id;
	
	
	public LinkedList<Person> friends;
	LinkedList<Group> members_of;
	
	byte depth;
	
	//network statistics
	int friend_count;
	int fof2; //friends of friends
	int fof3; //friends of friends of friends
	
	public double cluster_ratio;
	public boolean highlight;
	
	//drawable stuff
	public VisualNode vn;
	
	//moveable stuff
	int dx;
	int dy;

	public double old_page_rank;
	public double new_page_rank;
	
	public Person()
	{
		friend_count = 0;
		friend = true;
		highlight = false;
		dx = 0; dy = 0;
	}
	public Person(String first, String last, boolean bff)
	{
		first_name = first;
		last_name = last;
		friend = bff;
		friends = new LinkedList<Person>();
		members_of = new LinkedList<Group>();
		highlight = false;
		//initialize network stats
		friend_count = 0;
		fof2 = -1;
		fof3 = -1;
		dx = 0; dy = 0;
	}
	//Friend methods
	public void set_friends(LinkedList<Person> f)
	{
		friends = f;
		friend_count = friends.size();
	}
	public void add_friend(Person p)
	{
		friends.add(p);
		friend_count++;
	}
	public void add_friends(List<Person> list)
	{
		friends.addAll(list);
	}
	public void remove_friend(Person p)
	{
		friends.remove(p);
		friend_count--;
	}
	
	//heuristic functions
	public double net_eval()
	{
		//summary of all network statistics
		//TODO
		return -1;
	}
	public double source_eval()
	{
		//summary of stats relating to the source
		//TODO
		return -1;
	}
	
	//local network measurement methods
	public void complete_update()
	{
		update_fof();
	}
	public void update_fof()
	{
		//update the fof2 and fof3
	}
	
	//Group methods
	public void add_membership(Group g)
	{
		members_of.add(g);
	}
	public void remove_membership(Group g)
	{
		members_of.remove(g);
	}
	
	//stuff
	public String name()
	{
		return first_name+" "+last_name;
	}
	public void set_string_id(String id)
	{
		this.id = id;
	}
	
	public String toString()
	{
		if(first_name != null && last_name !=null)
		{
			String composition = first_name+" "+last_name;
			if (id != null)
			{
				composition+= " ["+id+"]";
			}
			return composition;
		}
		return "err";
	}
	
}
