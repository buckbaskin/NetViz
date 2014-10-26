package network_representation;

import java.util.ArrayList;

import facebook.*;

public class DataInterface 
{
	FacebookInterface access_point;
	StorageInterface store_point;
	
	public DataInterface(String access_token)
	{
		access_point = new FacebookInterface(access_token);
		store_point = new StorageInterface();
	}
	public ArrayList<Person> getFriends(Person p, boolean local_only, boolean save_data)
	{
		//if local only, read from friends storage
		//else, read from online and then write to storage
		local_only = false;
		save_data = false;
		if(local_only)
		{
			return new ArrayList<Person>();
		}
		else
		{
			//read from online
			ArrayList<Person> friends = access_point.get_friends(p);
			// if save_data, write to the storage
			if(save_data)
			{
				store_point.save(p, friends);
			}
			return friends;
		}
	}
}
