package Application;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Domain.Land;
import Domain.Landparser;

public class AITest {

	@Test
	public void IsAITrueTest() {
		Player Ai = new Player("ai", 0, true);
		Ai.getAI();
		assertTrue(Ai.getAI() == true);

	}

	@Test
	public void ChooseStartLandAITest() {
		Landparser lp = new Landparser();
		Player ki = new Player("ki", 0, true);
		Land l = new Land();
		Land l2 = new Land();
		Land l3 = new Land();
		Land[] Land = new Land[3];
		Land[0] = l;
		Land[1] = l2;
		Land[2] = l3;
		lp.initStartLands();

		ki.chooseStartLandAI(lp, Land);
		assertTrue(ki.getLand() != null);

	}

	@Test
	public void attackLandAITest() {
		Player ki = new Player("KI", 0, true);
		Player p = new Player("p1", null);
		Land l = new Land();
		l.setName("nachbarland");
		l.setBorderland("nachbarland");
		ArrayList<Land> land = new ArrayList<Land>(1);
		land.add(l);
		l.setOwner(p);
		
		ki.attackLandAI(land, l);
		assertTrue(ki.getAttackLand().getName().equals("nachbarland"));

	}



}
