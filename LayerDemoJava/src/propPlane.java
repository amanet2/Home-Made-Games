import java.util.*;	
	
	public class propPlane implements aircraftProperties, propPlaneProperties
	{
			ArrayList<machineGun> machineGuns;
			ArrayList<cannon> cannons;
			
		public String name(){ return "";}
		
		public propPlane()
		{
							
				machineGuns = new ArrayList<machineGun>();
				cannons = new ArrayList<cannon>();
		}	
	
		public String description(){ return "A propellor-driven plane.";}
	
		public double weight(){ return 0;}
		public double wingSpan(){ return 0;}
		public double topSpeed(){ return 0;}
		public double climbrate(){ return 0;}
		
		
		public String engineType(){ return "";}
		public double engineHorsepower(){ return 0;}
	}