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

public class mapController
{
	imagePanel backgroundPanel;
        ArrayList<object> enemies;
	
	public void moveMapX(int offset)
	{
		backgroundPanel.x = backgroundPanel.x + offset;
		backgroundPanel.setBounds(backgroundPanel.x,backgroundPanel.y,backgroundPanel.width,backgroundPanel.height);
	}
	
	public void moveMapY(int offset)
	{
		backgroundPanel.y = backgroundPanel.y + offset;
		backgroundPanel.setBounds(backgroundPanel.x,backgroundPanel.y,backgroundPanel.width,backgroundPanel.height);
	}
	
	public mapController(int w, int h)
	{
		backgroundPanel = new imagePanel(w,h,"background.jpg",true,0);
		enemies = new ArrayList<object>();
	}
}
