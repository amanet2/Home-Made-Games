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

class goalZone
{
	public Ellipse2D screenObject;
	public Rectangle goal;
	public int xCoord, yCoord;
	public int radius;
	public Color zoneColor;
	public Color teamColor;
	
	public goalZone()
	{
		xCoord = 0;
		yCoord = 0;
		radius = 100;
		zoneColor = Color.CYAN;
		teamColor = Color.GREEN;
		screenObject = new Ellipse2D.Float(xCoord,yCoord,radius,radius);
		goal = new Rectangle(xCoord,yCoord,-10,radius);
	}

	public goalZone(int x, int y, int r, int team)
	{
		radius = r;
		xCoord = x;
		yCoord = y;
		zoneColor = Color.CYAN;

		if(team == 1){	//on left side
			teamColor = Color.GREEN;
			goal = new Rectangle(xCoord+radius/2-5,yCoord,10,radius);
		}
		else{	//on right side
			 teamColor = Color.BLUE;
			goal = new Rectangle(xCoord+radius/2-6,yCoord,10,radius);
		}

		screenObject = new Ellipse2D.Float(xCoord,yCoord,radius,radius);
	}
}

class player
{
	public Rectangle screenObject;
	public int xCoord, yCoord;
	public boolean up,down,left,right;
	public int width, height;
	public Color teamColor;
	public int score;
	public int velocity;
	public int screenWidth,screenHeight;

	public void move()
	{

		if(velocity == 0){ up = false; down = false; left = false; right = false;}
		
 		// Detect edges and bounce.
		if(xCoord+width >= (screenWidth)){ right = false; xCoord = screenWidth - width; }
		if(yCoord+height >= (screenHeight)){ down = false; yCoord = screenHeight-height;}
    		if(xCoord <= 0){ left = false; xCoord = 0; }
   		if(yCoord <= 0){ up = false; yCoord = 0; }

		if(left == true && up == true)
		{ 
			xCoord -= velocity;
			yCoord -= velocity;
		}	
		else if(right == true && up == true)
		{
			xCoord += velocity;
			yCoord -= velocity;
		}
		else if(right == true && down == true)
		{
			xCoord += velocity;
			yCoord += velocity;
		}
		else if(left == true && down == true)
		{
			xCoord -= velocity;
			yCoord += velocity;
		}
		else if(left == true)
		{
			xCoord -= velocity;
		}
		else if(right == true)
		{
			xCoord += velocity;
		}
		else if(up == true)
			yCoord -= velocity;
		else if(down == true)
			yCoord += velocity;

		if(velocity > 0 && up == false && down == false && left == false && right == false) velocity--;
	}

	public player()
	{
		screenWidth = 600;
		screenHeight = 400;
		xCoord = 40;
		yCoord = 40;
		down = false;
		up = false;
		left = false;
		right = false;
		height = 30;
		width = 30;
		teamColor = Color.MAGENTA;
		score = 0;
		velocity = 0;
		screenObject = new Rectangle(xCoord,yCoord,width,height);
	}

	public player(int t,int w, int h) //0 for green team, 1 for blue team
	{
		screenWidth = w;
		screenHeight = h;
		down = false;
		up = false;
		left = false;
		right = false;
		height = h/10-10;
		width = h/10-10;
		score = 0;
		velocity = 0;

		if(t == 0)
		{
			xCoord = w/2-h/4;
			yCoord = h/2-height/2;
			teamColor = Color.GREEN;	
		}
		else
		{
			xCoord = w/2+h/4-width;
			yCoord = h/2-height/2;
			teamColor = Color.BLUE;
		}
	

		screenObject = new Rectangle(xCoord,yCoord,width,height);	
	}

}

class puckObject
{

	public int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	/*
		TODO: INSERT HORIZONTAL/VERTICAL MODIFIERS IN MOVEMENT
	*/
	public Rectangle screenObject;	//the representation of the puck on screen
	public int xCoord, yCoord;	//coordinates of puck as public ints
	public boolean down,right;	//direction of travel
	public boolean left, up;		//if the puck is to travel on a flat vector to the left/right, up/down
	public int width, height;
	public int horizontalModifier,verticalModifier;

	public int screenWidth,screenHeight; //for bouncing off sides

	public int velocity;
	public int pastT;
	public int firstHit;

