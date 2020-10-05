	
	public class pilot implements fireArmFunction, pilotFunction
	{
		propPlane currentPlane;
		
		public pilot(propPlane p)
		{
			currentPlane = p;
		}
		
		public double fire()
		{
			int gunCount = 1;
			double damageSum = 0;
			for(machineGun mg : currentPlane.machineGuns)
			{
				damageSum += (mg.caliber() * mg.velocity());
				System.out.println("Gun " + gunCount + ": " + (mg.caliber() * mg.velocity()) + " dmg");
				gunCount++;
			}
			for(cannon c : currentPlane.cannons)
			{
				damageSum += (c.caliber() * c.velocity());
				System.out.println("Gun " + gunCount + ": " + (c.caliber() * c.velocity()) + " dmg");
				gunCount++;
			}
			return damageSum;
		}
		
		public void reload()
		{
			System.out.println("All " + (currentPlane.machineGuns.size() + currentPlane.cannons.size()) + " guns reloaded.");
		}
		
		public void printLoadout()
		{
			System.out.println(currentPlane.name() + " - " + currentPlane.description() + "\n--------");
			System.out.println("	Weight: " + currentPlane.weight() + "kg");
			System.out.println("	Wingspan: " + currentPlane.wingSpan() + "m");
			System.out.println("	Top Speed: " + currentPlane.topSpeed() + "kmph");
			System.out.println("	Climb Rate: " + currentPlane.climbrate() + "m/s");
			System.out.println("	Armament\n	------");
			System.out.println("		Machine Guns\n		------");
			
				for(machineGun mg : currentPlane.machineGuns)
				{
					System.out.println("			" + mg.description());
					System.out.println("			Caliber: " + mg.caliber() + "mm");
					System.out.println("			Muzzle Velocity: " + mg.velocity() + "m/s");
					System.out.println("\n");
				}
				
			System.out.println("		Cannons\n		------");
			
				for(cannon mg : currentPlane.cannons)
				{
					System.out.println("			" + mg.description());
					System.out.println("			Caliber: " + mg.caliber() + "mm");
					System.out.println("			Muzzle Velocity: " + mg.velocity() + "m/s");
					System.out.println("\n");
				}
		}
	}