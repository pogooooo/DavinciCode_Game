import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameUI extends JPanel {
    private JPanel playerTilePanel, comTilePanel, mainPanel, playerBtnPanel, comBtnPanel;
    private Tile selectedTile;
    private Tile[] computerDeckGame;
    private Tile[] playerDeckGame;
    private Tile[] tilesGame;
    private Tile[] fullTileGame;

    public GameUI(Tile[] computerDeck, Tile[] playerDeck, Tile[] fullTiles, Tile[] tiles) {

        computerDeckGame = computerDeck;
        playerDeckGame = playerDeck;
        tilesGame = tiles;
        fullTileGame = fullTiles;


        setBackground(new Color(13, 13, 13));
        setLayout(null);

        // ** set Panel
        comTilePanel = new JPanel();
        comTilePanel.setBounds(0, 0, 1000, 120);
        comTilePanel.setBackground(Color.white);
        for (Tile tile : computerDeck) {
            if (tile != null) {
                JButton button = tile.getButton();
                button.setPreferredSize(new Dimension(70, 100));  // 버튼 크기 설정
                button.addActionListener(new ComTileButtonListener());
                comTilePanel.add(button);
            }
        }
        add(comTilePanel);

        comBtnPanel = new JPanel();
        comBtnPanel.setBounds(0, 120, 1000, 10);
        comBtnPanel.setBackground(Color.black);
        comBtnPanel.setLayout(null);
        add(comBtnPanel);

        mainPanel = new JPanel();
        mainPanel.setBounds(0, 130, 1000, 400);
        mainPanel.setBackground(Color.white);
        for (Tile tile : fullTiles) {
            if (tile != null) {
                JButton button = tile.getButton();
                button.setPreferredSize(new Dimension(70, 100));  // 버튼 크기 설정
                button.addActionListener(new MainPanelButtonListener());
                mainPanel.add(button);
            }
        }
        add(mainPanel);

        playerBtnPanel = new JPanel();
        playerBtnPanel.setBounds(0, 530, 1000, 10); // x, y, w, h
        playerBtnPanel.setBackground(Color.black);
        playerBtnPanel.setLayout(null);
        add(playerBtnPanel);

        playerTilePanel = new JPanel();
        addPlayerTile(tiles, playerDeck, playerTilePanel);
        playerTilePanel.setBounds(0, 540, 1000, 120); // x, y, w, h
        playerTilePanel.setBackground(Color.white);
        for (Tile tile : playerDeck) {
            if (tile != null) {
                JButton button = tile.getButton();
                button.setPreferredSize(new Dimension(70, 100));  // 버튼 크기 설정
                button.setText(tile.toString());
                playerTilePanel.add(button);
            }
        }
        add(playerTilePanel);
    }

    private class ComTileButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { //플레이어턴에 컴퓨터 타일 선택
            JButton clickedButton = (JButton) e.getSource(); //클릭된 버튼
            if (selectedTile == getTileByButton(clickedButton, computerDeckGame)) { //선택된 버튼이 있는데 같은 버튼 클릭
                selectedTile = null; //선택 해제하기
                clickedButton.setBorder(BorderFactory.createLineBorder(Color.black));
            }
            else { //선택할 버튼이 선택되지 않았을 때 or 다른 버튼 선택하려고 할 때
                if (selectedTile != null) {selectedTile.getButton().setBorder(BorderFactory.createLineBorder(Color.black));}
                selectedTile = getTileByButton(clickedButton, computerDeckGame);
                clickedButton.setBorder(BorderFactory.createLineBorder(Color.blue));
            }
        }
    }

    private class MainPanelButtonListener implements ActionListener { //플레이어 턴에 컴퓨터 타일 맞추기
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            if(checkEnd(playerDeckGame, computerDeckGame) == 1){
                System.out.println("computer win");
            }
            else if (checkEnd(playerDeckGame, computerDeckGame) == 2){
                System.out.print("player win");
            }

            if(selectedTile == null) {System.out.println("no selected tile");} //상대 카드 선택 안하고 맞추려는 경우
            else {
                Tile tempTile = getTileByButton(clickedButton, fullTileGame);
                if(selectedTile.getNumber() == tempTile.getNumber()){ //상대 카드를 맞춘 경우
                    selectedTile.getButton().setBorder(BorderFactory.createLineBorder(Color.black));
                    selectedTile.flip();
                }

                else { // 상대 카드를 맞추기 못한 경우
                    //내 덱에 하나 까기
                    tempTile = chooseRandomTile(playerDeckGame);
                    while (!tempTile.isFaceUp()){
                        tempTile.flip();
                    }

                    for(int i = 0 ; i < 11 ; i++ ){
                        if(playerDeckGame[i] != null && playerDeckGame[i].isFaceUp()){
                            playerDeckGame[i].getButton().setBorder(BorderFactory.createLineBorder(Color.yellow));
                        }
                    }
                }
            }
        }
    }

    private JPanel addPlayerTile(Tile[] tilesGame, Tile[] playerDeckGame, JPanel playerTilePanel) {

        // tilesGame에서 마지막 타일 가져오기
        for (int i = tilesGame.length - 1; i >= 0; i--) {
            if (tilesGame[i] != null) {
                selectedTile = tilesGame[i];
                tilesGame[i] = null;
                break;
            }
        }

        if (selectedTile == null) {
            System.out.println("No tile to add");
            return null;
        }

        // playerDeckGame에 타일 추가
        for (int i = 0; i < playerDeckGame.length; i++) {
            if (playerDeckGame[i] == null) {
                playerDeckGame[i] = selectedTile;
                break;
            }
        }

        // playerTilePanel에 버튼 추가
        JButton button = selectedTile.getButton();
        button.setPreferredSize(new Dimension(70, 100));  // 버튼 크기 설정
        button.setText(selectedTile.toString());
        playerTilePanel.add(button);

        // UI 업데이트
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

    private Tile chooseRandomTile(Tile[] tofliptiles) {
        if (tofliptiles == null || tofliptiles.length == 0) {
            return null; // 타일 리스트가 비어있을 때를 대비한 처리
        }
        Random rand = new Random();
        Tile result = tofliptiles[rand.nextInt(tofliptiles.length)];
        while (result == null) {
            result = tofliptiles[rand.nextInt(tofliptiles.length)];
        }
        return result;
    }

    //컴퓨터 알고리즘 시작 시에도 작동
    private int checkEnd(Tile[] player, Tile[] computer){
        if(Tile.isFullFaced(player)) {
            return 1; //computer player win
        }

        if(Tile.isFullFaced(computer)){
            return 2; //player win
        }

        return 0;
    }

    public Tile[] returnPlayerDeck(){
        return playerDeckGame;
    }

    public Tile[] returnTiles(){
        return tilesGame;
    }
}
