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

public class planesMenu extends JLayeredPane
{
	
	public planesMenu()
	{
		
		add(new planesSelectionBackground(), new Integer(0),0);
		
			final planesSelectionPanel selectionBox = new planesSelectionPanel();
		add(selectionBox, new Integer(1),0);
		
		add(new exitButtonPanel(), new Integer(2), 0);
		
			final selectedPlaneBackground selectedPlanePanel = new selectedPlaneBackground();
		add(selectedPlanePanel, new Integer(3),0);
		
			final selectedPlaneInfoPanel selectedPlaneEnginePanel = new selectedPlaneInfoPanel();
			//selectedPlaneEnginePanel.setOpaque(false);
		add(selectedPlaneEnginePanel, new Integer(4),0);
		
			final selectedPlaneInfoPanel horsepowerPanel = new selectedPlaneInfoPanel();
			horsepowerPanel.setBounds(600,150,200,50);
			//horsepowerPanel.setOpaque(false);
		add(horsepowerPanel, new Integer(5),0);

			final selectedPlaneInfoPanel topspeedPanel = new selectedPlaneInfoPanel();
			topspeedPanel.setBounds(600,200,200,50);
			//topspeedPanel.setOpaque(false);
		add(topspeedPanel, new Integer(6),0);

		final selectedPlaneInfoPanel maxClimbPanel = new selectedPlaneInfoPanel();
			maxClimbPanel.setBounds(600,250,200,50);
			//maxClimbPanel.setOpaque(false);
		add(maxClimbPanel, new Integer(7),0);
		
		final selectedPlaneInfoPanel wingspanPanel = new selectedPlaneInfoPanel();
			wingspanPanel.setBounds(600,300,200,50);
			//wingspanPanel.setOpaque(false);
		add(wingspanPanel, new Integer(8),0);
		
		final selectedPlaneInfoPanel weightPanel = new selectedPlaneInfoPanel();
			weightPanel.setBounds(600,350,200,50);
			//weightPanel.setOpaque(false);
		add(weightPanel, new Integer(9),0);
		
		final selectedPlaneInfoPanel armsPanel = new selectedPlaneInfoPanel();
			armsPanel.setBounds(600,400,300,100);
			//armsPanel.setOpaque(false);
			armsPanel.setLayout(new GridLayout(6,1));
		add(armsPanel, new Integer(10),0);
			
		
		setBounds(0, 0, 1024, 768);

		final ArrayList<String> uniqueGuns = new ArrayList<String>();
		final ArrayList<String> allGuns = new ArrayList<String>();
				
				selectionBox.getList().addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
										try{
							propPlane selectedPlane = selectionBox.allPlanes.getPlane(selectionBox.getList().getSelectedItem().toString());
										
							System.out.println(selectionBox.getList().getSelectedItem().toString().replaceAll("\\s+","").toLowerCase());
							BufferedImage myPicture = ImageIO.read(new File("Images/"+selectionBox.getList().getSelectedItem().toString().replaceAll("\\s+","").toLowerCase()+".jpg"));
							
							//JLabel newLabel = new JLabel(new ImageIcon(myPicture));
							
							selectedPlanePanel.getLabel().setIcon(new ImageIcon(myPicture));
	
							selectedPlaneEnginePanel.getLabel().setText("ENGINE: " + selectionBox.allPlanes.getPlane(selectionBox.getList().getSelectedItem().toString()).engineType());

							horsepowerPanel.getLabel().setText("MAXIMUM " + selectionBox.allPlanes.getPlane(selectionBox.getList().getSelectedItem().toString()).engineHorsepower() + " HORSEPOWER");

							topspeedPanel.getLabel().setText("TOP SPEED: " + selectionBox.allPlanes.getPlane(selectionBox.getList().getSelectedItem().toString()).topSpeed() + " KM/H");

							maxClimbPanel.getLabel().setText("MAX CLIMB RATE: " + selectionBox.allPlanes.getPlane(selectionBox.getList().getSelectedItem().toString()).climbrate() + " M/S");
							
							wingspanPanel.getLabel().setText("WING SPAN: " + selectionBox.allPlanes.getPlane(selectionBox.getList().getSelectedItem().toString()).wingSpan() + " M");
							
							weightPanel.getLabel().setText("LOADED WEIGHT: " + selectionBox.allPlanes.getPlane(selectionBox.getList().getSelectedItem().toString()).weight() + " KG");
							
							
							//clear the panels and lists
							armsPanel.removeAll();
							uniqueGuns.clear();
							allGuns.clear();

							//find all unique machine guns
							for(gun g : selectedPlane.machineGuns)
							{
								if(!(uniqueGuns.contains(g.description())))
								{
									uniqueGuns.add(g.description());
									allGuns.add(g.description());
								}
								else allGuns.add(g.description());
							}
							//same for cannons
							for(gun g : selectedPlane.cannons)
							{
								if(!(uniqueGuns.contains(g.description())))
								{
									uniqueGuns.add(g.description());
									allGuns.add(g.description());
								}
								else allGuns.add(g.description());
							}
							
							//each unique gun must hav a quantity

							int[] quantityArray = new int[uniqueGuns.size()];

							for(int i = 0; i < uniqueGuns.size(); i++)
							{
								for(int j = 0; j < allGuns.size(); j++)
								{
									if(allGuns.get(j).equalsIgnoreCase(uniqueGuns.get(i)))	
										quantityArray[i]++;
								}
							}

							armsPanel.add(new JLabel("<html><font color='red'>-----  ARMAMENT  -----</font></html>"));
							for(int i = 0; i < uniqueGuns.size(); i++)
							{
								armsPanel.add(new JLabel(" - " + quantityArray[i] + "x " + uniqueGuns.get(i)));
							}
							for(int i = 0; i < (5-uniqueGuns.size());i++)
							{
								armsPanel.add(new JLabel(""));
							}

							}
							catch(Exception ee)
							{
								System.out.println("EXCEPTION OCCURED");
								ee.printStackTrace();
							}
			}});
	}
}
