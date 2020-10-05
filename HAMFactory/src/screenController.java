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

public class screenController extends JLayeredPane
{
	int screenWidth, screenHeight;	//height of the screen
	int x,y;	//adjust these for moving the "viewbox" around the map
	
	public synchronized void setObjects(ArrayList<object> ar)	//will draw all objects in the arraylist
	{
		object[] array = new object[ar.size()];
		array = ar.toArray(array);
		for(int i=0;i<array.length;i++)
		{
			if(array[i].drawYes == true) add(array[i].model,new Integer(array[i].model.ind),0);
		}
	}
	
	public void setObject(object o)	//will draw one object
	{
		add(o.model,new Integer(o.model.ind),0);
	}

	public void setMap(mapController m)	//will draw the mapController's bg as the bottom layer
	{
		add(m.backgroundPanel, new Integer(1),0);
	}
	
	public screenController(int w, int h)	//construct the screen controller
	{
		super();
		
		screenWidth = w;
		screenHeight = h;
		x=0;	//start at the top left corner for now
		y=0;
	}
}