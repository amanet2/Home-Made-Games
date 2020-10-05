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

public class exitButtonPanel extends JPanel
{
	public void decoratePanel()
	{
		JButton button = new JButton("EXIT");
		
		button.addActionListener(new ActionListener(){	//action listener for enter key pressing
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}});
		
		add(button);
	}

	public exitButtonPanel()
	{
	setOpaque(false);
		setPreferredSize(new Dimension(100,50));
		setBounds(930, 600, 80, 40);
		decoratePanel();
	}
}
