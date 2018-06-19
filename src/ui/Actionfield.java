package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Actionfield extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int am = 0;
	
	private Image picture = null;
	
	public Actionfield(String visibility, Dimension cardDim){
		
		if(am == 0){
			try{
				this.picture = ImageIO.read(new File("./data/images/Cards/Action_move.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(am == 1){
			try{
				this.picture = ImageIO.read(new File("./data/images/Cards/Action_army3.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(am == 2){
			try{
				this.picture = ImageIO.read(new File("./data/images/Cards/Action_army5.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(am == 3){
			try{
				this.picture = ImageIO.read(new File("./data/images/Cards/Action_church.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(am == 4){
			try{
				this.picture = ImageIO.read(new File("./data/images/Cards/Action_palace.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(am == 5){
			try{
				this.picture = ImageIO.read(new File("./data/images/Cards/Action_mansion.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(am == 6){
			try{
				this.picture = ImageIO.read(new File("./data/images/Cards/Action_crop.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(am == 7){
			try{
				this.picture = ImageIO.read(new File("./data/images/Cards/Action_thaler.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(am == 8){
			try{
				this.picture = ImageIO.read(new File("./data/images/Cards/Action_fightA.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(am == 9){
			try{
				this.picture = ImageIO.read(new File("./data/images/Cards/Action_fightB.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		else if(am >=10 || am <0){
			am=0;
		}
		
		
		this.picture = scaleSize(picture, cardDim.width, cardDim.height);
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		am++;
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
