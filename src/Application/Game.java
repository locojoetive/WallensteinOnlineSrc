package Application;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.RoundEnvironment;

import Application.Server.Server;
import Application.Server.User;
import Domain.Eventcards;
import Domain.Landparser;

public class Game {
	
	private Server server;
	private ArrayList<Player> players;
	private Tower tower;
	private Eventcards event;
	private Landparser lp;
	private Action action;
	private String[] actionOrder;
	private int roundNum;
	String advantageOrderString = "AdvantageOrder";
	String actionOrderString = "ActionOrder";
	String[] advantageOrder;
	ArrayList<String> advantagePlateLate;
	
	public ArrayList<Player> getPlayers(){
			return players;
		}
	
	public String getActionOrderString(){
		return actionOrderString;
	}
	
	public String getAdvantageOrderString(){
		return advantageOrderString;
	}
	public Eventcards getEvent(){
		return event;
	}
	public Game(List<User> user, Server server){
		players = new ArrayList<Player>();
		this.server=server;
		tower = new Tower();
		event = new Eventcards();
		lp = new Landparser();
		if(user.size()==3){
			lp.remove3PlayerLands();
		}
		
		action = new Action(tower, server);
		actionOrder= new String[10];
		advantageOrder = new String[5];
		for(int i = 0; i<user.size(); i++){
			players.add(new Player(user.get(i).getUserName(), user.get(i).getPrintwriter()));
			players.get(i).thalerCount(user.size());
			System.out.println("spieler erstellt: "+user.get(i).getUserName());
		}
		
		tower.initializeTower(players);
		
		//action planning + bid on advantage
		event.initializeEventcards();
		event.pickEvents();
		
		event.chooseActiveEvent();
		
		String startGameString="InitGame";
		server.deliverToPlayers(players, startGameString);
		
		
		for(int i=0;i<players.size();i++)
		{
			startGameString=players.get(i).toString();
			server.deliverToPlayers(players, startGameString);
		}
		server.deliverToPlayers(players, "StartGame");
		
		for(int i=0;i<players.size();i++){
			server.deliverToPlayers(players, players.get(i).updateToString());
		}
		Player.setID(0);
		
		lp.initStartLands();
		
		actionOrder = action.setActionOrder();
		for(int i=0;i<10;i++)
		{
			actionOrderString+="-"+actionOrder[i];
		}
		advantageOrder = action.setAdvantageOrder();
		for(int i = 0; i<5; i++){
			advantageOrderString+="-" + advantageOrder[i];
		}
		server.deliverToSinglePlayer(players.get(0), lp.startLandsToString());
		roundNum = 1;
		advantagePlateLate = new ArrayList<String>(Arrays.asList(new String[] {"crop", "thaler", "army", "defend", "attack"}));
	}
	
	public Game(User user, Server server){
		players = new ArrayList<Player>();
		this.server=server;
		tower = new Tower();
		
		event = new Eventcards();
		lp = new Landparser();
		action = new Action(tower, server);
		actionOrder= new String[10];
		advantageOrder = new String[5];
		players.add(new Player(user.getUserName(), user.getPrintwriter()));
		players.add(new Player("AI1", 1, true));
		players.add(new Player("AI2", 2, true));
		players.add(new Player("AI3", 3, true));
		tower.initializeTower(players);

		//action planning + bid on advantage
		event.initializeEventcards();
		event.pickEvents();
		
		event.chooseActiveEvent();
		
		String startGameString="InitGame";
		server.deliverToSinglePlayer(players.get(0), startGameString);
		
		
		for(int i=0;i<players.size();i++)
		{
			startGameString=players.get(i).toString();
			server.deliverToSinglePlayer(players.get(0), startGameString);
		}
		server.deliverToSinglePlayer(players.get(0), "StartGame");
		System.out.println("StartGame");
		
		for(int i=0;i<players.size();i++){
			server.deliverToSinglePlayer(players.get(0), players.get(i).updateToString());
		}
		Player.setID(0);
		
		lp.initStartLands();
		
		actionOrder = action.setActionOrder();
		for(int i=0;i<10;i++)
		{
			actionOrderString+="-"+actionOrder[i];
		}
		advantageOrder = action.setAdvantageOrder();
		for(int i = 0; i<5; i++){
			advantageOrderString+="-" + advantageOrder[i];
		}
		server.deliverToSinglePlayer(players.get(0), lp.startLandsToString());
		System.out.println(lp.startLandsToString());
		roundNum = 1;
		advantagePlateLate = new ArrayList<String>(Arrays.asList(new String[] {"crop", "thaler", "army", "defend", "attack"}));
	}
	public int getAdvantageOrderIndex(String advantage){
		for(int i=0;i<advantageOrder.length;i++){
			if(advantageOrder[i].equals(advantage)){
				return i;
			}
		}
		return 0;
	}
	public Landparser getLandParser(){
		return lp;
	}
	
