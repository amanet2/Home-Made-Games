	public class dehavillandmosquito extends propPlane 
	{
			hispano2 rightFuselageCannon;
			hispano2 leftFuselageCannon;
			
			browning303 rightNoseMG;
			browning303 lCenterNoseMG;
			browning303 rCenterNoseMG;
			browning303 leftNoseMG;
	
			public String name(){return "DE HAVILLAND MOSQUITO";}
	
			public double weight(){return 8028;}
			public double wingSpan(){return 16.52;}
			public double topSpeed(){return 589;}
			public double climbrate(){return 8.8;}
			
			public String engineType(){return " 2 x Rolls-Royce Merlin 21 or 23 liquid-cooled V12";}
			public double engineHorsepower(){return 1480;}
			
			public dehavillandmosquito()
			{
				rightFuselageCannon = new hispano2();
				leftFuselageCannon = new hispano2();
				
				rightNoseMG = new browning303();
				lCenterNoseMG = new browning303();
				rCenterNoseMG = new browning303();
				leftNoseMG = new browning303();
				
				machineGuns.add(rightNoseMG);
				machineGuns.add(leftNoseMG);
				machineGuns.add(lCenterNoseMG);
				machineGuns.add(rCenterNoseMG);
				
				cannons.add(rightFuselageCannon);
				cannons.add(leftFuselageCannon);
			}
	}
