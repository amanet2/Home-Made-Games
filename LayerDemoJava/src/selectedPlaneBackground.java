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

public class selectedPlaneBackground extends JPanel
{
	public JLabel picLabel;
	
	public JLabel getLabel()
	{
		if(picLabel == null)
			picLabel = new JLabel();
			
		return picLabel;
	}
	
	public void decoratePanel()
	{
		try{
			BufferedImage myPicture = ImageIO.read(new File("Images/blank.jpg"));
			picLabel = new JLabel(new ImageIcon(myPicture));
			
			add(picLabel);
		}
		catch(Exception e)
		{
			//return;
		}
	}
	
	
	
	public selectedPlaneBackground()
	{
		setPreferredSize(new Dimension(1024,768));
		setBounds(50, 100, 300, 300);
		decoratePanel();
	}
}
