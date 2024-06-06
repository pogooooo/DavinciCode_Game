/*
 * DaVinci Code / StartUI Class
 * 
 * Main menu UI setting
 * */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class StartUI extends JPanel {

	private JPanel titlePanel, buttonPanel;
	private JLabel lblTitle;
	private JButton btnStart, btnHowto, btnEnd;

	private boolean IsStart = false, IsEnd = false;

	public StartUI() {
		setBackground(Color.white);
		setLayout(null);

		// set Panel
		titlePanel = new JPanel();
		titlePanel.setBounds(0, 0, 700, 700); // x, y, w, h
		titlePanel.setBackground(new Color(220, 220, 220));
		titlePanel.setLayout(null);
		add(titlePanel);

		buttonPanel = new JPanel();
		buttonPanel.setBounds(700, 0, 300, 700);
		buttonPanel.setBackground(new Color(240, 240, 240));
		buttonPanel.setLayout(null);
		add(buttonPanel);

		// set Label
		lblTitle = new JLabel("DaVinci Code Game");
		lblTitle.setBounds(10, 10, 700, 700);
		lblTitle.setFont(new Font("Helvetica Neue", Font.BOLD | Font.ITALIC, 50));
		lblTitle.setVerticalAlignment(JLabel.CENTER);
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		titlePanel.add(lblTitle);

		// set Button
		btnStart = new JButton("Start");
		btnStart.setFont(new Font("Helvetica Neue", Font.BOLD | Font.ITALIC, 50));
		btnStart.setBounds(10, 15, 280, 330);
		buttonPanel.add(btnStart);

		btnEnd = new JButton("End");
		btnEnd.setFont(new Font("Helvetica Neue", Font.BOLD | Font.ITALIC, 50));
		btnEnd.setBounds(10, 355, 280, 330);
		btnEnd.addActionListener(new EndAction());
		buttonPanel.add(btnEnd);
	}

	// btnStart Action
	public JButton getBtnStart() {
		return btnStart;
	}

	// btnEnd Action
	private class EndAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			IsEnd = true;
			System.exit(0);
		}
	}

}