	public void move(int t)
	{
 		// Detect edges and bounce.
		if(velocity == 0){up =false; down = false; right = false; left = false;}

		if(xCoord >= (screenWidth - width) && !(yCoord <= screenHeight/2+screenHeight/8 && yCoord >= screenHeight/2-screenHeight/8)){ right = false; left = true;xCoord = screenWidth - width; }
		else if(yCoord >= (screenHeight - height)){ down  = false; up = true; yCoord = screenHeight - height;}
    		else if(xCoord <= 0 && !(yCoord <= screenHeight/2+screenHeight/8 && yCoord >= screenHeight/2-screenHeight/8)){ right = true; left = false; xCoord = 0; }
   		else if(yCoord <= 0){ down  = true; up = false; yCoord = 0; }


		if(left == false && right == true && up == false && down == false)
		{
			xCoord += velocity;
		}
		else if(left == true && right == false && up == false && down == false)
		{
			xCoord -= velocity;
		}
		else if(left == false && right == false && up == false && down == true)
		{
			yCoord += velocity;
		}
		else if(left == false && right == false && up == true && down == false)
		{
			yCoord -= velocity;
		}
		else if(down == true && right == true)
		{
				xCoord += velocity/horizontalModifier;
				yCoord += velocity/verticalModifier;
		}
		else if(down == true && left == true)
		{
				xCoord -= velocity/horizontalModifier;
				yCoord += velocity/verticalModifier;
		}
		else if(up == true && right == true)
		{
				xCoord += velocity/horizontalModifier;
				yCoord -= velocity/verticalModifier;
		}
		else if(up == true && left == true)
		{
				xCoord -= velocity/horizontalModifier;
				yCoord -= velocity/verticalModifier;
		}

		
		if(velocity > 0 && t != pastT){
			velocity--;
			if(horizontalModifier > 1) horizontalModifier--;
			if(verticalModifier > 1) verticalModifier--;
			pastT = t;
		}
		else if(t != pastT)
		{
			if(horizontalModifier > 1) horizontalModifier--;
			if(verticalModifier > 1) verticalModifier--;
			pastT = t;
		}
	}
	
	public void setPuck()
	{
	velocity = 3;
		switch(randInt(1,8)){
			case 1:  	
				up = true;
				down = false;
				left = false;
				right = false;
				break;
			case 2:  	
				up = true;
				down = false;
				left = false;
				right = true;
				break;
			case 3:  	
				up = false;
				down = false;
				left = false;
				right = true;
				break;
			case 4:  	
				up = false;
				down = true;
				left = false;
				right = true;
				break;
			case 5:  	
				up = false;
				down = true;
				left = false;
				right = false;
				break;
			case 6:  	
				up = false;
				down = true;
				left = true;
				right = false;
				break;
			case 7:  	
				up = false;
				down = false;
				left = true;
				right = false;
				break;
			case 8:  	
				up = true;
				down = false;
				left = true;
				right = false;
				break;
			default:  	
				up = true;
				down = false;
				left = false;
				right = false;
				break;
		}
	}

	public puckObject()
	{
		screenWidth = 600;
		screenHeight = 400;
		xCoord = 293;
		yCoord = 193;
		width = 15;
		height = 15;	
		setPuck();
		pastT = 0;
		horizontalModifier = 1;
		verticalModifier = 1;
		firstHit = 0;
		screenObject = new Rectangle(xCoord,yCoord,width,height);
	}

	public puckObject(int w, int h)
	{
		screenWidth = w;
		screenHeight = h;
		width = (h/10-10)/2;
		height = (h/10-10)/2;
		xCoord = w/2-width/2;
		yCoord = h/2-height/2;
		setPuck();
		pastT = 0;
		horizontalModifier = 1;
		verticalModifier = 1;
		firstHit = 0;
		screenObject = new Rectangle(xCoord,yCoord,width,height);
	}
}

class gameScreen extends JPanel
{
	//for audio in the game
	AudioInputStream audioIn;
	Clip audioClip;

	public Rectangle screen; // The screen area
	int screenWidth, screenHeight;
	public Rectangle bounds;	//boundaires of the drawing area
	public Rectangle ground;
	public JFrame frame; // A JFrame to put the graphics into.
	public VGTimerTask vgTask; // The TimerTask that runs the game.
	public JMenuBar titleBar;
	public JMenu blueTeam,greenTeam;
	public JMenu blueScore,greenScore;
	public JMenu greenControls, blueControls;

	public puckObject puck;
	public goalZone blueGoal;
	public goalZone greenGoal;

	public player player1;	//green
	public player player2;	//blue
	public ArrayList<player> players;

	public javax.swing.Timer timer;

	int timeAccumulated = 0;

	int loss = 0; //1 if p1 loses, 2 if p2 loses

	public ActionListener taskPerformer = new ActionListener() { //handler for the cuckoo clock
		public void actionPerformed(ActionEvent e) {	//action performed
			timeAccumulated++;
		}
	};

	public void updateScoreboard()
	{
		blueScore.setText("["+Integer.toString(player2.score)+"]");
		greenScore.setText("["+Integer.toString(player1.score)+"]");
	}

	private class keyboardHandler implements KeyListener
	{	
		
		public synchronized void keyTyped(KeyEvent e) 
		{
		//System.out.println("KEY TYPED: ");
		}

