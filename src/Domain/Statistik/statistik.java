package Domain.Statistik;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import Application.Player;

public class statistik {
	
	BufferedReader br;
	String username;
	
	public void addWinCounter(Player player) {
		
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("./data/userdata.txt")));
			String line = br.readLine();
			while(line!=null){
				if(username.equals(line))
				{
//					Passwort Zeile überspringen und +1 in die userdata.txt schreiben
				}
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void addLoseCounter(Player player) {
		
		
				try {
		BufferedReader br = new BufferedReader(new FileReader(new File("./data/userdata.txt")));
		String line = br.readLine();
		while(line!=null){
			if(username.equals(line))
			{
//					Password Zeile und Win Zeile überspringen und + 1 in die userdata.txt schreiben
			}
			line = br.readLine();
		}
		br.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
		
	}
				
	}
	
	public void addRanking(Player player) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("./data/userdata.txt")));
			String line = br.readLine();
			while(line!=null){
				if(username.equals(line))
				{
//					Password Zeile überspringen, Win Zeile auslesen, Lose Zeile auslesen, Win * 5 - Lose * 4 
//					in die userdata.txt schreiben
				}
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
	}

}
