package network_representation;

import java.util.ArrayList;

public class Group {
	public ArrayList<Person> groupies;
	
	public Group()
	{
		groupies = new ArrayList<Person>();
	}
}

class GroupMember
{
	Person important;
	Double confidence;
	public GroupMember(Person p, double confidence)
	{
		important = p;
		this.confidence = confidence;
	}
}