package ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public abstract class GeneralWindow extends JFrame{
	
	private Toolkit t;
	private Dimension d;
	int x=0, y=0, width=400, height=250;
	public void Centralize(){
		t = Toolkit.getDefaultToolkit();
		d= t.getScreenSize();
		x=(int) ((d.getWidth()-width)/2); //centralizing window
		y=(int) ((d.getHeight()-height)/2);
		setBounds(x,y,width,height);
	}

}
