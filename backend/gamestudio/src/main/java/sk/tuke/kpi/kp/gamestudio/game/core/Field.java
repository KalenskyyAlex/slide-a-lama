package sk.tuke.kpi.kp.gamestudio.game.core;

import java.util.Random;

public class Field {

    private static final int boardSize = 5;

    private Tile[][] tiles = new Tile[boardSize][boardSize];

    private TileFront front = new TileFront(4);

    private Cursor cursor = new Cursor(1, Cursor.Side.LEFT);

    public void generate() {
        do {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    tiles[i][j] = Tile.values()[new Random().nextInt(1, 8)];
                }
            }
        } while (checkForMatch() != null);
    }
    
    public void insert(){
        int pos = getCursor().getPosition() - 1;
        switch (getCursor().getSide()) {
            case UP -> {
                boolean leadingEmpty = false;

                for(int i = 0; i < tiles.length; i ++){
                    if(tiles[i][pos] == Tile.EMPTY) {
                        tiles[0][pos] = front.pull();
                        leadingEmpty = true;
                        break;
                    }
                }

                if(!leadingEmpty){
                    for(int i = tiles.length - 1; i > 0;  i--){
                        tiles[i][pos] = tiles[i - 1][pos];
                    }

                    tiles[0][pos] = front.pull();
                }
            }
            case LEFT -> {
                boolean leadingEmpty = false;

                int lastEmptyIndex = 0;
                while(tiles[tiles.length - pos - 1][lastEmptyIndex] == Tile.EMPTY){
                    leadingEmpty = true;
                    lastEmptyIndex++;
                }
                lastEmptyIndex--;

                if(!leadingEmpty){
                    int start;
                    for(start = 0; start < tiles.length; start++){
                        if(tiles[tiles.length - pos - 1][start] == Tile.EMPTY) break;
                    }
                    if(start == tiles.length) start--;

                    for(int j = start; j > 0; j--){
                        tiles[tiles.length - pos - 1][j] = tiles[tiles.length - pos - 1][j - 1];
                    }

                    tiles[tiles.length - pos - 1][0] = front.pull();
                }
                else{
                    tiles[tiles.length - pos - 1][lastEmptyIndex] = front.pull();
                }
            }
            case RIGHT -> {
                boolean leadingEmpty = false;

                int lastEmptyIndex = tiles.length - 1;
                while(tiles[pos][lastEmptyIndex] == Tile.EMPTY){
                    leadingEmpty = true;
                    lastEmptyIndex--;
                }
                lastEmptyIndex++;

                if(!leadingEmpty){
                    int start;
                    for(start = tiles.length - 1; start >= 0; start--){
                        if(tiles[pos][start] == Tile.EMPTY) break;
                    }
                    if(start == -1) start++;

                    for(int j = start; j < tiles.length - 1; j++){
                        tiles[pos][j] = tiles[pos][j + 1];
                    }

                    tiles[pos][tiles.length - 1] = front.pull();
                }
                else {
                    tiles[pos][lastEmptyIndex] = front.pull();
                }

            }
        }

        for(int i = 0; i < tiles.length; i++){
            applyGravity();
        }
    }

    public Tile[][] getTiles()
    {
        return tiles;
    }

    public void setTiles(Tile[][] tiles)
    {
        this.tiles = tiles;
    }

    public MatchResult checkForMatch() {
        for(int i = Match.values().length - 1; i >= 0; i--){
            MatchResult buffer = Match.values()[i].checkForMatch(tiles);

            if (buffer != null) return buffer;
        }

        return null;
    }

    public TileFront getFront() {
        return front;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public void update(MatchResult m) {
        if (m == null) return;

        if(m.getOrientation() == 0){
            int i = m.getY();
            for(int j = 0; j < m.getLength(); j++){
                tiles[i][j + m.getX()] = Tile.EMPTY;
            }
        }
        else {
            int j = m.getX();
            for(int i = 0; i < m.getLength(); i++) {
                tiles[i + m.getY()][j] = Tile.EMPTY;
            }
        }

        for(int i = 0; i < tiles.length; i++){
            applyGravity();
        }
    }

    private void applyGravity(){
        for(int j = 0; j < tiles.length; j++){
            for(int i = tiles.length - 1; i > 0; i--){
                if (tiles[i][j] == Tile.EMPTY){
                    tiles[i][j] = tiles[i - 1][j];
                    tiles[i - 1][j] = Tile.EMPTY;
                }
            }
        }
    }

    public void setFront(TileFront front) {
        this.front = front;
    }
}
