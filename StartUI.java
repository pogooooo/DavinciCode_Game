// Main window
// go to Start, End, view How to play game
// 메인화면 관리 클래스

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class StartUI extends JPanel {

	// visual
	private JPanel titlePanel, buttonPanel;
	private JLabel lblTitle;
	private JButton btnStart, btnHowto, btnEnd;

	// control
	private UIControl uiControl;
	private SwitchListener switchL;

	public StartUI(UIControl uiControl) {

		this.uiControl = uiControl;

		// set frame
		setBackground(Color.white);
		setLayout(null);

		switchL = new SwitchListener();

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
		lblTitle = new JLabel("DaVinci Code");
		lblTitle.setBounds(0, 0, 500, 50);
		lblTitle.setFont(new Font("Helvetica Neue", Font.BOLD | Font.ITALIC, 50));
		lblTitle.setVerticalAlignment(SwingConstants.CENTER);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		titlePanel.add(lblTitle);

		// set Button
		btnStart = new JButton("Start");
		btnStart.setBounds(10, 10, 280, 330);
		btnStart.addActionListener(switchL);
		buttonPanel.add(btnStart);

		btnHowto = new JButton("How To");
		btnHowto.setBounds(10, 350, 280, 165);
		btnHowto.addActionListener(switchL);
		buttonPanel.add(btnHowto);

		btnEnd = new JButton("End");
		btnEnd.setBounds(10, 525, 280, 165);
		btnEnd.addActionListener(switchL);
		buttonPanel.add(btnEnd);

	} // StartUIPanal()

	// button works
	private class SwitchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			Object obj = e.getSource();

			if (obj == btnStart) {

				UIControl.switchToGameUI(uiControl);

			} else if (obj == btnHowto) {

				UIControl.switchToHowToUI(uiControl);

			} else if (obj == btnEnd) {

				System.exit(0);

			} // if..else if

		} // actionPerformed()

	} // SwitchListener class

} // StartUI class