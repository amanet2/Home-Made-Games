import java.util.*;
// Import the basic graphics classes.
import java.lang.Math;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class gameController
{
	private JFrame frame;
	private screenController gameDisplay;
	private objectController objectManager;
	
	private object camera;
	private object bigRed;
	private object visualAltimeter;
	private object visualSpeedometer;
        private object cops[];
	
	private object minimapBackground;
	private object playerMiniMapIcon;

        private object copIcons[];
    
	private int x,y;
	
	private mapController map;
	private mouseHandler mouse;
	public VGTimerTask vgTask; // The TimerTask that runs the game.
	
	private class VGTimerTask extends TimerTask
	{
		public synchronized void run()
		{
					updateCameraState();
					transformMap();
					rotateRedCamera();
					updateRed();
					updateAltimeter();
					updateSpeedometer();
					rotateRedMouse(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);
					
					objectManager.moveRotateObjects();
					objectManager.moveObjects();
					
					checkIntersection();
					updateCopDM();
					updateMinimap();
					
					gameDisplay.setObjects(objectManager.objectsMovableRotatable);
					gameDisplay.setObjects(objectManager.objectsMovable);
					gameDisplay.setObjects(objectManager.objectsStatic);
					
					objectManager.destroyPastDueMovableObjects();
					

					frame.repaint();
		}
	}
	
	public void checkIntersection() //iterate through all moving objects to detect any collisions with other objects
	{
		object[] array = new object[objectManager.objectsMovable.size()];
		array = objectManager.objectsMovable.toArray(array);
		for(int i=0;i<array.length;i++)
		{
		    for(int j=0;j<cops.length;j++)
		    {
			object cop = cops[j];
			if(cop.drawYes == true && cop.hitBox.intersects(array[i].hitBox) && array[i] != cop && array[i] != bigRed)
			{
				array[i].drawYes = false;
				array[i].model.setVisible(false);
				objectManager.objectsMovable.remove(array[i]);
				System.out.println("COP collides with an object!" + cop.health);
				cop.health-=5;
			}
		    }
		}
	}
	
	public void updateCopDM()
	{
	    for(int index = 0; index < cops.length;index++)
	    {
			object cop = cops[index];
			if(cop.health > 0)
			{
				if(cop.health <= 80 && cop.health >= 20)
				{
					if(cop.health > 60)cop.model.setIcon(new ImageIcon(getClass().getResource("Images/cop80.gif")));
					else if(cop.health > 40)cop.model.setIcon(new ImageIcon(getClass().getResource("Images/cop60.gif")));
					else if(cop.health > 20)cop.model.setIcon(new ImageIcon(getClass().getResource("Images/cop40.gif")));
					else if(cop.health > 0)cop.model.setIcon(new ImageIcon(getClass().getResource("Images/cop20.gif")));
				}
			}
			else if(cop.drawYes == true)
			{	
					cop.drawYes = false;
					//cop.model.setVisible(false);
					//objectManager.objectsMovable.remove(cop);
					copIcons[index].drawYes = false;
					copIcons[index].model.setVisible(false);
					objectManager.objectsStatic.remove(copIcons[index]);
					cop.model.setBounds(cop.model.x,cop.model.y-107,200,200);
					cop.model.setIcon(bigRed.model.deathAnimationFrames.get(0));
					cop.model.ind = 0;
			}
			else if(cop.drawYes == false)
			{
				if(cop.model.ind < bigRed.model.deathAnimationFrames.size()-1)
				{
						cop.model.setBounds(cop.model.x,cop.model.y-107,200,200);
						cop.model.ind = cop.model.ind+1;
						cop.model.setIcon(bigRed.model.deathAnimationFrames.get(cop.model.ind));
				}
				else 
				{
					objectManager.objectsMovable.remove(cop);
					//objectManager.objectsMovableRotatable.remove(bigRed);
					cop.model.setVisible(false);
				}
				/*
				
					ADVANCE THROUGH THE EXPLOSION OBJECT'S DEATHANIMATION IMAGEICON ARRAYLIST
				
				*/
			}
	    }
	}
	
	public void updateMinimap()
	{
		//System.out.println(bigRed.xCoord + " "+bigRed.yCoord);
		//playerMiniMapIcon.model.x -= (playerMiniMapIcon.model.x - (bigRed.xCoord/10));
		//playerMiniMapIcon.model.y -= (playerMiniMapIcon.model.y - (bigRed.yCoord/10));
		playerMiniMapIcon.model.setBounds(minimapBackground.model.x+x/10,minimapBackground.model.y+y/10,playerMiniMapIcon.model.width,playerMiniMapIcon.model.height);
	}
	
	public int altimeter()
	{
		return ((bigRed.yCoord-bigRed.model.height)-(gameDisplay.screenHeight-map.backgroundPanel.height));
	}
	
	public void updateAltimeter()
	{
		visualAltimeter.model.texture.setText("ALT: "+altimeter()+"m");
	}
	
	public int speedometer()
	{
		return camera.movementManager.velocity*100;
	}
	
	public void updateSpeedometer()
	{
		visualSpeedometer.model.texture.setText("SPEED: "+speedometer()+"kmph");
	}
	
	public void updateRed()
	{
		if(bigRed.yCoord-bigRed.model.height < gameDisplay.screenHeight-map.backgroundPanel.height && camera.moveable == true)
		{
			System.out.println("you died");
			camera.moveable = false;
			bigRed.movementManager.velocity = 0;
			bigRed.moveable = false;
			bigRed.rotatable= false;
			bigRed.drawYes = false;
			objectManager.objectsMovableRotatable.remove(bigRed);
			playerMiniMapIcon.model.setVisible(false);
			//bigRed.model.setVisible(false);
			
			bigRed.model.setBounds(bigRed.model.x-50,bigRed.model.y-50,200,200);
			bigRed.model.setIcon(bigRed.model.deathAnimationFrames.get(0));
			bigRed.model.ind=0;
		}
		else if(camera.moveable == false)
		{
			if(bigRed.model.ind < bigRed.model.deathAnimationFrames.size()-1)
			{
					bigRed.model.ind = bigRed.model.ind+1;
					bigRed.model.setIcon(bigRed.model.deathAnimationFrames.get(bigRed.model.ind));
			}
			else 
			{
				objectManager.objectsMovable.remove(bigRed);
				objectManager.objectsMovableRotatable.remove(bigRed);
				bigRed.model.setVisible(false);
			}
			/*
			
				ADVANCE THROUGH THE EXPLOSION OBJECT'S DEATHANIMATION IMAGEICON ARRAYLIST
			
			*/
		}
	}
	
	private void shoot()
	{
		object b = new object(2000,System.currentTimeMillis(),new imagePanel(bigRed.model.x+bigRed.width/2,bigRed.model.y+bigRed.height/2,16,16,"fireball.gif",false,666),true,false,bigRed.model.x+bigRed.width/2,bigRed.model.y+bigRed.height/2,20);
		b.movementManager.setForwardV(bigRed.movementManager.forwardV);
		objectManager.objectsMovable.add(b); 
	}
	
	private void rotateRedCamera()
	{	
		bigRed.rotate(camera.movementManager.forward,camera.movementManager.backward,camera.movementManager.strafeL,camera.movementManager.strafeR);
	}
	
	private void rotateRedMouse(int mx, int my)
	{
		if(camera.movementManager.velocity == 0) camera.movementManager.velocity++;
	
		if(!((mx >= bigRed.model.x && mx <= bigRed.model.x+bigRed.model.width) &&(my >= bigRed.model.y && my <= bigRed.model.y+bigRed.model.height)))	//dont do this if we're hovering the mouse over the center object
		{
		  //find angle of line(x,y)->(mx,my) relative to x-axis
			double delta_x = (double)mx - ((double)bigRed.model.x+(double)bigRed.model.width/2.0);
			double delta_y = (double)my-((double)bigRed.model.y+(double)bigRed.model.height/2.0);
			double theta_radians = Math.atan2(delta_y, delta_x);
			
			//visualAltimeter.model.texture.setText(Double.toString(theta_radians));
			
			//compare ranges
			if(theta_radians >= -1.9 && theta_radians <= -0.5)	//top -7/8 -> 1/8
			{	
				//if(theta_radians < 2*Math.PI
				bigRed.rotate(true,false,false,false);
				camera.movementManager.setForwardV(0);
				camera.movementManager.sprint = false;
				camera.movementManager.walk = false;
				camera.movementManager.superSprint = false;
				camera.movementManager.slowWalk = true;
			}
			if(theta_radians >= -0.5 && theta_radians <= -0.13)	//topR 1/8 -> 2/8
			{	
				//if(theta_radians < 2*Math.PI
				bigRed.rotate(true,false,false,true);
				camera.movementManager.setForwardV(1);
				camera.movementManager.sprint = false;
				camera.movementManager.walk = true;
				camera.movementManager.superSprint = false;
				camera.movementManager.slowWalk = false;
			}
			if(theta_radians >= -0.13 && theta_radians <= 0.35)	//R 2/8 -> 3/8
			{	
				//if(theta_radians < 2*Math.PI
				bigRed.rotate(false,false,false,true);
				camera.movementManager.setForwardV(2);
				camera.movementManager.sprint = false;
				camera.movementManager.walk = false;
				camera.movementManager.superSprint = false;
				camera.movementManager.slowWalk = false;
			}
			if(theta_radians >= 0.35 && theta_radians <= .6)	//botR 3/8 -> 4/8
			{	
				//if(theta_radians < 2*Math.PI
				bigRed.rotate(false,true,false,true);
				camera.movementManager.setForwardV(3);
				camera.movementManager.sprint = true;
				camera.movementManager.walk = false;
				camera.movementManager.superSprint = false;
				camera.movementManager.slowWalk = false;
			}
			if(theta_radians >= 0.6 && theta_radians <= 1.8)	//bot 4/8 -> 5/8
			{	
				//if(theta_radians < 2*Math.PI
				bigRed.rotate(false,true,false,false);
				camera.movementManager.setForwardV(4);
				camera.movementManager.sprint = false;
				camera.movementManager.walk = false;
				camera.movementManager.superSprint = true;
				camera.movementManager.slowWalk = false;
			}
			if(theta_radians >= 1.8 && theta_radians <= 2.6)	//botL 5/8 -> 6/8
			{	
				//if(theta_radians < 2*Math.PI
				bigRed.rotate(false,true,true,false);
				camera.movementManager.setForwardV(5);
				camera.movementManager.sprint = true;
				camera.movementManager.walk = false;
				camera.movementManager.superSprint = false;
				camera.movementManager.slowWalk = false;
			}
			if(theta_radians >= 2.6)	//L 5/8 -> 6/8
			{	
				//if(theta_radians < 2*Math.PI
				bigRed.rotate(false,false,true,false);
				camera.movementManager.setForwardV(6);
				camera.movementManager.sprint = false;
				camera.movementManager.walk = false;
				camera.movementManager.superSprint = false;
				camera.movementManager.slowWalk = false;
			}
			if(theta_radians <= -1.9)	//topL 6/8 -> 7/8
			{	
				//if(theta_radians < 2*Math.PI
				bigRed.rotate(true,false,true,false);
				camera.movementManager.setForwardV(7);
				camera.movementManager.sprint = false;
				camera.movementManager.walk = true;
				camera.movementManager.superSprint = false;
				camera.movementManager.slowWalk = false;
			}
		}
	}
	
	private void initRed()
	{
		bigRed.model.rotations.add(new ImageIcon(getClass().getResource("Images/bigRed0.png")));	//0..315 degr
		bigRed.model.rotations.add(new ImageIcon(getClass().getResource("Images/bigRed45.png")));	//0..315 degr
		bigRed.model.rotations.add(new ImageIcon(getClass().getResource("Images/bigRed90.png")));	//0..315 degr
		bigRed.model.rotations.add(new ImageIcon(getClass().getResource("Images/bigRed135.png")));	//0..315 degr
		bigRed.model.rotations.add(new ImageIcon(getClass().getResource("Images/bigRed180.png")));	//0..315 degr
		bigRed.model.rotations.add(new ImageIcon(getClass().getResource("Images/bigRed225.png")));	//0..315 degr
		bigRed.model.rotations.add(new ImageIcon(getClass().getResource("Images/bigRed270.png")));	//0..315 degr
		bigRed.model.rotations.add(new ImageIcon(getClass().getResource("Images/bigRed315.png")));	//0..315 degr
		
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion0.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion0.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion1.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion1.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion2.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion2.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion3.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion3.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion4.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion4.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion5.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion5.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion6.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion6.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion7.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion7.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion8.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion8.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion9.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion9.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion10.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion10.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion11.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion11.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion12.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion12.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion13.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion13.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion14.gif")));	//0..315 degr
		bigRed.model.deathAnimationFrames.add(new ImageIcon(getClass().getResource("Images/explosion14.gif")));	//0..315 degr
	}
	
	private void updateCameraState()
	{
	
		if(camera.moveable == true)
		{
			if(camera.movementManager.superSprint == true)
			{
				if(camera.movementManager.velocity < 6) camera.movementManager.velocity++;
			}
			else if(camera.movementManager.sprint == true){
				if(camera.movementManager.velocity > 4) camera.movementManager.velocity--;
				else if(camera.movementManager.velocity < 4) camera.movementManager.velocity++;
			}
			else if(camera.movementManager.walk == true){
				if(camera.movementManager.velocity > 2) camera.movementManager.velocity--;
				else if(camera.movementManager.velocity < 2) camera.movementManager.velocity++;
			}
			else if(camera.movementManager.slowWalk == true){
				if(camera.movementManager.velocity > 1) camera.movementManager.velocity--;
			}
			else 
			{
				if(camera.movementManager.velocity < 3 && !(camera.movementManager.forward == false && camera.movementManager.backward == false && camera.movementManager.strafeL == false && camera.movementManager.strafeR == false)) camera.movementManager.velocity++;
				else if((camera.movementManager.velocity > 0 && camera.movementManager.forward == false && camera.movementManager.backward == false && camera.movementManager.strafeL == false && camera.movementManager.strafeR == false) || camera.movementManager.velocity > 3) camera.movementManager.velocity--;
			}
			
			//detect plane at boundaries
			//System.out.println(x);
			if(bigRed.xCoord+bigRed.model.width/2 >= ((gameDisplay.screenWidth/2-bigRed.model.width/2)+gameDisplay.screenWidth/2)) camera.movementManager.strafeL = false;
			if(bigRed.yCoord+bigRed.model.height/2 >= ((gameDisplay.screenHeight/2-bigRed.model.height/2)+gameDisplay.screenHeight/2)) camera.movementManager.forward = false;
			if(x+bigRed.model.width >= map.backgroundPanel.width) camera.movementManager.strafeR = false;
		}
		else camera.movementManager.velocity = 0;
	}
	
	private void transformMap()
	{
			if(camera.movementManager.forward == true && camera.movementManager.backward == false && camera.movementManager.strafeL == false && camera.movementManager.strafeR == false)
			{
				map.moveMapY(camera.movementManager.velocity);
				objectManager.transformObjectsY(camera.movementManager.velocity);
				
				y=y-camera.movementManager.velocity;
			}
			else if(camera.movementManager.forward == true && camera.movementManager.backward == false && camera.movementManager.strafeL == false && camera.movementManager.strafeR == true)
			{
				map.moveMapY(camera.movementManager.velocity);
				objectManager.transformObjectsY(camera.movementManager.velocity);
				map.moveMapX(-(camera.movementManager.velocity));
				objectManager.transformObjectsX(-(camera.movementManager.velocity));
				
				y=y-camera.movementManager.velocity;
				x=x+camera.movementManager.velocity;
			}
			else if(camera.movementManager.forward == false && camera.movementManager.backward == false && camera.movementManager.strafeL == false && camera.movementManager.strafeR == true)
			{
				map.moveMapX(-(camera.movementManager.velocity));
				objectManager.transformObjectsX(-(camera.movementManager.velocity));
				
				x=x+camera.movementManager.velocity;
			}
			else if(camera.movementManager.forward == false && camera.movementManager.backward == true && camera.movementManager.strafeL == false && camera.movementManager.strafeR == true)
			{
				map.moveMapY(-(camera.movementManager.velocity));
				objectManager.transformObjectsY(-(camera.movementManager.velocity));
				map.moveMapX(-(camera.movementManager.velocity));
				objectManager.transformObjectsX(-(camera.movementManager.velocity));
				
				y=y+camera.movementManager.velocity;
				x=x+camera.movementManager.velocity;
			}
			else if(camera.movementManager.forward == false && camera.movementManager.backward == true && camera.movementManager.strafeL == false && camera.movementManager.strafeR == false)
			{
				map.moveMapY(-(camera.movementManager.velocity));
				objectManager.transformObjectsY(-(camera.movementManager.velocity));
				
				y=y+camera.movementManager.velocity;
			}
			else if(camera.movementManager.forward == false && camera.movementManager.backward == true && camera.movementManager.strafeL == true && camera.movementManager.strafeR == false)
			{
				map.moveMapY(-(camera.movementManager.velocity));
				objectManager.transformObjectsY(-(camera.movementManager.velocity));
				map.moveMapX(camera.movementManager.velocity);
				objectManager.transformObjectsX(camera.movementManager.velocity);
				
				y=y+camera.movementManager.velocity;
				x=x-camera.movementManager.velocity;
			}
			else if(camera.movementManager.forward == false && camera.movementManager.backward == false && camera.movementManager.strafeL == true && camera.movementManager.strafeR == false)
			{
				map.moveMapX(camera.movementManager.velocity);
				objectManager.transformObjectsX(camera.movementManager.velocity);
				
				x=x-camera.movementManager.velocity;
			}
			else if(camera.movementManager.forward == true && camera.movementManager.backward == false && camera.movementManager.strafeL == true && camera.movementManager.strafeR == false)
			{
				map.moveMapY(camera.movementManager.velocity);
				objectManager.transformObjectsY(camera.movementManager.velocity);
				map.moveMapX(camera.movementManager.velocity);
				objectManager.transformObjectsX(camera.movementManager.velocity);
				
				y=y-camera.movementManager.velocity;
				x=x-camera.movementManager.velocity;
			}		
	}
	
	private class keyboardHandler extends Thread implements KeyListener 
	{	
		public synchronized void keyTyped(KeyEvent e)
		{
			
		}
		
		public synchronized void keyPressed(KeyEvent e)
		{
		    /*if(e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				shoot();
			}	
			if(e.getKeyCode() == KeyEvent.VK_W)
			{
				camera.movementManager.forward = true;
				camera.movementManager.backward = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_S)
			{
				camera.movementManager.forward = false;
				camera.movementManager.backward = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_D)
			{
				camera.movementManager.strafeR = true;
				camera.movementManager.strafeL = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_A)
			{
				camera.movementManager.strafeR = false;
				camera.movementManager.strafeL = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_SHIFT)
			{
				camera.movementManager.sprint = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_CONTROL)
			{
				camera.movementManager.walk = true;
			}*/
		}

	    public synchronized void keyReleased(KeyEvent e)
		{
		//System.out.println("KEY RELEASED");
			/*if(e.getKeyCode() == KeyEvent.VK_W)
			{
				camera.movementManager.forward = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_S)
			{
				camera.movementManager.backward = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_D)
			{
				camera.movementManager.strafeR = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_A)
			{
				camera.movementManager.strafeL = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_SHIFT)
			{
				camera.movementManager.sprint = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_CONTROL)
			{
				camera.movementManager.walk = false;
			}*/
		}
	}
	
	private class mouseHandler extends Thread implements MouseListener 
	{	
		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
			shoot();
		}
	}
	
	public void showFrame()
	{
		if(gameDisplay != null)
		{
	    // Set up our JFRame
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setPreferredSize(new Dimension(gameDisplay.screenWidth,gameDisplay.screenHeight+20));
		frame.pack();
	    frame.setContentPane(gameDisplay); 
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
		
		java.util.Timer vgTimer = new java.util.Timer();  // Create a Timer.
	    // Set up a timer to do the vgTask regularly.
	    vgTimer.schedule(vgTask, 0, 20);	//update 50 times a second
		}
	}
	
	public gameController(int w, int h)
	{
		frame = new JFrame("Use the mouse to move around, left/right-click to fire machine guns.");
		frame.setResizable(false);
		frame.addKeyListener(new keyboardHandler());
		mouse = new mouseHandler();
		frame.addMouseListener(mouse);
		gameDisplay = new screenController(w,h);
		objectManager = new objectController();
		camera = new object(new imagePanel(1, 1, 0),true,false);
		camera.model.setVisible(false);
		camera.drawYes = false;
		
		map = new mapController(3000,3000);
		gameDisplay.setMap(map);
		
		bigRed = new object(new imagePanel(w/2-36,h/2-36,72,72,"bigRed90.png",false,666),true,true,w/2-36,h/2-36,0); 
		objectManager.objectsStatic.add(bigRed);
		initRed();

		cops = new object[6];
		for(int i=1;i<=cops.length;i++)
		{
			cops[i-1] = new object(new imagePanel(map.backgroundPanel.width-500*i,map.backgroundPanel.height-200,222,93,"cop.gif",false,5),true,false,map.backgroundPanel.width-500*i,map.backgroundPanel.height-200,0);
			objectManager.objectsMovable.add(cops[i-1]);
		}
		
		visualAltimeter = new object(new imagePanel("visual altimeter",gameDisplay.screenWidth-200,gameDisplay.screenHeight-100,100,50,"ALT: "+Integer.toString(altimeter()) + "m",true,700),true,false,gameDisplay.screenWidth-200,gameDisplay.screenHeight-100,0);
		objectManager.objectsStatic.add(visualAltimeter);
		visualAltimeter.model.setBackground(new java.awt.Color(255, 165, 0, 180));
		
		visualSpeedometer = new object(new imagePanel("visual speedometer",gameDisplay.screenWidth-310,gameDisplay.screenHeight-100,100,50,"SPEED: "+Integer.toString(speedometer()) + "kmph",true,701),true,false,gameDisplay.screenWidth-310,gameDisplay.screenHeight-100,0);
		objectManager.objectsStatic.add(visualSpeedometer);
		visualSpeedometer.model.setBackground(new java.awt.Color(255,165,0,180));
		
		minimapBackground = new object(new imagePanel("minimap",10,gameDisplay.screenHeight-map.backgroundPanel.height/10-10,map.backgroundPanel.width/10,map.backgroundPanel.height/10,"",true,702),true,false,10,gameDisplay.screenHeight-map.backgroundPanel.height/10-10,0);
		objectManager.objectsStatic.add(minimapBackground);
		minimapBackground.model.setBackground(new java.awt.Color(255, 165, 0, 255));
		
		playerMiniMapIcon = new object(new imagePanel(minimapBackground.model.x + ((w/2-36)/10),minimapBackground.model.y + ((h/2-36)/10),25,25,"minimapicon_ger.png",false,703),false,false,minimapBackground.model.x + ((w/2-36)/10),minimapBackground.model.y + ((h/2-36)/10),0); 
		objectManager.objectsStatic.add(playerMiniMapIcon);

		copIcons = new object[cops.length];
		
		for(int i = 0; i < copIcons.length;i++)
		{
			copIcons[i] = new object(new imagePanel(minimapBackground.model.x+cops[i].model.x/10,minimapBackground.model.y+cops[i].model.y/10,16,16,"minimapicon_generic.png",false,705),false,false,minimapBackground.model.x,minimapBackground.model.y,0);
			//	copIcons[0].miniX = 100;
			//copIcons[0].miniY = 200;
			objectManager.objectsStatic.add(copIcons[i]);
		}
		
		vgTask = new VGTimerTask();
		
		x=w/2-36;
		y=h/2-36;
	}
	
	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			gameController gC = new gameController(Integer.valueOf(args[0]),Integer.valueOf(args[1]));
			
			gC.showFrame();
			//System.exit(0);
		}
	}		
}
