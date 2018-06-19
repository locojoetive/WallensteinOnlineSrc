package ui;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JTextField;

import Application.Player;
import Domain.Land;

public class LandPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int gapP, xP, gapC, xC, gapT, xT;
	Land land;
	private int lpSize = 0;
	BgLabel army, grain, thaler, mansion, church, palace, checkC, checkT,
			checkP, uncheckC, uncheckT, uncheckP, riot;
	JTextField armyText = new JTextField("0"), grainText = new JTextField("0"),
			thalerText = new JTextField("0"), tAvailable = new JTextField("-"),
			cAvailable = new JTextField("-"), pAvailable = new JTextField("-"),
			riotText = new JTextField("0");
	Color color;

	LandPanel(int mapWidth, Land ld) {
		int x = (int) ((float) mapWidth * 0.05);

		this.land = ld;

		armyText.setEditable(false);
		thalerText.setEditable(false);
		grainText.setEditable(false);
		tAvailable.setEditable(false);
		cAvailable.setEditable(false);
		pAvailable.setEditable(false);
		riotText.setEditable(false);

		armyText = new JTextField(String.valueOf(land.getArmy()));
		grainText = new JTextField(String.valueOf(land.getGetreide()));
		thalerText = new JTextField(String.valueOf(land.getTaler()));
		riotText = new JTextField(String.valueOf(land.getRiotMarker()));

		land.setName(land.getName());
		setSize(5 * x, 5 * x);
		setLayout(null);

		armyText.setSize(x, x);
		armyText.setHorizontalAlignment(JTextField.CENTER);

		grainText.setSize(x, x);
		grainText.setHorizontalAlignment(JTextField.CENTER);

		thalerText.setSize(x, x);
		thalerText.setHorizontalAlignment(JTextField.CENTER);
		
		riotText.setHorizontalAlignment(JTextField.CENTER);

		tAvailable.setSize(x, x);
		tAvailable.setHorizontalAlignment(JTextField.CENTER);

		cAvailable.setSize(x, x);
		cAvailable.setHorizontalAlignment(JTextField.CENTER);

		pAvailable.setSize(x, x);
		pAvailable.setHorizontalAlignment(JTextField.CENTER);

		army = new BgLabel("pArmy", x, x);
		thaler = new BgLabel("lpThaler", x, x);
		grain = new BgLabel("lpGrain", x, x);
		mansion = new BgLabel("lpMansion", x, x);
		church = new BgLabel("lpChurch", x, x);
		palace = new BgLabel("lpPalace", x, x);
		checkC = new BgLabel("check", x, x);
		checkT = new BgLabel("check", x, x);
		checkP = new BgLabel("check", x, x);
		uncheckC = new BgLabel("uncheck", x, x);
		uncheckT = new BgLabel("uncheck", x, x);
		uncheckP = new BgLabel("uncheck", x, x);
		riot = new BgLabel("lpRiot", x, x);

		int gap = (int) ((float) x * 0.1);
		army.setBounds(gap, gap, x, x);
		add(army);

		armyText.setBounds(2 * gap + x, gap, x, x);
		add(armyText);

		church.setBounds(3 * gap + 2 * x, gap, x, x);
		add(church);

		this.gapC = gap;
		this.xC = x;
		setChurchPanel(gap, x);

		grain.setBounds(gap, 2 * gap + x, x, x);
		add(grain);

		grainText.setBounds(2 * gap + x, 2 * gap + x, x, x);
		add(grainText);

		palace.setBounds(3 * gap + 2 * x, 2 * gap + x, x, x);
		add(palace);

		this.gapP = gap;
		this.xP = x;
		setPalacePanel(gap, x);

		thaler.setBounds(gap, 3 * gap + 2 * x, x, x);
		add(thaler);

		thalerText.setBounds(2 * gap + x, 3 * gap + 2 * x, x, x);
		add(thalerText);

		mansion.setBounds(3 * gap + 2 * x, 3 * gap + 2 * x, x, x);
		add(mansion);

		this.gapT = gap;
		this.xT = x;
		setMansionPanel(gap, x);

		riot.setBounds(2*gap+x, 4*gap+3*x, x, x);
		add(riot);
		
		riotText.setBounds(3*gap + 2*x, 4*gap + 3*x, x, x);
		add(riotText);
		

		setOpaque(false);
		this.lpSize = x;
	}

	public int getLpSize() {
		return lpSize;
	}

	private void setChurchPanel(int gap, int x) {
		if (land.getChurch() == true) {
			checkC.setBounds(4 * gap + 3 * x, gap, x, x);
			add(checkP);
		}

		else if (land.getChurch() == false
				&& (land.getAnzahlBauplatz() - land.getBuildingCount() > 0)) {
			cAvailable.setBounds(4 * gap + 3 * x, gap, x, x);
			add(cAvailable);
		}

		else if (land.getPalace() == false
				&& (land.getAnzahlBauplatz() - land.getBuildingCount() == 0)) {
			uncheckC.setBounds(4 * gap + 3 * x, gap, x, x);
			add(uncheckC);
		}
	}

	private void setPalacePanel(int gap, int x) {
		if (land.getPalace() == true) {
			checkP.setBounds(4 * gap + 3 * x, 2 * gap + x, x, x);
			add(checkP);
		}

		else if (land.getPalace() == false
				&& (land.getAnzahlBauplatz() - land.getBuildingCount() > 0)) {
			pAvailable.setBounds(4 * gap + 3 * x, 2 * gap + x, x, x);
			add(pAvailable);
		}

		else if (land.getPalace() == false
				&& (land.getAnzahlBauplatz() - land.getBuildingCount() == 0)) {
			uncheckP.setBounds(4 * gap + 3 * x, 2 * gap + x, x, x);
			add(uncheckP);
		}
	}

	private void setMansionPanel(int gap, int x) {
		if (land.getMansion() == true) {
			checkT.setBounds(4 * gap + 3 * x, 3 * gap + 2 * x, x, x);
			add(checkT);
		}

		else if (land.getMansion() == false
				&& (land.getAnzahlBauplatz() - land.getBuildingCount() > 0)) {
			tAvailable.setBounds(4 * gap + 3 * x, 3 * gap + 2 * x, x, x);
			add(tAvailable);
		}

		else if (land.getMansion() == false
				&& (land.getAnzahlBauplatz() - land.getBuildingCount() == 0)) {
			uncheckT.setBounds(4 * gap + 3 * x, 3 * gap + 2 * x, x, x);
			add(uncheckT);
		}
	}

	// sets Army, amount can also be negative
	public void setArmy(Player pl, int amount) {
		int x = 0;
		for(int i = 0; i<pl.getLand().size(); i++){
			if(pl.getLand().get(i).getName().equals(land.getName())){
				x=i;
				break;
			}
		}
		armyText.setText(String.valueOf(pl.getLand().get(x).getArmy()));
	}


	
	public void setRiot(Land ld){
		riotText.setText(String.valueOf(ld.getRiotMarker()));
	}
	
	public void setBuildings(Land ld){
		this.land = ld;
		
		setChurchPanel(gapC, xC);
		setMansionPanel(gapT, xT);
		setPalacePanel(gapP, xP);
		
	}

}