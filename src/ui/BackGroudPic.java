package ui;
  import java.awt.Component;
  import java.awt.Dimension;
  import java.awt.Graphics;
  
  import javax.swing.ImageIcon;
  import javax.swing.JFrame;
  import javax.swing.JPanel;
  
  public class BackGroudPic extends JFrame 
  {
	  
 ///////////////////////////////////////////////////////////////
	  
    public BackGroudPic()
    {
        add(new BackgroundPanel(new ImageIcon("C:/Users/Michael/git/Group-6/Wallenstein/data/images/wood.jpg")));
        pack();
        setVisible(true);
    }

///////////////////////////////////////////////////////////////////////
    
						    class BackgroundPanel extends JPanel 
						    {
						        ImageIcon icon;
						  
						        public BackgroundPanel(ImageIcon icon)
						        {
						            this.icon = icon;
						        }
						  
						        protected void paintComponent(Graphics g) 
						        {
						            super.paintComponent(g);
						            g.drawImage(icon.getImage(), 0, 0, this); 
						        }
						    }
 /////////////////////////////////////////////////////////////////////////
						    
    public static void main(String[] args)
    {
        BackGroudPic pic = new BackGroudPic();
       
    }
  }