	public Player getPlayerByUsername(String username){
		for(int i=0;i<players.size();i++){
			if(players.get(i).getUsername().equals(username)){
				return players.get(i);
			}
		}
		return null;
	}
	public Player getPlayerByID(int id){
		for(int i=0;i<players.size();i++){
			if(players.get(i).getId()==id){
				return players.get(i);
			}
		}
		return null;
	}
	public boolean checkExecuteAction(){
		for(int i=0;i<players.size();i++)
		{
			if(players.get(i).getActionPlanned()==false)
			{
				return false;
			}	
		}
		return true;
	}
	public boolean checkBidPlaced(){
		for(int i=0;i<players.size();i++){
			if(players.get(i).getBidPlaced()==false){
				return false;
			}
		}
		return true;
	}
	
	public List<Player> getBidOrder(){
		Player tempPlayer;
		List<Player> playerList=new ArrayList<Player>();
		for(int i = 0; i<players.size(); i++){
			playerList.add(players.get(i));
		}
		for(int i = 0; i<players.size(); i++){
			for(int j = 0; j<players.size()-1; j++){
				if(players.get(j).getBid()<players.get(j+1).getBid()){
					tempPlayer = players.get(j);
					playerList.set(j, players.get(j+1));
					playerList.set(j+1, tempPlayer);
				}
			}		
		}
		return playerList;
	}
	public int getRoundNum(){
		return roundNum;
	}
	public void increasRoundNum(){
		roundNum++;
	}
	public ArrayList<String> getAdvantagePlatelate(){
		return advantagePlateLate;
	}
	
	
	public Player getPlayerByPlayerOrder(int playerOrder){
		for(int i=0;i<players.size();i++){
			System.out.println(players.get(i).getPlayerOrder());
			if(playerOrder==players.get(i).getPlayerOrder()){
				return players.get(i);
			}
		}
		return null;
	}
	public Action getAction(){
		return action;
	}
	public String[] getActionOrder(){
		return actionOrder;
	}
	
	public Tower getTower(){
		return tower;
	}
	
	public void updatePlayerOrder(ArrayList<Player> playerList){
		Player tempPlayer;
		for(int j = 0; j<players.size()-1; j++){
			if(players.get(j).getPlayerOrder()<players.get(j+1).getPlayerOrder()){
				tempPlayer = players.get(j);
				playerList.set(j, players.get(j+1));
				playerList.set(j+1, tempPlayer);
			}
		}		
		for(int i=0;i<playerList.size();i++){
			playerList.get(i).setPlayerOrder(i);
		}
	}
	
	
	public List<Player> getWinners(){
		List<Player> winners = new ArrayList<Player>();
		Player tempPlayer;
		for(int i = 1; i<players.size(); i++){
			for(int j = 0; j<players.size(); j++){
				if(players.get(j).getAchievementPoints()<players.get(j+1).getAchievementPoints()){
					tempPlayer = players.get(j);
					players.set(j, players.get(j+1));
					players.set(j+1, tempPlayer);
				}
			}
		}
		int j = 0;
		winners.add(players.get(0));
		while(players.get(j).getAchievementPoints() == players.get(j+1).getAchievementPoints()){
			winners.add(players.get(j+1));
			j++;
		}
		return winners;
	}
}
