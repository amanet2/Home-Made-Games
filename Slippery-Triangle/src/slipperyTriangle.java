// Import Timer and other useful stuff:
import java.util.*;
// Import the basic graphics classes.
import java.lang.Math;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

class obstacle
{
	public Rectangle topRectangle;
	public Rectangle bottomRectangle;
	public Polygon shit;

	public int theX;
	public int theY;

	Line2D obsLine1;
	Line2D obsLine2;
	Line2D obsLine3;
	Line2D obsLine4;
	Line2D obsLine5;
	Line2D obsLine6;
	Line2D obsLine7;
	Line2D obsLine8;

	public int[] xS;
	public int[] yS;

	public int rectWidth = 20;
	public int rectHeight = 400;
	public int gap = 80;

	public obstacle()
	{
		theX = 500;
		theY = -200;

		topRectangle = new Rectangle(theX,theY,rectWidth,rectHeight);
		bottomRectangle = new Rectangle(theX, theY+rectHeight+gap,rectWidth,rectHeight);
	}

	public obstacle(int xc, int yc)
	{
		theX = xc;
		theY = yc;

		topRectangle = new Rectangle(theX,theY,rectWidth,rectHeight);
		bottomRectangle = new Rectangle(theX, theY+rectHeight+gap,rectWidth,rectHeight);
	}
}

class player
{
	public Polygon aircraft;

	public int angle;

	public int forward;
	public int backward;
	public int up;
	public int down;

	public int lastKeyHeld;	//0 for forward, 1 for backward,2 for up, 3 for down

	public int velocity;

	Line2D plLine1;
	Line2D plLine2;
	Line2D plLine3;

	public int xCoord;
	public int yCoord;
	public int[] xs;
	public int[] ys;

	public void updateMove(int code)
	{	//0 for forward, 1 for back, 2 for L, 3 for R

		if(code == 0)
		{
			backward = 0;
			forward = 1;
		}
		else if(code == 1)
		{
			forward = 0;
			backward = 1;
		}
	}

	public void move()
	{
		if(xCoord <= 1)
		{
			xCoord = 0;
			backward = 0;
			forward = 1;
		}
		else if(yCoord <= 1)
		{
			yCoord = 0;
			up = 0;
			down = 1;
		}
		else if((xCoord+60) >= 599)
		{
			xCoord = 540;
			backward = 1;
			forward = 0;
		}
		else if((yCoord+40) >= 399)
		{
			yCoord = 360;
			up = 1;
			down = 0;
		}

		if(velocity > 0)
			velocity--;

		if(forward == 1)
			xCoord+=velocity;
		else if(backward == 1)
			xCoord -=velocity;
		else if(up == 1)
		{
			yCoord -= velocity;
		}
		else if(down == 1)
		{		
			yCoord += velocity;
		}
		else
		{
			if(lastKeyHeld == 0) xCoord += velocity;
			else if(lastKeyHeld == 1) xCoord -= velocity;
			else if(lastKeyHeld == 2)	//up
			{
				yCoord -= velocity;
			}
			else if(lastKeyHeld == 3)
			{
				yCoord += velocity;
			}
		}


		//System.out.println(velocity);
		
		
		xs[0] = xCoord;
		xs[1] = xCoord+ 60;
		xs[2] = xCoord;

		ys[0] = yCoord;
		ys[1] = yCoord+20;
		ys[2] = yCoord+40;
	}

	public player()
	{
		super();

		xs = new int[]{xCoord,xCoord+ 60,xCoord};
		ys = new int[]{yCoord,yCoord+20,yCoord+40};
		

		xCoord = 40;
		yCoord = 40;
		aircraft = new Polygon(xs,ys,3);
	}
}

class gameScreen extends JPanel
{
	public Rectangle screen; // The screen area
	public Rectangle bounds;	//boundaires of the drawing area
	public JFrame frame; // A JFrame to put the graphics into.
	public VGTimerTask vgTask; // The TimerTask that runs the game.
	public player playerOne;
	//public obstacle theObstacle;
	public ArrayList<obstacle> obstacles;
	public javax.swing.Timer timer;

	int timeAccumulated = 0;

	public int loss;
	
	int speed = 7;
	public void moveObstacles()
	{
		//System.out.println(timeAccumulated);
		if((timeAccumulated % 200) == 0)
		{
			speed += 1;	
		}

		for(obstacle theObstacle : obstacles)
		{
			theObstacle.theX -= speed;
		}
	}

	private class keyboardHandler implements KeyListener
	{	

	    public void keyTyped(KeyEvent e) {
		//System.out.println("KEY TYPED: ");
	    }

