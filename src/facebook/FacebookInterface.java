package facebook;

import java.util.ArrayList;
import network_representation.*;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultLegacyFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.LegacyFacebookClient;
import com.restfb.types.User;

public class FacebookInterface {
	
	LegacyFacebookClient fbkClient;
	
	public FacebookInterface(String access_token)
	{
		fbkClient = new DefaultLegacyFacebookClient(access_token);
	}
	/*
	public void set_info(Person p)
	{
		//update information for the person p
		User user = fbkClient.fetchObject(p.id, User.class);
		p.set_string_id(user.getThirdPartyId());
		p.add_friends(get_friends(p));
	}
	
	public ArrayList<Person> get_friends(Person p)
	{
		// get the friends of a person
		Connection<User> myFriends = fbkClient.fetchConnection(p.id+"/friends", User.class);
		ArrayList<Person> friend_list = new ArrayList<Person>();
		for(User u : myFriends.getData())
		{
			friend_list.add(new Person(u.getFirstName(),u.getLastName(),true));
		}
		
		return new ArrayList<Person>();
	}
	*/
}