		public synchronized void keyPressed(KeyEvent e)
		{
			//System.out.println("KEY PRESSED");
			if(e.getKeyCode() == KeyEvent.VK_W)
			{
				player1.up = true;
				player1.down = false;

				player1.velocity = (screenHeight/100);
			}
			if(e.getKeyCode() == KeyEvent.VK_S)
			{
				
				player1.down = true;
				player1.up = false;

				player1.velocity = (screenHeight/100);
			}

			if(e.getKeyCode() == KeyEvent.VK_D)
			{
				
				player1.right = true;
				player1.left = false;

				player1.velocity = (screenHeight/100);
			}
			if(e.getKeyCode() == KeyEvent.VK_A)
			{
		
				player1.left = true;
				player1.right = false;

				player1.velocity = (screenHeight/100);
			}


			//System.out.println("KEY PRESSED");
			if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				player2.up = true;
				player2.down = false;

				player2.velocity = (screenHeight/100);
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				
				player2.down = true;
				player2.up = false;

				player2.velocity = (screenHeight/100);
			}

			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				
				player2.right = true;
				player2.left = false;

				player2.velocity = (screenHeight/100);
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
		
				player2.left = true;
				player2.right = false;
				player2.velocity = (screenHeight/100);
			}
			
		}

	        public synchronized void keyReleased(KeyEvent e)
		{
		//System.out.println("KEY RELEASED");
			if(e.getKeyCode() == KeyEvent.VK_W)
			{
				player1.up = false;

			}
			if(e.getKeyCode() == KeyEvent.VK_S)
			{
				
				player1.down = false;

			}
			if(e.getKeyCode() == KeyEvent.VK_D)
			{
				player1.right = false;
				
			}
			if(e.getKeyCode() == KeyEvent.VK_A)
			{
				player1.left = false;

			}

			if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				player2.up = false;

			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				
				player2.down = false;

			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				player2.left = false;
				
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				player2.right = false;

			}
		}
	}
		

	public void checkIntersection()
	{	
		/*
			TODO: INSERT CODE TO CREATE HORIZONTAL/VERTICAL MODIFIERS
		*/
			//player 1 is green

				// Detect edges and bounce.
		if(player1.xCoord+player1.width >= 2*player1.screenWidth/3){ 
			player1.right = false; 
			player1.left = true; 	//punish the player for going too far >8)
			player1.xCoord = 2*player1.screenWidth/3 - player1.width; }
		if(player2.xCoord <= player2.screenWidth/3){
			player2.left = false; 
			player2.right = true; 
			player2.xCoord = player2.screenWidth/3;}
		//System.out.println(Integer.toString(p1.xCoord+width));
		/*
			CREATE LINES AND CHECK INTERSECTIONS AND ACT ACCORDINGLY
		*/
		Line2D puTopLeft = new Line2D.Float(puck.xCoord-puck.velocity,puck.yCoord,puck.xCoord,puck.yCoord);
		Line2D puBottomLeft = new Line2D.Float(puck.xCoord-puck.velocity,puck.yCoord+puck.height,puck.xCoord,puck.yCoord+puck.height);
		Line2D puTopRightUp = new Line2D.Float(puck.xCoord+puck.width,puck.yCoord-puck.velocity,puck.xCoord+puck.width,puck.yCoord);
		Line2D puTopLeftUp = new Line2D.Float(puck.xCoord,puck.yCoord-puck.velocity,puck.xCoord,puck.yCoord);
		Line2D puTopRight = new Line2D.Float(puck.xCoord+puck.width,puck.yCoord,puck.xCoord+puck.width+puck.velocity,puck.yCoord);	
		Line2D puBottomRight = new Line2D.Float(puck.xCoord+puck.width,puck.yCoord+puck.height,puck.xCoord+puck.width+puck.velocity,puck.yCoord+puck.height);
		Line2D puBottomLeftDown = new Line2D.Float(puck.xCoord,puck.yCoord+puck.height,puck.xCoord,puck.yCoord+puck.height+puck.velocity);
		Line2D puBottomRightDown = new Line2D.Float(puck.xCoord+puck.width,puck.yCoord+puck.height,puck.xCoord+puck.width,puck.yCoord+puck.height+puck.velocity);

		Line2D puCenterLatitude = new Line2D.Float(puck.xCoord,puck.yCoord+puck.height/2,puck.xCoord+puck.width,puck.yCoord+puck.height/2);
		Line2D puCenterLongitude = new Line2D.Float(puck.xCoord+puck.width/2,puck.yCoord,puck.xCoord+puck.width/2,puck.yCoord+puck.height);

		for(player p : players){

		/*	Line2D p1RightSide = new Line2D.Float(p.xCoord+p.width,p.yCoord-p.velocity,p.xCoord+p.width,p.yCoord+p.height+p.velocity);
			Line2D p1TopSide = new Line2D.Float(p.xCoord-p.velocity,p.yCoord,p.xCoord+p.width+p.velocity,p.yCoord);
			Line2D p1LeftSide = new Line2D.Float(p.xCoord,p.yCoord-p.velocity,p.xCoord,p.yCoord+p.height+p.velocity);
			Line2D p1BottomSide = new Line2D.Float(p.xCoord-p.velocity,p.yCoord+p.height,p.xCoord+p.width+p.velocity,p.yCoord+p.height);*/	//old one with extra long lines

			Line2D p1RightSide = new Line2D.Float(p.xCoord+p.width,p.yCoord,p.xCoord+p.width,p.yCoord+p.height);
			Line2D p1TopSide = new Line2D.Float(p.xCoord,p.yCoord,p.xCoord+p.width,p.yCoord);
			Line2D p1LeftSide = new Line2D.Float(p.xCoord,p.yCoord,p.xCoord,p.yCoord+p.height);
			Line2D p1BottomSide = new Line2D.Float(p.xCoord,p.yCoord+p.height,p.xCoord+p.width,p.yCoord+p.height);


			boolean hit = false;
			if((puTopLeft.intersectsLine(p1RightSide) == true && puBottomLeft.intersectsLine(p1RightSide) == false))
			{	//hits the bottom right side of the green player's paddle
				//&& puck.right == false
				hit = true;
				
				puck.xCoord = p.xCoord+p.width+p.velocity-2+puck.velocity;

				if(puck.down == true)
				{
					puck.down = true;
					puck.up = false;
					puck.horizontalModifier = 3;
				}
				else if(puck.up == true)
				{
					puck.up = true;
					puck.down = false;
					puck.horizontalModifier = 3;
				}
				else{ puck.down = true;
				puck.horizontalModifier = 2;}

				
				puck.left = false;
				puck.right = true;
			}
			else if((puTopLeft.intersectsLine(p1RightSide) == true && puBottomLeft.intersectsLine(p1RightSide) == true))
			{	//hits the center right side of the green player's paddle
			
				//&& puck.right == false
				puck.xCoord = p.xCoord+p.width+p.velocity-2+puck.velocity;
				puck.right = true;
				puck.left = false;

				hit = true;
			}
			else if((puTopLeft.intersectsLine(p1RightSide) == false && puBottomLeft.intersectsLine(p1RightSide) == true))
			{	//hits the top right side of the green player's paddle
				//&& puck.right == false
				hit = true;
				puck.xCoord = p.xCoord+p.width+p.velocity-2+puck.velocity;

				if(puck.down == true)
				{
					puck.down = true;
					puck.up = false;
					puck.horizontalModifier = 3;
				}
				else if(puck.up == true)
				{
					puck.up = true;
					puck.down = false;
					puck.horizontalModifier = 3;
				}
				else{ puck.up = true;
				puck.horizontalModifier = 2;}

				puck.right = true;
				puck.left = false;

			}	
			else if(puTopLeftUp.intersectsLine(p1BottomSide) && puTopRightUp.intersectsLine(p1BottomSide) == false)
			{
				// && puck.down == false
				hit = true;
				puck.yCoord = p.yCoord+p.height+p.velocity-2+puck.velocity;

				if(puck.right == true){
					puck.right = true;
					puck.left = false;
					puck.verticalModifier = 3;
				}
				else if(puck.left == true)
				{
					puck.left = true;
					puck.right = false;
					puck.verticalModifier = 3;
				}
				else{
					puck.right = true;
					puck.verticalModifier = 2;
				}

				puck.down = true;
				puck.up = false;

			}	
			else if(puTopLeftUp.intersectsLine(p1BottomSide) && puTopRightUp.intersectsLine(p1BottomSide))
			{

				//&& puck.down == false)
				puck.yCoord = p.yCoord+p.height+p.velocity-2+puck.velocity;

				puck.down = true;
				puck.up = false;
				hit = true;
			}	
			else if(puTopLeftUp.intersectsLine(p1BottomSide) == false && puTopRightUp.intersectsLine(p1BottomSide))
			{
				// && puck.down == false
				hit = true;
				puck.yCoord = p.yCoord+p.height+p.velocity-2+puck.velocity;

				if(puck.right == true){
					puck.right = true;
					puck.left = false;
					puck.verticalModifier = 3;
				}
				else if(puck.left == true)
				{
					puck.left = true;
					puck.right = false;
					puck.verticalModifier = 3;
				}
				else{
					puck.left = true;
					puck.verticalModifier = 2;
				}

				puck.down = true;
				puck.up = false;

			}	
			else if(puTopRight.intersectsLine(p1LeftSide) && puBottomRight.intersectsLine(p1LeftSide) == false )
			{
				//&& puck.right == true
				puck.xCoord = p.xCoord-puck.width-p.velocity-2-puck.velocity;

				if(puck.down == true){
					puck.down = true;
					puck.up = false;
					puck.horizontalModifier = 3;
				}
				else if(puck.up == true)
				{
					puck.down = false;
					puck.up = true;
					puck.horizontalModifier = 3;
				}
				else{
					puck.down = true;
					puck.horizontalModifier = 2;
				}				

				puck.right = false;
				puck.left = true;
				hit = true;
			}
			else if(puTopRight.intersectsLine(p1LeftSide) && puBottomRight.intersectsLine(p1LeftSide))
			{
				 //&& puck.right == true
				puck.xCoord = p.xCoord-puck.width-p.velocity-2-puck.velocity;
				puck.right = false;
				puck.left = true;
				hit = true;
			}
			else if(puTopRight.intersectsLine(p1LeftSide) == false && puBottomRight.intersectsLine(p1LeftSide) )
			{
				//&& puck.right == true
				puck.xCoord = p.xCoord-puck.width-p.velocity-2-puck.velocity;

				if(puck.down == true){
					puck.down = true;
					puck.up = false;
					puck.horizontalModifier = 3;
				}
				else if(puck.up == true)
				{
					puck.down = false;
					puck.up = true;
					puck.horizontalModifier = 3;
				}
				else{
					puck.up = true;
					puck.horizontalModifier = 2;
				}				

				puck.right = false;
				puck.left = true;
				hit = true;

			}	
			else if(puBottomLeftDown.intersectsLine(p1TopSide) && puBottomRightDown.intersectsLine(p1TopSide)==false )
			{
				//&& puck.down == true
				puck.yCoord = p.yCoord-puck.height-p.velocity-2-puck.velocity;
				if(puck.right == true){
					puck.left = false;
					puck.right = true;
					puck.verticalModifier = 3;
				}
				else if(puck.left == true){
					puck.left = true;
					puck.right = false;
					puck.verticalModifier = 3;
				}
				else{
					puck.right = true;
					puck.verticalModifier = 2;
				}

				puck.up = true;
				puck.down = false;
				hit = true;
			}
			else if(puBottomLeftDown.intersectsLine(p1TopSide) && puBottomRightDown.intersectsLine(p1TopSide))
			{
				// && puck.down == true
				puck.yCoord = p.yCoord-puck.height-p.velocity-2-puck.velocity;
				puck.down = false;
				puck.up = true;
				hit = true;

			}	//TODO: Finish editing collision events
			else if(puBottomLeftDown.intersectsLine(p1TopSide) == false && puBottomRightDown.intersectsLine(p1TopSide) )
			{
				//if(puck.down == true)
				puck.yCoord = p.yCoord-puck.height-p.velocity-2-puck.velocity;
				if(puck.left == true){
					puck.left = true;
					puck.right = false;
					puck.horizontalModifier = 3;
				}
				else if(puck.right == true)
				{
					puck.right = true;
					puck.left = true;
					puck.horizontalModifier = 3;
				}
				else{
					puck.right = true;
					puck.horizontalModifier = 2;
				}

				puck.down = false;
				puck.up = true;
				hit = true;
			}
			else if(puCenterLatitude.intersectsLine(p1RightSide))	//player 1 hits puck ->
			{
				puck.xCoord = p.xCoord + p.width+puck.velocity+p.velocity+2+puck.velocity;
				if(p.teamColor == player1.teamColor)
				{
					puck.left = false;
					puck.right = true;
				}
				else 
					puck.right = false;

				hit = true;
			}
			else if(puCenterLatitude.intersectsLine(p1LeftSide))	//player 1 hits puck <-
			{
				puck.xCoord = p.xCoord - puck.width - puck.velocity-puck.velocity-p.velocity-2-puck.velocity;
				if(p.teamColor == player1.teamColor)
				{
					puck.left = false;
				}
				else{
					puck.right = false;
					puck.left = true;
				}

				hit = true;
			}
			else if(puCenterLongitude.intersectsLine(p1TopSide))	//player 1 hits puck ->
			{
				puck.yCoord = p.yCoord - puck.height-puck.velocity-puck.velocity-p.velocity-2-puck.velocity;
				puck.down = false;
				puck.up = true;

				if(p.teamColor == player1.teamColor)
				{
					puck.left = false;	
				}
				else 
				{
					puck.right = false;
				}
				hit = true;
			}
			else if(puCenterLongitude.intersectsLine(p1BottomSide))	//player 1 hits puck ->
			{
				puck.yCoord = p.yCoord + p.height+puck.velocity+puck.velocity+p.velocity+2+puck.velocity;

				puck.down = true;
				puck.up = false;

				if(p.teamColor == player1.teamColor)
				{
					puck.left = false;
				}
				else 
				{
					puck.right = false;
				}
				hit = true;
			}
			else if(((puck.xCoord >= p.xCoord && puck.xCoord <= p.xCoord+p.width) && (puck.yCoord >= p.yCoord && puck.yCoord <= p.yCoord+p.height)) || ((puck.xCoord+puck.width >= p.xCoord && puck.xCoord+puck.width <= p.xCoord+p.width) && (puck.yCoord >= p.yCoord && puck.yCoord <= p.yCoord+p.height)) || ((puck.xCoord >= p.xCoord && puck.xCoord <= p.xCoord+p.width) && (puck.yCoord+puck.height >= p.yCoord && puck.yCoord+puck.height <= p.yCoord+p.height)) || ((puck.xCoord+puck.width >= p.xCoord && puck.xCoord+puck.width <= p.xCoord+p.width) && (puck.yCoord+puck.height >= p.yCoord && puck.yCoord+puck.height <= p.yCoord+p.height)))	//if the puck somehow gets inside a player's square: topLCorner ||topRCorner||bottomLCorner||bottomRCorner
			{
				hit = true;
				if(p.teamColor == player1.teamColor)
				{
					puck.xCoord = p.xCoord+p.width+puck.velocity+p.velocity+2+puck.velocity;
					puck.left = false;
					puck.right = true;
				}
				else if(p.teamColor == player2.teamColor)
				{
					puck.xCoord = p.xCoord-puck.width-puck.velocity-p.velocity-2-puck.velocity;
					puck.left = true;
					puck.right = false;
				}
			}
			
			if(hit == true)
			{
			//System.out.println("sdf");
				try{
					if(audioClip != null) audioClip.stop();			
					//start playing main screen sounds
					audioIn = AudioSystem.getAudioInputStream(getClass().getResource("sound/knock.wav"));
					audioClip = AudioSystem.getClip();
					audioClip.open(audioIn);
					audioClip.start();
				}
				catch(Exception e)
				{
					System.out.println("FFF");
					break;
				}
			}

			if(p.velocity >= 3 && puck.velocity <= puck.screenHeight/20 && hit == true){ 
				puck.velocity += (p.velocity-2);
				if(puck.firstHit == 0){ 
					puck.firstHit = 1;
				}
				//if(puck.horizontalModifier > 1) puck.horizontalModifier--;
				//if(puck.verticalModifier > 1) puck.verticalModifier--;
			}
		}
			
		
	}

	public void checkForGoal()
	{
		//line 1 is left, line 2 is right side of puck
  		/*Line2D puckLine1 = new Line2D.Float(puck.xCoord, puck.yCoord, puck.xCoord, puck.yCoord+puck.height);
  		Line2D puckLine2 = new Line2D.Float(puck.xCoord+puck.width, puck.yCoord, puck.xCoord+puck.width, puck.yCoord+puck.height);

		//the court-facing side of the goal rects
		Line2D blueGoalLine = new Line2D.Float(blueGoal.xCoord+35,blueGoal.yCoord,blueGoal.xCoord+35,blueGoal.yCoord+blueGoal.radius);
		Line2D greenGoalLine = new Line2D.Float(greenGoal.xCoord+54,greenGoal.yCoord,greenGoal.xCoord+54,greenGoal.yCoord+greenGoal.radius);*/

		//System.out.println(puck.xCoord);

		if((puck.xCoord <= 0) && (puck.yCoord <= greenGoal.yCoord+greenGoal.radius && puck.yCoord >= greenGoal.yCoord)){ 
			
				try{
					if(audioClip != null) audioClip.stop();			
					//start playing main screen sounds
					audioIn = AudioSystem.getAudioInputStream(getClass().getResource("sound/cheer.wav"));
					audioClip = AudioSystem.getClip();
					audioClip.open(audioIn);
					audioClip.start();
				}
				catch(Exception e)
				{
					System.out.println("FFF");
					//break;
				}
			
			player2.score++; 
			for(player p : players){
			p.up = false; p.down = false; p.left = false; p.right = false;}
			System.out.println("Score for the blue team!" + player2.score);
			puck.xCoord = puck.screenWidth/2-puck.width/2;
			puck.yCoord = puck.screenHeight/2-puck.height/2;
			puck.velocity = 0;
			player1.xCoord = player1.screenWidth/2-player1.screenHeight/4;
			player1.yCoord = player1.screenHeight/2-player1.height/2;
			player2.xCoord = player2.screenWidth/2+player2.screenHeight/4-player2.width;
			player2.yCoord = player2.screenHeight/2-player2.height/2;
			puck.firstHit = 0;
			updateScoreboard();
			puck.setPuck();		
		}
		else if((puck.xCoord+puck.width >= puck.screenWidth) && (puck.yCoord <= blueGoal.yCoord+blueGoal.radius && puck.yCoord >= blueGoal.yCoord)){
			
				try{
					if(audioClip != null) audioClip.stop();			
					//start playing main screen sounds
					audioIn = AudioSystem.getAudioInputStream(getClass().getResource("sound/cheer.wav"));
					audioClip = AudioSystem.getClip();
					audioClip.open(audioIn);
					audioClip.start();
				}
				catch(Exception e)
				{
					System.out.println("FFF");
					//break;
				}
			
			player1.score++;
			for(player p : players){
			p.up = false; p.down = false; p.left = false; p.right = false;}
			 System.out.println("Score for the green team!" + player1.score);
			puck.xCoord = puck.screenWidth/2-puck.width/2;
			puck.yCoord = puck.screenHeight/2-puck.height/2;
			puck.velocity = 0;
			player1.xCoord = player1.screenWidth/2-player1.screenHeight/4;
			player1.yCoord = player1.screenHeight/2-player1.height/2;
			player2.xCoord = player2.screenWidth/2+player2.screenHeight/4-player2.width;
			player2.yCoord = player2.screenHeight/2-player2.height/2;
			puck.firstHit = 0;
			updateScoreboard();
			puck.setPuck();
		}
		
	}

	class VGTimerTask extends TimerTask
	{
		int firstRun;
		public void run()
		{
			if(firstRun == 1)
			{	
				timer.start();
				firstRun = 0;
			}

			if(loss == 0)
			{
				puck.move(timeAccumulated);
				player2.move();
				player1.move();
				frame.repaint();
				checkForGoal();
				checkIntersection();
			}
		}
	}

	public gameScreen(int w, int h)
	{
		super();
		screen = new Rectangle(0, 0, w, h);
		bounds = new Rectangle(0, 0, w, h); // Give some starter values.
		screenWidth = w;
		screenHeight = h;
		frame = new JFrame("HOCKEY");
			frame.addKeyListener(new keyboardHandler());

		titleBar = new JMenuBar();
		
		blueTeam = new JMenu("Blue Team:");
		greenTeam = new JMenu("Green Team:");
		blueScore = new JMenu("[0]");
		greenScore = new JMenu("[0]");
		greenControls = new JMenu("W-up,S-down,A-left,D-right");
		blueControls = new JMenu(" (Move with the arrow keys) ");
		blueTeam.setForeground(Color.BLUE);
		greenTeam.setForeground(new Color( 34,139,34));
		blueScore.setForeground(Color.BLUE);
		greenScore.setForeground(new Color( 34,139,34));
		blueControls.setForeground(Color.BLUE);
		greenControls.setForeground(new Color( 34,139,34));
		//titleBar.add(new JMenu(" "));
		titleBar.add(greenTeam);
		titleBar.add(greenControls);
		//titleBar.add(new JMenu(" "));
		titleBar.add(blueTeam);
		titleBar.add(blueControls);
		titleBar.add(new JMenu(" SCORE: "));
		titleBar.add(greenScore);
		titleBar.add(blueScore);

		frame.setJMenuBar(titleBar);
		

		loss = 0;

		puck = new puckObject(w,h);
		blueGoal = new goalZone(w-h/4,h/2-h/4,h/2,0);
		greenGoal = new goalZone(0-h/4,h/2-h/4,h/2,1);
		
		player1 = new player(0,w,h);
		player2 = new player(1,w,h);
		players = new ArrayList<player>();
		players.add(player1);
		players.add(player2);

		timer = new javax.swing.Timer(1000, taskPerformer);

		vgTask = new VGTimerTask();
			vgTask.firstRun = 1;
	}

  	public gameScreen()
	{
		super();
		screenHeight = 400;
		screenWidth = 600;
		screen = new Rectangle(0, 0, 600, 400);
		bounds = new Rectangle(0, 0, 600, 400); // Give some starter values.

		frame = new JFrame("HOCKEY");
			frame.addKeyListener(new keyboardHandler());

		titleBar = new JMenuBar();
		
		blueTeam = new JMenu("Blue Team:");
		greenTeam = new JMenu("Green Team:");
		blueScore = new JMenu("[0]");
		greenScore = new JMenu("[0]");
		greenControls = new JMenu("W-up,S-down,A-left,D-right");
		blueControls = new JMenu(" (Move with the arrow keys) ");
		blueTeam.setForeground(Color.BLUE);
		greenTeam.setForeground(new Color( 34,139,34));
		blueScore.setForeground(Color.BLUE);
		greenScore.setForeground(new Color( 34,139,34));
		blueControls.setForeground(Color.BLUE);
		greenControls.setForeground(new Color( 34,139,34));
		//titleBar.add(new JMenu(" "));
		titleBar.add(greenTeam);
		titleBar.add(greenControls);
		//titleBar.add(new JMenu(" "));
		titleBar.add(blueTeam);
		titleBar.add(blueControls);
		titleBar.add(new JMenu(" SCORE: "));
		titleBar.add(greenScore);
		titleBar.add(blueScore);

		frame.setJMenuBar(titleBar);
		

		loss = 0;

		puck = new puckObject();
		blueGoal = new goalZone(500,100,200,0);
		greenGoal = new goalZone(-100,100,200,1);
		
		player1 = new player(0,600,400);
		player2 = new player(1,600,400);
		players = new ArrayList<player>();
		players.add(player1);
		players.add(player2);

		timer = new javax.swing.Timer(1000, taskPerformer);

		vgTask = new VGTimerTask();
			vgTask.firstRun = 1;
	}

	public int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

		int rand = 0;
	public void paintComponent(Graphics g)
	{
		int barWidth = 10;

		if(screenHeight == 500) barWidth = 20;
		else if(screenHeight == 600) barWidth = 30;
		else if(screenHeight == 700) barWidth = 40;

		//System.out.println(player1.velocity);
		//System.out.println(player2.velocity);

		Graphics2D g2d = (Graphics2D)g;
		g2d.setBackground(Color.WHITE);
	    // Get the drawing area bounds for game logic.
	    	bounds = g2d.getClipBounds();
	    // Clear the drawing area
	    	g2d.clearRect(screen.x, screen.y, screen.width, screen.height);

		//draw the halfway line
		g2d.setColor(Color.RED);
		g2d.fillRect(screenWidth/2-barWidth/2,0,barWidth,screenHeight);


		g2d.setColor(Color.YELLOW);
		//draw the offsides lines
		for(int i = 0; i < screenHeight/50;i++)
		{
			
			g2d.fillRect(screenWidth/3-barWidth/2,50*i+7,barWidth,35);
		}

		for(int i = 0; i < screenHeight/50;i++)
		{
			g2d.fillRect(2*screenWidth/3-barWidth/2,50*i+7,barWidth,35);
		}

		//drw the goals
		g2d.setColor(blueGoal.zoneColor);	//right
		g2d.fill(blueGoal.screenObject);
		g2d.setColor(blueGoal.teamColor);
		g2d.fill(blueGoal.goal);

		g2d.setColor(greenGoal.zoneColor);	//left
		g2d.fill(greenGoal.screenObject);
		g2d.setColor(greenGoal.teamColor);
		g2d.fill(greenGoal.goal);
		
		//draw the scores
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2));
		for(int i = 0; i < player1.score; i++)
		{
			g2d.setColor(player1.teamColor);
			g2d.fillRect(0+screenHeight/20+screenHeight/20*i+screenHeight/50,screenHeight-screenHeight/10,screenHeight/20,screenHeight/20);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(0+screenHeight/20+screenHeight/20*i+screenHeight/50,screenHeight-screenHeight/10,screenHeight/20,screenHeight/20);
		}
		for(int i = 0; i < player2.score;i++)
		{
			g2d.setColor(player2.teamColor);
			g2d.fillRect(screenWidth-screenHeight/20-screenHeight/20*i-screenHeight/50,screenHeight-screenHeight/8,screenHeight/20,screenHeight/20);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(screenWidth-screenHeight/20-screenHeight/20*i-screenHeight/50,screenHeight-screenHeight/8,screenHeight/20,screenHeight/20);
		}
		g2d.setStroke(oldStroke);
		
		g2d.setColor(Color.BLACK);
		for(int i = 0; i < screenWidth; i += 10)
		{
			for(int j = 0; j < screenHeight; j += 10)
			{
				g2d.fillRect(i,j,2,2);
			}
		}

		//draw the team's "hockey sticks"
		g2d.setColor(player1.teamColor);
		g2d.fillRect(player1.xCoord,player1.yCoord,player1.width+player1.velocity,player1.height+player1.velocity);
		g2d.setColor(player2.teamColor);
		g2d.fillRect(player2.xCoord,player2.yCoord,player2.width+player2.velocity,player2.height+player2.velocity);

		g2d.setColor(Color.BLACK);
		g2d.drawRect(player1.xCoord,player1.yCoord,player1.width+player1.velocity,player1.height+player1.velocity);
		g2d.drawRect(player2.xCoord,player2.yCoord,player2.width+player2.velocity,player2.height+player2.velocity);

		//draw the puck
		g2d.setColor(Color.BLACK);
		g2d.fillRect(puck.xCoord,puck.yCoord,puck.width+puck.velocity,puck.height+puck.velocity);
	}
}

