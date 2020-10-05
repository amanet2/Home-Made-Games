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

public class mainMenuTitlePanel extends JPanel
{
	public void decoratePanel()
	{
		JLabel title = new JLabel("GRAND ENCYCLOPEDIA OF WAR PLANES");

		add(title);
	}

	public mainMenuTitlePanel()
	{
		setOpaque(false);
		setPreferredSize(new Dimension(100,50));
		setBounds(50, 50,410, 50);
		decoratePanel();
	}
}