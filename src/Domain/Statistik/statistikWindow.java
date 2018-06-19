package Domain.Statistik;

import javax.swing.*;
import javax.swing.text.html.parser.Element;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class statistikWindow extends JFrame implements ActionListener {

	BufferedReader br;
	JTextArea textarea;
	JButton button;
	JLabel label;
	String text = "";
	JScrollPane scrollPane_Bar;
	int i = 1;

	public statistikWindow() {

		//setDefaultCloseOperation(EXIT_ON_CLOSE);

		setSize(800, 800);
		setLocationRelativeTo(null);
		setTitle("Statistiken der Spieler");

		setLayout(new FlowLayout());

		textarea = new JTextArea(30, 30);
		add(textarea);
		textarea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textarea);
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(scrollPane);
		this.getContentPane().add(panel);

		button = new JButton("Spieler Statistiken anzeigen");
		add(button);

		event ee = new event();
		button.addActionListener(ee);

		setVisible(true);
	}

	public class event implements ActionListener {
		public void actionPerformed(ActionEvent ee) {

			try {
				br = new BufferedReader(new FileReader(new File(
						"./data/userdata.txt")));

				for (String line = br.readLine(); line != null; line = br
						.readLine()) {
					
					if (i % 6 != 2){

					text = text + line + "\n";

					System.out.println(text);

					textarea.setText(text);
					}

					button.setEnabled(false);
					
					i = i + 1;
					

				}

				textarea.setText(text);

				br.close();

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		new statistikWindow();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
