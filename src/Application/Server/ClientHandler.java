package Application.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import Application.Game;
import Application.Player;
import Application.Tower;
import Domain.Land;
import Domain.Statistik.Matchmaking;

public class ClientHandler implements Runnable {

	BufferedReader reader;
	Socket client;
	PrintWriter pw;
	Server server;
	Matchmaking m;
	Game actualGame;
	Player actualPlayer;
	String advantageString;
	
	public ClientHandler(Socket client, Server server, PrintWriter pw) {
		try {
			this.pw = pw;
			this.server = server;
			this.client = client;
			reader = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void login(String[] stringArray, BufferedReader br) {
		String username = stringArray[1];
		String passwort = stringArray[2];
		try {
			String line = br.readLine();
			while (line != null) {
				Iterator it = server.getUserList().iterator();

				while (it.hasNext()) {
					User user = (User) it.next();
					if (user == null) {
						continue;
					} else if (user.getUserName().equals(username)) {
						server.answerToClient("userLoggedIn", pw);
						return;
					}
				}
				if (username.equals(line)) {
					line = br.readLine();
					if (passwort.equals(line)) {
						server.answerToClient("LogIn-" + username, pw);
						User user = new User(username, Integer.parseInt(br
								.readLine()), Integer.parseInt(br.readLine()),
								pw);
						server.getUserList().add(user);
						System.out.println(user.getUserName());
						return;

					} else {
						server.answerToClient("wrongPw", pw);
						return;
					}
				}
				line = br.readLine();
			}
			server.answerToClient("wrongUsername", pw);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	void register(String stringArray[], BufferedReader br) {
		String username = stringArray[1];
		String passwort = stringArray[2];
		try {
			String line = br.readLine();
			while (line != null) {
				if (username.equals(line)) {
					server.answerToClient("registerWrongUsername", pw);
					return;
				}
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Storage s = new Storage();
		s.register(username, passwort);
		server.answerToClient("register", pw);
	}

	public void run() {
		String message;
		String[] stringArray;
		m=server.getMatchmaking();
		try {
			while ((message = reader.readLine()) != null) {
				stringArray = message.split("-");
				String command = stringArray[0];
				String username="";
				if(stringArray.length>1){
					username = stringArray[1];
				}
				BufferedReader br = new BufferedReader(new FileReader(new File(
						"./data/userdata.txt")));
				if (command.equals("LogIn")) {
					login(stringArray, br);
				} else if (command.equals("register")) {
					register(stringArray, br);
				} else if (command.equals("Chat")) {
					server.deliverToAllClients("Chat-" + username + "-"
							+ stringArray[2]);
				} else if(command.equals("LogOut")){
					server.deleteUser(username);
				} else if(command.equals("MultiPlayerGame")){
					System.out.println("Neuer Spieler!");
					m.addPlayerqueue(username);
					if(m.getPlayerQueue().size()>2)
					{	
						System.out.println("spielstart!");
						ArrayList<User> startingPlayers= m.startGame(3);
						actualGame= new Game(startingPlayers, server);
						server.getGameList().add(actualGame);
					}

				}
				else if(command.equals("SinglePlayerGame")){
					Game newSinglePlayerGame = new Game(server.getUserByUserName(stringArray[1]), server);
					server.getGameList().add(newSinglePlayerGame);
				}
				else if(command.equals("chooseLand")){
					actualGame = server.getGameByUsername(username);
					System.out.println(actualGame);
					Land land = actualGame.getLandParser().getStartLand(stringArray[2]);
					actualPlayer = actualGame.getPlayerByUsername(username);
					
					actualGame.getLandParser().repositionStartLands(stringArray[2]);	
					actualPlayer.chooseLand(land);
					actualPlayer.useArmy(Integer.parseInt(stringArray[3]));
					land.setArmy(Integer.parseInt(stringArray[3]));
					if(actualGame.getPlayers().get(1).getAI()){
						server.deliverToSinglePlayer(actualGame.getPlayers().get(0), actualGame.getPlayerByUsername(username).updateToString());
					}
					else{
						server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayerByUsername(username).updateToString());
					}
					Player nextPlayer = actualGame.getPlayerByID((actualPlayer.getId()+1)%actualGame.getPlayers().size());
					if(nextPlayer.getAI()){
						for(int i=1;i<4;i++){
							actualGame.getPlayers().get(i).chooseStartLandAI(actualGame.getLandParser() ,actualGame.getLandParser().getStartLands());
							server.deliverToSinglePlayer(actualGame.getPlayerByID(0), actualGame.getPlayers().get(i).updateToString());
							System.out.println(actualGame.getPlayers().get(i).getLand());
						}
						server.deliverToSinglePlayer(actualGame.getPlayers().get(0), actualGame.getLandParser().startLandsToString());
					}
					else{
						server.deliverToSinglePlayer(nextPlayer, actualGame.getLandParser().startLandsToString());
					}
				}
				else if(command.equals("finishedChooseLand")){
					actualGame=server.getGameByUsername(username);
					if(actualGame.getPlayerByID(1).getAI()){
						server.deliverToSinglePlayer(actualGame.getPlayers().get(0), "EventCards-" +actualGame.getEvent().toString());
						server.deliverToSinglePlayer(actualGame.getPlayers().get(0), actualGame.getAdvantageOrderString());
						server.deliverToSinglePlayer(actualGame.getPlayers().get(0), actualGame.getActionOrderString());
						server.deliverToSinglePlayer(actualGame.getPlayers().get(0), "Bid");
					} else{
						server.deliverToPlayers(actualGame.getPlayers(), "EventCards-" +actualGame.getEvent().toString());
						server.deliverToPlayers(actualGame.getPlayers(), actualGame.getAdvantageOrderString());
						server.deliverToPlayers(actualGame.getPlayers(), actualGame.getActionOrderString());
						server.deliverToPlayers(actualGame.getPlayers(), "Bid");
					}
				}
				else if(command.equals("Bid")){
					actualGame=server.getGameByUsername(username);
					actualGame.getPlayerByUsername(username).setBidPlaced(true);
					actualGame.getPlayerByUsername(username).placeBid(Integer.parseInt(stringArray [2]));
					System.out.println("Gebot Player: " + username + " " + actualGame.getPlayerByUsername(username).getBid());
					if(actualGame.getPlayerByID(1).getAI()){
						for(int i = 1; i<4; i++){
							actualGame.getPlayerByID(i).placeBidAI();
							actualGame.getPlayerByID(i).setBidPlaced(true);
						}
					}
					if(actualGame.checkBidPlaced()==true){
						if(actualGame.getPlayerByID(1).getAI()){
							List<Player> tempList = actualGame.getBidOrder();
							System.out.println(tempList.get(0).getUsername()+tempList.get(1).getUsername()+tempList.get(0).getUsername()+tempList.get(1).getBid()+tempList.get(2).getUsername()+tempList.get(3).getUsername());
							for(int i = 0; i<actualGame.getPlayers().size(); i++){
								if(tempList.get(i).getAI()){
									int tempNum = tempList.get(i).chooseAdvantageAI(actualGame.getAdvantagePlatelate());
									String tempAdvantage = actualGame.getAdvantagePlatelate().get(tempNum);
									actualGame.getPlayerByID(i).setPlayerOrder(actualGame.getAdvantageOrderIndex(tempAdvantage));
									if(tempAdvantage.equals("thaler")){
										actualGame.getPlayerByID(tempList.get(i).getId()).advantageThaler();
										server.deliverToPlayers(actualGame.getPlayers(), "ChosenAdvantagePlatelate-"+ tempList.get(i).getUsername() +"-"+"thaler");
									}
									else if(tempAdvantage.equals("army")){
										actualGame.getPlayerByID(tempList.get(i).getId()).advantageArmy();
										server.deliverToPlayers(actualGame.getPlayers(), "ChosenAdvantagePlatelate-"+ tempList.get(i).getUsername() +"-"+"army");
									}
									else if(tempAdvantage.equals("attack")){
										actualGame.getPlayerByID(tempList.get(i).getId()).advantageAttack();
										server.deliverToPlayers(actualGame.getPlayers(), "ChosenAdvantagePlatelate-"+ tempList.get(i).getUsername() +"-"+"attack");
									}
									else if(tempAdvantage.equals("crop")){
										actualGame.getPlayerByID(tempList.get(i).getId()).advantageCrop();
										server.deliverToPlayers(actualGame.getPlayers(), "ChosenAdvantagePlatelate-"+ tempList.get(i).getUsername() +"-"+"crop");
									}
									else if(tempAdvantage.equals("defend")){
										actualGame.getPlayerByID(tempList.get(i).getId()).advantageDefence();
										server.deliverToPlayers(actualGame.getPlayers(), "ChosenAdvantagePlatelate-"+ tempList.get(i).getUsername() +"-"+"defend");
									}
									for(int j=0;j<actualGame.getAdvantagePlatelate().size();j++){
										if(actualGame.getAdvantagePlatelate().get(j).equals(tempAdvantage)){
											actualGame.getAdvantagePlatelate().remove(j);
										}
									}
									
								}
								else{
									advantageString="ChooseAdvantagePlatelate-0";
									for(int j=0;j<actualGame.
											getAdvantagePlatelate().size();j++){
										advantageString+="-" + actualGame.getAdvantagePlatelate().get(j);
									}
									System.out.println(advantageString);
									server.deliverToSinglePlayer(actualGame.getBidOrder().get(0), advantageString);
								}
							}
						}
						else{
							advantageString="ChooseAdvantagePlatelate-0";
							for(int i=0;i<actualGame.getAdvantagePlatelate().size();i++){
								advantageString+="-" + actualGame.getAdvantagePlatelate().get(i);
							}
							System.out.println(advantageString);
							server.deliverToSinglePlayer(actualGame.getBidOrder().get(0), advantageString);
						}
						
					}
				}
				else if(command.equals("ChosenAdvantagePlatelate")){
					actualGame =server.getGameByUsername(username);
					actualPlayer=actualGame.getPlayerByUsername(username);
					int i= Integer.parseInt(stringArray[3]);
					actualPlayer.setPlayerOrder(actualGame.getAdvantageOrderIndex(stringArray[2]));
					if(stringArray[2].equals("thaler")){
						actualPlayer.advantageThaler();
					}
					else if(stringArray[2].equals("army")){
						actualPlayer.advantageArmy();
					}
					else if(stringArray[2].equals("attack")){
						actualPlayer.advantageAttack();
					}
					else if(stringArray[2].equals("crop")){
						actualPlayer.advantageCrop();
					}
					else if(stringArray[2].equals("defend")){
						actualPlayer.advantageDefence();
					}
					
					
					for(int j=0;j<actualGame.getAdvantagePlatelate().size();j++){
						if(actualGame.getAdvantagePlatelate().get(j).equals(stringArray[2])){
							actualGame.getAdvantagePlatelate().remove(j);
						}
					}
					server.deliverToPlayers(actualGame.getPlayers(), "ChosenAdvantagePlatelate-"+stringArray[1]+"-"+stringArray[2]);
					advantageString="ChooseAdvantagePlatelate-"+ (i+1);
					for(int j=0;j<actualGame.getAdvantagePlatelate().size();j++){
						advantageString+="-"+actualGame.getAdvantagePlatelate().get(j);
					}
					if(i<actualGame.getPlayers().size()-1){
						server.deliverToSinglePlayer(actualGame.getBidOrder().get(i+1), advantageString);
					}
					else{
						actualGame.updatePlayerOrder(actualGame.getPlayers());
						server.deliverToPlayers(actualGame.getPlayers(), "ActiveEvent-" + actualGame.getEvent().getActiveEvent());
					}
				}				
				else if(command.equals("ActionPlanned")){
					actualGame=server.getGameByUsername(username);
					System.out.println(username);
			
					actualGame.
					getPlayerByUsername(username).
					setActionPlanned(true);
					actualGame.getPlayerByUsername(username).setActionMove(actualGame.getLandParser().getLand(stringArray[2]));
					actualGame.getPlayerByUsername(username).setActionArmy3(actualGame.getLandParser().getLand(stringArray[3]));
					actualGame.getPlayerByUsername(username).setActionArmy5(actualGame.getLandParser().getLand(stringArray[4]));
					actualGame.getPlayerByUsername(username).setActionBuildChurch(actualGame.getLandParser().getLand(stringArray[5]));
					actualGame.getPlayerByUsername(username).setActionBuildPalace(actualGame.getLandParser().getLand(stringArray[6]));
					actualGame.getPlayerByUsername(username).setActionBuildMansion(actualGame.getLandParser().getLand(stringArray[7]));
					actualGame.getPlayerByUsername(username).setActionCrop(actualGame.getLandParser().getLand(stringArray[8]));
					actualGame.getPlayerByUsername(username).setActionThaler(actualGame.getLandParser().getLand(stringArray[9]));
					actualGame.getPlayerByUsername(username).setActionFightA(actualGame.getLandParser().getLand(stringArray[10]));
					actualGame.getPlayerByUsername(username).setActionFightB(actualGame.getLandParser().getLand(stringArray[11]));
					
					System.out.println("Move: " + actualGame.getLandParser().getLand(stringArray[2]).getName());
					System.out.println("Army3: " + actualGame.getLandParser().getLand(stringArray[3]).getName());
					System.out.println("Army5: " + actualGame.getLandParser().getLand(stringArray[4]).getName());
					System.out.println("Church: " + actualGame.getLandParser().getLand(stringArray[5]).getName());
					System.out.println("Palace: " + actualGame.getLandParser().getLand(stringArray[6]).getName());
					System.out.println("Mansion: " + actualGame.getLandParser().getLand(stringArray[7]).getName());
					System.out.println("Crop: " + actualGame.getLandParser().getLand(stringArray[8]).getName());
					System.out.println("Thaler: " + actualGame.getLandParser().getLand(stringArray[9]).getName());
					System.out.println("FightA: " + actualGame.getLandParser().getLand(stringArray[10]).getName());
					System.out.println("FightB: " + actualGame.getLandParser().getLand(stringArray[11]).getName());
					
//					actualGame.getPlayerByUsername(username)
					
					
					if(actualGame.getPlayerByID(1).getAI()){
						for(int i = 1; i<4; i++){
							actualGame.getPlayerByID(i).planActionAI();
							List<Land> tempLand = actualGame.getPlayerByID(i).getPlanned();
							for(int j = 0; j< tempLand.size(); j++){
								actualGame.getPlayerByID(i).randomActionPLannerAI(tempLand.get(j));
							}
						}
						for(int i=0;i<10;i++){
							for(int j=0;j<actualGame.getPlayers().size();j++){
								if(actualGame.getActionOrder()[i].equals("army3")){
									actualGame.getAction().actionArmy3(actualGame.getPlayerByPlayerOrder(j));
									server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
								}
								if(actualGame.getActionOrder()[i].equals("army5")){
									System.out.println("Army5 auf " + actualGame.getPlayerByPlayerOrder(j).getActionArmy5().getName());
									actualGame.getAction().actionArmy5(actualGame.getPlayerByPlayerOrder(j));
									server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
								}
								if(actualGame.getActionOrder()[i].equals("crop")){
									actualGame.getAction().actionCrop(actualGame.getPlayerByPlayerOrder(j), actualGame.getPlayers());
									server.deliverToPlayers(actualGame.getPlayers(), "updateSchaleFarmer-"+ actualGame.getTower().getSchaleFarmer());
									server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
								}
								if(actualGame.getActionOrder()[i].equals("thaler")){
									actualGame.getAction().actionThaler(actualGame.getPlayerByPlayerOrder(j), actualGame.getPlayers());
									server.deliverToPlayers(actualGame.getPlayers(), "updateSchaleFarmer-"+ actualGame.getTower().getSchaleFarmer());
									server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
								}	
								if(actualGame.getActionOrder()[i].equals("palace")){
									actualGame.getAction().actionBuildPalace(actualGame.getPlayerByPlayerOrder(j));
									server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
								}	
								if(actualGame.getActionOrder()[i].equals("mansion")){
									actualGame.getAction().actionBuildMansion(actualGame.getPlayerByPlayerOrder(j));
									server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
								}
								if(actualGame.getActionOrder()[i].equals("church")){
									actualGame.getAction().actionBuildChurch(actualGame.getPlayerByPlayerOrder(j));
									server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
								}
								if(actualGame.getActionOrder()[i].equals("move")){
									server.deliverToSinglePlayer(actualGame.getPlayerByPlayerOrder(j), "chooseMoveLand");
								}
								if(actualGame.getActionOrder()[i].equals("fightA")){
									server.deliverToSinglePlayer(actualGame.getPlayerByPlayerOrder(j), "chooseFight-" + actualGame.getPlayerByPlayerOrder(j).getActionFightA()+"-"+actualGame.getPlayerByPlayerOrder(j).getActionFightA().getArmy());
								}
								if(actualGame.getActionOrder()[i].equals("fightB")){
									server.deliverToSinglePlayer(actualGame.getPlayerByPlayerOrder(j), "chooseFight-" + actualGame.getPlayerByPlayerOrder(j).getActionFightB());
								}
							}
						}
						actualGame.increasRoundNum();
						if(actualGame.getRoundNum()==4){
							actualGame.getEvent().chooseActiveEvent();
							server.deliverToPlayers(actualGame.getPlayers(), "ActiveEvent-" + actualGame.getEvent().getActiveEvent());
							actualGame.getTower().winterRound(actualGame.getPlayers());
							
							for(int i=0;i<actualGame.getPlayers().size();i++){
								actualGame.getPlayers().get(i).advantageNeutralize();
								actualGame.getPlayers().get(i).nullifyActions();
							}
							actualGame.increasRoundNum();
							actualGame.getEvent().chooseActiveEvent();
							actualGame.getAction().setActionOrder();
							actualGame.getAction().setAdvantageOrder();
							server.deliverToPlayers(actualGame.getPlayers(), actualGame.getAdvantageOrderString());
							server.deliverToPlayers(actualGame.getPlayers(), actualGame.getActionOrderString());
							server.deliverToPlayers(actualGame.getPlayers(), "Bid");
							
						}
						else if(actualGame.getRoundNum()==8){
							actualGame.getEvent().chooseActiveEvent();
							server.deliverToPlayers(actualGame.getPlayers(), "ActiveEvent-" + actualGame.getEvent().getActiveEvent());
							actualGame.getTower().winterRound(actualGame.getPlayers());
							List<Player> winners = actualGame.getWinners();
							
							Storage s = new Storage();
							
							
						}
						else{
							for(int i=0;i<actualGame.getPlayers().size();i++){
								actualGame.getPlayers().get(i).advantageNeutralize();
								actualGame.getPlayers().get(i).nullifyActions();
							}
							actualGame.getEvent().chooseActiveEvent();
							actualGame.getAction().setActionOrder();
							actualGame.getAction().setAdvantageOrder();
							server.deliverToPlayers(actualGame.getPlayers(), actualGame.getAdvantageOrderString());
							server.deliverToPlayers(actualGame.getPlayers(), actualGame.getActionOrderString());
							server.deliverToPlayers(actualGame.getPlayers(), "Bid");
						}
						for(int i=0;i<actualGame.getPlayers().size();i++){
							actualGame.getPlayers().get(i).setActionPlanned(false);
						}
					
				
						
						
					}
					else{
						if(actualGame.checkExecuteAction()==true){
							for(int i=0;i<10;i++){
								for(int j=0;j<actualGame.getPlayers().size();j++){
									if(actualGame.getActionOrder()[i].equals("army3")){
										System.out.println(actualGame.getPlayerByPlayerOrder(j));
										actualGame.getAction().actionArmy3(actualGame.getPlayerByPlayerOrder(j));
										server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
									}
									if(actualGame.getActionOrder()[i].equals("army5")){
										System.out.println("Army5: " + actualGame.getPlayerByPlayerOrder(j).updateToString());
										actualGame.getAction().actionArmy5(actualGame.getPlayerByPlayerOrder(j));
										server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
									}
									if(actualGame.getActionOrder()[i].equals("crop")){
										actualGame.getAction().actionCrop(actualGame.getPlayerByPlayerOrder(j), actualGame.getPlayers());
										server.deliverToPlayers(actualGame.getPlayers(), "updateSchaleFarmer-"+ actualGame.getTower().getSchaleFarmer());
										server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
									}
									if(actualGame.getActionOrder()[i].equals("thaler")){
										actualGame.getAction().actionThaler(actualGame.getPlayerByPlayerOrder(j), actualGame.getPlayers());
										server.deliverToPlayers(actualGame.getPlayers(), "updateSchaleFarmer-"+ actualGame.getTower().getSchaleFarmer());
										server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
									}	
									if(actualGame.getActionOrder()[i].equals("palace")){
										actualGame.getAction().actionBuildPalace(actualGame.getPlayerByPlayerOrder(j));
										server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
									}	
									if(actualGame.getActionOrder()[i].equals("mansion")){
										actualGame.getAction().actionBuildMansion(actualGame.getPlayerByPlayerOrder(j));
										server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
									}
									if(actualGame.getActionOrder()[i].equals("church")){
										actualGame.getAction().actionBuildChurch(actualGame.getPlayerByPlayerOrder(j));
										server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayers().get(j).updateToString());
									}
									if(actualGame.getActionOrder()[i].equals("move")){
										server.deliverToSinglePlayer(actualGame.getPlayerByPlayerOrder(j), "chooseMoveLand");
									}
									if(actualGame.getActionOrder()[i].equals("fightA")){
										server.deliverToSinglePlayer(actualGame.getPlayerByPlayerOrder(j), "chooseFightA");
									}
									if(actualGame.getActionOrder()[i].equals("fightB")){
										server.deliverToSinglePlayer(actualGame.getPlayerByPlayerOrder(j), "chooseFightB");
									}
								}
							}
						}
						actualGame.increasRoundNum();
						if(actualGame.getRoundNum()==4){
							actualGame.getEvent().chooseActiveEvent();
							server.deliverToPlayers(actualGame.getPlayers(), "ActiveEvent-" + actualGame.getEvent().getActiveEvent());
							actualGame.getTower().winterRound(actualGame.getPlayers());
							
							for(int i=0;i<actualGame.getPlayers().size();i++){
								actualGame.getPlayers().get(i).advantageNeutralize();
								actualGame.getPlayers().get(i).nullifyActions();
							}
							actualGame.increasRoundNum();
							actualGame.getEvent().chooseActiveEvent();
							actualGame.getAction().setActionOrder();
							actualGame.getAction().setAdvantageOrder();
							server.deliverToPlayers(actualGame.getPlayers(), actualGame.getAdvantageOrderString());
							server.deliverToPlayers(actualGame.getPlayers(), actualGame.getActionOrderString());
							server.deliverToPlayers(actualGame.getPlayers(), "Bid");
							
						}
						else if(actualGame.getRoundNum()==8){
							actualGame.getEvent().chooseActiveEvent();
							server.deliverToPlayers(actualGame.getPlayers(), "ActiveEvent-" + actualGame.getEvent().getActiveEvent());
							actualGame.getTower().winterRound(actualGame.getPlayers());
							List<Player> winners = actualGame.getWinners();
							
							Storage s = new Storage();
							
							
						}
						else{
							for(int i=0;i<actualGame.getPlayers().size();i++){
								actualGame.getPlayers().get(i).advantageNeutralize();
								actualGame.getPlayers().get(i).nullifyActions();
							}
							actualGame.getEvent().chooseActiveEvent();
							actualGame.getAction().setActionOrder();
							actualGame.getAction().setAdvantageOrder();
							server.deliverToPlayers(actualGame.getPlayers(), actualGame.getAdvantageOrderString());
							server.deliverToPlayers(actualGame.getPlayers(), actualGame.getActionOrderString());
							server.deliverToPlayers(actualGame.getPlayers(), "Bid");
						}
						for(int i=0;i<actualGame.getPlayers().size();i++){
							actualGame.getPlayers().get(i).setActionPlanned(false);
						}
					}
					
				}
				else if(command.equals("moveLand")){
					actualGame.getAction().actionMove(actualGame.getPlayerByUsername(username), actualGame.getPlayerByUsername(username).getLandByName(stringArray[2]), Integer.parseInt(stringArray[3]));
					server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayerByUsername(username).updateToString());
				}
				else if(command.equals("fightA")){
					actualGame.getAction().actionFightA(actualGame.getPlayerByUsername(username), actualGame.getPlayerByUsername(username).getLandByName(stringArray[2]), Integer.parseInt(stringArray[3]), actualGame.getPlayers());
					server.deliverToPlayers(actualGame.getPlayers(), "updateSchaleFarmer-"+ actualGame.getTower().getSchaleFarmer());
					server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayerByUsername(username).updateToString());
				}
				else if(command.equals("fightB")){
					actualGame.getAction().actionFightB(actualGame.getPlayerByUsername(username), actualGame.getPlayerByUsername(username).getLandByName(stringArray[2]), Integer.parseInt(stringArray[3]), actualGame.getPlayers());
					server.deliverToPlayers(actualGame.getPlayers(), "updateSchaleFarmer-"+ actualGame.getTower().getSchaleFarmer());
					server.deliverToPlayers(actualGame.getPlayers(), actualGame.getPlayerByUsername(username).updateToString());
				}
				else {
					System.out.println(message);
				}
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
