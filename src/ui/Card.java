package ui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Application.Player;
import Domain.Land;

public class Card{

	private Image card = null;
	private String path = "";
	private Land land;
	private boolean availability;
	private int lpx = 0, lpy = 0;
	public int lpWidth = 0, lpHeight = 0;
	public JPanel lpArea = new JPanel();
	private JLabel owner = new JLabel(), army = new JLabel();
	
	private int mapW = 0, mapH = 0;
	
	public LandPanel lp;


	public Card(Land ld, int mapWidth, int mapHeight) {
		this.land = ld;
		this.mapW = mapWidth;
		this.mapH = mapHeight;
		this.lp = new LandPanel(mapWidth, land);
		try {

			this.path = "./data/images/cards/Land_" + land.getRegion() + "_" + land.getName() + ".jpg";
			this.card = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		//Brandenburg
		
		if (land.getName().equals("Holstein")) {
			setPlace(29, 21, 13, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}

		else if (land.getName().equals("Mecklenburg")) {
			setPlace(46, 25, 11, 4);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}

		else if (land.getName().equals("Vorpommern")) {
			setPlace(60, 21, 13, 7);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}

		else if (land.getName().equals("Neumark")) {
			setPlace(70, 29, 8, 9);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}

		else if (land.getName().equals("Mittelmark")) {
			setPlace(62, 31, 7, 7);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}

		else if (land.getName().equals("Altmark")) {
			setPlace(53, 31, 7, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}

		else if (land.getName().equals("Lueneburg")) {
			setPlace(42, 31, 10, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}

		else if (land.getName().equals("Osnabrueck")) {
			setPlace(26, 35, 8, 4);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}

		else if (land.getName().equals("Bremen")) {
			setPlace(22, 28, 11, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		////////////////////////////////////////////////////
		
		
		//Sachsen
		
		else if (land.getName().equals("Anhalt")) {
			setPlace(51, 41, 9, 5);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Lausitz")) {
			setPlace(70, 40, 7, 8);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Paderborn")) {
			setPlace(28, 41, 8, 4);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("HessenDarmstadt")) {
			setPlace(28, 53, 11, 4);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("HessenKassel")) {
			setPlace(28, 48, 12, 2);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Kursachsen")) {
			setPlace(63, 44, 5, 8);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Lausitz")) {
			setPlace(70, 40, 7, 8);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Wolfenbuettel")) {
			setPlace(41, 40, 6, 4);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Vogtland")) {
			setPlace(56, 52, 5, 4);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("SaechsLande")) {
			setPlace(43, 47, 10, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		

		////////////////////////////////////////////////////
		
		
		//Bayern
		
		else if (land.getName().equals("Baden")) {
			setPlace(28, 60, 9, 4);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Wuerttemberg")) {
			setPlace(30, 68, 9, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Konstanz")) {
			setPlace(31, 75, 9, 5);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Augsburg")) {
			setPlace(41, 67, 8, 10);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Wuerzburg")) {
			setPlace(40, 56, 9, 10);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Regensburg")) {
			setPlace(51, 64, 10, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Oberpfalz")) {
			setPlace(50, 56, 7, 7);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Bayern")) {
			setPlace(51, 71, 10, 4);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Salzburg")) {
			setPlace(53, 77, 18, 3);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}

		////////////////////////////////////////////////////
		
		//Oesterreich
		
		else if (land.getName().equals("Tirol")) {
			setPlace(49, 81, 14, 3);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Kaernten")) {
			setPlace(71, 76, 6, 8);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Steiermark")) {
			setPlace(80, 77, 9, 7);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Niederoesterreich")) {
			setPlace(81, 66, 10, 7);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Oberoesterreich")) {
			setPlace(71, 63, 7, 10);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Passau")) {
			setPlace(62, 61, 8, 7);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Boehmen")) {
			setPlace(69, 53, 13, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Maehren")) {
			setPlace(84, 54, 6, 10);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Schlesien")) {
			setPlace(79, 40, 12, 9);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}


		////////////////////////////////////////////////////
		
		//Kurpfalz
		
		else if (land.getName().equals("Mark")) {
			setPlace(21, 43, 5, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Koeln")) {
			setPlace(12, 42, 5, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Luettich")) {
			setPlace(8, 51, 9, 5);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Trier")) {
			setPlace(19, 53, 7, 4);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Zweibruecken")) {
			setPlace(17, 61, 9, 4);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Strassburg")) {
			setPlace(17, 66, 9, 5);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Breisgau")) {
			setPlace(20, 73, 9, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}
		
		else if (land.getName().equals("Burgund")) {
			setPlace(6, 72, 11, 6);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());
		}

		else if (land.getName().equals("Lothringen")) {
			setPlace(11, 60, 4, 8);
			this.lpArea.setBounds(this.lpx, this.lpy, this.lpWidth,
					this.lpHeight);
			this.lpArea.setVisible(true);
			this.lp.setBounds(lpx - (int) ((float) 2.2 * lp.getLpSize()) + (int) ((float) 0.5 * lpWidth), lpy - 4
					* this.lp.getLpSize(), this.lp.getWidth(),
					this.lp.getHeight());	
		}
		

	}
	
	public String getImagePath(){
		return path;
	}
	
	
	
	public Image scaleSize(Image img, int imWidth, int imHeight) { // Scales
																		// image
																		// to
																		// fit
																		// in
																		// frame
		BufferedImage resized = new BufferedImage(imWidth, imHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resized.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, imWidth, imHeight, null);
		g.dispose();
		return resized;
	}

	public void setCoordinates(int lpX, int lpY) {
		lp.setBounds(lpX, lpY, lp.getWidth(), lp.getHeight());
	}

	public void setPlace(int a, int b, int c, int d) {
		this.lpx = (int) ((float)(((float) a /100) * (float) this.mapW));
		this.lpy =  (int) ((float)(((float) b /100) * (float) this.mapH));
		this.lpWidth = (int) ((float)(((float) c /100) * (float) this.mapW));
		this.lpHeight = (int) ((float)(((float) d /100) * (float) this.mapH));
	}

	public void setAvailability(boolean b) {
		availability = b;
	}

	public boolean getAvailability() {
		return availability;
	}

	public Dimension getCoordinates() {
		return new Dimension(lpx, lpy);
	}
	
	public JLabel getOwner(Player player){
		owner.setBackground(player.getColor());
		owner.setOpaque(true);
		owner.setBounds(lpArea.getX() + lpArea.getWidth() - 10, lpArea.getY() + lpArea.getHeight() - 20,10, 10);
		owner.setVisible(true);
		return owner;
	}
	
	public void setNeutral(){
		lpArea.remove(owner);
		lpArea.revalidate();
		
	}
	
	public Land getLand(){
		return land;
	}
	
}