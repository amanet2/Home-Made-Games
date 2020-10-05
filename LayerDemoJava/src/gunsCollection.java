import java.util.*;

public class gunsCollection
{
	public gun getGun(String s)
	{
		for(gun g : getGunCollection())
		{
			if(s.equalsIgnoreCase(g.name()))
				return g;
		}
		return null;
	}
	
	ArrayList<gun> listOfGuns;
	
	public ArrayList<gun> getGunCollection()
	{
		if(listOfGuns == null)
		{
			listOfGuns = new ArrayList<gun>();
			populateGuns();
		}
		
		return listOfGuns;
	}
	
	public void populateGuns()
	{
		getGunCollection().add(new m2());
		getGunCollection().add(new hispano2());
		getGunCollection().add(new mg131());
		getGunCollection().add(new mg151());
		getGunCollection().add(new vickers303());
		getGunCollection().add(new browning303());
		getGunCollection().add(new m4());
	}
	
	public gunsCollection()
	{
		listOfGuns = getGunCollection();
	}
}
