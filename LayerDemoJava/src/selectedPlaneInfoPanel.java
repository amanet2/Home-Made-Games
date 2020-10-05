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

public class selectedPlaneInfoPanel extends JPanel
{
	public JLabel textLabel;
	
	public JLabel getLabel()
	{
		if(textLabel == null)
			textLabel = new JLabel();
			
		return textLabel;
	}
	
	public void decoratePanel()
	{
			String tmp = "foo";
			textLabel = new JLabel(tmp);
			textLabel.setForeground(Color.RED);
			add(textLabel);
			
	}
	
	
	public selectedPlaneInfoPanel()
	{
		//setOpaque(false);
		setPreferredSize(new Dimension(1024,768));
		setBounds(500, 100, 400, 50);
		decoratePanel();
	}
}
