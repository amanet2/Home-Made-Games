import java.util.*;
// Import the basic graphics classes.
import java.lang.Math;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class movementController
{
	public int velocity;
	public boolean up,down,left,right;	//for player
	public boolean forward,backward,strafeL,strafeR;	//for player
	int forwardV;//0-7 for aimer
	public boolean superSprint;
	public boolean sprint;	//for player
	public boolean walk;
	public boolean slowWalk;
	
	public void setForwardV(int i)
	{
		switch(i)
		{
			case 0 :
				forward = true;
				backward = false;
				strafeR = false;
				strafeL = false;
				break;
			case 1 :
				forward = true;
				backward = false;
				strafeR = true;
				strafeL = false;
				break;
			case 2 :
				forward=false;
				backward=false;
				strafeR = true;
				strafeL = false;
				break;
			case 3 :
				backward = true;
				forward = false;
				strafeR = true;
				strafeL = false;
				break;
			case 4 :
				backward = true;
				forward = false;
				strafeR=false;
				strafeL=false;
				break;
			case 5 :
				backward = true;
				forward = false;
				strafeL = true;
				strafeR = false;
				break;
			case 6 :
				forward=false;
				backward=false;
				strafeL = true;
				strafeR=false;
				break;
			case 7 :
				forward = true;
				backward=false;
				strafeL = true;
				strafeR=false;
				break;
		}	
	}
	
	public movementController()
	{
		forwardV=2;
		velocity = 0;
		up = false;
		down = false;
		left = false;
		right = false;
		forward = false;
		backward = false;
		strafeL = false;
		strafeR = false;
		sprint = false;
		walk = false;
		superSprint=false;
		slowWalk=false;
	}
}