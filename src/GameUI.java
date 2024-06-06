import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class GameUI extends JPanel {
	private JPanel playerTilePanel, comTilePanel, mainPanel, playerBtnPanel, comBtnPanel;
	private Tile selectedTile;
	private Tile[] computerDeckGame;
	private Tile[] playerDeckGame;
	private Tile[] tilesGame;
	private Tile[] fullTileGame;
	ComputerPlayer cm = new ComputerPlayer();

	public GameUI(Tile[] computerDeck, Tile[] playerDeck, Tile[] fullTiles, Tile[] tiles) {
		computerDeckGame = computerDeck;
		playerDeckGame = playerDeck;
		tilesGame = tiles;
		fullTileGame = fullTiles;

		setBackground(new Color(13, 13, 13));
		setLayout(null);

		// Computer Panel set
		comTilePanel = new JPanel();
		comTilePanel.setBounds(0, 0, 1000, 120);
		comTilePanel.setBackground(Color.black);
		for (Tile tile : computerDeck) {
			if (tile != null) {
				JButton button = tile.getButton();
				button.setPreferredSize(new Dimension(70, 100)); // 버튼 크기 설정
				button.addActionListener(new ComTileButtonListener());
				comTilePanel.add(button);
			}
		}
		add(comTilePanel);

		comBtnPanel = new JPanel();
		comBtnPanel.setBounds(0, 120, 1000, 10);
		comBtnPanel.setBackground(Color.white);
		comBtnPanel.setLayout(null);
		add(comBtnPanel);

		// Main Panel set
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 130, 1000, 400);
		mainPanel.setBackground(Color.white);
		for (Tile tile : fullTiles) {
			if (tile != null) {
				JButton button = tile.getButton();
				button.setPreferredSize(new Dimension(70, 100)); // 버튼 크기 설정
				button.addActionListener(new MainPanelButtonListener());
				button.setText(tile.toStringNumber());
				mainPanel.add(button);
			}
		}
		add(mainPanel);

		// Player Panel set
		playerBtnPanel = new JPanel();
		playerBtnPanel.setBounds(0, 530, 1000, 10); // x, y, w, h
		playerBtnPanel.setBackground(Color.black);
		playerBtnPanel.setLayout(null);
		add(playerBtnPanel);

		playerTilePanel = new JPanel();
		addPlayerTile(tiles, playerDeck, playerTilePanel);
		playerTilePanel.setBounds(0, 540, 1000, 120); // x, y, w, h
		playerTilePanel.setBackground(Color.black);
		for (Tile tile : playerDeck) {
			if (tile != null) {
				JButton button = tile.getButton();
				button.setPreferredSize(new Dimension(70, 100)); // 버튼 크기 설정
				button.setText(tile.toStringNumber());
				playerTilePanel.add(button);
			}
		}
		add(playerTilePanel);

		// Randomly decide first starter
		Random rand = new Random();
		int firstTurn = rand.nextInt(2);
		if (firstTurn == 0) {
			addComputerTile(tilesGame, computerDeckGame, comTilePanel);
			computerTurn();
			System.out.println("computer first");
		} else {
			System.out.println("player first");
		}
	}

	private class ComTileButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) { // Select computer tile on player turn
			JButton clickedButton = (JButton) e.getSource(); // button clicked
			Tile clickedTile = getTileByButton(clickedButton, computerDeckGame);

			if (clickedTile != null && !clickedTile.isFaceUp()) { // Select only if the tile is not front
				if (selectedTile == clickedTile) { // click the same button
					selectedTile = null; // disable select
					clickedButton.setBorder(BorderFactory.createLineBorder(Color.black));
				} else { // button to select is not selected or trying to select another button
					if (selectedTile != null) {
						selectedTile.getButton().setBorder(BorderFactory.createLineBorder(Color.black));
					}
					selectedTile = clickedTile;
					clickedButton.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
				}
			}
		}
	}

	private class MainPanelButtonListener implements ActionListener { // Guess computer tiles on player turns
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton clickedButton = (JButton) e.getSource();
			if (checkEnd(playerDeckGame, computerDeckGame) == 1) {
				System.out.println("computer win");
			} else if (checkEnd(playerDeckGame, computerDeckGame) == 2) {
				System.out.print("player win");
			}

			if (selectedTile == null) {
				System.out.println("no selected tile"); // match without selecting
				return;
			}

			Tile tempTile = getTileByButton(clickedButton, fullTileGame);
			if (selectedTile.getNumber() == tempTile.getNumber()) { // match the computer's card
				selectedTile.getButton().setBorder(BorderFactory.createLineBorder(Color.black));
				selectedTile.flip();
				selectedTile.getButton().setText(selectedTile.toStringNumber());
				selectedTile = null;

				if (checkEnd(playerDeckGame, computerDeckGame) == 1) {
					System.out.println("computer win");
				} else if (checkEnd(playerDeckGame, computerDeckGame) == 2) {
					System.out.println("player win");
				}
			} else { // Fail match the computer's card
				// open one in my deck
				tempTile = Tile.chooseRandomTile(playerDeckGame);
				while (tempTile.isFaceUp()) {
					tempTile = Tile.chooseRandomTile(playerDeckGame);
				}
				tempTile.flip();

				for (int i = 0; i < 11; i++) {
					if (playerDeckGame[i] != null && playerDeckGame[i].isFaceUp()) {
						playerDeckGame[i].getButton().setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
					}
				}

				selectedTile.getButton().setBorder(BorderFactory.createLineBorder(Color.black));
				selectedTile = null;

				addComputerTile(tilesGame, computerDeckGame, comTilePanel);
				computerTurn();
				addPlayerTile(tilesGame, playerDeckGame, playerTilePanel);
			}
		}
	}

	private void computerTurn() {
		Tile[] selectTile;

		if (checkEnd(playerDeckGame, computerDeckGame) == 1) {
			System.out.println("computer win");
			return;
		} else if (checkEnd(playerDeckGame, computerDeckGame) == 2) {
			System.out.print("player win");
			return;
		}

		// Add cards to deck

		// Match card
		selectTile = cm.selectTile(playerDeckGame, computerDeckGame, fullTileGame);

		// Matched : open computer's tile and match again
		if (selectTile[0].getNumber() == selectTile[1].getNumber()) {
			for (Tile tile : playerDeckGame) {
				if (tile == null) {
					continue;
				}
				if (tile.getNumber() == selectTile[0].getNumber()) {
					tile.flip();
				}
			}

			computerTurn();
		} else {
			Tile tempTile;
			// open player's deck
			tempTile = Tile.chooseRandomTile(computerDeckGame);
			while (tempTile.isFaceUp()) {
				tempTile = Tile.chooseRandomTile(computerDeckGame);
			}
			tempTile.flip();
			tempTile.getButton().setText(tempTile.toStringNumber());
		}

		// test
		System.out.println("target:" + selectTile[0] + " answer:" + selectTile[1]);
		System.out.println("computer deck and state");
		for (Tile tile : computerDeckGame) {
			if (tile == null) {
				continue;
			}
			System.out.println("number: " + tile.getNumber() + " state: " + tile.isFaceUp());
		}

		// add player deck
	}

	private JPanel addComputerTile(Tile[] tilesGame, Tile[] computerDeckGame, JPanel computerTilePanel) {
		Tile addTile = null;

		// Get last tile from tilesGame()
		for (int i = tilesGame.length - 1; i >= 0; i--) {
			if (tilesGame[i] != null) {
				addTile = tilesGame[i];
				tilesGame[i] = null;
				break;
			}
		}

		if (addTile == null) {
			System.out.println("No tile to add");
			return null;
		}

		// Add tile in computerDeckGame()
		for (int i = 0; i < 11; i++) {
			if (computerDeckGame[i] == null) {
				computerDeckGame[i] = addTile;
				break;
			}
		}

		Tile.sortByModulo(computerDeckGame);

		computerTilePanel.removeAll();

		// add button in computerTilePanel
		for (Tile tile : computerDeckGame) {
			if (tile != null) {
				JButton button = tile.getButton();
				for (ActionListener al : button.getActionListeners()) {
					button.removeActionListener(al);
				}
				button.addActionListener(new ComTileButtonListener()); // ActionListener 추가
				button.setPreferredSize(new Dimension(70, 100)); // 버튼 크기 설정
				computerTilePanel.add(button);
			}
		}

		// UI update
		computerTilePanel.revalidate();
		computerTilePanel.repaint();

		return computerTilePanel;
	}

	// Add tile in player's deck
	private JPanel addPlayerTile(Tile[] tilesGame, Tile[] playerDeckGame, JPanel playerTilePanel) {
		Tile addTile = null;

		// get last tile from tilesGame
		for (int i = tilesGame.length - 1; i >= 0; i--) {
			if (tilesGame[i] != null) {
				addTile = tilesGame[i];
				tilesGame[i] = null;
				break;
			}
		}

		if (addTile == null) {
			System.out.println("No tile to add");
			return null;
		}

		// Add Tile in playerDeckGame
		for (int i = 0; i < 11; i++) {
			if (playerDeckGame[i] == null) {
				playerDeckGame[i] = addTile;
				break;
			}
		}

		Tile.sortByModulo(playerDeckGame);

		playerTilePanel.removeAll();

		// Add button in playerTilePanel
		for (Tile tile : playerDeckGame) {
			if (tile != null) {
				JButton button = tile.getButton();
				button.setPreferredSize(new Dimension(70, 100)); // 버튼 크기 설정
				button.setText(tile.toStringNumber());
				playerTilePanel.add(button);
			}
		}

		// UI update
		playerTilePanel.revalidate();
		playerTilePanel.repaint();

		return playerTilePanel;
	}

	private Tile getTileByButton(JButton button, Tile[] deck) {
		for (Tile tile : deck) {
			if (tile != null && tile.getButton() == button) {
				return tile;
			}
		}
		return null;
	}

	// Computer algorithm
	private int checkEnd(Tile[] player, Tile[] computer) {
		if (Tile.isFullFaced(player)) {
			JOptionPane.showMessageDialog(null, "Lose...");
			return 1; // computer player win
		}

		if (Tile.isFullFaced(computer)) {
			JOptionPane.showMessageDialog(null, "Win!");
			return 2; // player win
		}

		return 0;
	}

	public Tile[] returnPlayerDeck() {
		return playerDeckGame;
	}

	public Tile[] returnTiles() {
		return tilesGame;
	}
}
