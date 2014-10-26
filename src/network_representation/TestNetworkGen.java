package network_representation;

public class TestNetworkGen {
	public static void main(String[] args)
	{
		Person me = new Person();
		Network n = new Network(me,"C:\\Users\\Buck\\Documents\\GitHub\\LifeNet\\src\\network_representation\\smile.simile");
		n.complete_refresh_network();
	}
}
