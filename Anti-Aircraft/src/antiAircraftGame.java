// Import Timer and other useful stuff:
import java.util.*;
// Import the basic graphics classes.
import java.lang.Math;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

class aircraft
{
	public Rectangle body;
	public int alive;

	public int velocity;
	public int xCoord;
	public int yCoord;

	public void move()
	{
		xCoord -= velocity;
	}

	public aircraft()
	{
		body = new Rectangle(0,0,30,10);
		alive = 1;
		velocity = 0;
		xCoord = 0;
		yCoord = 0;
	}

	public aircraft(int x, int y, int v)
	{
		body = new Rectangle(x,y,30,10);
		alive = 1;
		velocity = v;
		xCoord = x;
		yCoord = y;
	}
}

class projectile
{
	public Rectangle bullet;
	public int velocity;
	public int xCoord;
	public int yCoord;

	public int prevX;
	public int prevY;

	public void move()
	{
		prevY = yCoord;
		yCoord -= velocity;
	}

	public projectile()
	{
		bullet = new Rectangle(0,0,5,5);
		xCoord = 0;
		yCoord = 0;
		prevX = 0;
		prevY = 0;
		velocity = 0;
	}

	public projectile(int x, int y)
	{
		bullet = new Rectangle(x,y,5,5);
		xCoord = x;
		yCoord = y;
		prevX = 0;
		prevY = 0;
		velocity = 50;
	}
}

class player
{
	public Polygon gun;

	public int score;

	public int left;
	public int right;

	public int velocity;
	public int lastKeyHeld;

	public int xCoord;
	public int yCoord;
	public int[] xs;
	public int[] ys;

	public void move()
	{
		if(xCoord <= 1)
		{
			left = 0;
			right = 1;
		}
		else if((xCoord+40) >= 599)
		{
			left = 1;
			right = 0;
		}

		if(velocity > 0)
			velocity--;

		if(right == 1)
			xCoord+=velocity;
		else if(left == 1)
			xCoord -=velocity;
		else
		{
			if(lastKeyHeld == 0) xCoord += velocity;
			else if(lastKeyHeld == 1) xCoord -= velocity;
		}


		//System.out.println(velocity);
		
		
		xs[0] = xCoord;
		xs[1] = xCoord+20;
		xs[2] = xCoord+40;

		ys[0] = yCoord;
		ys[1] = yCoord-20;
		ys[2] = yCoord;
	}

	public player()
	{
		super();

		xCoord = 100;
		yCoord = 380;

		xs = new int[]{xCoord,xCoord+20,xCoord+40};
		ys = new int[]{yCoord,yCoord-20,yCoord};
		
		gun = new Polygon(xs,ys,3);
		score = 0;
	}
}

class gameScreen extends JPanel
{
	public Rectangle screen; // The screen area
	public Rectangle bounds;	//boundaires of the drawing area
	public Rectangle ground;
	public JFrame frame; // A JFrame to put the graphics into.
	public VGTimerTask vgTask; // The TimerTask that runs the game.
	public player playerOne;
	public ArrayList<aircraft> enemies;
	public ArrayList<projectile> projectiles;

	public javax.swing.Timer timer;

	int timeAccumulated = 0;
	int adjT1 = 200;
	int adjT2 = 200;	
	int adjT3 = 200;
	public int loss;
	int speed = 8;

	public void fire()
	{
		if(adjT1 != timeAccumulated || adjT2 != timeAccumulated || adjT3 != timeAccumulated){

			projectiles.add(new projectile(playerOne.xCoord+20,playerOne.yCoord-21));
			
			if(adjT1 != timeAccumulated)
				adjT1 = timeAccumulated;
			else if(adjT2 != timeAccumulated)
				adjT2 = timeAccumulated;
			else if(adjT3 != timeAccumulated)
				adjT3 = timeAccumulated;
		}

		
	}

	private class keyboardHandler implements KeyListener
	{	

	    public synchronized void keyTyped(KeyEvent e) {
		//System.out.println("KEY TYPED: ");
	    }

