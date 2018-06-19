package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class IP_Window extends GeneralWindow implements ActionListener
{
	JLabel Ip_Label;
	JTextField Ip_Textfield;
	JButton Ip_confirm_Button;
	Client client;
	Register_LogIn_Window rlw;
	
	public IP_Window(Client client)
	{
		Centralize();
		this.client=client;
		Ip_Textfield = new JTextField();
		Ip_Textfield.setMaximumSize(new Dimension(100,20));
		Ip_Textfield.addActionListener(this);
		Ip_Label = new JLabel("IP Nummer: ");
		Ip_confirm_Button = new JButton("connect");
		Ip_confirm_Button.addActionListener(this);
		setSize(400,80);
		setLayout();
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	void setLayout(){
		GroupLayout gl = new GroupLayout(this.getContentPane());
		setLayout(gl);
		
		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addGap(70)
				.addComponent(Ip_Label)
				.addGap(10)
				.addComponent(Ip_Textfield)
				.addGap(10)
				.addComponent(Ip_confirm_Button));
		
		gl.setVerticalGroup(gl.createParallelGroup(Alignment.CENTER)
				.addGap(50)
				.addComponent(Ip_Label)
				.addComponent(Ip_Textfield)
				.addComponent(Ip_confirm_Button));
	}
	public Register_LogIn_Window getRLW(){
		return rlw;
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource() == Ip_confirm_Button || ev.getSource() == Ip_Textfield)
		{
			if(client.connectToServer(Ip_Textfield.getText())){
				client.startThread();
				rlw = new Register_LogIn_Window(client);
				this.hide();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "DuBischNOarschloch!");
			}
		}
			
	
	}
	
	
	
	
	
	
	
}
