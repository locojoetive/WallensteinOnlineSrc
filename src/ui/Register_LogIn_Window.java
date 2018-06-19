package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import Application.Server.Storage;

@SuppressWarnings("serial")
public class Register_LogIn_Window extends GeneralWindow implements
		ActionListener {

	// components
	
	private JTabbedPane tabs;
	private JPanel panelLogIn;
	private JPanel panelRegister;
	private JLabel labelLogInUsername;
	private JLabel labelLogInPasswort;
	private JLabel labelRegisterUsername;
	private JLabel labelRegisterPasswort;
	private JLabel labelRegisterConfirmPasswort;
	private JTextField textLogInUsername;
	private JPasswordField textLogInPasswort;
	private JTextField textRegisterUsername;
	private JPasswordField textRegisterPasswort;
	private JPasswordField textConfirmPasswort;
	private JButton buttonLogIn;
	private JButton buttonRegister;

	private Client client;

	public Client getRegisterLoginClient() {
		return client;
	}

	public Register_LogIn_Window(Client client) // creates new window to
												// register or sign in
	{
		this.client = client;
		setTitle("Log-In Fenster");
		width = 400;
		height = 250;
		Centralize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close application
														// with x-button
		this.initComponents();
		this.initListeners();
		this.setResizable(false);
		setVisible(true);
	}

	private void initComponents() // initializes components
	{
		panelLogIn = new JPanel();
		panelLogIn.setBackground(Color.lightGray);
		panelRegister = new JPanel();
		panelRegister.setBackground(Color.lightGray);
		tabs = new JTabbedPane();
		labelLogInUsername = new JLabel("Benutzername:");
		labelLogInPasswort = new JLabel("Passwort:");
		labelRegisterUsername = new JLabel("Benutzername wählen:");
		labelRegisterPasswort = new JLabel("Passwort wählen:");
		labelRegisterConfirmPasswort = new JLabel("Passwort bestätigen:");
		textLogInUsername = new JTextField();
		textLogInPasswort = new JPasswordField();
		textLogInPasswort.addActionListener(this);
		textRegisterUsername = new JTextField();
		textRegisterPasswort = new JPasswordField();
		textConfirmPasswort = new JPasswordField();
		textConfirmPasswort.addActionListener(this);
		buttonLogIn = new JButton("Einloggen");
		buttonRegister = new JButton("Registrieren");

		tabs.add("Einloggen", panelLogIn);
		tabs.add("Registrieren", panelRegister);
		this.getContentPane().add(tabs, BorderLayout.PAGE_START);
		this.setLogInLayout();
		this.setRegisterLayout();
	}

	private void setLogInLayout() // sets layout of log-in window
	{
		GroupLayout layoutLogIn = new GroupLayout(panelLogIn);
		panelLogIn.setLayout(layoutLogIn);
		layoutLogIn.setAutoCreateGaps(true);
		layoutLogIn.setAutoCreateContainerGaps(true);

		// horizontal alignment of components
		layoutLogIn.setHorizontalGroup(layoutLogIn
				.createSequentialGroup()
				.addGroup(
						layoutLogIn.createParallelGroup(Alignment.LEADING)
								.addComponent(labelLogInUsername)
								.addComponent(labelLogInPasswort)
								.addComponent(buttonLogIn))
				.addGroup(
						layoutLogIn.createParallelGroup(Alignment.LEADING)
								.addComponent(textLogInUsername)
								.addComponent(textLogInPasswort)));

		// vertical alignment of components
		layoutLogIn.setVerticalGroup(layoutLogIn
				.createSequentialGroup()
				.addGroup(
						layoutLogIn.createParallelGroup(Alignment.CENTER)
								.addComponent(labelLogInUsername)
								.addComponent(textLogInUsername))
				.addGroup(
						layoutLogIn.createParallelGroup(Alignment.CENTER)
								.addComponent(labelLogInPasswort)
								.addComponent(textLogInPasswort)).addGap(60)
				.addComponent(buttonLogIn).addGap(800));
	}

	private void setRegisterLayout() // sets layout of register window
	{
		GroupLayout layoutRegister = new GroupLayout(panelRegister);
		panelRegister.setLayout(layoutRegister);
		layoutRegister.setAutoCreateGaps(true);
		layoutRegister.setAutoCreateContainerGaps(true);

		// horizontal alignment of components
		layoutRegister.setHorizontalGroup(layoutRegister
				.createSequentialGroup()
				.addGroup(
						layoutRegister.createParallelGroup(Alignment.LEADING)
								.addComponent(labelRegisterUsername)
								.addComponent(labelRegisterPasswort)
								.addComponent(labelRegisterConfirmPasswort)
								.addComponent(buttonRegister))
				.addGroup(
						layoutRegister.createParallelGroup(Alignment.LEADING)
								.addComponent(textRegisterUsername)
								.addComponent(textRegisterPasswort)
								.addComponent(textConfirmPasswort)));

		// vertical alignment of components
		layoutRegister.setVerticalGroup(layoutRegister
				.createSequentialGroup()
				.addGroup(
						layoutRegister.createParallelGroup(Alignment.CENTER)
								.addComponent(labelRegisterUsername)
								.addComponent(textRegisterUsername))
				.addGroup(
						layoutRegister.createParallelGroup(Alignment.CENTER)
								.addComponent(labelRegisterPasswort)
								.addComponent(textRegisterPasswort))
				.addGroup(
						layoutRegister.createParallelGroup(Alignment.CENTER)
								.addComponent(labelRegisterConfirmPasswort)
								.addComponent(textConfirmPasswort)).addGap(20)
				.addComponent(buttonRegister).addGap(800));
	}

	private void initListeners() // initializes listeners
	{
		buttonLogIn.addActionListener(this);
		buttonRegister.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == buttonRegister || ev.getSource() == textConfirmPasswort ) {
			String username = textRegisterUsername.getText();
			@SuppressWarnings("deprecation")
			String passwort = textRegisterPasswort.getText();
			@SuppressWarnings("deprecation")
			String confirmPasswort = textConfirmPasswort.getText();

			if (username.equals("")) {
				JOptionPane.showMessageDialog(null,
						"Bitte geben sie einen Benutzernamen ein!");
				return;
			}
			for(int i=0;i<username.length();i++){
				if(username.charAt(i)=='-')
				{
					JOptionPane.showMessageDialog(null, "Bitte verwenden sie nur Buchstaben oder zahlen!");
					return;
				}
			}
			
			if (passwort.equals("")) {
				JOptionPane.showMessageDialog(null,
						"Bitte geben sie ein Passwort ein!");
				return;
			}
			if (!passwort.equals(confirmPasswort)) {
				JOptionPane.showMessageDialog(null,
						"Passwörter stimmen nicht überein!");
				return;
			}
			String message = "register-"+textRegisterUsername.getText()+"-"+textRegisterPasswort.getText();
			client.stringToServer(message);
		}else if (ev.getSource() == buttonLogIn || ev.getSource() == textLogInPasswort ) {
			
			String message="LogIn-"+textLogInUsername.getText()+"-"+textLogInPasswort.getText();
			client.stringToServer(message);
		}
	}
}
