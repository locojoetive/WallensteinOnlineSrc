package ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComboBox;

import Application.Player;
import Domain.Landparser;
import Domain.Land;

public class InGame extends JFrame {

	private static final long serialVersionUID = -3468703950992005517L;

	int cardAm = 1, frameX, frameY, lpParameter;
	String username = "";
	JButton ready = new JButton("ready");
	BgPanel map, tablet;
	BgLabel grain, thaler, winning, event, armyGroups;
	Actionfield[] aCardVis = new Actionfield[5];
	Actionfield[] aCardInv = new Actionfield[5];
	Client client;
	int farmer = 0;

	// AdvPlatelette
	BgLabel[] aPl = new BgLabel[5];

	// Action Events
	@SuppressWarnings("unchecked")
	JComboBox<String>[] land2Action = new JComboBox[10];
	Land[] actionLands = new Land[10];
	boolean readyForAction = false;

	BgLabel[] actionCards = new BgLabel[10];
	// ...//

	// Deck & HandCards
	Card[] cards;
	String[] handS = new String[0];
	Card[] hand = new Card[0];
	// ...//

	// Events
	boolean eventCheck = false;
	BgLabel[] events;
	BgLabel soloEvent;
	int evtX = 0, evtY = 0, evtW = 0, evtH = 0, evtPanelW = 0, evtPanelH = 0;
	// ...//

	// MyStats
	JTextField grainText = new JTextField("00");
	JTextField thalerText = new JTextField("00");
	JTextField winningText = new JTextField("00");
	JTextField season = new JTextField("Spring");
	JTextField armyText = new JTextField("00");
	Dimension cardDim;
	Dimension screenSize;
	Container cp;
	JPanel statsOp;
	// ...//

	Landparser lp = new Landparser();

	// OpponentStats Attribute
	Player player;
	List<Player> players = new ArrayList<Player>();
	int statsOpX = 0, statsOpY = 0, statsOpGapX = 0, statsOpGapY = 0,
			statsOpWidth = 0, statsOpHeight = 0;
	Dimension gap;
	// ...//

	// KartenVerteilen
	JPanel cardChoice = null;
	static int cCround = 0;
	int cardChoiceArmies = 0, caWidth = 0, caHeight = 0;
	Card c1 = null, c2 = null, c3 = null, myChoice = null;
	BgLabel ca1, ca2, ca3;
	JLabel caText, jl;
	Integer[] aGroups = { 1 };
	JComboBox<Integer> caArmy;
	// ...//

	// Gebote
	JPanel bid = new JPanel();
	JComboBox<String> bidChoice;
	int[] takenAdv = { 6, 6, 6, 6, 6 };

	String[] advCombo = { "---" };
	JComboBox<String> advJCombo = null;
	JPanel advPanel;
	JLabel choosy = null;
	// ...//

	// Schale
	JPanel schaleCont = new JPanel();
	private JTextField[][] data = new JTextField[7][2];

