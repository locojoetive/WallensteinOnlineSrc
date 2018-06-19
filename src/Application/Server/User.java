package Application.Server;

import java.io.PrintWriter;
import java.io.PushbackReader;

public class User {
	private String userName;
	private int winCounter;
	private int loseCounter;
	private double ranking;
	private PrintWriter pw;
	
	public User(String username, int winCounter, int loseCounter, PrintWriter pw){
		this.userName = username;
		this.winCounter=winCounter;
		this.loseCounter = loseCounter;
		this.pw=pw;
		ranking=(winCounter * 5) - (loseCounter * 4);
	}
	
	public PrintWriter getPrintwriter(){
		return pw;
	}
	public double getRanking(){
		return ranking;
	}
	public String getUserName()
	{
		return userName;
	}
	
	public int getWinCounter() {
		return winCounter;
	}

	public void setWinCounter(int winCounter) {
		this.winCounter = winCounter;
	}

	public int getLoseCounter() {
		return  loseCounter;
		
	}
		
	public void setLoseCounter(int loseCounter) {
		this.loseCounter = loseCounter;
	}
	
	
}
