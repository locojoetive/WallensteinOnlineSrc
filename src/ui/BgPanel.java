

package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JLayeredPane;
import javax.imageio.ImageIO;


public class BgPanel extends JLayeredPane{
/**
	 * 
	 */
	private static final long serialVersionUID = -199960849045624883L;
	private Image picture = null;
	
	public BgPanel(String type, int x, int y){
		
		if(type == "tablet"){
			try{
				this.picture = ImageIO.read(new File("./data/images/wood.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(type == "map"){
			try{
				this.picture = ImageIO.read(new File("./data/images/Spielplan.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}	
		}
		
		this.picture = scaleSize(picture, x, y);
		setSize(new Dimension(x,y));
		
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(picture, 0, 0, null);
	}
	protected Image scaleSize(Image img, int x, int y){			//Scales image to fit in frame
		BufferedImage resized = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resized.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, x, y, null);
		g.dispose();
		return resized;
	}
	
}
