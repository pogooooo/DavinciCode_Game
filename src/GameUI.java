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
    ComputerPlayer cm = new ComputerPlayer();

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
        comTilePanel.setBackground(Color.black);
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
        comBtnPanel.setBackground(Color.white);
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
                button.setText(tile.toStringNumber());
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
        playerTilePanel.setBackground(Color.black);
        for (Tile tile : playerDeck) {
            if (tile != null) {
                JButton button = tile.getButton();
                button.setPreferredSize(new Dimension(70, 100));  // 버튼 크기 설정
                button.setText(tile.toStringNumber());
                playerTilePanel.add(button);
            }
        }
        add(playerTilePanel);
    }

    private class ComTileButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { //플레이어턴에 컴퓨터 타일 선택
            JButton clickedButton = (JButton) e.getSource(); //클릭된 버튼
            Tile clickedTile = getTileByButton(clickedButton, computerDeckGame);

            if (clickedTile != null && !clickedTile.isFaceUp()) { // 타일이 선택 가능하고 앞면이 아닌 경우에만 선택
                if (selectedTile == clickedTile) { //선택된 버튼이 있는데 같은 버튼 클릭
                    selectedTile = null; //선택 해제하기
                    clickedButton.setBorder(BorderFactory.createLineBorder(Color.black));
                } else { //선택할 버튼이 선택되지 않았을 때 or 다른 버튼 선택하려고 할 때
                    if (selectedTile != null) {
                        selectedTile.getButton().setBorder(BorderFactory.createLineBorder(Color.black));
                    }
                    selectedTile = clickedTile;
                    clickedButton.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
                }
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

            if(selectedTile == null) {
                System.out.println("no selected tile"); //상대 카드 선택 안하고 맞추려는 경우
                return;
            }

            Tile tempTile = getTileByButton(clickedButton, fullTileGame);
            if(selectedTile.getNumber() == tempTile.getNumber()){ //상대 카드를 맞춘 경우
                selectedTile.getButton().setBorder(BorderFactory.createLineBorder(Color.black));
                selectedTile.flip();
                selectedTile = null;
            } else { // 상대 카드를 맞추기 못한 경우
                //내 덱에 하나 오픈
                tempTile = Tile.chooseRandomTile(playerDeckGame);
                while (tempTile.isFaceUp()){
                    tempTile = Tile.chooseRandomTile(playerDeckGame);
                }
                tempTile.flip();

                for(int i = 0; i < 11; i++){
                    if(playerDeckGame[i] != null && playerDeckGame[i].isFaceUp()){
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

    private void computerTurn(){
        Tile[] selectTile;

        if(checkEnd(playerDeckGame, computerDeckGame) == 1){
            System.out.println("computer win");
            return;
        } else if (checkEnd(playerDeckGame, computerDeckGame) == 2){
            System.out.print("player win");
            return;
        }

        //덱에 카드 추가

        //카드 맞추기
        selectTile = cm.selectTile(playerDeckGame, computerDeckGame, fullTileGame);

        //맞췄을 때 : 상대 타일 공개하고 한번 더 맞추기
        if(selectTile[0].getNumber() == selectTile[1].getNumber()){
            for(Tile tile : playerDeckGame){
                if(tile == null){continue;}
                if(tile.getNumber() == selectTile[0].getNumber()){
                    tile.flip();
                }
            }

            computerTurn();
        } else {
            Tile tempTile;
            //내 덱 하나 까기
            tempTile = Tile.chooseRandomTile(computerDeckGame);
            while (tempTile.isFaceUp()){
                tempTile = Tile.chooseRandomTile(computerDeckGame);
            }
            tempTile.flip();
            tempTile.getButton().setText(tempTile.toStringNumber());
        }

        //테스트를 위한 출력으로 지워도 됩니다.
        System.out.println("target:"+selectTile[0]+" answer:"+selectTile[1]);
        System.out.println("computer deck and state");
        for(Tile tile : computerDeckGame){
            if(tile == null){continue;}
            System.out.println("number: "+tile.getNumber()+" state: "+tile.isFaceUp());
        }

        //플레이어 덱 추가
    }

    private JPanel addComputerTile(Tile[] tilesGame, Tile[] computerDeckGame, JPanel computerTilePanel) {
        Tile addTile = null;

        // tilesGame에서 마지막 타일 가져오기
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

        // computerDeckGame에 타일 추가
        for (int i = 0; i < 11; i++) {
            if (computerDeckGame[i] == null) {
                computerDeckGame[i] = addTile;
                break;
            }
        }

        Tile.sortByModulo(computerDeckGame);

        computerTilePanel.removeAll();

        // computerTilePanel에 버튼 추가
        for (Tile tile : computerDeckGame) {
            if (tile != null) {
                JButton button = tile.getButton();
                for (ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }
                button.addActionListener(new ComTileButtonListener()); // ActionListener 추가
                button.setPreferredSize(new Dimension(70, 100));  // 버튼 크기 설정
                computerTilePanel.add(button);
            }
        }

        // UI 업데이트
        computerTilePanel.revalidate();
        computerTilePanel.repaint();

        return computerTilePanel;
    }


    //플레이어 덱에 카드 하나 추가
    private JPanel addPlayerTile(Tile[] tilesGame, Tile[] playerDeckGame, JPanel playerTilePanel) {
        Tile addTile = null;

        // tilesGame에서 마지막 타일 가져오기
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

        // playerDeckGame에 타일 추가
        for (int i = 0; i < 11; i++) {
            if (playerDeckGame[i] == null) {
                playerDeckGame[i] = addTile;
                break;
            }
        }

        Tile.sortByModulo(playerDeckGame);

        playerTilePanel.removeAll();

        // playerTilePanel에 버튼 추가
        for (Tile tile : playerDeckGame) {
            if (tile != null) {
                JButton button = tile.getButton();
                button.setPreferredSize(new Dimension(70, 100));  // 버튼 크기 설정
                button.setText(tile.toStringNumber());
                playerTilePanel.add(button);
            }
        }

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
