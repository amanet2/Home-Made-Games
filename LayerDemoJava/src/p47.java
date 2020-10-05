	public class p47 extends propPlane 
	{
			m2 rightRWGun;
			m2 rCenterRWGun;
			m2 lCenterRWGun;
			m2 leftRWGun;
			
			m2 rightLWGun;
			m2 rCenterLWGun;
			m2 lCenterLWGun;
			m2 leftLWGun;
	
			public String name(){return "P47 THUNDERBOLT";}
	
			public double weight(){return 6032;}
			public double wingSpan(){return 12.42;}
			public double topSpeed(){return 697;}
			public double climbrate(){return 15.9;}
			
			public String engineType(){return "Pratt & Whitney R-2800-59 twin-row radial";}
			public double engineHorsepower(){return 2535.0;}
			
			public p47()
			{
			m2 rightRWGun = new m2();
			m2 rCenterRWGun = new m2();
			m2 lCenterRWGun = new m2();
			m2 leftRWGun = new m2();
			
			m2 rightLWGun = new m2();
			m2 rCenterLWGun = new m2();
			m2 lCenterLWGun = new m2();
			m2 leftLWGun = new m2();
				
				machineGuns.add(rightRWGun);
				machineGuns.add(rCenterRWGun);
				machineGuns.add(lCenterRWGun);
				machineGuns.add(leftRWGun);
				machineGuns.add(rCenterLWGun);
				machineGuns.add(lCenterLWGun);
				machineGuns.add(rightLWGun);
				machineGuns.add(leftLWGun);
			}
	}
