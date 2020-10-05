	public class p39 extends propPlane 
	{
			m2 rightNoseGun;
			m2 leftNoseGun;
		
			m4 centerNoseGun;

			browning303 lLeftWingGun;
			browning303 rLeftWingGun;
			browning303 lRightWingGun;
			browning303 rRightWingGun;
	
			public String name(){return "P39 AIRCOBRA";}
	
			public double weight(){return 3347;}
			public double wingSpan(){return 10.4;}
			public double topSpeed(){return 605;}
			public double climbrate(){return 19;}
			
			public String engineType(){return "Allison V-1710-85 liquid-cooled V-12";}
			public double engineHorsepower(){return 1200;}
			
			public p39()
			{
				rightNoseGun = new m2();
				leftNoseGun = new m2();
		
				centerNoseGun = new m4();

				lLeftWingGun = new browning303();
				rLeftWingGun = new browning303();
				lRightWingGun = new browning303();
				rRightWingGun = new browning303();
				
				machineGuns.add(rightNoseGun);
				machineGuns.add(leftNoseGun);
				machineGuns.add(lLeftWingGun);
				machineGuns.add(rLeftWingGun);
				machineGuns.add(lRightWingGun);
				machineGuns.add(rRightWingGun);
				
				cannons.add(centerNoseGun);
			}
	}
