package network_representation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import network_analysis.NetworkRanker;

public class Network 
{
	public ArrayList<Person> nodes;
	Person source;
	String access_token_file;
	DataInterface data;
	NetworkRanker analyzer;
	
	public Network(Person s)
	{
		source = s;
		source.origin = this;
		analyzer = new NetworkRanker();
		nodes = new ArrayList<Person>();
		nodes.add(source);
	}
	public Network(Person s, String access_token_file)
	{
		source = s;
		source.origin = this;
		analyzer = new NetworkRanker();
		///*
		String access_token = "";
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(access_token_file));
			access_token = br.readLine();
		}
		catch(IOException ioe)
		{
			System.out.println("IO Error on access token read");
		}
		
		data = new DataInterface(access_token);
		complete_refresh_network();
		//*/
	}
	public static void main(String[] args)
	{
		Person me = new Person("Buck","Baskin",true);
		me.set_string_id("00000");
		Network n = new Network(me);
		ManualNetworkBuilder builder = new ManualNetworkBuilder(n);
		builder.read_network_from_file("save_file.txt", n);
		System.out.println("\n\nlast node");
		System.out.println(n.nodes.get(n.nodes.size()-1));
		builder.input_new_nodes(n);
		builder.write_network_to_file("save_file.txt");
		System.out.println("done");
	}
	
	public ArrayList<Person> add_node(Person p)
	{
		p.origin = this;
		nodes.add(p);
		//System.out.println("new nodes:\n"+nodes);
		return nodes;
	}
	public void remove_node(Person p)
	{
		//remove the node from the list
		nodes.remove(p);
		//remove the node from other friend lists
		for(Person remaining : nodes)
		{
			remaining.remove_friend(p);
		}
	}
	
	public void complete_refresh_network()
	{
		nodes = new ArrayList<Person>();
		nodes.add(source);
		int start_index = 0;
		for(int i = 0; i < 3; i++)
		{
			int node_size = nodes.size();
			for(int j = start_index; j < node_size; j++)
			{
				ArrayList<Person> friend_list = data.getFriends(nodes.get(j), false, false);
				for(Person node: nodes)
				{
					friend_list.remove(node);
				}
				nodes.addAll(friend_list);
			}
			start_index = start_index+node_size;
			//add 3 levels worth of people
		}
		//I think this should do it
	}
	
	
	public void update_existing_network()
	{
		for(Person p : nodes)
		{
			p.complete_update();
		}
		analyzer.page_rank(this);
		analyzer.flow_rank(this);
	}
	public ArrayList<Person> output_nodes()
	{
		return nodes;
	}
}
