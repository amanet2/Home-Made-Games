import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.Math.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class gunMenu extends JLayeredPane
{
	
	public gunMenu()
	{
			JPanel firstPanel = new gunMenuBackground();
			firstPanel.setOpaque(true);
	
		add(firstPanel, new Integer(0), 0);
		add(new exitButtonPanel(), new Integer(1), 0);
		
		final gunsSelectionPanel selectionBox = new gunsSelectionPanel();
		add(selectionBox, new Integer(2),0);
		
		selectionBox.getList().addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
										try{
		System.out.println(selectionBox.allGuns.getGun(selectionBox.getList().getSelectedItem().toString()).description());			
						
							}	
							catch(Exception ee)
							{
								//return;
							}
			}});
		
		setBounds(0, 0, 1024, 768);
	}
}