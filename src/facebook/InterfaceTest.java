package facebook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import network_representation.Person;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.LegacyFacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;

public class InterfaceTest 
{
	public static void main(String[] args)
	{
		String access_token_file = "C:\\Users\\Buck\\Documents\\GitHub\\LifeNet\\src\\network_representation\\smile.simile";
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

		FacebookInterface fbki = new FacebookInterface(access_token);
		LegacyFacebookClient client = fbki.fbkClient;
		Long uid = client.execute("users.getLoggedInUser", Long.class);
		String query =
			  "SELECT name, pic_big, affiliations FROM user " +
			  "WHERE uid IN (SELECT uid2 FROM friend WHERE uid1="+uid.toString()+")";
		System.out.println("uid: "+uid);
		List<User> users = client.executeForList("fql.query", User.class,
			    Parameter.with("query", query), Parameter.with("return_ssl_resources", "true"));
		for (User user : users)
			  System.out.println(user + "\n");
	}
}
