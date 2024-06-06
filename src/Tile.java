/*
 * DaVinci Code / Tile
 * 
 * Setting Tile info
 * */

import java.awt.*;
import java.util.*;

import javax.swing.*;

public class Tile {

	private int number; // Tile Number
	private boolean isFaceUp; // Check Tile Flip
	private JButton button;

	public Tile(int number) {
		this.number = number;
		this.isFaceUp = false; // set default back
		this.button = new JButton("<");

		updateBackgroundColor();
		button.setPreferredSize(new Dimension(100, 30));
	}

	// sort Array
	public static void sortByModulo(Tile[] tiles) {
		Arrays.sort(tiles, new Comparator<Tile>() {
			@Override
			public int compare(Tile t1, Tile t2) {
				if (t1 == null || t2 == null) {
					return 0;
				}
				return Integer.compare(t1.getNumber() % 100, t2.getNumber() % 100);
			}
		});
	}

	// Check tile array flip
	public static boolean isFullFaced(Tile[] tiles) {
		for (Tile tile : tiles) {
			if (tile != null) {
				if (!tile.isFaceUp()) {
					return false;
				}
			}
		}
		return true;
	}

	// Check if the target tile is in the list
	public static boolean isInList(Tile target, int[] tileList) {
		if (target == null) {
			return false;
		}

		for (int i : tileList) {
			if (i == 0) {
				continue;
			}

			if (target.getNumber() == i) {
				return true;
			}
		}

		return false;
	}

	// Select tiles randomly
	public static Tile chooseRandomTile(Tile[] tofliptiles) {
		if (tofliptiles == null || tofliptiles.length == 0) {
			return null; // Handling when the tile list is empty
		}
		Random rand = new Random();
		Tile result = tofliptiles[rand.nextInt(tofliptiles.length)];
		while (result == null) {
			result = tofliptiles[rand.nextInt(tofliptiles.length)];
		}
		return result;
	}

	public int getNumber() {
		return number;
	}

	// Return flip or not
	public boolean isFaceUp() {
		return isFaceUp;
	}

	public void flip() {
		isFaceUp = !isFaceUp;

		button.setText(isFaceUp ? String.valueOf(number) : "<");

	}

	public JButton getButton() {
		return button;
	}

	public String toString() {
		return " " + number + " ";
	}

	public String toStringNumber() {
		return " " + number % 100 + " ";
	}

	private void updateBackgroundColor() {
		if (number / 100 == 1) {
			button.setBackground(Color.WHITE);
			button.setForeground(Color.BLACK);
		} else if (number / 100 == 2) {
			button.setBackground(Color.BLACK);
			button.setForeground(Color.WHITE);
		}

	}
}
