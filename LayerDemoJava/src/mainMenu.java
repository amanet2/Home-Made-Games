import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.Math.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class mainMenu extends JLayeredPane
{
	
	public mainMenu()
	{
			JPanel firstPanel = new mainMenuBackground();
			firstPanel.setOpaque(true);
	
			JPanel exitButtonPanel = new exitButtonPanel();
			JPanel mainMenuTitlePanel = new mainMenuTitlePanel();
	
		add(firstPanel, new Integer(0), 0);
		add(new exitButtonPanel(), new Integer(1), 0);
		add(mainMenuTitlePanel, new Integer(2),0);
		
		
		
		setBounds(0, 0, 1024, 768);
	}
}