public class hockeyGame
{
	int screenWidth, screenHeight;
	gameScreen screen;

	public hockeyGame(){
		screen = new gameScreen();
	}
	
	public hockeyGame(int h)
	{
		screenWidth = h + h/2;
		screenHeight = h;

		screen = new gameScreen(screenWidth,screenHeight);
	}
	
	public static void main(String[] args)
	{
		java.util.Timer vgTimer = new java.util.Timer();  // Create a Timer.
		hockeyGame game;
		game = null;
		if(args.length == 1)
		{
			int h = Integer.parseInt(args[0]);

			if(h != 300 && h != 400 && h != 500 && h != 600 && h != 700 && h != 800 && h != 900 && h != 1000)
			{
				System.out.println("UNSUPPORTED RESOLUTION ERROR: 1");
				System.exit(1);
			}
			else
			{
				game = new hockeyGame(h); 
			}
			
		}
		else
		{
	    		game = new hockeyGame(); // Create and instance of our kernel.
	    
		}

		if(game != null)
		{
	    // Set up our JFRame
	    game.screen.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    game.screen.frame.setSize(game.screen.screen.width+15, game.screen.screen.height+60);
	    game.screen.frame.setContentPane(game.screen); 
	    game.screen.frame.setLocationRelativeTo(null);
	//game.screen.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    game.screen.frame.setVisible(true);

	    // Set up a timer to do the vgTask regularly.
	    vgTimer.schedule(game.screen.vgTask, 0, 20);

		}//System.exit(0);
		
	}		
}