	public InGame(List<Player> players, String username, Client cl) {
		super("SUPER WALLENSTEIN DELUXE: ULTIMATE SHOWDOWN");

		this.client = cl;
		// get Fullscreen Dimension
		setExtendedState(MAXIMIZED_BOTH);
		setResizable(false);
		setUndecorated(true);
		//
		this.username = username;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getUsername().equals(username))
				player = players.get(i);
			this.players.add(players.get(i));
		}

		// ////////////////////////////////////////Set available AG
		if (players.size() + 1 == 3) {
			aGroups = new Integer[10];
			aGroups[0] = 0;
			aGroups[1] = 2;
			aGroups[2] = 2;
			aGroups[3] = 2;
			aGroups[4] = 2;
			aGroups[5] = 3;
			aGroups[6] = 3;
			aGroups[7] = 4;
			aGroups[8] = 4;
			aGroups[9] = 5;
		}

		else if (players.size() + 1 == 4) {
			aGroups = new Integer[9];
			aGroups[0] = 0;
			aGroups[1] = 2;
			aGroups[2] = 2;
			aGroups[3] = 2;
			aGroups[4] = 3;
			aGroups[5] = 3;
			aGroups[6] = 4;
			aGroups[7] = 4;
			aGroups[8] = 5;
		}

		else if (players.size() + 1 == 5) {
			aGroups = new Integer[8];
			aGroups[0] = 0;
			aGroups[1] = 2;
			aGroups[2] = 2;
			aGroups[3] = 3;
			aGroups[4] = 3;
			aGroups[5] = 4;
			aGroups[6] = 4;
			aGroups[7] = 5;
		}
		// ////////////////////////////////////////////////////
		int[] soldiers = new int[players.size() + 1];
		for (int i = 0; i < soldiers.length; i++) {
			soldiers[i] = 0;
		}

		armyText = new JTextField(String.valueOf(player.getArmy()));

		cp = getContentPane();
		cp.setLayout(null);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int frameY = (int) screenSize.getHeight();
		int frameX = (int) screenSize.getWidth();

		initializeMainComp(frameX, frameY);

		double cardY = tablet.getHeight() * 0.2;
		double cardX = cardY * 0.66;
		cardDim = new Dimension((int) cardX, (int) cardY);

		evtX = (int) (map.getWidth() * 0.18);
		evtY = (int) (map.getHeight() * 0.04);
		evtPanelW = (int) (map.getWidth() * 0.72);
		evtW = (int) (evtPanelW * 0.27);
		evtH = (int) (map.getHeight() * 0.11);

		addComponents(frameX, frameY);

		initializeCards();

		lp.initStartLands();

		addPlayerStatus(players);

		cardChoice = new JPanel();

		repaint();


		// TestButtonListeners

		final String us = username;

		ready.addActionListener(new ActionListener() {
			boolean checked = false;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!checked) {
					String[] landToAction = new String[10];
					String messg = "ActionPlanned-" + us + "-";
					for (int i = 0; i < 10; i++) {
						if (!(land2Action[i].getSelectedItem().equals("---"))) {
							landToAction[i] = (String) land2Action[i]
									.getSelectedItem();
						} 
						else
							landToAction[i] = ".";
						if (i < 9)
							messg += landToAction[i] + "-";
						else
							messg += landToAction[i];
					}
					checked = true;
					System.out.println(messg);
					client.stringToServer(messg);
				}
			}

		});

	}

	// initializes landcards
	private void initializeCards() {
		if (players.size() == 3) {
			lp.remove3PlayerLands();
		}
		if (cards == null) {
			int x = map.getWidth();
			int y = map.getHeight();
			this.lpParameter = x;
			cards = new Card[lp.landList.size()];
			for (int i = 0; i < lp.landList.size(); i++) {
				cards[i] = new Card(lp.landList.get(i), x, y);

				if (cards[i] != null) {
					cards[i].setAvailability(true);
					cards[i].lpArea.setOpaque(false);
					cards[i].lpArea
							.addMouseMotionListener(new LandPanelListener(
									cards[i]));
					cards[i].lp.setVisible(false);

					map.setLayer(cards[i].lp, 9);
					map.add(cards[i].lp);
					map.setLayer(cards[i].lpArea, 3);
					map.add(cards[i].lpArea);
				}
			}

			map.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseMoved(MouseEvent arg0) {
					for (int i = 0; i < cards.length; i++) {
						cards[i].lp.setVisible(false);
					}
				}

				@Override
				public void mouseDragged(MouseEvent arg0) {
					// TODO Auto-generated method stub
				}
			});
		}
	}

	private void initializeMainComp(int x, int y) {
		// initialize map

		int fX = (int) (0.36 * x);

		map = new BgPanel("map", fX, y);
		map.setLayout(null);
		//

		// initialize tablet

		fX = (int) (0.64 * x);
		tablet = new BgPanel("tablet", fX, y);
		tablet.setLayout(null);

		for (int i = 0; i < 10; i++) {
			land2Action[i] = new JComboBox<String>(handS);
			land2Action[i].addItem("---");
			final int f = i;
			land2Action[i].addItemListener(new ItemListener() {

				String clone1 = "", clone2 = "";

				public void itemStateChanged(ItemEvent arg0) {
					changeAvailability(f);

					if (!("---".equals((String) land2Action[f]
							.getSelectedItem()))) {
						cards[getPos((String) land2Action[f].getSelectedItem())].lp
								.setVisible(true);

					}

				}

				private void changeAvailability(int index) {
					clone2 = (String) land2Action[f].getSelectedItem();
					if (!(clone1.equals(clone2)) || !(clone1.equals(""))) {
						for (int k = 0; k < land2Action.length; k++) {
							if (k != f) {
								if (!(clone1.equals(""))
										&& !(clone1.equals("---")))
									land2Action[k].addItem(clone1);
								if (!(clone2.equals("---")))
									land2Action[k].removeItem(clone2);
							}
						}

						clone1 = clone2;
					}
				}
			});
		}
	}

	private void initializeAFields() {

		for (int i = 0; i < 5; i++) {
			aCardVis[i] = new Actionfield("visible", cardDim);
			aCardInv[i] = new Actionfield("invisible", cardDim);

			aCardVis[i].setOpaque(false);
			aCardInv[i].setOpaque(false);
		}

	}

	private void initializePointCol(int x) {


		for (int i = 0; i < players.size(); i++) {
			data[i][0] = new JTextField(players.get(i).getUsername());
			data[i][0].setForeground(players.get(i).getColor());
			data[i][0].setHorizontalAlignment(JTextField.CENTER);
			data[i][0].setEditable(false);

			data[i][1] = new JTextField("0");
			data[i][1].setForeground(players.get(i).getColor());
			data[i][1].setHorizontalAlignment(JTextField.CENTER);
			data[i][1].setEditable(false);
		}

		data[players.size()][0] = new JTextField("Farmer");
		data[players.size()][0].setHorizontalAlignment(JTextField.CENTER);
		data[players.size()][0].setEditable(false);
		
		data[players.size()][1] = new JTextField("0");
		data[players.size()][1].setHorizontalAlignment(JTextField.CENTER);
		data[players.size()][1].setEditable(false);

		if (players.size() < 5) {
			for (int i = players.size() + 1; i < 6; i++) {
				data[i][0] = new JTextField("");
				data[i][0].setHorizontalAlignment(JTextField.CENTER);
				data[i][0].setEditable(false);

				data[i][1] = new JTextField("");
				data[i][1].setHorizontalAlignment(JTextField.CENTER);
				data[i][1].setEditable(false);

				System.out.println(data[i][0].getText());
				System.out.println(data[i][1].getText());
			}
		}

		thaler = new BgLabel("lpThaler", x, x);
		grain = new BgLabel("lpGrain", x, x);
		winning = new BgLabel("pWinning", x, x);
		armyGroups = new BgLabel("pArmy", x, x);

		thalerText.setEditable(false);
		grainText.setEditable(false);
		winningText.setEditable(false);
		season.setEditable(false);
		armyText.setEditable(false);
	}

	private void addComponents(int frameX, int frameY) {

		float g = (float) ((float) (0.64 * frameX) - (float) (5 * cardDim.width)) / 6;

		Dimension gp = new Dimension((int) g, (int) g);

		int gx = gp.width;
		int gy = gp.height;
		int textY = (int) (0.5 * gy);

		initializeAFields();

		initializePointCol(textY);

		grainText.setHorizontalAlignment(JTextField.CENTER);
		winningText.setHorizontalAlignment(JTextField.CENTER);
		thalerText.setHorizontalAlignment(JTextField.CENTER);
		season.setHorizontalAlignment(JTextField.CENTER);
		armyText.setHorizontalAlignment(JTextField.CENTER);

		gx = (int) (0.5 * gx);
		gy = (int) (0.8 * gp.height) - textY;
		int gy2 = (int) (0.1 * gy);

		this.statsOpX = (int) (0.5 * gx);
		this.statsOpY = gy;
		this.statsOpWidth = cardDim.width;
		this.statsOpHeight = textY;

		// Army
		armyGroups.setBounds(gx, gy, textY, textY);
		tablet.add(armyGroups);

		armyText.setBounds(gx + 2 * textY, gy, cardDim.width, textY);
		tablet.add(armyText);

		// GrainPoints

		gy = gy + textY + gy2;

		this.statsOpGapX = (int) (0.5 * textY);
		this.statsOpGapY = gy2;

		grain.setBounds(gx, gy, textY, textY);

		tablet.add(grain);

		grainText.setBounds(gx + 2 * textY, gy, cardDim.width, textY);
		tablet.add(grainText);

		// Thaler
		gy = gy + textY + gy2;

		thaler.setBounds(gx, gy, textY, textY);
		tablet.add(thaler);

		thalerText.setBounds(gx + 2 * textY, gy, cardDim.width, textY);
		tablet.add(thalerText);

		// Winning
		gy = gy + textY + gy2;

		winning.setBounds(gx, gy, textY, textY);
		tablet.add(winning);

		winningText.setBounds(gx + 2 * textY, gy, cardDim.width, textY);
		tablet.add(winningText);
		
		
		
		gy = gy + textY + gy2;

		season.setBounds(gx, gy, 2 * textY + cardDim.width, textY);
		tablet.add(season);

		// Status

		gy = gy + textY + gy2;

		statsOp = new JPanel();
		statsOp.setOpaque(false);
		statsOp.setLayout(null);
		statsOp.setBounds(2 * gp.width + cardDim.width, 0, tablet.getWidth()
				- 2 * gp.width + cardDim.width, gy + textY + gy2 + (int) (0.3 * (float) gp.height));
		statsOp.setBorder(BorderFactory.createRaisedBevelBorder());

		this.gap = gp;

		tablet.add(statsOp);

		// ACards

		gx = gp.width;
		gy = gp.height + cardDim.height + 3 * textY;

		aCardVis[0].setBounds(gx, gy, cardDim.width, cardDim.height);
		tablet.add(aCardVis[0]);

		// JComboBox
		land2Action[0].setBounds(gx, gy - 2 * textY, cardDim.width, textY);
		tablet.add(land2Action[0]);

		aCardInv[0].setBounds(gx, gap.height + 2 * cardDim.height + 6 * textY,
				cardDim.width, cardDim.height);
		tablet.add(aCardInv[0]);

		// JComboBox
		land2Action[1].setBounds(gx, gap.height + 2 * cardDim.height + 6
				* textY - 2 * textY, cardDim.width, textY);
		tablet.add(land2Action[1]);

		gx = 2 * gap.width + cardDim.width;

		aCardVis[1].setBounds(gx, gap.height + cardDim.height + 3 * textY,
				cardDim.width, cardDim.height);
		tablet.add(aCardVis[1]);

		// JComboBox
		land2Action[2].setBounds(gx, gap.height + cardDim.height + 3 * textY
				- 2 * textY, cardDim.width, textY);
		tablet.add(land2Action[2]);

		aCardInv[1].setBounds(gx, gap.height + 2 * cardDim.height + 6 * textY,
				cardDim.width, cardDim.height);
		tablet.add(aCardInv[1]);

		// JComboBox
		land2Action[3].setBounds(gx, gap.height + 2 * cardDim.height + 6
				* textY - 2 * textY, cardDim.width, textY);
		tablet.add(land2Action[3]);

		gx = gx + gap.width + cardDim.width;

		aCardVis[2].setBounds(gx, gap.height + cardDim.height + 3 * textY,
				cardDim.width, cardDim.height);
		tablet.add(aCardVis[2]);

		// JComboBox
		land2Action[4].setBounds(gx, gap.height + cardDim.height + 3 * textY
				- 2 * textY, cardDim.width, textY);
		tablet.add(land2Action[4]);

		aCardInv[2].setBounds(gx, gap.height + 2 * cardDim.height + 6 * textY,
				cardDim.width, cardDim.height);
		tablet.add(aCardInv[2]);

		// JComboBox
		land2Action[5].setBounds(gx, gap.height + 2 * cardDim.height + 6
				* textY - 2 * textY, cardDim.width, textY);
		tablet.add(land2Action[5]);

		gx = gx + gap.width + cardDim.width;

		aCardVis[3].setBounds(gx, gap.height + cardDim.height + 3 * textY,
				cardDim.width, cardDim.height);
		tablet.add(aCardVis[3]);

		// JComboBox
		land2Action[6].setBounds(gx, gap.height + cardDim.height + 3 * textY
				- 2 * textY, cardDim.width, textY);
		tablet.add(land2Action[6]);

		aCardInv[3].setBounds(gx, gap.height + 2 * cardDim.height + 6 * textY,
				cardDim.width, cardDim.height);
		tablet.add(aCardInv[3]);

		// JComboBox
		land2Action[7].setBounds(gx, gap.height + 2 * cardDim.height + 6
				* textY - 2 * textY, cardDim.width, textY);
		tablet.add(land2Action[7]);

		gx = gx + gap.width + cardDim.width;

		aCardVis[4].setBounds(gx, gap.height + cardDim.height + 3 * textY,
				cardDim.width, cardDim.height);
		tablet.add(aCardVis[4]);

		// JComboBox
		land2Action[8].setBounds(gx, gap.height + cardDim.height + 3 * textY
				- 2 * textY, cardDim.width, textY);
		tablet.add(land2Action[8]);

		aCardInv[4].setBounds(gx, gap.height + 2 * cardDim.height + 6 * textY,
				cardDim.width, cardDim.height);
		tablet.add(aCardInv[4]);

		// JComboBox
		land2Action[9].setBounds(gx, gap.height + 2 * cardDim.height + 6
				* textY - 2 * textY, cardDim.width, textY);
		tablet.add(land2Action[9]);
		//

		ready.setBounds((int)(tablet.getWidth()*0.5) - 50, tablet.getHeight() -50,
				100, 50);
		tablet.add(ready);
		//

		// Map & Tablet

		int fX = (int) ((float) frameX * 0.36);

		map.setBounds(0, 0, fX, frameY);

		cp.add(map);

		tablet.setBounds(fX, 0, (int) ((float) frameX * 0.64), frameY);

		cp.add(tablet);
		//
	}

	public List<Land> getHandCards() {
		List<Land> handList = new ArrayList<Land>();

		for (int i = 0; i < hand.length; i++) {
			handList.add(hand[i].getLand());
		}

		return handList;
	}

	public void addCard(Land land) {

		String[] cloneS = new String[handS.length + 1];
		Card[] clone = new Card[hand.length + 1];

		for (int i = 0; i < hand.length; i++) {
			cloneS[i] = handS[i];
			clone[i] = hand[i];
		}

		clone[hand.length] = cards[getPos(land.getName())];
		cloneS[hand.length] = cards[getPos(land.getName())].getLand().getName();
		for (int i = 0; i < land2Action.length; i++) {
			land2Action[i].addItem(land.getName());
		}
		clone[hand.length].setAvailability(true);
		hand = clone;
		handS = cloneS;

		System.out.println(":::Added Land:::");
		System.out.println("Land: "
				+ cards[getPos(land.getName())].getLand().getName());
		System.out.println("Region: "
				+ cards[getPos(land.getName())].getLand().getRegion());
		System.out.println("ID: " + String.valueOf(getPos(land.getName())));
		System.out.println();

	}

	private void removeCard(Land land) {

		boolean check = false;

		for (int i = 0; i < hand.length; i++) {
			if (hand[i].getLand().getName().equals(land.getName()))
				check = true;
		}

		if (check == true) {
			String[] cloneS = new String[handS.length - 1];
			Card[] clone = new Card[hand.length - 1];

			for (int j = 0; j < hand.length - 1; j++) {
				if (j < getPos(land.getName())) {
					clone[j] = hand[j];
					cloneS[j] = handS[j];
				} else if (j > getPos(land.getName())) {
					clone[j] = hand[j + 1];
					cloneS[j] = handS[j + 1];
				}
			}

			hand = clone;
			handS = cloneS;

			cards[getPos(land.getName())].setAvailability(true);
			for (int i = 0; i < land2Action.length; i++) {
				for (int j = 0; j < handS.length + 1; j++) {
					if (land2Action[i].getItemAt(j).equals(land))
						land2Action[i].remove(j);
				}
			}
			this.player.loseLand(land);
		}
	}

	public void addPlayerStatus(List<Player> players) {

		boolean isOnHand = false;
		if (player.getLand().size() > 0) {
			for (int i = 0; i < player.getLand().size(); i++) {
				for (int j = 0; j < handS.length; j++) {
					if (player.getLand().get(i).getName().equals(handS[j]))
						isOnHand = true;
				}
				if (!isOnHand) {
					addCard(player.getLand().get(i));
				}
				isOnHand = false;
			}

			boolean isInList = true;
			for (int i = 0; i < handS.length; i++) {
				for (int j = 0; j < player.getLand().size(); j++) {
					if (handS[i].equals(player.getLand().get(j).getName()))
						isInList = true;
				}
				if (!isInList) {
					removeCard(hand[i].getLand());
				}
				isInList = true;
			}
		}

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getUsername().equals(username))
				this.player = players.get(i);
		}

		armyText.setText(String.valueOf(player.getArmy()));
		grainText.setText(String.valueOf(player.getCrop()));
		thalerText.setText(String.valueOf(player.getThaler()));
		winningText.setText(String.valueOf(player.getAchievementPoints()));

		Rectangle rect = statsOp.getBounds();
		tablet.remove(statsOp);
		statsOp.removeAll();
		statsOp.setBounds(rect);
		Dimension cloneGap = gap;
		int cloneY = statsOpY, cloneX = statsOpX;
		initSchale(farmer, players);
		int cloneWidth = (int) (statsOpWidth * 0.8);
		JTextField[] stats = new JTextField[6];

		for (int i = 0; i < 7; i++) {
			if (i < players.size()) {
				if (!(players.get(i).getUsername().equals(username))) {
					stats[0] = new JTextField(String.valueOf(players.get(i)
							.getArmy()));
					stats[1] = new JTextField(String.valueOf(players.get(i)
							.getCrop()));
					stats[2] = new JTextField(String.valueOf(players.get(i)
							.getThaler()));
					stats[3] = new JTextField(String.valueOf(players.get(i)
							.getAchievementPoints()));
					stats[4] = new JTextField(players.get(i).getUsername());

					for (int j = 0; j < stats.length-1; j++) {
						stats[j].setHorizontalAlignment(JTextField.CENTER);
						stats[j].setEditable(false);
						stats[j].setBounds(statsOpX, statsOpY, cloneWidth,
								statsOpHeight);
						statsOp.add(stats[j]);
						statsOpY += statsOpHeight + statsOpGapY;
					}
				}
			}

			else if (i >= players.size()) {
				if (i == 5) {
					statsOpX += cloneWidth + 3;

					stats[0] = data[0][0];
					stats[1] = data[1][0];
					stats[2] = data[2][0];
					stats[3] = data[3][0];
					stats[4] = data[4][0];
					stats[5] = data[5][0];
					for (int j = 0; j < stats.length; j++) {
						stats[j].setHorizontalAlignment(JTextField.CENTER);
						stats[j].setEditable(false);
						stats[j].setBounds(statsOpX, statsOpY, cloneWidth,
								statsOpHeight);
						statsOp.add(stats[j]);
						statsOpY += statsOpHeight + statsOpGapY;
					}
				} else if (i == 6) {
					stats[0] = data[0][1];
					stats[1] = data[1][1];
					stats[2] = data[2][1];
					stats[3] = data[3][1];
					stats[4] = data[4][1];
					stats[5] = data[5][1];
					for (int j = 0; j < stats.length; j++) {
						stats[j].setHorizontalAlignment(JTextField.CENTER);
						stats[j].setEditable(false);
						stats[j].setBounds(statsOpX, statsOpY, cloneWidth,
								statsOpHeight);
						statsOp.add(stats[j]);
						statsOpY += statsOpHeight + statsOpGapY;
					}
				}

			}
			statsOpY = cloneY;
			if(players.size()>i){
				if(!(players.get(i).getUsername().equals(username)))
					statsOpX+= cloneWidth + 3;
			}else if(players.size()<i){
				statsOpX+= cloneWidth + 3;
			}
		}

		for (int i = 0; i < players.size(); i++) {
			for (int j = 0; j < players.get(i).getLand().size(); j++) {
				if (players.get(i).getLand().size() > 0) {
					cards[getPos(players.get(i).getLand().get(j).getName())].lp
							.setArmy(players.get(i), players.get(i).getLand()
									.get(j).getArmy());
					cards[getPos(players.get(i).getLand().get(j).getName())].lp
							.setRiot(players.get(i).getLand().get(j));
					cards[getPos(players.get(i).getLand().get(j).getName())].lp
							.setBuildings(players.get(i).getLand().get(j));
					JLabel Z = cards[getPos(players.get(i).getLand().get(j)
							.getName())].getOwner(players.get(i));
					map.setLayer(Z, 9);
					map.add(Z);
					map.repaint();
					map.revalidate();
				}
			}
		}
		tablet.add(statsOp);
		statsOpX = cloneX;
		statsOpY = cloneY;
		revalidate();
		this.gap = cloneGap;

	}

	private int getPos(String land) {
		for (int i = 0; i < cards.length; i++) {
			if (cards[i].getLand().getName().equals(land)) {
				return i;
			}
		}
		return -1;
	}

	public void chooseYourAgPanel(String s1, String s2, String s3) {

		if (aGroups.length == 1) {
			client.stringToServer("finishedChooseLand-" + player.getUsername());
		} else {
			cardChoice.setLayout(null);
			cardChoice.setBounds((int) ((float) tablet.getWidth() / 4),
					(int) ((float) tablet.getHeight() / 3) + 20,
					(int) (2 * ((float) tablet.getWidth() / 4)),
					(int) ((float) (0.75 * (float) tablet.getHeight() / 3)));

			caWidth = (int) ((float) (cardChoice.getWidth() / 5));
			caHeight = (int) ((float) (cardChoice.getHeight() * 0.75));

			this.c1 = findLand(s1);
			this.c2 = findLand(s2);
			this.c3 = findLand(s3);

			ca1 = new BgLabel("", caWidth, caHeight);
			ca1.setImage(c1.getImagePath(), caWidth, caHeight);

			ca2 = new BgLabel("", caWidth, caHeight);
			ca2.setImage(c2.getImagePath(), caWidth, caHeight);

			ca3 = new BgLabel("", caWidth, caHeight);
			ca3.setImage("./data/images/cards/Blank.jpg", caWidth, caHeight);

			caText = new JLabel("Your turn to choose a Land!");

			caArmy = new JComboBox<Integer>(aGroups);

			ca1.setBounds((int) ((float) caWidth / 2), 10, caWidth, caHeight);
			ca2.setBounds(2 * caWidth, 10, caWidth, caHeight);
			ca3.setBounds(3 * caWidth + (int) ((float) caWidth / 2), 10,
					caWidth, caHeight);
			caText.setBounds(0, caHeight + 10, cardChoice.getWidth(),
					cardChoice.getHeight() - caHeight - 10);
			caText.setHorizontalAlignment(JLabel.CENTER);

			cardChoice.add(ca1);
			cardChoice.add(ca2);
			cardChoice.add(ca3);
			cardChoice.add(caText);
			cardChoice.setBorder(BorderFactory.createRaisedBevelBorder());

			tablet.add(cardChoice);
			tablet.setLayer(cardChoice, 8);
			tablet.setLayer(ca1, 9);
			tablet.setLayer(ca2, 9);
			tablet.setLayer(ca3, 9);
			tablet.repaint();
			cardChoice.revalidate();
			ca1.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					cards[getPos(c1.getLand().getName())].lp.setVisible(true);
					jl = new JLabel(
							"Now choose how many Army Groups you wanna place there");
					jl.setHorizontalAlignment(JLabel.CENTER);
					myChoice = c1;
					jl.setBounds(0, caHeight + 10, cardChoice.getWidth(),
							cardChoice.getHeight() - caHeight - 10);
					caArmy.setBounds(
							(int) ((float) (0.5 * (float) cardChoice.getWidth())) - 60,
							(int) ((float) 0.5 * (float) cardChoice.getHeight()) - 30,
							120, 60);

					cardChoice.remove(ca1);
					cardChoice.remove(ca2);
					cardChoice.remove(ca3);
					cardChoice.remove(caText);

					cardChoice.add(caArmy);
					cardChoice.add(jl);

					repaint();
					revalidate();
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
				}

			});

			ca2.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					cards[getPos(c2.getLand().getName())].lp.setVisible(true);
					myChoice = c2;
					jl = new JLabel(
							"Now choose how many Army Groups you wanna place there");
					jl.setHorizontalAlignment(JLabel.CENTER);

					jl.setBounds(0, caHeight + 10, cardChoice.getWidth(),
							cardChoice.getHeight() - caHeight - 10);
					caArmy.setBounds(
							(int) ((float) (0.5 * (float) cardChoice.getWidth())) - 60,
							(int) ((float) 0.5 * (float) cardChoice.getHeight()) - 30,
							120, 60);

					cardChoice.remove(ca1);
					cardChoice.remove(ca2);
					cardChoice.remove(ca3);
					cardChoice.remove(caText);

					cardChoice.add(caArmy);
					cardChoice.add(jl);

					cardChoice.repaint();
					cardChoice.revalidate();
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

			});

			ca3.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					myChoice = c3;
					cards[getPos(myChoice.getLand().getName())].lp
							.setVisible(true);

					jl = new JLabel(
							"Now choose how many Army Groups you wanna place there");
					jl.setHorizontalAlignment(JLabel.CENTER);

					jl.setBounds(0, caHeight + 10, cardChoice.getWidth(),
							cardChoice.getHeight() - caHeight - 10);
					caArmy.setBounds(
							(int) ((float) (0.5 * (float) cardChoice.getWidth())) - 60,
							(int) ((float) 0.5 * (float) cardChoice.getHeight()) - 30,
							120, 60);

					cardChoice.remove(ca1);
					cardChoice.remove(ca2);
					cardChoice.remove(ca3);
					cardChoice.remove(caText);

					cardChoice.add(caArmy);
					cardChoice.add(jl);

					tablet.repaint();
					tablet.revalidate();
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

			});

			caArmy.addItemListener(new ItemListener() {
				boolean set = false;

				@Override
				public void itemStateChanged(ItemEvent arg0) {

					if (set == false) {
						Integer clone = 0;
						clone = (int) caArmy.getSelectedItem();

						int pos = caArmy.getSelectedIndex();
						cardChoice.removeAll();
						tablet.remove(cardChoice);
						aGroups = removeElement(aGroups, pos);
						client.stringToServer("chooseLand-"
								+ player.getUsername() + "-"
								+ myChoice.getLand().getName() + "-" + clone);
					}
					set = true;
				}
			});
		}
	}

	public void setC1(Land ld) {
		this.c1 = cards[getPos(ld.getName())];
	}

	public void setC2(Land ld) {
		this.c2 = cards[getPos(ld.getName())];
	}

	private Integer[] removeElement(Integer[] x, Integer pos) {
		Integer[] clone = new Integer[x.length - 1];
		for (int i = 0; i < x.length - 1; i++) {
			if (i < pos) {
				clone[i] = x[i];
			} else {
				clone[i] = x[i + 1];
			}
		}
		return clone;
	}

	public void setAdvPl(String[] adv) {
		int posX = (int) (map.getWidth() * 0.07);
		int posY = (int) (map.getHeight() * 0.05);
		int x = (int) (map.getWidth() * 0.09);

		aPl = new BgLabel[5];

		for (int i = 0; i < aPl.length; i++) {
			aPl[i] = new BgLabel(adv[i], x, x);
		}

		aPl[0].setBounds(posX, posY, x, x);
		map.add(aPl[0]);

		posY = (int) (map.getHeight() * 0.12);

		aPl[1].setBounds(posX, posY, x, x);
		map.add(aPl[1]);

		posY = (int) (map.getHeight() * 0.19);

		aPl[2].setBounds(posX, posY, x, x);
		map.add(aPl[2]);

		posY = (int) (map.getHeight() * 0.26);

		aPl[3].setBounds(posX, posY, x, x);
		map.add(aPl[3]);

		posY = (int) (map.getHeight() * 0.33);

		aPl[4].setBounds(posX, posY, x, x);
		map.add(aPl[4]);

	}

	private Card findLand(String s1) {
		Card l = null;
		for (int i = 0; i < cards.length; i++) {
			if (cards[i].getLand().getName().equals(s1)) {
				l = cards[i];
				break;
			}
		}
		return l;
	}

	public void placeAction(String[] action) {
		int posX = (int) (map.getWidth() * 0.03);
		int posY = (int) (map.getHeight() * 0.89);
		int x = (int) (map.getWidth() * 0.08);
		int y = (int) (map.getHeight() * 0.09);

		for (int i = 0; i < action.length; i++) {
			actionCards[i] = new BgLabel(action[i], x, y);
		}

		actionCards[0].setBounds(posX, posY, x, y);
		map.add(actionCards[0]);

		posX = (int) (map.getWidth() * 0.12);

		actionCards[1].setBounds(posX, posY, x, y);
		map.add(actionCards[1]);

		posX = (int) (map.getWidth() * 0.22);

		actionCards[2].setBounds(posX, posY, x, y);
		map.add(actionCards[2]);

		posX = (int) (map.getWidth() * 0.31);

		actionCards[3].setBounds(posX, posY, x, y);
		map.add(actionCards[3]);

		posX = (int) (map.getWidth() * 0.41);

		actionCards[4].setBounds(posX, posY, x, y);
		map.add(actionCards[4]);

		posX = (int) (map.getWidth() * 0.50);

		actionCards[5].setBounds(posX, posY, x, y);
		map.add(actionCards[5]);
		actionCards[5].setVisible(false);

		posX = (int) (map.getWidth() * 0.60);

		actionCards[6].setBounds(posX, posY, x, y);
		map.add(actionCards[6]);
		actionCards[6].setVisible(false);

		posX = (int) (map.getWidth() * 0.69);

		actionCards[7].setBounds(posX, posY, x, y);
		map.add(actionCards[7]);
		actionCards[7].setVisible(false);

		posX = (int) (map.getWidth() * 0.79);

		actionCards[8].setBounds(posX, posY, x, y);
		map.add(actionCards[8]);
		actionCards[8].setVisible(false);

		posX = (int) (map.getWidth() * 0.88);

		actionCards[9].setBounds(posX, posY, x, y);
		map.add(actionCards[9]);
		actionCards[9].setVisible(false);

	}

	public void flipAction(int i) {
		if (5 <= i && 9 >= i)
			actionCards[i].setVisible(true);
	}

	public void placeEventCards(String[] e) {
		int tempX = evtX;
		events = new BgLabel[e.length];

		for (int i = 0; i < e.length; i++) {
			events[i] = new BgLabel(e[i], evtW, evtH);
			events[i].setBounds(evtX, evtY, evtW, evtH);
			events[i].setVisible(true);

			map.add(events[i]);
			map.setLayer(events[i], 3);
			System.out.println(evtX);
			evtX = evtX + evtW;
		}
		evtX = tempX;
	}

	public void setEventCard(String event) {
		if (eventCheck)
			map.remove(soloEvent);

		int x = (int) (map.getWidth() * 0.06);
		int y = (int) (map.getHeight() * 0.81);
		int w = (int) (map.getWidth() * 0.13);
		int h = (int) (map.getHeight() * 0.06);

		soloEvent = new BgLabel(event, w, h);
		soloEvent.setBounds(x, y, w, h);
		soloEvent.setVisible(true);
		map.add(soloEvent);
		map.setLayer(soloEvent, 3);
		eventCheck = true;
	}

	public void setDeck(Card[] cd) {
		this.cards = cd;
	}

	public Card[] getDeck() {
		return cards;
	}

	public void placeBid() {
		String[] temp = new String[handS.length + 6];
		temp[0] = null;
		for (int i = 1; i < temp.length; i++) {
			if (i <= 5) {
				temp[i] = String.valueOf(i - 1);
			} else {
				temp[i] = handS[i - 6];
			}
		}
		bid.setLayout(null);
		bid.setBounds((int) (tablet.getWidth() / 4),
				(int) (tablet.getHeight() / 3) + 20,
				(int) (2 * (tablet.getWidth() / 4)),
				(int) (0.75 * (tablet.getHeight() / 3)));

		int x = (int) (bid.getWidth() / 2);
		int y = (int) (bid.getHeight() / 2);
		bidChoice = new JComboBox<String>(temp);

		bidChoice.setBounds(x - 60, y - 20, 120, 40);
		bid.add(bidChoice);

		JLabel bidzz = new JLabel("Place ya bid, Dawg");
		bidzz.setHorizontalAlignment(JLabel.CENTER);
		bidzz.setBounds(0, y + 20, bid.getWidth(), bid.getHeight() - y - 20);
		bid.add(bidzz);

		tablet.setLayer(bid, 9);
		tablet.add(bid);
		bidChoice.addItemListener(new ItemListener() {
			boolean check = false;
			String bidz = "Bid-" + username + "-";

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (!check) {
					if (bidChoice.getSelectedItem().equals("1")) {
						bidz += "2";
					} else if (bidChoice.getSelectedItem().equals("2")) {
						bidz += "3";
					} else if (bidChoice.getSelectedItem().equals("3")) {
						bidz += "4";
					} else if (bidChoice.getSelectedItem().equals("4")) {
						bidz += "5";
					} else if (bidChoice.getSelectedItem().equals("0")) {
						bidz += "0";
					} else {
						bidz += "1";
						for (int i = 0; i < 10; i++) {
							land2Action[i].removeItem(bidChoice
									.getSelectedItem());
						}
					}

					tablet.remove(bid);
					tablet.repaint();
					tablet.revalidate();
					client.stringToServer(bidz);
					System.out.println(bidz);
					check = true;

					JOptionPane
							.showMessageDialog(
									null,
									"And now? Plan your actions, buddy and conquer the wor.. uhm i mean old school germany!");
					JOptionPane
							.showMessageDialog(null,
									"Oh yeah, and press the ready button when your finished!");

				}
			}

		});
	}

	public void attack(String[] attackArray) {
		int armyTroups = Integer.parseInt(attackArray[2]);
		if (armyTroups > 1) {
			JPanel attack = new JPanel();
			attack.setLayout(null);

			attack.setBounds((tablet.getWidth() / 4),
					(int) (tablet.getHeight() / 3) + 20,
					(int) (2 * (tablet.getWidth() / 4)),
					(int) ((0.75 * tablet.getHeight() / 3)));

			JButton fight = new JButton("Fight!");
			JLabel attackText = new JLabel(
					"Choose which land and with how many army troups you wanna attack from "
							+ attackArray[1] + "!");
			attackText.setHorizontalAlignment(JLabel.CENTER);

			Integer[] x = new Integer[armyTroups];
			for (int i = 1; i <= armyTroups - 1; i++) {
				x[i] = i;
			}
			final JComboBox<Integer> armies = new JComboBox<Integer>(x);
			String[] nachbarn = { "1", "2" };
			for (int i = 0; i < lp.landList.size(); i++) {
				
				if (attackArray[1].equals(lp.landList.get(i).getName())) {
					nachbarn = new String[lp.landList.get(i).getborderland()
							.size()];
					for (int j = 0; j < lp.landList.get(i).getborderland()
							.size(); j++) {
						boolean check = false;
						for(int k = 0; k<handS.length; k++){
							if(handS[k].equals(lp.landList.get(i).getborderland().get(j)))
								check = true;
							}
						if(!check)
							nachbarn[j] = lp.landList.get(i).getborderland().get(j);
					}
					break;
				}
			}
			final JComboBox<String> borderLand = new JComboBox<String>(nachbarn);
			int a = (int) (attack.getWidth() / 4);
			int b = (int) (attack.getHeight() / 2);
			
			borderLand.setBounds(a - 60, b - 15, 120, 30);
			int a1 = 2 * (a - 30);
			a = a * 3;
			armies.setBounds(a - 60, b - 15, 120, 30);
			attackText.setBounds(0, 0, attack.getWidth(), attack.getHeight()
					- b - 15);
			b = (int) ((b - 15) / 2);
			fight.setBounds(a1, attack.getHeight() - b - 30, 120, b - 20);

			attack.add(borderLand);
			attack.add(armies);
			attack.add(attackText);
			attack.add(fight);
			tablet.add(attack);
			final String kay = attackArray[0];
			fight.addActionListener(new ActionListener() {
				boolean check = false;
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String bLand = (String) borderLand.getSelectedItem();
					String arm = (String) armies.getSelectedItem();
					client.stringToServer("fight"+ kay + "-" + username + "-" + bLand + "-" + arm);
				}
				

			});

			attack.setBorder(BorderFactory.createRaisedBevelBorder());

			tablet.setLayer(attack, 9);
			tablet.add(attack);
		}
	}
	
	public void movement(String[] moveArray) {
		int armyTroups = Integer.parseInt(moveArray[1]);
		if (armyTroups > 1) {
			JPanel move = new JPanel();
			move.setLayout(null);

			move.setBounds((tablet.getWidth() / 4),
					(int) (tablet.getHeight() / 3) + 20,
					(int) (2 * (tablet.getWidth() / 4)),
					(int) ((0.75 * tablet.getHeight() / 3)));

			JButton movement = new JButton("Movement!");
			JLabel moveText = new JLabel(
					"Choose where you want to lead you army!");
			moveText.setHorizontalAlignment(JLabel.CENTER);

			Integer[] x = new Integer[armyTroups];
			for (int i = 1; i <= armyTroups - 1; i++) {
				x[i] = i;
			}
			final JComboBox<Integer> armies = new JComboBox<Integer>(x);
			String[] nachbarn = { "1", "2" };
			for (int i = 0; i < lp.landList.size(); i++) {
				if (moveArray[0].equals(lp.landList.get(i).getName())) {
					nachbarn = new String[lp.landList.get(i).getborderland()
							.size()];
					for (int j = 0; j < lp.landList.get(i).getborderland()
							.size(); j++) {
						boolean check = false;
						for(int k = 0; k<handS.length; k++){
							if(handS[k].equals(lp.landList.get(i).getborderland().get(j)))
								check = true;
							}
						if(check)
							nachbarn[j] = lp.landList.get(i).getborderland().get(j);
					}
					break;
				}
			}
			final JComboBox<String> borderLand = new JComboBox<String>(nachbarn);
			int a = (int) (move.getWidth() / 4);
			int b = (int) (move.getHeight() / 2);
			
			borderLand.setBounds(a - 60, b - 15, 120, 30);
			int a1 = 2 * (a - 30);
			a = a * 3;
			armies.setBounds(a - 60, b - 15, 120, 30);
			moveText.setBounds(0, 0, move.getWidth(), move.getHeight()
					- b - 15);
			b = (int) ((b - 15) / 2);
			movement.setBounds(a1, move.getHeight() - b - 30, 120, b - 20);

			move.add(borderLand);
			move.add(armies);
			move.add(moveText);
			move.add(movement);
			tablet.add(movement);
			movement.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String bLand = (String) borderLand.getSelectedItem();
					String arm = (String) armies.getSelectedItem();
					client.stringToServer("Movement" + "-" + username + "-" + bLand + "-" + arm);
				}
				

			});

			move.setBorder(BorderFactory.createRaisedBevelBorder());

			tablet.setLayer(move, 9);
			tablet.add(move);
		}
	}

	public void actionActive(String[] addArmyArray) {

	}

	private class LandPanelListener implements MouseMotionListener {

		Card c;

		public LandPanelListener(Card ca) {
			super();
			this.c = ca;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			{
				if (e.getSource().equals(c.lpArea)) {

					c.lp.setVisible(true);

					c.lp.setLocation(
							(int) (c.getCoordinates().getWidth()
									- (int) (2.2 * c.lp.getLpSize()) + (int) (0.5 * c.lpWidth)),
							(int) (c.getCoordinates().getHeight() - 4 * c.lp
									.getLpSize()));
				} else if (!(e.getSource().equals(c.lpArea))) {

				}
			}

		}
	}

	public void actionPassiv(String[] stringArray) {
		if (stringArray[1].equals("army3")) {
			String actionHappend = stringArray[0]
					+ " placed  3 army troups in " + stringArray[2] + "!";
			JOptionPane.showMessageDialog(null, actionHappend);
		} else if (stringArray[1].equals("army5")) {
			String actionHappend = stringArray[0]
					+ " placed  5 army troups in " + stringArray[2] + "!";
			JOptionPane.showMessageDialog(null, actionHappend);
		} else if (stringArray[1].equals("move")) {
			String actionHappend = stringArray[0] + " moved from "
					+ stringArray[2] + " to " + stringArray[3] + "!";
			JOptionPane.showMessageDialog(null, actionHappend);
		} else if (stringArray[1].equals("fightA")
				|| stringArray[1].equals("fightB")) {
			String actionHappend = stringArray[0] + " attacked "
					+ stringArray[2] + " with " + stringArray[3]
					+ " army troups!";
			JOptionPane.showMessageDialog(null, actionHappend);
		} else if (stringArray[1].equals("palace")
				|| stringArray[1].equals("mansion")
				|| stringArray[1].equals("church")) {
			String actionHappend = stringArray[0] + " built " + stringArray[1]
					+ " in " + stringArray[2] + " for his folks!";
			JOptionPane.showMessageDialog(null, actionHappend);
		} else if (stringArray[1].equals(thaler)) {
			String actionHappend = stringArray[0] + " robbed his folks in "
					+ stringArray[1] + " and called it ''taxes''!";
			JOptionPane.showMessageDialog(null, actionHappend);
		} else if (stringArray[1].equals("crop")) {
			String actionHappend = stringArray[0] + " robbed his folks in "
					+ stringArray[1] + " and called it ''taxes''!";
			JOptionPane.showMessageDialog(null, actionHappend);
		}
	}

	public void chooseYourAdvPl(final String[] stringArray) {

		advPanel = new JPanel();
		advPanel.setLayout(null);

		choosy = new JLabel("Your turn to choose an advantage platelatte");
		choosy.setHorizontalAlignment(JLabel.CENTER);

		advJCombo = new JComboBox<String>(stringArray);

		advPanel.setBounds((int) (tablet.getWidth() / 4),
				(int) (tablet.getHeight() / 2), (int) (tablet.getWidth() / 2),
				(int) (tablet.getWidth() / 8));
		advJCombo.setBounds((int) (advPanel.getWidth() / 2) - 60,
				(int) (advPanel.getHeight() / 4) - 15, 120, 30);
		choosy.setBounds(0, (int) (advPanel.getHeight() / 4) * 3 - 15,
				advPanel.getWidth(),
				advPanel.getHeight() - (int) (advPanel.getHeight() / 4) * 3
						- 15);

		advPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		advPanel.add(advJCombo);
		advPanel.add(choosy);

		tablet.setLayer(advPanel, 9);
		tablet.add(advPanel);

		advJCombo.addItemListener(new ItemListener() {
			boolean check = false;

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (!check) {

					String chosen = (String) advJCombo.getSelectedItem();

					int reply = JOptionPane.showConfirmDialog(null,
							"You chose " + chosen + ". Are you fou real?",
							"Advantage Choice", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						advPanel.remove(choosy);
						advPanel.remove(advJCombo);
						tablet.remove(advPanel);
						tablet.repaint();
						tablet.revalidate();

						client.stringToServer("ChosenAdvantagePlatelate-"
								+ username + "-" + chosen + "-"
								+ stringArray[0]);
					} else {
						chooseYourAdvPl(stringArray);
					}
				}
				check = true;
			}
		});
	}

	public void markAdv(String[] stringArray) {
		Color cl = null;
		JLabel square = new JLabel();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getUsername().equals(stringArray[0]))
				cl = players.get(i).getColor();
		}
		for (int i = 0; i < aPl.length; i++) {
			if (aPl[i].getType().equals(stringArray[1])) {

				square.setBackground(cl);
				square.setOpaque(true);
				square.setBounds(aPl[i].getX()
						+ (int) (0.5 * aPl[i].getWidth()) - 10, aPl[i].getY()
						+ (int) (0.5 * aPl[i].getHeight()) - 10, 20, 20);
				map.setLayer(square,6);
				map.add(square);
			}
		}
	}

	public void initSchale(int farmers, List<Player> players) {
		for (int j = 0; j < players.size(); j++) {
			data[j][1] = new JTextField(String.valueOf(players.get(j).getSchalenContent()));
			data[j][0] = new JTextField(players.get(j).getUsername());
		}
		data[players.size()][1] = new JTextField(String.valueOf(farmers));
	}
	
	public void setFarmer(int i){
		this.farmer = i;
	}
}
