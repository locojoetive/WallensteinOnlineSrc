package Domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventcardsTest {

	@Test
	public void initializeEventcardsTest() {
		Eventcards.initializeEventcards();
		assertTrue(Eventcards.eventcards[0].getCropLoss()==4);
	}
	@Test
	public void pickEventCardsTest(){
		Eventcards ec = new Eventcards();
		Eventcards.initializeEventcards();
		ec.pickEvents();
		assertTrue(Eventcards.activeEventcards[0]!=null);
		
	}
	@Test
	public void neutralizeEventTest(){
		Eventcards ec= new Eventcards();
		ec.pickEvents();
		ec.neutralizeEvents();
		assertTrue(Eventcards.activeEventcards[0]==null);
	}

}
