// UI control
// all UIs switch in here
// UI 전환 관리 및 기본 프레임 

import javax.swing.*;

public class UIControl {

	private static JFrame frame;
	private static JPanel currentPanel, startUI;

	public UIControl() {

		frame = new JFrame("DaVinci Code");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 728);

		// set Panel
		currentPanel = new StartUI(this);
		frame.add(currentPanel);
		frame.setVisible(true);

	} // UIControl()

	public static void switchToGameUI(UIControl control) {

		frame.remove(currentPanel);
		currentPanel = new GameUI();
		frame.add(currentPanel);
		frame.revalidate();
		frame.repaint();

	} // switchToGameUI()

	public static void switchToHowToUI(UIControl control) {

		frame.remove(currentPanel);
		currentPanel = new HowToUI(control);
		frame.add(currentPanel);
		frame.revalidate();
		frame.repaint();

	} // switchToHowToUI()

	public static void switchToStartUI(UIControl control) {

		frame.remove(currentPanel);
		currentPanel = new StartUI(control);
		frame.add(currentPanel);
		frame.revalidate();
		frame.repaint();

	} // switchToStartUI()

	public static void main(String[] args) {

		// Thread safety
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				new UIControl();

			}

		});

	}

} // UIControl Class