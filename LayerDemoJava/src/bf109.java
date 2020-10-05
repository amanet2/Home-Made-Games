	public class bf109 extends propPlane 
	{
			mg131 rightNoseGun;
			mg131 leftNoseGun;
			
			mg151 centerNoseGun;
	
			public String name(){return "Messerschmitt BF109";}
	
			public double weight(){return 3148;}
			public double wingSpan(){return 9.925;}
			public double topSpeed(){return 640;}
			public double climbrate(){return 17.0;}

			public String engineType(){return "Daimler-Benz DB 605A-1 liquid-cooled inverted V12";}
			public double engineHorsepower(){return 1455.0;}
			
			public bf109()
			{
				rightNoseGun = new mg131();
				leftNoseGun = new mg131();
				centerNoseGun = new mg151();
				
				machineGuns.add(rightNoseGun);
				machineGuns.add(leftNoseGun);
				cannons.add(centerNoseGun);
			}
	}
