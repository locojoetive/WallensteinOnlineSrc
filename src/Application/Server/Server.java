

package Application.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import Application.Game;
import Application.Player;
import Domain.Statistik.Matchmaking;

public class Server{

	ServerSocket server;
	private ArrayList<User> list_user;
	private ArrayList<Game> list_game;
	private Matchmaking m;
	// ///////////////////////////////////////////////////////

	public static void main(String[] args)

	{
		Server s = new Server();
		if (s.runServer()) {
			s.listenToClients();
		}

		else {
			// Do nothing
		}
	}

	// ///////////////////////////////////////////////////////


	public ArrayList<User> getUserList(){
		return list_user;
	}
	
	public ArrayList<Game> getGameList(){
		return list_game;
	}
	public Matchmaking getMatchmaking(){
		return m;
	}
		
	public Game getGameByUsername(String userName){
		for(int i=0; i<list_game.size();i++){
			for(int j=0;j<list_game.get(i).getPlayers().size();j++){
				String tempUserName =list_game.get(i).getPlayers().get(j).getUsername();
				if(userName.equals(tempUserName)){
					return list_game.get(i);
				}
			}
		}
		return null;
	}
	public User getUserByUserName(String username){
		for (int i=0; i<list_user.size();i++){
			if(list_user.get(i).getUserName().equals(username)){
				return list_user.get(i);
			}
		}
		return null;
	}

	public void deleteUser(String username)
	{
		for(int i=0;i<list_user.size();i++)
		{
			if(username.equals(list_user.get(i).getUserName()))
			{
				list_user.remove(i);
			}
		}
	}
	public boolean runServer() {
		try {
			server = new ServerSocket(5555);
			System.out.println("Der Server wurde gestartet: "
					+ InetAddress.getLocalHost().toString());
			JOptionPane.showMessageDialog(null, "Ihre Ip-Adresse lautet: " + InetAddress.getLocalHost().toString());

			list_user = new ArrayList<User>();
			list_game = new ArrayList<Game>();
			m = new Matchmaking(this);
			return true;

		} catch (IOException e) {
			System.out.println("Server konnte nicht gestartet werden!");
			e.printStackTrace();
			return false;
		}
	}

	// ///////////////////////////////////////////////////////

	public void listenToClients() {
		while (true) {
			try {
				Socket client = server.accept();
				PrintWriter writer = new PrintWriter(client.getOutputStream());
				Thread clientThread = new Thread(new ClientHandler(client,
						this, writer));
				clientThread.start();
			}

			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ///////////////////////////////////////////////////////
	public void answerToClient(String message, PrintWriter pw) {
		{
			pw.println(message);
			pw.flush();

		}
	}
	public void deliverToSingleUser(String username, String message){
		Iterator<User> it = list_user.iterator();
		while (it.hasNext()) {
			User user =(User) it.next();			
			if(username.equals(user.getUserName())){
				user.getPrintwriter().println(message);
				user.getPrintwriter().flush();
			}	
		}
	}
	public void stringToUsers(ArrayList<User> users, String message){
		for(int i=0;i<users.size();i++)
		{
			users.get(i).getPrintwriter().println(message);
			users.get(i).getPrintwriter().flush();
		}
	}
	public void deliverToPlayers(ArrayList<Player> players, String message)
	{
		for(int i=0;i<players.size();i++){
			if(players.get(i).getAI()==false){
				players.get(i).getPrintWriter().println(message);
				players.get(i).getPrintWriter().flush();
			}
		}
			
	}
	public void deliverToSinglePlayer(Player player, String message){
		if(!player.getAI()){
			player.getPrintWriter().println(message);
			player.getPrintWriter().flush();
		}
	}
	
	public void deliverToAllClients(String message) {
		Iterator<User> it = list_user.iterator();
		while (it.hasNext()) {
			User user = (User) it.next();
			user.getPrintwriter().println(message);
			user.getPrintwriter().flush();
		}
	}
}