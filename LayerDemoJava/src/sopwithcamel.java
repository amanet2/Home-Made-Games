	public class sopwithcamel extends propPlane 
	{
			
			vickers303 rightNoseGun;
			vickers303 leftNoseGun;
	
			public String name(){return "SOPWITH CAMEL";}
	
			public double weight(){return 660;}
			public double wingSpan(){return 8.53;}
			public double topSpeed(){return 185;}
			public double climbrate(){return 5.5;}
			
			public String engineType(){return "Clerget 9B 9-Cylinder Rotary Engine";}
			public double engineHorsepower(){return 130.0;}
			
			public sopwithcamel()
			{
				rightNoseGun = new vickers303();
				leftNoseGun = new vickers303();
				
				machineGuns.add(rightNoseGun);
				machineGuns.add(leftNoseGun);
			}
	}
