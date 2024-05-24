// View 'How To Play Game?'
// 이후 게임 설명 들어갈 패널 

import java.awt.event.*;

import javax.swing.*;

public class HowToUI extends JPanel {

	private JButton btnGoStartUI;

	// control
	private UIControl uiControl;
	private GoHomeListener GoHomeL;

	public HowToUI(UIControl uiControl) {

		this.uiControl = uiControl;

		GoHomeL = new GoHomeListener();

		JLabel label = new JLabel("How To play game");
		add(label);

		btnGoStartUI = new JButton("Home");
		btnGoStartUI.setBounds(10, 10, 100, 100);
		btnGoStartUI.addActionListener(GoHomeL);
		add(btnGoStartUI);

	}

	private class GoHomeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			Object obj = e.getSource();

			if (obj == btnGoStartUI) {

				UIControl.switchToStartUI(uiControl);

			}

		}
	}

}