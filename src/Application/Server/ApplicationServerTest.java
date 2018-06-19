package Application.Server;

import static org.junit.Assert.*;

import org.junit.Test;

public class ApplicationServerTest {

	@Test
	public void RankingTest() {
		User u= new User("p1",2,0,null);
		assertTrue(u.getRanking()==10);
		
	}
	@Test
	public void addWinCounterTest(){
		User u = new User("",2,0,null);
		Statistik s = new Statistik();
		s.addWinCounter(u);
		assertTrue(u.getWinCounter()==3);
	}
	@Test
	public void loseWinCounterTest(){
		User u= new User("",2,0,null);
		Statistik s= new Statistik();
		s.addLoseCounter(u);
		assertTrue(u.getWinCounter()==2);
	}

}
