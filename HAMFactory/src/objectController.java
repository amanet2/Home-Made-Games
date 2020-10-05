import java.util.*;
// Import the basic graphics classes.
import java.lang.Math;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class objectController	//control objects
{
	ArrayList<object> objectsMovableRotatable;	//objects that move and rotate
	ArrayList<object> objectsMovable;			//objects that move
	ArrayList<object> objectsStatic;			//objects that stay still
	
	public void transformObjectsX(int offset)
	{
		object[] array = new object[objectsStatic.size()];
		array = objectsStatic.toArray(array);
		for(int i=0;i<array.length;i++)
		{
			array[i].xCoord = array[i].xCoord + offset;
		}
		object[] array2 = new object[objectsMovable.size()];
		array2 = objectsMovable.toArray(array2);
		for(int i=0;i<array2.length;i++)
		{
			array2[i].xCoord = array2[i].xCoord + offset;
			array2[i].miniX = array2[i].miniX+offset;
		}
		object[] array3 = new object[objectsMovableRotatable.size()];
		array3 = objectsMovableRotatable.toArray(array3);
		for(int i=0;i<array3.length;i++)
		{
			array3[i].xCoord = array3[i].xCoord + offset;
			array3[i].miniX = array3[i].miniX+offset;
		}
	}
	
	public void transformObjectsY(int offset)
	{
		object[] array = new object[objectsStatic.size()];
		array = objectsStatic.toArray(array);
		for(int i=0;i<array.length;i++)
		{
			array[i].yCoord = array[i].yCoord + offset;
			
		}
		object[] array2 = new object[objectsMovable.size()];
		array2 = objectsMovable.toArray(array2);
		for(int i=0;i<array2.length;i++)
		{
			array2[i].yCoord = array2[i].yCoord + offset;
			array2[i].miniY = array2[i].miniY+offset;
		}
		object[] array3 = new object[objectsMovableRotatable.size()];
		array3 = objectsMovableRotatable.toArray(array3);
		for(int i=0;i<array3.length;i++)
		{
			array3[i].yCoord = array3[i].yCoord + offset;
			array3[i].miniY = array3[i].miniY+offset;
		}
	}
	
	public void destroyPastDueMovableObjects()
	{
		object[] array2 = new object[objectsMovable.size()];
		array2 = objectsMovable.toArray(array2);
		for(int i=0;i<array2.length;i++)
		{
			if(array2[i].timeToLive != 0)
			{
				if(System.currentTimeMillis() > (array2[i].timeToLive+array2[i].timeCreated))
				{
					destroyMoveable(array2[i]);
					//System.out.println("asdf");
				}
			}
		}
	}
	
	public void destroyMoveable(object o)
	{
				o.drawYes = false;
				o.model.setVisible(false);
				objectsMovable.remove(o);
	}
	
	public void moveRotateObjects()	//advance objects according to their velocity and directions, then rotate objects to reflect any forward vector changes
	{
		object[] array = new object[objectsMovableRotatable.size()];
		array = objectsMovableRotatable.toArray(array);
		for(int i=0;i<array.length;i++)
		{
			array[i].advance();	//advance changes x,y coords according to movement manager, then sets the hitbox to reflect new coords
			array[i].rotate();	//rotate swaps out the model's texture for the appropriate picture
			array[i].model.setBounds(array[i].model.x,array[i].model.y,array[i].model.width,array[i].model.height);	//update the model's appearance on screen
		}
	}
	
	public void moveObjects() //advance objects according to their velocity and directions
	{
		object[] array = new object[objectsMovable.size()];
		array = objectsMovable.toArray(array);
		for(int i=0;i<array.length;i++)
		{
			array[i].advance();	//advance changes x,y coords according to movement manager, then sets the hitbox to reflect new coords
			array[i].model.setBounds(array[i].model.x,array[i].model.y,array[i].model.width,array[i].model.height);	//update the model's appearance on screen
		}
	}
	
	public objectController()	//initialize all our lists of objects
	{
		objectsMovableRotatable = new ArrayList<object>();
		objectsMovable = new ArrayList<object>();
		objectsStatic = new ArrayList<object>();
	}
}