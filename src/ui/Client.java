package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;


public class Client {

	Socket client;
	PrintWriter writer;
	BufferedReader reader;
	ServerHandler sh;
	IP_Window ipw;
	
	public Client()
	{
		ipw = new IP_Window(this);
	}

	// /////////////////MAIN//////////////////////////////
	public static void main(String[] args) {
		new Client();
	}

	public IP_Window getIPW(){
		return ipw;
	}
	// /////////////////////////////////////////////////////////////////////

	public boolean connectToServer(String ip) {
		try {
			client = new Socket(ip, 5555);
			reader = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			writer = new PrintWriter(client.getOutputStream());
			return true;

		}

		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// /////////////////////////////////////////////////////////////////////


	public void stringToServer(String message){
		writer.println(message);
		writer.flush();
	}
	public void startThread()
	{
		sh = new ServerHandler(this);
		Thread thread = new Thread(sh);
		thread.start();
	}
	

	// ///////////////////////// Alle Methoden um die Nachrichten
	// abzuschicken//////////////

	public class Enter implements KeyListener {
		public void keyPressed(KeyEvent arg0) {
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
//				chatMessageToServer();
			}
		}

		public void keyReleased(KeyEvent arg0) {
			// LEER
		}

		public void keyTyped(KeyEvent arg0) {
			// LEER
		}

	}

	// /////////////////////////////////////////////////////////////

	public ServerHandler getServerHandler(){
		return sh;
	}
}