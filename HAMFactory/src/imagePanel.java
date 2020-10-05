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

public class imagePanel extends JPanel
{
	int x, y;
	int width, height;
	ImageIcon img;
	JLabel texture;
	int ind;
	
	public ArrayList<ImageIcon> rotations;
	public ArrayList<ImageIcon> deathAnimationFrames;
	
	public void decoratePanel(String s)
	{
		try{
			img = new ImageIcon(getClass().getResource("Images/" + s));
			
			decoratePanel(img);
		}
		catch(Exception e)
		{
			setBackground(Color.RED);
			System.out.println("Exception occured, file is prob not there");
		}
	}
	
	public void decorateText(String s)
	{
		texture = new JLabel(s);
		add(texture);
	}
	
	public void decoratePanel(Icon i)
	{
		texture = new JLabel(i);

		add(texture);
	}
	
	public void setIcon(ImageIcon icon)
	{
		texture.setIcon(icon);
	}
	
	public imagePanel(String name, int xx, int yy, int w, int h, String text, boolean opac,int i)
	{
		//hudpanel, put in 700s
		super();
		ind = i;
		x=xx;
		y=yy;
		width=w;
		height=h;
		setOpaque(opac);
		setPreferredSize(new Dimension(w,h));
		setBounds(x,y,w,h);
		decorateText(text);
		setDoubleBuffered(true);
	}
	
	public imagePanel(int w, int h, int i)
	{
		super();
		rotations = new ArrayList<ImageIcon>();
		deathAnimationFrames = new ArrayList<ImageIcon>();
		ind = i;
		x = 0; 
		y = 0;
		width = w; 
		height = h;
		setPreferredSize(new Dimension(w,h));
		setBounds(0, 0,w,h);
		decoratePanel("");
		setDoubleBuffered(true);
	}
	
	public imagePanel(int w, int h, String s,boolean opacity, int i)
	{
		super();
		rotations = new ArrayList<ImageIcon>();
		deathAnimationFrames = new ArrayList<ImageIcon>();
		x = 0;
		y = 0;
		ind = i;
		width = w; 
		height = h;
		setPreferredSize(new Dimension(w,h));
		setBounds(0, 0,w,h);
		decoratePanel(s);
		setOpaque(opacity);
		setDoubleBuffered(true);
	}
	
	public imagePanel(int xx, int yy, int w, int h, String s,boolean opacity, int i)
	{
		super();
		rotations = new ArrayList<ImageIcon>();
		deathAnimationFrames = new ArrayList<ImageIcon>();
		x = xx;
		y = yy;
		ind = i;
		width = w; 
		height = h;
		setPreferredSize(new Dimension(w,h));
		setBounds(x,y,w,h);
		decoratePanel(s);
		setOpaque(opacity);
		setDoubleBuffered(true);
	}
	
	public imagePanel(int xx, int yy, int w, int h, ImageIcon ii,boolean opacity, int i)
	{
		super();
		rotations = new ArrayList<ImageIcon>();
		deathAnimationFrames = new ArrayList<ImageIcon>();
		x = xx;
		y = yy;
		ind = i;
		width = w; 
		height = h;
		setPreferredSize(new Dimension(w,h));
		setBounds(x,y,w,h);
		decoratePanel(ii);
		setOpaque(opacity);
		setDoubleBuffered(true);
	}
}
