import java.util.Random;


public class ComputerPlayer {

    public Tile[] selectTile(Tile[] player, Tile[] computer, Tile[] total){
        int[] white = new int[11], black = new int[11];
        int windex=0, bindex=0;

        Tile selectTile = null;
        Tile targetTile = null;

        Tile[] result = new Tile[2];


        for(Tile tile : computer){
            if(tile == null){continue;}
            else if(tile.getNumber()/100 == 1){
                white[windex] = tile.getNumber();
                windex = windex + 1;
            }
            else{
                black[bindex] = tile.getNumber();
                bindex = bindex + 1;
            }
        }

        for(Tile tile : player){
            if(tile == null) {continue;}
            if(tile.isFaceUp()){
                if(tile.getNumber()/100 == 1){
                    white[windex] = tile.getNumber();
                    windex = windex + 1;
                }
                else{
                    black[bindex] = tile.getNumber();
                    bindex = bindex + 1;
                }
            }
        }

        if(windex >= bindex){
            for(Tile tile : player){
                if(tile == null){continue;}
                else if(!tile.isFaceUp()){
                    if(tile.getNumber()/100 == 1){
                        targetTile = tile;
                    }
                }
            }

            if(targetTile == null){
                targetTile = Tile.chooseRandomTile(player);
                while(!targetTile.isFaceUp()){
                    targetTile = Tile.chooseRandomTile(player);
                }
            }

            while(true){
                selectTile = Tile.chooseRandomTile(total);
                if(selectTile.getNumber()/100 == 1 && !Tile.isInList(selectTile, white)){
                    break;
                }
            }
        }
        else{
            for(Tile tile : player){
                if(tile == null){continue;}
                else if(!tile.isFaceUp()){
                    if(tile.getNumber()/100 == 2){
                        targetTile = tile;
                    }
                }
            }

            if(targetTile == null){
                targetTile = Tile.chooseRandomTile(player);
                while(!targetTile.isFaceUp()){
                    targetTile = Tile.chooseRandomTile(player);
                }
            }

            while(true){
                selectTile = Tile.chooseRandomTile(total);
                if(selectTile.getNumber()/100 == 2 && !Tile.isInList(selectTile, black)){
                    break;
                }
            }
        }

        result[0] = targetTile;
        result[1] = selectTile;

        return result;
    }

}