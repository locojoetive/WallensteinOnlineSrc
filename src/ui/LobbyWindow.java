package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.MediaTracker;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.awt.Container;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;



import ui.Client.Enter;
import Application.Server.MusicPlayer;
import Domain.Statistik.statistikWindow;

@SuppressWarnings("serial")
public class LobbyWindow extends JFrame implements ActionListener {

	// ////////////LOBBY_COMPONENTS///////////////////////
	
	private GridBagConstraints gbc = new GridBagConstraints();
	private Client client;
	boolean clicked = false;
	JButton buttonPlayGame;
	JButton buttonViewStats;
	JButton buttonExit;
	JButton buttonJoin;	
	JButton buttonSend;
	Icon icon = new ImageIcon("./data/images/stone.jpg");

	// ///////////////CHAT_COMPONENTS/////////////////////

	JTextArea textArea_message;
	JTextField textField_ClientMessage;
	JButton button_sendMessage;
	JTextField textField_username;
	JLabel labelUser;
	JScrollPane scrollPane_Bar;

	// ///////////////LOBBYWINDOW_KONSTRUKTOR//////////////

	public LobbyWindow(Client client) throws IOException {
		
		
		
		super("Lobby Window");
//		new MusicPlayer();
		Container cp = getContentPane();
		gbc.fill = GridBagConstraints.BOTH;
		cp.setLayout(new GridBagLayout());
		setSize(800, 200);
		setResizable(false);
		this.client = client;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		
		setGridPos(0, 0, 2, 4);
		setWeight(10, 1);
		textArea_message = new JTextArea();
		textArea_message.setEditable(false);
		cp.add(new JScrollPane(textArea_message),gbc);
		
		setGridPos(0, 4, 1, 1);
		setWeight(1, 1);
		textField_username = new JTextField();
		textField_username.setEditable(false);
		cp.add(textField_username,gbc);
		
		setGridPos(1, 4, 1, 1);
		setWeight(10, 1);
		textField_ClientMessage = new JTextField();
		textField_ClientMessage.addActionListener(this);
		textField_ClientMessage.setActionCommand("send");
		cp.add(textField_ClientMessage,gbc);
		
		setGridPos(2, 0, 1, 1);
		setWeight(1, 1);
		buttonPlayGame = new JButton("Single Player", icon);
		buttonPlayGame.setForeground(Color.WHITE);
		//buttonPlayGame.setBorder(BorderFactory.createEmptyBorder());
		buttonPlayGame.setContentAreaFilled(false);
		buttonPlayGame.setHorizontalTextPosition(JButton.CENTER);
		buttonPlayGame.addActionListener(this);
		cp.add(buttonPlayGame,gbc);
		
		setGridPos(2, 1, 1, 1);
		buttonJoin = new JButton("Multi Player", icon);
		buttonJoin.setForeground(Color.WHITE);
	//	buttonJoin.setBorder(BorderFactory.createEmptyBorder());
		buttonJoin.setContentAreaFilled(false);
		buttonJoin.setHorizontalTextPosition(JButton.CENTER);
		buttonJoin.addActionListener(this);
		cp.add(buttonJoin,gbc);
		
		setGridPos(2, 2, 1, 1);
		buttonViewStats = new JButton("Statistik", icon);
		buttonViewStats.setForeground(Color.WHITE);
	//	buttonViewStats.setBorder(BorderFactory.createEmptyBorder());
		buttonViewStats.setContentAreaFilled(false);
		buttonViewStats.setHorizontalTextPosition(JButton.CENTER);
		buttonViewStats.addActionListener(this);
		cp.add(buttonViewStats,gbc);
		
		setGridPos(2, 3, 1, 1);
		buttonExit = new JButton("Exit", icon);
		buttonExit.setForeground(Color.WHITE);
//		buttonExit.setBorder(BorderFactory.createEmptyBorder());
		buttonExit.setContentAreaFilled(false);
		buttonExit.setHorizontalTextPosition(JButton.CENTER);
		buttonExit.addActionListener(this);
		cp.add(buttonExit,gbc);
		
		setGridPos(2, 4, 1, 1);
		button_sendMessage= new JButton("Send",icon);
		button_sendMessage.setForeground(Color.WHITE);
		//button_sendMessage.setBorder(BorderFactory.createEmptyBorder());
		button_sendMessage.setContentAreaFilled(false);
		button_sendMessage.setHorizontalTextPosition(JButton.CENTER);
		button_sendMessage.addActionListener(this);
		cp.add(button_sendMessage,gbc);
		setVisible(true);
	}

	

	public void setChat(String message) {
		textArea_message.append(message);
	}

	// //////////////////////////////////////////////////////////

	public JTextField getTextUsername() {
		return textField_username;
	}
	
	private void setGridPos(int gridx, int gridy, int gridwith, int gridheight){
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwith;
		gbc.gridheight = gridheight;
	}
	
	private void setWeight (int weightx, int weighty){
		gbc.weightx = weightx;
		gbc.weighty = weighty;
	}

	// ////////////////////ACTIONLISTENER////////////////////////////

		public void actionPerformed(ActionEvent ae) {
			
			if (ae.getSource() == buttonExit) {
				client.stringToServer("LogOut-" + textField_username.getText());
				System.exit(0);
			} else if (ae.getSource() == buttonPlayGame) {
				if(!clicked){
					client.stringToServer("SinglePlayerGame-" + textField_username.getText());
					clicked = true;
				}
			}
			else if(ae.getSource()==buttonJoin){
				if(!clicked){
					client.stringToServer("MultiPlayerGame-" + textField_username.getText());
					clicked = true;
				}
			}
			else if (ae.getSource() == button_sendMessage || ae.getActionCommand().equals("send")) {
				if (textField_ClientMessage.getText().equals("")) {

				} else {
					String chatMessage = textField_ClientMessage.getText();
					char[] charArray;
					charArray = chatMessage.toCharArray();
					chatMessage = "";
					for (int i = 0; i < charArray.length; i++) {
						if (charArray[i] == '-') {
							charArray[i] = '_';
						}

						chatMessage += charArray[i];
					}

					client.stringToServer("Chat-" + textField_username.getText()
							+ "-" + chatMessage);
					textField_ClientMessage.setText("");
					textArea_message.setCaretPosition(textArea_message.getDocument().getLength());
				}
			}

			else if (ae.getSource() == buttonViewStats) {
				statistikWindow sw = new statistikWindow();
			}
		}

		// /////////////////////////////////////////////////////////
}
