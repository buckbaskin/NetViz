package network_analysis;

import java.util.Collections;

import visualization.NetworkFrame;
import network_representation.ManualNetworkBuilder;
import network_representation.Network;
import network_representation.Person;

public class AnalysisRunner 
{
	public static void main(String[] args)
	{
		Person me = new Person("Buck","Baskin",true);
		me.set_string_id("00000");
		Network n = new Network(me);
		ManualNetworkBuilder builder = new ManualNetworkBuilder(n);
		builder.read_network_from_file("save_file.txt", n);
		//builder.input_new_nodes(n);
		//builder.write_network_to_file("save_file.txt");
		//Collections.shuffle(n.nodes);
		Person max = NetworkRanker.cluster_coefficient(n);
		max.highlight = true;
		//NetworkRanker.page_rank(n); //Page rank converges too quickly 
		for(Person max_friend: max.friends)
		{
			max_friend.highlight = true;
		}
		NetworkFrame frame = null; // = new NetworkFrame("Buck's social network w/ clustering "+"0"+" iterations",n, 0);
		for(int i = 0; i < 101; i = i+10)
		{
			frame = new NetworkFrame("Buck's social network w/ clustering "+i+" iterations",n, i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