	    public void keyPressed(KeyEvent e) {
		//System.out.println("press");
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			playerOne.backward = 0;
			playerOne.forward = 1;
			playerOne.up = 0;
			playerOne.down = 0;
			playerOne.lastKeyHeld = 0;
			playerOne.velocity = 12;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			playerOne.backward = 1;
			playerOne.forward = 0;
			playerOne.up = 0;
			playerOne.down = 0;
			playerOne.lastKeyHeld = 1;
			playerOne.velocity = 10;
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			playerOne.backward = 0;
			playerOne.forward = 0;
			playerOne.up = 1;
			playerOne.down = 0;
			playerOne.lastKeyHeld = 2;
			playerOne.velocity = 12;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			playerOne.forward = 0;
			playerOne.backward = 0;
			playerOne.up = 0;
			playerOne.down = 1;
			playerOne.lastKeyHeld = 3;
			playerOne.velocity = 12;
		}
	    }

    		/** Handle the key-released event from the text field. */
	    public void keyReleased(KeyEvent e) {
		//System.out.println("KEY RELEASED: ");
		playerOne.forward = 0;
		playerOne.backward = 0;
	    }
	}


	public void checkIntersection()
	{
  		Line2D plLine1 = new Line2D.Float(playerOne.xCoord, playerOne.yCoord, playerOne.xCoord+60, playerOne.yCoord+20);
		Line2D plLine2 = new Line2D.Float(playerOne.xCoord, playerOne.yCoord+40, playerOne.xCoord+60,playerOne.yCoord+20);
		Line2D plLine3 = new Line2D.Float(playerOne.xCoord, playerOne.yCoord, playerOne.xCoord, playerOne.yCoord+40);


		for(obstacle theObstacle : obstacles)
		{
		//topobs
		//1=leftside,2=rightside,3==top,4=bottom
		Line2D obsLine1 = new Line2D.Float(theObstacle.theX,theObstacle.theY, theObstacle.theX,theObstacle.theY+theObstacle.rectHeight);
		Line2D obsLine2 = new Line2D.Float(theObstacle.theX+theObstacle.rectWidth,theObstacle.theY, theObstacle.theX+theObstacle.rectWidth,theObstacle.theY+theObstacle.rectHeight);
		Line2D obsLine3 = new Line2D.Float(theObstacle.theX,theObstacle.theY, theObstacle.theX+theObstacle.rectWidth,theObstacle.theY);
		Line2D obsLine4 = new Line2D.Float(theObstacle.theX,theObstacle.theY+theObstacle.rectHeight, theObstacle.theX+theObstacle.rectWidth,theObstacle.theY+theObstacle.rectHeight);

		//bottomobs
		//5=left,6=right,7=top,8=bottom
		Line2D obsLine5 = new Line2D.Float(theObstacle.theX,theObstacle.theY+theObstacle.rectHeight+theObstacle.gap, theObstacle.theX,theObstacle.theY+(2*theObstacle.rectHeight)+theObstacle.gap);
		Line2D obsLine6 = new Line2D.Float(theObstacle.theX+theObstacle.rectWidth,theObstacle.theY+theObstacle.rectHeight+theObstacle.gap, theObstacle.theX+theObstacle.rectWidth,theObstacle.theY+(2*theObstacle.rectHeight)+theObstacle.gap);
		Line2D obsLine7 = new Line2D.Float(theObstacle.theX,theObstacle.theY+theObstacle.rectHeight+theObstacle.gap, theObstacle.theX+theObstacle.rectWidth,theObstacle.theY+theObstacle.rectHeight+theObstacle.gap);
		Line2D obsLine8 = new Line2D.Float(theObstacle.theX,theObstacle.theY+(2*theObstacle.rectHeight)+theObstacle.gap, theObstacle.theX+theObstacle.rectWidth,theObstacle.theY+(2*theObstacle.rectHeight)+theObstacle.gap);

		if(plLine1.intersectsLine(obsLine1) == true || plLine1.intersectsLine(obsLine2) == true || plLine1.intersectsLine(obsLine3) == true || plLine1.intersectsLine(obsLine4) == true || plLine2.intersectsLine(obsLine1) == true || plLine2.intersectsLine(obsLine2) == true || plLine2.intersectsLine(obsLine3) == true || plLine2.intersectsLine(obsLine4) == true || plLine3.intersectsLine(obsLine1) == true || plLine3.intersectsLine(obsLine2) == true || plLine3.intersectsLine(obsLine3) == true || plLine3.intersectsLine(obsLine4) == true || plLine1.intersectsLine(obsLine5) == true || plLine2.intersectsLine(obsLine5) == true || plLine3.intersectsLine(obsLine5) == true || plLine1.intersectsLine(obsLine6) == true || plLine2.intersectsLine(obsLine6) == true || plLine3.intersectsLine(obsLine6) == true || plLine1.intersectsLine(obsLine7) == true || plLine2.intersectsLine(obsLine7) == true || plLine3.intersectsLine(obsLine7) == true || plLine1.intersectsLine(obsLine8) == true || plLine2.intersectsLine(obsLine8) == true || plLine3.intersectsLine(obsLine8) == true) 
		{
			if(loss == 0)
			{
				loss = 1;
				break;				
			}
		}
		}
	}

	  class VGTimerTask extends TimerTask{
		int firstRun;
	    public void run(){
		if(firstRun == 1){	firstRun = 0;
			timer.start();}

		if(loss == 0)
		{
			playerOne.move();
			moveObstacles();
			checkIntersection();

	      		frame.repaint();
		}
		else
		{
			timer.stop();
			JOptionPane d = new JOptionPane("You lost");
			d.showMessageDialog(frame, "You lost after " + timeAccumulated + " seconds.");
			frame.setVisible(false);
			frame.dispose();


	    java.util.Timer vgTimer = new java.util.Timer();  // Create a Timer.
	    slipperyTriangle game = new slipperyTriangle(); // Create and instance of our kernel.
	    

	    // Set up our JFRame
	    game.screen.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    game.screen.frame.setSize(game.screen.screen.width, game.screen.screen.height);
	    game.screen.frame.setContentPane(game.screen); 
	    game.screen.frame.setVisible(true);

	    // Set up a timer to do the vgTask regularly.
	    vgTimer.schedule(game.screen.vgTask, 0, 100);

/*
			try{
			java.lang.Runtime.getRuntime().exec(new String[] { "java", "slipperyTriangle"});}
			catch(Exception ee){}
*/
			Scanner keyboard = new Scanner(System.in);
			System.out.println("Game oveer, press any key to continue...");
			String input = keyboard.nextLine();
			System.exit(0);
		}
	    }
	  }

	public ActionListener taskPerformer = new ActionListener() { //handler for the cuckoo clock
		public void actionPerformed(ActionEvent e) {	//action performed
			timeAccumulated++;
		}
	};

	public int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

  	public gameScreen(){
	    super();
	    screen = new Rectangle(0, 0, 600, 400);
	    bounds = new Rectangle(0, 0, 600, 400); // Give some starter values.
	    frame = new JFrame("Slippery Triangle");
	frame.addKeyListener(new keyboardHandler());
	timer = new javax.swing.Timer(1000, taskPerformer);

		playerOne = new player();
		obstacles = new ArrayList<obstacle>();
		
		for(int i = 0; i < 50; i++)
		{
			obstacles.add(new obstacle(650+600*i,randInt(-400,-80)));
		}

		loss = 0;

	    vgTask = new VGTimerTask();
		vgTask.firstRun = 1;
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setBackground(Color.BLACK);
	    // Get the drawing area bounds for game logic.
	    	bounds = g2d.getClipBounds();
	    // Clear the drawing area, then draw the ball.
	    	g2d.clearRect(screen.x, screen.y, screen.width, screen.height);

		int rand = randInt(1, 4);
		if(rand == 1)
			g2d.setColor(Color.RED);
		else if(rand == 2)
			g2d.setColor(Color.MAGENTA);
		else if(rand == 3)
			g2d.setColor(Color.ORANGE);
		else
			g2d.setColor(Color.BLUE);

		int inLoop = 1;
		while(inLoop == 1)
		{
			for(obstacle o : obstacles)
			{
				if(o.theX < -30)
				{
					obstacles.remove(o);
					break;
				}
			}
			inLoop = 0;
		}

		for(obstacle theObstacle : obstacles)
		{
		g2d.fillRect(theObstacle.theX,theObstacle.theY,theObstacle.rectWidth,theObstacle.rectHeight);
		g2d.fillRect(theObstacle.theX,theObstacle.theY+theObstacle.rectHeight+theObstacle.gap,theObstacle.rectWidth,theObstacle.rectHeight);
		}

		g2d.setColor(Color.CYAN);
		g2d.fillPolygon(playerOne.xs,playerOne.ys,3);
	}

}

public class slipperyTriangle
{
	gameScreen screen;

	public slipperyTriangle(){
		screen = new gameScreen();
	}
	
	public static void main(String[] args)
	{
	    java.util.Timer vgTimer = new java.util.Timer();  // Create a Timer.
	    slipperyTriangle game = new slipperyTriangle(); // Create and instance of our kernel.
	    

	    // Set up our JFRame
	    game.screen.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    game.screen.frame.setSize(game.screen.screen.width, game.screen.screen.height+30);
	    game.screen.frame.setContentPane(game.screen); 
	    game.screen.frame.setVisible(true);

	    // Set up a timer to do the vgTask regularly.
	    vgTimer.schedule(game.screen.vgTask, 0, 100);

		//System.exit(0);
	}		
}
