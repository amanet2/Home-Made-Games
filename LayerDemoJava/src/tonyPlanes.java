import java.util.*;

public class tonyPlanes
{

	public static void main(String[] args)
	{
		pilot thePilot = new pilot(new p51());
		pilot enemyPilot = new pilot(new fw190());
		
		
		thePilot.printLoadout();
		System.out.println("");
		enemyPilot.printLoadout();
	}
}
