	public class fw190 extends propPlane 
	{
			mg131 rightNoseGun;
			mg131 leftNoseGun;
			
			mg151 rightRWGun;
			mg151 leftRWGun;
			mg151 rightLWGun;
			mg151 leftLWGun;
	
			public String name(){return "FOCKE WULF FW190";}
	
			public double weight(){return 4417;}
			public double wingSpan(){return 10.51;}
			public double topSpeed(){return 656;}
			public double climbrate(){return 15;}
			
			public String engineType(){return "BMW 801 D-2 radial engine";}
			public double engineHorsepower(){return 1676.1;}
			
			public fw190()
			{
				rightNoseGun = new mg131();
				leftNoseGun = new mg131();
				
				rightRWGun = new mg151();
				leftRWGun = new mg151();
				rightLWGun = new mg151();
				leftLWGun = new mg151();
				
				machineGuns.add(rightNoseGun);
				machineGuns.add(leftNoseGun);
				
				cannons.add(rightRWGun);
				cannons.add(leftRWGun);
				cannons.add(rightLWGun);
				cannons.add(leftLWGun);
			}
	}
