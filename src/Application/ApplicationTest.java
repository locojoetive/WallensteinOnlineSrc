package Application;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.Test;

import Application.Server.User;
import Domain.Land;

public class ApplicationTest {

	// Test für PlayerKlasseAnfang
	@Test
	public void useArmyTest() {
		Player p1 = new Player("hans", null);
		p1.useArmy(2);
		assertTrue(p1.getArmy() == 60);
	}

	@Test
	public void returnArmyTest() {
		Player p2 = new Player("guggi", null);
		p2.returnArmy(3);
		assertTrue(p2.getArmy() == 65);

	}

	@Test
	public void chooseLandTest() {
		Player p3 = new Player("bla", null);
		Land l = new Land();

		p3.chooseLand(l);
		assertTrue(l.getOwner().equals(p3));
	}

	@Test
	public void loseLandTest() {
		Land l = new Land();
		Player p = new Player("", null);
		l.setName("Apodis");
		p.chooseLand(l);
		p.loseLand(l);
		assertFalse(p.getLand().equals(l));
	}

	@Test
	public void buildPalaceTest() {
		Land L = new Land();
		PrintWriter pr = null;
		Player p = new Player("", pr);
		L.setRegion("Sachsen");
		p.buildPalace(L.getRegion());
		assertTrue(p.getPalaceCount(0) == 1);

	}

	@Test
	public void destroyPalaceTest() {
		Land L = new Land();
		PrintWriter pr = null;
		Player p = new Player("", pr);
		L.setRegion("Sachsen");
		p.destroyPalace(L.getRegion());
		assertTrue(p.getPalaceCount(1) == 0);
	}

	@Test
	public void thalerCountTest() {
		Player p = new Player("", null);
		p.thalerCount(3);
		assertTrue(p.getThaler() == 18);
	}

	@Test
	public void updatePlayerTest() {

	}

	// Playerklasse Ende

	// Anfang ActionKlasse
	@Test
	public void nullifyAcationTest() {
		Player p = new Player("", null);
		Land l = new Land();
		p.setActionBuildPalace(l);

		p.nullifyActions();
		assertTrue(p.getActionBuildPalace() == null);
	}

	@Test
	public void actionThalerAdvantageThalerIsTrueTest() {
		Player p = new Player("", null);
		Action a = new Action(null, null);
		Land l = new Land();
		l.setTaler(1);
		p.setActionThaler(l);
		p.setThaler(3);
		p.advantageThaler();

		a.actionThaler(p, null);
		assertTrue(p.getThaler() == 5);// thaler von player+thaler vom land+1

	}

	@Test
	public void actionThalerAdvantageThalerIsFalseTest() {
		Player p = new Player("", null);
		Action a = new Action(null, null);
		Land l = new Land();
		l.setTaler(1);
		p.setActionThaler(l);
		p.setThaler(3);

		a.actionThaler(p, null);
		assertTrue(p.getThaler() == 4);// thaler von player+thaler von Land
	}

	@Test
	public void actionThalerRiotMarkerTest() {
		Player p = new Player("", null);
		Action a = new Action(null, null);
		Land l = new Land();
		p.setActionThaler(l);
		l.neutralizeRiotMarker();
		a.actionThaler(p, null);
		assertTrue(l.getRiotMarker() == 1);

	}

	// Ende ActionKlasse

}
