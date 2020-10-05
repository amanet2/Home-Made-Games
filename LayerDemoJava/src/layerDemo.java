
import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.Math.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class layerDemo extends Thread 
{
	JFrame theFrame;	//the big frame for everything
	

	
	JLayeredPane mainPane;	//main menu
	JLayeredPane planesPane;	//select a plane menu
	JLayeredPane gunsPane;
	
	JButton planesButton;	//button to take you to plane menu
	JButton backToMainMenuButton;	//button to take you to main menu
	JButton backToMainMenuButton2;
	JButton gunsButton;
	
	public layerDemo()
	{
		theFrame = new JFrame("Grand Encyclopedia of Planes");
		theFrame.setLayout(new BorderLayout());
		theFrame.setPreferredSize(new Dimension(1024,768));
		
		mainPane = new mainMenu();
		mainPane.setBounds(0, 0, 1024, 768);
		
		planesPane = new planesMenu();
		planesPane.setBounds(0,0,1024,768);
		
		gunsPane = new gunMenu();
		gunsPane.setBounds(0,0,1024,768);
	}
	
	public class MouseClickHandler extends MouseAdapter implements ActionListener 	//handle button presses
	{
		public void actionPerformed (ActionEvent e) 
		{
						
			if(e.getSource() == planesButton)	//if we want to quit
			{
				changeDisplay(planesPane);
			}
			if(e.getSource() == backToMainMenuButton || e.getSource() == backToMainMenuButton2)
			{
				changeDisplay(mainPane);
			}
			if(e.getSource() == gunsButton)	//if we want to quit
			{
				changeDisplay(gunsPane);
			}
		}
	}
	
	public void decorateFrame()
	{

		/*create the panels and buttons for the elements
		 that need to be accessed in this class*/
		JPanel planesButtonPanel = new planesButtonPanel();
		JPanel gunsButtonPanel = new gunsButtonPanel();
		JPanel backButtonPanel = new backButtonPanel();
		JPanel backButtonPanel2 = new backButtonPanel();
		
		planesButton = new JButton("Planes");
			planesButton.addActionListener(new MouseClickHandler());
		planesButtonPanel.add(planesButton);
		
		backToMainMenuButton = new JButton("Back");
			backToMainMenuButton.addActionListener(new MouseClickHandler());
		backButtonPanel.add(backToMainMenuButton);
		
		backToMainMenuButton2 = new JButton("Back");
			backToMainMenuButton2.addActionListener(new MouseClickHandler());
		backButtonPanel2.add(backToMainMenuButton2);
		
		gunsButton = new JButton("Guns");
			gunsButton.addActionListener(new MouseClickHandler());
		gunsButtonPanel.add(gunsButton);
		
		//add them to the respective pages
		mainPane.add(planesButtonPanel, new Integer(0), 0);
		mainPane.add(gunsButtonPanel, new Integer(1),0);
		
		planesPane.add(backButtonPanel, new Integer(0), 0);
		
		gunsPane.add(backButtonPanel2, new Integer(0),0);

		//start out with our main menu
		theFrame.add(mainPane, BorderLayout.CENTER);
	}
	
	public void changeDisplay(JLayeredPane newPane)
	{		
		theFrame.setContentPane(newPane);
		theFrame.repaint(); 
	}
	

	
	private void createGUI()
	{	
	
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		decorateFrame();
		
		theFrame.pack();
		theFrame.setVisible(true);
	}
	
	public void run()
	{
		createGUI();
		//changeDisplay(); //comment this in to change display, use this with actions to desired effect
	}
	
	public static void main(String[] args)
	{
		layerDemo ld = new layerDemo();
		ld.start();
	}
}
