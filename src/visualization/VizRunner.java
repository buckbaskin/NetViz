package visualization;

import java.awt.Graphics2D;

import javax.swing.JFrame;

import network_representation.ManualNetworkBuilder;
import network_representation.Network;
import network_representation.Person;

public class VizRunner {
	
	public static void main(String[] args)
	{
		Person me = new Person("Buck","Baskin",true);
		me.set_string_id("00000");
		Network n = new Network(me);
		ManualNetworkBuilder builder = new ManualNetworkBuilder(n);
		builder.read_network_from_file("save_file.txt", n);
		builder.input_new_nodes(n);
		builder.write_network_to_file("save_file.txt");
		
		NetworkFrame frame = new NetworkFrame("Buck's social network",n);
	}
}
