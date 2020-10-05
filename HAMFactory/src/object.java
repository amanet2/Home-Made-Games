import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.Math.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class object
{
	public Rectangle hitBox;	//rectangle is drawn to determine intersections
	public int xCoord, yCoord;	//xCoord and yCoord of object
	public int miniX,miniY;		//for minimap
	public int width, height;	//the dimensions of the object
	public imagePanel model;	//the visual representation of the object
	
	public int health;			//health of object
	public boolean drawYes;		//whether or not to draw the object
	
	public movementController movementManager;	// controller for movement
	
	public boolean rotatable;
	public boolean moveable;
	
	public long timeToLive;//limit
	public long timeCreated;//timestamp
	
	public void moveX(int offset)
	{
		xCoord = xCoord + offset; 
		model.setBounds(xCoord,yCoord,width,height);
	}
	
	public void moveY(int offset)
	{
		yCoord = yCoord + offset;
		model.setBounds(xCoord,yCoord,width,height);
	}
	
	public void rotate()
	{
		if(rotatable == true)
		{
			rotate(movementManager.up,movementManager.down,movementManager.left,movementManager.right);
		}
	}
	
	public void rotate(boolean up, boolean down, boolean left, boolean right)
	{
		if(rotatable == true)
		{
			if(up == true && down == false && left == false && right == false)
			{
				model.setIcon(model.rotations.get(0));
				movementManager.forwardV = 0;
			}
			else if(up == true && down == false && left == false && right == true)
			{
				model.setIcon(model.rotations.get(1));
				movementManager.forwardV = 1;
			}
			else if(up == false && down == false && left == false && right == true)
			{
				model.setIcon(model.rotations.get(2));
				movementManager.forwardV = 2;
			}
			else if(up == false && down == true && left == false && right == true)
			{
				model.setIcon(model.rotations.get(3));
				movementManager.forwardV = 3;
			}
			else if(up == false && down == true && left == false && right == false)
			{
				model.setIcon(model.rotations.get(4));
				movementManager.forwardV = 4;
			}
			else if(up == false && down == true && left == true && right == false)
			{
				model.setIcon(model.rotations.get(5));
				movementManager.forwardV = 5;
			}
			else if(up == false && down == false && left == true && right == false)
			{
				model.setIcon(model.rotations.get(6));
				movementManager.forwardV = 6;
			}
			else if(up == true && down == false && left == true && right == false)
			{
				model.setIcon(model.rotations.get(7));
				movementManager.forwardV = 7;
			}
		}
	}
	
	public void advance()
	{
		if(moveable == true)
		{
			if(movementManager.forward == true && movementManager.backward == false && movementManager.strafeL == false && movementManager.strafeR == false)
			{
				yCoord = yCoord-movementManager.velocity;
				rotate(true,false,false,false);
			}
			else if(movementManager.forward == true && movementManager.backward == false && movementManager.strafeL == false && movementManager.strafeR == true)
			{
				yCoord = yCoord-movementManager.velocity;
				xCoord = xCoord+movementManager.velocity;
				rotate(true,false,false,true);
			}
			else if(movementManager.forward == false && movementManager.backward == false && movementManager.strafeL == false && movementManager.strafeR == true)
			{
				xCoord = xCoord+movementManager.velocity;
				rotate(false,false,false,true);
			}
			else if(movementManager.forward == false && movementManager.backward == true && movementManager.strafeL == false && movementManager.strafeR == true)
			{
				xCoord = xCoord+movementManager.velocity;
				yCoord = yCoord+movementManager.velocity;
				rotate(false,true,false,true);
			}
			else if(movementManager.forward == false && movementManager.backward == true && movementManager.strafeL == false && movementManager.strafeR == false)
			{
				yCoord = yCoord+movementManager.velocity;
				rotate(false,true,false,false);
			}
			else if(movementManager.forward == false && movementManager.backward == true && movementManager.strafeL == true && movementManager.strafeR == false)
			{
				xCoord = xCoord-movementManager.velocity;
				yCoord = yCoord+movementManager.velocity;
				rotate(false,true,true,false);
			}
			else if(movementManager.forward == false && movementManager.backward == false && movementManager.strafeL == true && movementManager.strafeR == false)
			{
				xCoord = xCoord-movementManager.velocity;
				rotate(false,false,true,false);
			}
			else if(movementManager.forward == true && movementManager.backward == false && movementManager.strafeL == true && movementManager.strafeR == false)
			{
				yCoord = yCoord-movementManager.velocity;
				xCoord = xCoord-movementManager.velocity;
				rotate(true,false,true,false);
			}
			model.x = xCoord;
			model.y = yCoord;
			
			hitBox.setBounds(xCoord,yCoord,width,height);
		}
	}
	
	public object(imagePanel iP, boolean mv, boolean r)
	{
		health=100;
		model = iP;
		xCoord = 0;
		yCoord = 0;
		width = model.width;
		height = model.height;
		rotatable = r;
		moveable = mv;
		hitBox = new Rectangle(xCoord,yCoord,width,height);
		drawYes = true;
		
		movementManager = new movementController();
		movementManager.velocity = 0;
		
		timeToLive = 0;
		timeCreated = 0;
		
		miniX = 0;
		miniY=0;
	}
	
	public object(imagePanel iP, boolean mv, boolean r, int x, int y, int v)
	{
		health = 100;
		model = iP;
		xCoord = x;
		yCoord = y;
		width = model.width;
		height = model.height;
		rotatable = r;
		moveable = mv;
		hitBox = new Rectangle(xCoord,yCoord,width,height);
		drawYes = true;
		
		movementManager = new movementController();
		movementManager.velocity=v;
		
		timeToLive = 0;
		timeCreated = 0;

		miniX = 0;
		miniY=0;
	}
	
	public object(long ttl, long ts,imagePanel iP, boolean mv, boolean r, int x, int y, int v)
	{
		health = 100;
		model = iP;
		xCoord = x;
		yCoord = y;
		width = model.width;
		height = model.height;
		rotatable = r;
		moveable = mv;
		hitBox = new Rectangle(xCoord,yCoord,width,height);
		drawYes = true;
		
		movementManager = new movementController();
		movementManager.velocity=v;
		
		timeToLive = ttl;
		timeCreated = ts;
	}
}
