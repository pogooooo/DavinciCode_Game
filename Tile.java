import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class Tile {

    private int number;
    private boolean isFaceUp;
    private JButton button;

    public Tile(int number) {
        this.number = number;
        this.isFaceUp = false;
        this.button = new JButton("?");

        updateBackgroundColor();
        button.setPreferredSize(new Dimension(100, 30));
    }

//    public static void sortTile(Tile[] tiles) {
//        Arrays.sort(tiles, new Comparator<Tile>() {
//            @Override
//            public int compare(Tile t1, Tile t2) {
//                return Integer.compare(t1.getNumber(), t2.getNumber());
//            }
//        });
//    }

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

    public int getNumber(){
        return number;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void flip(){
        isFaceUp = !isFaceUp;

        button.setText(isFaceUp ? String.valueOf(number) : "?");
    }

    public JButton getButton() {
        return button;
    }

    public String toString() {
        return " " + number + " ";
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
