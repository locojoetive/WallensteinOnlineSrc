package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.imageio.ImageIO;

public class BgLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -199960849045624883L;
	private Image picture = null;
	private String path;
	private String type;

	public BgLabel(String type, int x, int y) {

		if (type.equals("lpGrain")) {
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/PointsGrain.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("lpThaler")) {
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/PointsThaler.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("pWinning")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/PointsWinning.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("pAdvantage")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/AdvantagePlatelette.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("arrow")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/redArrow.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("pArmy")){
			try {
				this.picture = ImageIO
						.read(new File("./data/images/remArm.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		
		//LandPanel
		else if (type.equals("lpChurch")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/buildingChurch.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("lpPalace")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/buildingPalace.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("lpMansion")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/buildingMansion.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("uncheck")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/uncheck.gif"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("check")){
			try {
				this.picture = ImageIO
						.read(new File("./data/images/check.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("lpRiot")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/riotMarker.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Actions
		else if (type.equals("move")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/cards/Action_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("army3")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/cards/Action_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("army5")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/cards/Action_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("fightA")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/cards/Action_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("fightB")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/cards/Action_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("thaler")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/cards/Action_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("crop")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/cards/Action_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("church")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/cards/Action_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("palace")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/cards/Action_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("mansion")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/cards/Action_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// AdvPlatelettes
		else if (type.equals("thaler")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Adv/thaler.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("crop")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Adv/crop.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("defend")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Adv/defend.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("attack")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Adv/attack.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type.equals("army")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Adv/army.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Events
		else if (type.equals("noAttack4")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("noAttack3")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("army1")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("farmer3")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("maxCrop4")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("minCrop3")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("palace2")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("palace6")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("riot5")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("riot7")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("minThaler2")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (type.equals("maxThaler0")){
			try {
				this.picture = ImageIO.read(new File(
						"./data/images/Cards/Event_" + type + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.type = type;
		if (picture != null)
			this.picture = scaleSize(picture, x, y);
		setSize(new Dimension(x, y));
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
	}

	public void setImage(String path, int x, int y) {
		try {
			this.path = path;
			this.picture = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		picture = scaleSize(picture, x, y);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(picture, 0, 0, null);
	}

	protected Image scaleSize(Image img, int x, int y) {
		BufferedImage resized = new BufferedImage(x, y,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resized.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, x, y, null);
		g.dispose();
		return resized;
	}
	
	public String getType(){
		return type;
	}
}