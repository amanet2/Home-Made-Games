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

public class planesSelectionPanel extends JPanel
{
		public planesCollection allPlanes;
		public JComboBox planeList;
		
	public void decoratePanel()
	{
			String[] temp = new String[allPlanes.getPlaneCollection().size()];
			
			int ind = 0;
			for(propPlane pp : allPlanes.getPlaneCollection())
			{
				temp[ind] = pp.name();
				ind++;
			}
		
		 java.util.Arrays.sort(temp);

		 planeList = new JComboBox(temp);
		
		planeList.setSelectedIndex(0);
		

			
		add(planeList);
		
	}
	
	public JComboBox getList()
	{
		if(planeList == null)
			planeList = new JComboBox();
			
		return planeList;
	}

	public planesSelectionPanel()
	{
		allPlanes = new planesCollection();
	
		setOpaque(false);
		setPreferredSize(new Dimension(100,50));
		setBounds(50, 50,410, 410);
		decoratePanel();
	}
}
