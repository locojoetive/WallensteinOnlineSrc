package Domain;

import java.util.Random;

import Application.Action;
import Application.Tower;

public class Eventcards {
	private String event;
	private int cropLoss;
	private boolean[] free = {true, true, true, true, true, true, true, true, true, true, true, true};
	private boolean[] activeFree = {true, true, true, true};
	Random rnd = new Random();
	static Eventcards[] eventcards = new Eventcards[12];
	static Eventcards[] activeEventcards = new Eventcards[4];
	static Eventcards activeEvent;
	
	public Eventcards(){
	}
	
	public static void initializeEventcards(){
		for(int i = 0; i<12; i++){
			eventcards[i] = new Eventcards();
		}
		eventcards[0].setEvent("noAttack");				//cannot attack countries with churches
		eventcards[0].setcropLoss(4);
		eventcards[1].setEvent("noAttack");
		eventcards[1].setcropLoss(3);
		eventcards[2].setEvent("minThaler");
		eventcards[2].setcropLoss(2);
		eventcards[3].setEvent("maxThaler");
		eventcards[3].setcropLoss(0);
		eventcards[4].setEvent("army");					//armyproduction reduced
		eventcards[4].setcropLoss(1);
		eventcards[5].setEvent("riot"); 				
		eventcards[5].setcropLoss(7); 					//riotmarker removed where mansions are
		eventcards[6].setEvent("riot");
		eventcards[6].setcropLoss(5);
		eventcards[7].setEvent("minCrop");
		eventcards[7].setcropLoss(3);
		eventcards[8].setEvent("maxCrop");
		eventcards[8].setcropLoss(4);
		eventcards[9].setEvent("palace");
		eventcards[9].setcropLoss(6);					//one army for palace
		eventcards[10].setEvent("farmer");				//two farmers instead of one
		eventcards[10].setcropLoss(3);
		eventcards[11].setEvent("palace");
		eventcards[11].setcropLoss(2);
	
	}
	
	public Eventcards[] pickEvents(){
		for (int j = 0; j < 4; j++){
			activeEventcards[j] = new Eventcards();
			int eventNum = rnd.nextInt(12);
			while (free[eventNum]==false){
				eventNum = rnd.nextInt(12);
			}
			activeEventcards[j] = eventcards[eventNum];
			free[eventNum] = false;
		}
		return activeEventcards;
	}

	public Eventcards chooseActiveEvent(){
		int eventNum = rnd.nextInt(4);
		while (activeFree[eventNum]==false){
			eventNum = rnd.nextInt(4);
		}
		activeFree[eventNum]=false;
		activeEvent = activeEventcards[eventNum];
		Action.event =activeEvent;
		Tower.event = activeEvent;
		return activeEvent;
	}
	
	
	public Eventcards getActiveEvent(){
		return activeEvent;
	}
	
	public void setEvent(String event){
		this.event = event;
	}
	public String getEvent(){
		return event;
	}
	
	public void setcropLoss(int crop){
		this.cropLoss = crop;
	}
	public int getCropLoss(){
		return cropLoss;
	}
	
	public void neutralizeEvents(){
		for (int i = 0; i<4; i++){			//neutralizing events for next round
			activeEventcards[i] = null;
			activeFree[i] = true;
		}
	}
	
	public String toString(){
		return activeEventcards[0].getEvent() + activeEventcards[0].getCropLoss() + "-" + activeEventcards[1].getEvent() + activeEventcards[1].getCropLoss()
				+ "-" + activeEventcards[2].getEvent() + activeEventcards[2].getCropLoss() + "-" + activeEventcards[3].getEvent() + activeEventcards[3].getCropLoss();
	}
}
