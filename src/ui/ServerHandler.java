package ui;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import Application.Game;
import Application.Player;
import Application.Server.MusicPlayer;
import Domain.Land;

public class ServerHandler implements Runnable {
	Client c;
	LobbyWindow lw;
	ArrayList<Player> players;
	ArrayList<Land> lands;
	InGame ig;
	Register_LogIn_Window rlw;
	
	public ServerHandler(Client c){
			this.c=c;
	}
	
	public Player getPlayerByUsername(String username){
		for(int i=0;i<players.size();i++){
			if(players.get(i).getUsername().equals(username)){
				return players.get(i);
			}
		}
		return null;
	}
		
	public void run() {
		String message;
		String[] stringArray;
		
		try {
			message = c.reader.readLine();
			while ( message != null ){
				
				stringArray=message.split("-");
				String command = stringArray[0];
				if(command.equals("LogIn"))
				{
					c.getIPW().getRLW().hide();
					lw = new LobbyWindow(c);
					lw.getTextUsername().setText(stringArray[1]);
					
				}
				else if(command.equals("wrongPw"))
				{
					new MusicPlayer("");
				}
				else if(command.equals("wrongUsername"))
				{
					JOptionPane.showMessageDialog(null, "Falscher Nutzername!");
				}
				else if(command.equals("registerWrongUsername")){
					JOptionPane.showMessageDialog(null, "Nutzername existiert bereits!");
				}
				else if(command.equals("register"))
				{
					JOptionPane.showMessageDialog(null, "Nutzerkonto angelegt!");
				}
				else if(command.equals("userLoggedIn"))
				{
					JOptionPane.showMessageDialog(null, "Ihr Account ist bereits eingelogged!");
				}
				else if(command.equals("Chat")){
					String chatMessage = stringArray[2];
					char[] charArray;
					charArray = chatMessage.toCharArray();
					chatMessage="";
					for(int i=0; i<charArray.length; i++){
						if(charArray[i]=='_'){
							charArray[i]='-';	
						}
						chatMessage+=charArray[i];
					}
					lw.setChat(stringArray[1]+": " + chatMessage+ "\n");
				}
				else if(command.equals("InitGame")){
					players = new ArrayList<Player>();		
				}
				else if(command.equals("Player")){
					if(stringArray[2].equals("0")){
						players.add(new Player(stringArray[1], Integer.parseInt(stringArray[2])));
					}
					else{
						players.add(new Player(stringArray[1], Integer.parseInt(stringArray[2]), true));
					}
				}
				else if(command.equals("StartGame")){
					System.out.println("StartGame");
					ig = new InGame(players, lw.getTextUsername().getText(), c);
					ig.setVisible(true);
				} else if(command.equals("chooseLand")){
					for(int i=0;i<stringArray.length;i++){
					}
					ig.chooseYourAgPanel(stringArray[1], stringArray[2], stringArray[3]);
				}
				else if(command.equals("updatePlayer")){
					Player player = getPlayerByUsername(stringArray[1]);
					Player.updatePlayer(stringArray, player);

					ig.addPlayerStatus(players);
				}
				else if(command.equals("EventCards")){
					String[] tempArray=new String[stringArray.length-1];
					for(int i=1;i<stringArray.length;i++){
						tempArray[i-1]=stringArray[i];
						System.out.println("Events: " + tempArray[i-1]);
					}
					ig.placeEventCards(tempArray);
				}
				else if(command.equals("ActionOrder")){
					String[] tempArray=new String[stringArray.length-1];
					for(int i=1;i<stringArray.length;i++){
						tempArray[i-1]=stringArray[i];
						System.out.println("Action: " + tempArray[i-1]);
					}
					ig.placeAction(tempArray);
				}
				else if(command.equals("AdvantageOrder")){
					String[] tempArray=new String[stringArray.length-1];
					for(int i=1;i<stringArray.length;i++){
						tempArray[i-1]=stringArray[i];
						System.out.println("Advantage: " + tempArray[i-1]);
					}
					ig.setAdvPl(tempArray);
				}
				else if(command.equals("Bid")){
					ig.placeBid();
				}
				else if(command.equals("ChooseAdvantagePlatelate")){
					String[] tempArray = new String[stringArray.length-1];
					for(int i=1;i<stringArray.length;i++){
						tempArray[i-1]= stringArray[i];
						System.out.println(tempArray[i-1]);
					}
					ig.chooseYourAdvPl(tempArray);
				}
				else if(command.equals("ChosenAdvantagePlatelate")){
					String[] tempArray = new String[stringArray.length-1];
					for(int i=1;i<stringArray.length;i++){
						tempArray[i-1]= stringArray[i];
						System.out.println(tempArray[i-1]);
					}
					ig.markAdv(tempArray);
				}
				else if(command.equals("ActiveEvent")){
					ig.setEventCard(stringArray[1]);
				}
				else if(command.equals("updateSchaleFarmer")){
					ig.setFarmer(Integer.parseInt(stringArray[1]));
					ig.addPlayerStatus(players);
				}
				else if(command.equals("fightADone")){
					JOptionPane.showMessageDialog(null, "Action: 'Fight A' executed!");
				}
				else if(command.equals("fightAFailed")){
					JOptionPane.showMessageDialog(null, "Action: 'Fight A' failed!");
				}
				else if(command.equals("fightBDone")){
					JOptionPane.showMessageDialog(null, "Action: 'Fight B' executed!");
				}
				else if(command.equals("fightBFailed")){
					JOptionPane.showMessageDialog(null, "Action: 'Fight B' failed!");
				}
				else if(command.equals("palaceDone")){
					JOptionPane.showMessageDialog(null, "Action: 'Build Palace' executed!");
				}
				else if(command.equals("palaceFailed")){
					JOptionPane.showMessageDialog(null, "Action: 'Build Palace' failed!");
				}
				else if(command.equals("mansionDone")){
					JOptionPane.showMessageDialog(null, "Action: 'Build Mansion' executed!");
				}
				else if(command.equals("mansionFailed")){
					JOptionPane.showMessageDialog(null, "Action: 'Build Mansion' failed!");
				}
				else if(command.equals("churchDone")){
					JOptionPane.showMessageDialog(null, "Action: 'Build Church' executed!");
				}
				else if(command.equals("churchFailed")){
					JOptionPane.showMessageDialog(null, "Action: 'Build Church' failed!");
				}
				else if(command.equals("moveDone")){
					JOptionPane.showMessageDialog(null, "Action: 'Move Army' executed!");
				}
				else if(command.equals("moveFailed")){
					JOptionPane.showMessageDialog(null, "Action: 'Move Army' failed!");
				}
				else if(command.equals("army3Done")){
					JOptionPane.showMessageDialog(null, "Action: 'Build 3 Army' executed!");
				}
				else if(command.equals("army3Failed")){
					JOptionPane.showMessageDialog(null, "Action: 'Build 3 Army' failed!");
				}
				else if(command.equals("army5Done")){
					JOptionPane.showMessageDialog(null, "Action: 'Build 5 Army' executed!");
				}
				else if(command.equals("army5Failed")){
					JOptionPane.showMessageDialog(null, "Action: 'Build 5 Army' failed!");
				}
				else if(command.equals("cropDone")){
					JOptionPane.showMessageDialog(null, "Action: 'Collect Crop' executed!");
				}
				else if(command.equals("cropFailed")){
					JOptionPane.showMessageDialog(null, "Action: 'Collect Crop' failed!");
				}
				else if(command.equals("thalerDone")){
					JOptionPane.showMessageDialog(null, "Action: 'Collect 'taxes'' executed!");
				}
				else if(command.equals("thalerFailed")){
					JOptionPane.showMessageDialog(null, "Action: 'Collect 'taxes'' failed!");
				}
				else if(command.equals("chooseMoveLand")){
				}
				else if(command.equals("chooseFight")){
					System.out.println("chooseFight");
					String[] tempString = new String[2];
					tempString[0]=stringArray[1];
					tempString[1]=stringArray[2];
					ig.attack(tempString);
				}
				else if(command.equals("updateSchaleFarmer")){
					ig.setFarmer(Integer.parseInt(stringArray[1]));
				}
				else{
					System.out.println(message);
				}
				message = c.reader.readLine();
			}
		}

		catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
