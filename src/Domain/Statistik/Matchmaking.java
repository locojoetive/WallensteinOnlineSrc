package Domain.Statistik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Application.Server.Server;
import Application.Server.User;


public class Matchmaking {

	public Matchmaking(Server server){
		this.server=server;
	}
	Server server;
	static ArrayList<User> queue = new ArrayList<User>();
	ArrayList<User> startingPlayers = new ArrayList <User>();
	
	
	public Server getServer() {
		return server;
	}


	
	public void addPlayerqueue(String userName){
		User user;
		for(int i=0;i<server.getUserList().size();i++)
		{
			if(userName.equals(server.getUserList().get(i).getUserName()))
			{
				user= server.getUserList().get(i);
				queue.add(user);
			}
		}
	}
	
	
	public ArrayList<User> getStartingPlayers() {
		return startingPlayers;
	}


	public void setStartingPlayers(ArrayList<User> startingPlayers) {
		this.startingPlayers = startingPlayers;
	}


	public void sort(){
	
		Collections.sort(queue, new Comparator<User>() {
		    @Override
		    public int compare(User user1, User user2) {
		        if (user1.getRanking() > user2.getRanking())
		            return 1;
		        if (user1.getRanking() < user2.getRanking())
		            return -1;
		        return 0;
		    }
		});
		}
	
	public ArrayList<User> startGame(int numberPlayers){
		
		if(queue.size()>=numberPlayers){
			for(int i=0;i<numberPlayers;i++){
				startingPlayers.add(queue.get(0));
				queue.remove(0);
			}
			return startingPlayers;
		}
		return null;
		// Die Spieler mit denen das Spiel beginnt.
		
	}


	public ArrayList<User> getPlayerQueue() {
		return queue;
	}
	

		

	

}
