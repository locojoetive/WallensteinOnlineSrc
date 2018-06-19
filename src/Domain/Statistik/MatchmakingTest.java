package Domain.Statistik;

import static org.junit.Assert.*;


import org.junit.Test;

import Application.Server.Server;
import Application.Server.User;

public class MatchmakingTest {
	@Test
	public void addPlayertoQueQue(){
		Server s= new Server();
		Matchmaking m= new Matchmaking(s);
		
		
	}


	@Test
	public void startGameTest(){
		Server s = new Server();
		Matchmaking m = new Matchmaking(s);
		User u= new User("p1",2,0,null);
		User u1= new User("p2",3,0,null);
		User u2= new User("p3",4,0,null);
		
		Matchmaking.queue.add(u);
		Matchmaking.queue.add(u1);
		Matchmaking.queue.add(u2);
		
		m.startGame(2);
		assertTrue(m.getStartingPlayers().get(0).getUserName().equals("p1"));
		
	}

}
