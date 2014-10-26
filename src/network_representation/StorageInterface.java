package network_representation;

import java.util.ArrayList;
import java.util.LinkedList;

public class StorageInterface 
{
	LinkedList<SaveFile> information;
	
	public StorageInterface()
	{
		information = new LinkedList<SaveFile>();
	}
	
	public SaveFile search(Person p)
	{
		//TODO
		return new SaveFile("er","test");
	}
	public ArrayList<Person> get_friend_list(Person p)
	{
		SaveFile info = search(p);
		return info.read_friend_list();
	}

	public void save(Person p, ArrayList<Person> friends) {
		// TODO
		
	}
}

class SaveFile
{
	String file;
	String name;
	public SaveFile(String filename, String person_name)
	{
		file = filename;
		name = person_name;
	}
	public ArrayList<Person> read_friend_list()
	{
		//TODO
		return new ArrayList<Person>();
	}
	public Person file_to_object()
	{
		//TODO
		return new Person();
	}
}