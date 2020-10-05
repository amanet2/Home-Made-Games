	public class supermarinespitfire extends propPlane 
	{
			hispano2 rightWingCannon;
			hispano2 leftWingCannon;
			
			browning303 rRightWingMG;
			browning303 lRightWingMG;
			browning303 rLeftWingMG;
			browning303 lLeftWingMG;
			
			public String name(){return "SUPERMARINE SPITFIRE";}
	
			public double weight(){return 3000;}
			public double wingSpan(){return 11.23;}
			public double topSpeed(){return 595;}
			public double climbrate(){return 13.2;}

			public String engineType(){return "Rolls-Royce Merlin 45 supercharged V12 engine";}
			public double engineHorsepower(){return 1470.0;}
			
			public supermarinespitfire()
			{
				rightWingCannon = new hispano2();
				leftWingCannon = new hispano2();
				
				rRightWingMG = new browning303();
				lRightWingMG = new browning303();
				rLeftWingMG = new browning303();
				lLeftWingMG = new browning303();
				
				machineGuns.add(rRightWingMG);
				machineGuns.add(lRightWingMG);
				machineGuns.add(rLeftWingMG);
				machineGuns.add(lLeftWingMG);
				
				cannons.add(rightWingCannon);
				cannons.add(leftWingCannon);
			}
	}