	    public synchronized void keyPressed(KeyEvent e) {
		//System.out.println("press");
		int l = 0;
		int r = 0;
		int lkh = 0;
		int v = 0;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			l = 0;
			r = 1;
			lkh = 0;
			v = 10;
			playerOne.left = 0;
			playerOne.right = 1;
			playerOne.lastKeyHeld = 0;
			playerOne.velocity = 15;
		}

		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			l = 1;
			r = 0;
			lkh = 1;
			v = 10;
			playerOne.left = 1;
			playerOne.right = 0;
			playerOne.lastKeyHeld = 1;
			playerOne.velocity = 15;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			fire();

		
	    }

	        public synchronized void keyReleased(KeyEvent e)
		{
			playerOne.left = 0;
			playerOne.right = 0;
		}
	}

	  class VGTimerTask extends TimerTask{
		int firstRun;
		int[] indexes;
	    public void run(){
		if(firstRun == 1){	firstRun = 0;
			timer.start();
			 indexes = new int[enemies.size()];}

		if(loss == 0)
		{
			
			for(projectile p : projectiles)
			{
				p.move();
			}
			for(aircraft a : enemies)
			{
				a.move();
			}
			playerOne.move();
			playerOne.score += checkIntersection(indexes);
		
			for(int i = 0; i < indexes.length; i++)
			{
				if(indexes[i] == 1) enemies.get(i).alive = 0;
			}
	      		frame.repaint();

			if(enemies.size() == 0)
			{
				loss = 1;
			}
		}
		else
		{
			timer.stop();
			JOptionPane d = new JOptionPane("Game Over");
			d.showMessageDialog(frame, "You shot down " + playerOne.score + "/50 aircraft");
			frame.setVisible(false);
			frame.dispose();


	    		java.util.Timer vgTimer = new java.util.Timer();  // Create a Timer.
	    		antiAircraftGame game = new antiAircraftGame(); // Create and instance of our kernel.
	    

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
	    ground = new Rectangle(0,380,600,20);
	    frame = new JFrame("Anti-Aircraft");
		frame.addKeyListener(new keyboardHandler());
		timer = new javax.swing.Timer(1000, taskPerformer);

		playerOne = new player();
		enemies = new ArrayList<aircraft>();
		projectiles = new ArrayList<projectile>();

		for(int i = 0; i < 50; i++)
		{
			enemies.add(new aircraft(randInt(400,5000),randInt(20,300),randInt(9,15)));
		}

		loss = 0;

	    vgTask = new VGTimerTask();
		vgTask.firstRun = 1;
	}
	public int checkIntersection(int[] arr)
	{
		int s = 0;

  		Line2D plLine1 = new Line2D.Float(playerOne.xCoord, playerOne.yCoord, playerOne.xCoord+20, playerOne.yCoord-20);
		Line2D plLine2 = new Line2D.Float(playerOne.xCoord, playerOne.yCoord, playerOne.xCoord+40,playerOne.yCoord);
		Line2D plLine3 = new Line2D.Float(playerOne.xCoord+20, playerOne.yCoord-20, playerOne.xCoord+40, playerOne.yCoord);

			for(int i = 0; i < arr.length; i++)
			{
				arr[i] = 0;
			}

		for(projectile p : projectiles)
		{
			//1=bottom,2=top,3=left,4=right
			Line2D pLine1 = new Line2D.Float(p.xCoord,p.yCoord+5,p.xCoord+5,p.yCoord+5);
			Line2D pLine2 = new Line2D.Float(p.xCoord,p.yCoord,p.xCoord+5,p.yCoord);
			//Line2D pLine3 = new Line2D.Float(p.xCoord,p.yCoord,p.xCoord,p.yCoord+5);
			//Line2D pLine4 = new Line2D.Float(p.xCoord+5,p.yCoord,p.xCoord+5,p.yCoord+5);

			Line2D leadLine = new Line2D.Float(p.xCoord,p.yCoord,p.xCoord,p.yCoord-50);

			int ind = 0;
			for(aircraft a : enemies)
			{
				//1=bottom,2=top,3=left,4=right
				Line2D aLine1 = new Line2D.Float(a.xCoord,a.yCoord+10,a.xCoord+30,a.yCoord+10);
				Line2D aLeadLine = new Line2D.Float(a.xCoord-a.velocity,a.yCoord+10,a.xCoord,a.yCoord+10);
				
				Line2D aLine2 = new Line2D.Float(a.xCoord,a.yCoord,a.xCoord+30,a.yCoord);
				//Line2D aLine3 = new Line2D.Float(a.xCoord,a.yCoord,a.xCoord,a.yCoord+10);
				//Line2D aLine4 = new Line2D.Float(a.xCoord+30,a.yCoord,a.xCoord+30,a.yCoord+10);
/*
if(pLine1.intersectsLine(aLine1) == true || pLine1.intersectsLine(aLine2) == true || pLine1.intersectsLine(aLine3) == true || pLine1.intersectsLine(aLine4) == true || pLine2.intersectsLine(aLine1) == true || pLine2.intersectsLine(aLine2) == true || pLine2.intersectsLine(aLine3) == true || pLine2.intersectsLine(aLine4) == true || pLine3.intersectsLine(aLine1) == true || pLine3.intersectsLine(aLine2) == true || pLine3.intersectsLine(aLine3) == true || pLine3.intersectsLine(aLine4) == true || pLine4.intersectsLine(aLine1) == true || pLine4.intersectsLine(aLine2) == true || pLine4.intersectsLine(aLine3) == true || pLine4.intersectsLine(aLine4) == true)
*/				
				/*System.out.println("projectileLine: (" + p.xCoord + "," + Integer.toString(p.yCoord+5) + ")("+Integer.toString(p.xCoord+5)+","+Integer.toString(p.yCoord+5)+")");

				System.out.println("projectileleadLine: (" + p.xCoord + "," + Integer.toString(p.prevY) + ")("+Integer.toString(p.xCoord)+","+Integer.toString(p.yCoord)+")");

				System.out.println("enemyLine: (" + a.xCoord + "," + Integer.toString(a.yCoord+10) + ")("+Integer.toString(a.xCoord+30)+","+Integer.toString(a.yCoord+10)+")");*/

				if(pLine2.intersectsLine(aLine1) == true  || leadLine.intersectsLine(aLine1) == true || pLine2.intersectsLine(aLine2) == true  || leadLine.intersectsLine(aLine2) == true || pLine2.intersectsLine(aLeadLine) == true || leadLine.intersectsLine(aLeadLine) == true)
				{
					s++;
					arr[ind] = 1;
					//System.out.println("playeronescore: "+s);
				}
				ind++;
			}
		}

		return s;
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setBackground(Color.CYAN);
	    // Get the drawing area bounds for game logic.
	    	bounds = g2d.getClipBounds();
	    // Clear the drawing area, then draw the ball.
	    	g2d.clearRect(screen.x, screen.y, screen.width, screen.height);

		g2d.setColor(Color.GREEN);
		g2d.fillRect(ground.x,ground.y,ground.width,ground.height);

		g2d.setColor(Color.BLACK);
		g2d.fillPolygon(playerOne.xs,playerOne.ys,3);

		for(aircraft a : enemies)
		{
			g2d.setColor(Color.BLACK);
			if(a.alive == 1)
			{
				g2d.fillRect(a.xCoord,a.yCoord,30,10);
				g2d.setColor(Color.RED);
				//g2d.drawLine(a.xCoord-a.velocity,a.yCoord+10,a.xCoord,a.yCoord+10);
			}
		}

		int inLoop = 1;
		while(inLoop == 1)
		{
			for(aircraft a : enemies)
			{
				if(a.alive == 0 || a.xCoord < -30)
				{
					enemies.remove(a);
					break;
				}
			}
			inLoop = 0;
		}

		g2d.setColor(Color.ORANGE);
		for(projectile p : projectiles)
		{
			g2d.fillRect(p.xCoord,p.yCoord,5,5);
			g2d.fillRect(p.xCoord,p.yCoord-50,5,50);
		}
	}

}

public class antiAircraftGame
{
	gameScreen screen;

	public antiAircraftGame()
	{
		screen = new gameScreen();
	}
	
	public static void main(String[] args)
	{
		java.util.Timer vgTimer = new java.util.Timer();  // Create a Timer.
	    antiAircraftGame game = new antiAircraftGame(); // Create and instance of our kernel.
	    

	    // Set up our JFRame
	    game.screen.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    game.screen.frame.setSize(game.screen.screen.width, game.screen.screen.height+20);
	    game.screen.frame.setContentPane(game.screen);
	    game.screen.frame.setVisible(true);

	    // Set up a timer to do the vgTask regularly.
	    vgTimer.schedule(game.screen.vgTask, 0, 100);
	}
}
