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

public class mainMenuBackground extends JPanel
{
	
	public void decoratePanel()
	{
		try{
			BufferedImage myPicture = ImageIO.read(new File("Images/mainMenuBackground.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			
			add(picLabel);
		}
		catch(Exception e)
		{
			//return;
		}
	}
	
	public mainMenuBackground()
	{
		setPreferredSize(new Dimension(1024,768));
		setBounds(0, 0, 1024, 768);
		decoratePanel();
	}
}