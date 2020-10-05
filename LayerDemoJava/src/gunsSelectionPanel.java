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

public class gunsSelectionPanel extends JPanel
{
		public gunsCollection allGuns;
		public JComboBox gunList;
		
	public void decoratePanel()
	{
			String[] temp = new String[allGuns.getGunCollection().size()];
			
			int ind = 0;
			for(gun g : allGuns.getGunCollection())
			{
				temp[ind] = g.name();
				ind++;
			}
		
		 gunList = new JComboBox(temp);
		
		gunList.setSelectedIndex(0);
		

			
		add(gunList);
		
	}
	
	public JComboBox getList()
	{
		if(gunList == null)
			gunList = new JComboBox();
			
		return gunList;
	}

	public gunsSelectionPanel()
	{
		allGuns = new gunsCollection();
	
		setOpaque(false);
		setPreferredSize(new Dimension(100,50));
		setBounds(50, 50,410, 410);
		decoratePanel();
	}
}
