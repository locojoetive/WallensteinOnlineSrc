package Application.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Application.Player;

public class Storage {
	
	FileWriter userWriter, tempWriter;
	BufferedReader userReader, tempReader;
	int i;
	
	String lineBreak = System.lineSeparator();
	public void register(String nutzername, String passwort){
		try {
			userWriter = new FileWriter(new File("./data/userdata.txt"));
			userWriter.write(nutzername + lineBreak+ passwort + lineBreak +"0" + lineBreak + "0"+ lineBreak + lineBreak); 
			userWriter.flush();
			userWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void setWin(ArrayList<Player> players){
		try {
			
			String line = userReader.readLine();
			while (line != null){
				for(int i=0;i<players.size();i++){
					userReader = new BufferedReader(new FileReader(new File("./data/userdata.txt")));
					tempReader = new BufferedReader(new FileReader(new File("./data/temp.txt")));
					userWriter = new FileWriter(new File("./data/userdata.txt"));
					tempWriter = new FileWriter(new File("./data/temp.txt"));
					
					if(line.equals(players.get(i).getUsername())){
						tempWriter.write(line);
						line=userReader.readLine();
						i = Integer.parseInt(line);
						i++;
//						tempWriter.write(str);
						
					}
				line=userReader.readLine();
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
	
