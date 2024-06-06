import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

public class Tile {

    private int number;
    private boolean isFaceUp;
    private JButton button;

    public Tile(int number) {
        this.number = number;
        this.isFaceUp = false;
        this.button = new JButton("<");

        updateBackgroundColor();
        button.setPreferredSize(new Dimension(100, 30));
    }

    //배열 정렬
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

    // 타일 배열의 모든 타일이 뒤집혀졌는지 확인
    public static boolean isFullFaced(Tile[] tiles) {
        for (Tile tile : tiles) {
            if(tile != null){
                if(!tile.isFaceUp()){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isInList(Tile target, int[] tileList){
        if(target == null){
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

    public static Tile chooseRandomTile(Tile[] tofliptiles) {
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

    public int getNumber(){
        return number;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void flip(){
        isFaceUp = !isFaceUp;

        button.setText(isFaceUp ? String.valueOf(number) : "<");

//        if(!isFaceUp){
//            new ImageIcon(Objects.requireNonNull(getClass().getResource("/Tiles/" + number + ".png")));
//        }
//        else if(number/100 == 1){
//            new ImageIcon(Objects.requireNonNull(getClass().getResource("/Tiles/" + 100 + ".png")));
//        }
//        else{
//            new ImageIcon(Objects.requireNonNull(getClass().getResource("/Tiles/" + 200 + ".png")));
//        }

    }

    public JButton getButton() {
        return button;
    }

    public String toString() {
        return " " + number + " ";
    }

    public String toStringNumber(){
        return " " + number%100 + " ";
    }

    private void updateBackgroundColor() {
        if (number / 100 == 1) {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        } else if (number / 100 == 2) {
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
        }

//        String imagePath = "/Tiles/" + number + ".png";
//        URL imgURL = getClass().getResource(imagePath);
//        if (imgURL != null) {
//            ImageIcon imageIcon = new ImageIcon(imgURL);
//            button.setIcon(imageIcon);
//        } else {
//            System.out.println("이미지 파일을 찾을 수 없습니다: " + imagePath);
//        }
//        button.setFocusPainted(false);
//        button.setContentAreaFilled(false);
    }
}
