package network_representation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ManualNetworkBuilder 
{
	Network n;
	Scanner input_machine;
	public ManualNetworkBuilder()
	{
		this(new Network(new Person("Buck","Baskin",true)));
	}
	public ManualNetworkBuilder(Network existing)
	{
		n = existing;
		input_machine = new Scanner(System.in);
	}
	//call input_new_nodes or read_network_from_file after constructor
	
	public void input_new_nodes(Network n)
	{
		System.out.println("Please give next index");
		int i = input_machine.nextInt();
		Person node = get_new_input(i, n);
		//System.out.println("got input. processing node "+node);
		i++;
		while(node != null)
		{
			n.nodes = n.add_node(node);
			//System.out.println("node added, getting new input");
			node = get_new_input(i,n);
			i++;
		}
	}
	public Person get_new_input(int id, Network n)
	{
		String first_name = "";
		String last_name = "";
		String id_str = "00000"+id;
		id_str = id_str.substring(id_str.length()-5);
		System.out.println("First Name: ");
		first_name = input_machine.next();
		if(first_name.equals("end"))
		{
			return null;
		}
		System.out.println("Last Name: ");
		last_name = input_machine.next();
		if(last_name.equals("end"))
		{
			return null;
		}
		Person new_mem = new Person(first_name, last_name, true);
		new_mem.set_string_id(id_str);
		System.out.println("Friend Search: ");
		//System.out.println("n.nodes"+" len("+n.nodes.size()+")"+"\n"+n.nodes.toString());
		existing_friend_search(n, new_mem);
		
		return new_mem;
	}
	public void existing_friend_search(Network n, Person new_entry)
	{
		System.out.println(n.nodes.size()+" existing nodes: ");
		String search_string = input_machine.next();
		while(!search_string.equals("end"))
		{
			Person result = lookup(search_string, n);
			if(result != null)
			{
				result.add_friend(new_entry);
				new_entry.add_friend(result);
			}
			search_string = input_machine.next();
		}
	}
	public Person lookup(String str, Network n)
	{
		ArrayList<Person> possibilities = new ArrayList<Person>();
		for(int i = 0; i < n.nodes.size(); i++)
		{
			if(n.nodes.get(i).toString().contains(str))
			{
				possibilities.add(n.nodes.get(i));
			}
		}
		if(possibilities.size()==1)
		{
			//System.out.println(" |-> friend found: "+possibilities.get(0).name());
			return possibilities.get(0);
		}
		else if (possibilities.size() > 0)
		{
			System.out.println("too many options: ");
			for(int i = 0; i < possibilities.size(); i++)
			{
				//System.out.println(i+" -> "+possibilities.get(i).name());
			}
			return null;
		}
		else
		{
			//System.out.println("No matches found. Please try again");
			return null;
		}
	}
	public void write_network_to_file(String file_name)
	{

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file_name));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			boolean debug = false;
			if(debug)
			{
				//echo file writes to the console
			}
			bw.write("!new network"+"\n");
			if(debug) System.out.print("!new network"+"\n");
			bw.write("$source: "+n.source.name()+" #"+n.source.id+"\n");
			if(debug) System.out.print("$source: "+n.source.name()+" #"+n.source.id+"\n");
			for(int i = 0; i < n.nodes.size(); i++)
			{
				bw.write("*node:<"+n.nodes.get(i).name()+"><"+n.nodes.get(i).id+">"+"\n");
				if(debug) System.out.print("*node:<"+n.nodes.get(i).name()+"><"+n.nodes.get(i).id+">"+"\n");
				bw.write("->connections:"+"\n");
				if(debug) System.out.print("->connections:"+"\n");
				Iterator<Person> iter = n.nodes.get(i).friends.iterator();
				while(iter.hasNext())
				{
					bw.write("    |"+iter.next().name()+"\n");
					if(debug) System.out.println("    | iteration");
				}
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void read_network_from_file(String filename, Network n)
	{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			String current_line = br.readLine();
			while(current_line!= null && !current_line.startsWith("!"))
			{
				current_line = br.readLine();
			}
			while(!current_line.startsWith("$"))
			{
				current_line = br.readLine();
			}
			int num_sign = current_line.indexOf("#");
			String source_name = current_line.substring(9,num_sign-1);
			String[] split = source_name.split(" ");
			String source_first = split[0];
			String source_last = split[1];
			String source_num = current_line.substring(num_sign+1);
			Person new_source = new Person(source_first, source_last, false);
			new_source.set_string_id(source_num);
			n.source = new_source;
			n.source.origin = n;
			n.nodes.clear();
			Person node = null;
			while(current_line!=null)
			{
				//System.out.println("current line -> "+current_line);
				if(current_line.startsWith("*"))
				{
					//add the old node
					if(node != null)
					{
						for(Person friend : node.friends)
						{
							friend.add_friend(node);
						}
						n.add_node(node);
					}
					// make the new one
					int close_index = current_line.substring(5).indexOf(">")+7;
					String node_name = current_line.substring(7,close_index-2);
					split = node_name.split(" ");
					String node_first = split[0];
					String node_last = split[1];
					int start_index = current_line.substring(close_index+1,current_line.length()-1).indexOf("<")+close_index+1;
					String node_num = current_line.substring(start_index,current_line.length()-1);
					node = new Person(node_first, node_last, false);
					node.set_string_id(node_num);
					//System.out.println("    -> new node "+node.name());
				}
				if(current_line.startsWith("    |"))
				{
					//add a new connection to the current node
					Person new_friend = lookup(current_line.substring(5), n);
					if(new_friend != null)
					{
						//System.out.println("    new connection -> "+new_friend);
						node.add_friend(new_friend);
					}
				}
				current_line = br.readLine();
			}
			//reached the end of the file
			if(node != null)
			{
				for(Person friend : node.friends)
				{
					friend.add_friend(node);
				}
				n.add_node(node);
			}
			br.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
