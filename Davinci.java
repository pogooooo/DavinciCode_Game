import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;


public class Davinci {
    private Tile[] tiles;
    private Tile[] fullTiles;
    private Tile[] computerDeck;
    private Tile[] playerDeck;
    private static JFrame frame;
    private static JPanel currentPanel;

    public Davinci() {
        tiles = new Tile[22];
        fullTiles = new Tile[22];
        computerDeck = new Tile[11];
        playerDeck = new Tile[11];
        initTiles(fullTiles);

        frame = new JFrame("DaVinci Code");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        // set Panel
        currentPanel = new StartUI();
        frame.add(currentPanel);
        frame.setVisible(true);
    }

    //create 22tiles
    private void initTiles(Tile[] tile) {
        for (int i = 0 ; i < 22 ; i++) {
            if (i < 11) {tile[i] = new Tile(i + 101);}
            else  {tile[i] = new Tile(i + 200 - 10);}
        }
    }

    public void startGame() {
        distributeTiles();
        printDecks();
        computerTrun();
    }

    public void playerTurn() {
        frame.remove(currentPanel);
        currentPanel = new GameUI(computerDeck, playerDeck, fullTiles, tiles);
        frame.add(currentPanel);
        frame.repaint();

        System.out.println("player turn");
    }

    public void computerTrun() {
        frame.remove(currentPanel);
        currentPanel = new GameUI(computerDeck, playerDeck, fullTiles, tiles);
        frame.add(currentPanel);
        frame.repaint();

        System.out.println("computer turn");
    }


    public void printDecks() {
        System.out.println("Computer Deck:");
        for (Tile tile : computerDeck) {
            if (tile != null) {
                System.out.println(tile.getNumber());
            }
        }

        System.out.println("\nPlayer Deck:");
        for (Tile tile : playerDeck) {
            if (tile != null) {
                System.out.println(tile.getNumber());
            }
        }
    }

    //타일 초기 분배
    private void distributeTiles(){
        // 타일을 무작위로 섞기 위한 로직을 추가합니다.
        java.util.Collections.shuffle(java.util.Arrays.asList(fullTiles));

        // 컴퓨터와 플레이어 덱에 타일 분배
        System.arraycopy(fullTiles, 0, computerDeck, 0, 3);
        System.arraycopy(fullTiles, 3, playerDeck, 0, 3);
        System.arraycopy(fullTiles, 6, tiles, 0, 16);
        initTiles(fullTiles);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Davinci game = new Davinci();
            game.startGame();
        });
    }
}
