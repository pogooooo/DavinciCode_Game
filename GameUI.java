// Game Play
// 메인 게임 플레이용 

import java.awt.*;

import javax.swing.*;

public class GameUI extends JPanel {

	private JPanel playerTilePanel, comTilePanel, mainPanel, playerBtnPanel, comBtnPanel;
	private JLabel[] lblPlayerDeck = new JLabel[13];
	private JComboBox<Integer> cbNumChoice;
	private JButton[] btnComDeck = new JButton[13];
	private JButton[] btnTileDeck = new JButton[13];
	private JButton btnTurnOver, btnContinue;

	private int[] tileLoc = new int[13];
	private int[][][] tileDeckLoc = new int[3][9][2];
	private ImageIcon[][] tileIcon = new ImageIcon[3][14];

	public GameUI() {

		// ** set frame Layout
		setBackground(new Color(13, 13, 13));
		setLayout(null);

		// ** set Panel
		comTilePanel = new JPanel();
		comTilePanel.setBounds(0, 0, 1000, 120);
		comTilePanel.setBackground(new Color(220, 220, 220));
		comTilePanel.setLayout(null);
		add(comTilePanel);

		comBtnPanel = new JPanel();
		comBtnPanel.setBounds(0, 120, 1000, 30);
		comBtnPanel.setBackground(new Color(210, 210, 210));
		comBtnPanel.setLayout(null);
		add(comBtnPanel);

		mainPanel = new JPanel();
		mainPanel.setBounds(0, 150, 1000, 400);
		mainPanel.setBackground(new Color(200, 200, 200));
		mainPanel.setLayout(null);
		add(mainPanel);

		playerBtnPanel = new JPanel();
		playerBtnPanel.setBounds(0, 550, 1000, 30); // x, y, w, h
		playerBtnPanel.setBackground(new Color(210, 210, 210));
		playerBtnPanel.setLayout(null);
		add(playerBtnPanel);

		playerTilePanel = new JPanel();
		playerTilePanel.setBounds(0, 580, 1000, 120); // x, y, w, h
		playerTilePanel.setBackground(new Color(220, 220, 220));
		playerTilePanel.setLayout(null);
		add(playerTilePanel);

		// ** set ComboBox
		Integer[] numOptions = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		cbNumChoice = new JComboBox<>(numOptions);
		cbNumChoice.setBounds(700, 0, 100, 30);
		playerBtnPanel.add(cbNumChoice);

		// ** set Image array
		// 이미지 저장 배열입니다 tileIcon[][]으로 참조 가능합니다
		// tileIcon[흰색 0, 검정 1][숫자 0~11, 12 조커, 13 뒷면]
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j <= 13; j++) {

				// white = 0, black = 1, joker = 12, back = 13
				tileIcon[i][j] = new ImageIcon("Images/Tiles/tile_" + i + "_" + j + ".png");

			} // for(j)
		} // for(i)

		// ** set Tile Location array
		// 타일 정렬 시 x 좌표를 저장하는 배열입니다
		// 위치는 아래와 같이 설정가능
		// setBounds(setTileLoc[0~13], 10, 70, 101)
		// 컴퓨터/플레이어는 패널만 바꿔서 설정하면 됩니다
		for (int i = 0; i < 13; i++) {

			tileLoc[i] = (70 * i) + (6 * (i + 1));

		} // for(i)

		// tile deck 설정 시 사용하는 좌표저장
		// tileDeckLoc[0~2번째 줄][0~8번째 타일][0 = x 좌표, 1 = y 좌표]
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {

				tileDeckLoc[i][j][0] = 45 + (105 * j); // x
				tileDeckLoc[i][j][1] = 30 + (120 * i); // y

			} // for(j)
		} // for(i)

		// ** set Label
		// 자기 덱은 선택할 일이 없어서 Label
		// 일단 1~조커까지 할당해놨는데 수정 후 사용하시면 됩니다
		for (int i = 0; i < 13; i++) {

			lblPlayerDeck[i] = new JLabel(tileIcon[0][i]);
			lblPlayerDeck[i].setBounds(tileLoc[i], 10, 70, 101);
			playerTilePanel.add(lblPlayerDeck[i]);

		} // for(i)

		// ** set Button
		// 일단 1~조커까지 할당해놨는데 수정 후 사용하시면 됩니다
		for (int i = 0; i < 13; i++) {

			btnComDeck[i] = new JButton(tileIcon[1][i]);
			btnComDeck[i].setBounds(tileLoc[i], 10, 70, 101);
			comTilePanel.add(btnComDeck[i]);

		} // for(i)

		// 뒤집어진 타일 덱
		// 버튼으로 구현했으니 인덱스 이용해서 사용
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {

				btnTileDeck[i] = new JButton(tileIcon[i % 2][13]);
				btnTileDeck[i].setBounds(tileDeckLoc[i][j][0], tileDeckLoc[i][j][1], 70, 101);
				mainPanel.add(btnTileDeck[i]);

			} // for(j)
		} // for(i)

		btnContinue = new JButton("Continue");
		btnContinue.setBounds(800, 0, 100, 30);
		playerBtnPanel.add(btnContinue);

		btnTurnOver = new JButton("Turn End");
		btnTurnOver.setBounds(900, 0, 100, 30);
		playerBtnPanel.add(btnTurnOver);

	} // GameUI()

}// GameUI Class