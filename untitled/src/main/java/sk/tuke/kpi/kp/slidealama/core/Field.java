package sk.tuke.kpi.kp.slidealama.core;

import java.util.Random;

public class Field {

    private static final int boardSize = 5;

    private Tile[][] tiles = new Tile[boardSize][boardSize];

    private TileFront front = new TileFront(3);

    private Cursor cursor = new Cursor(1, Cursor.Side.LEFT);

    public void generate() {
        do {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    tiles[i][j] = Tile.values()[new Random().nextInt(7)];
                }
            }
        } while (checkForMatch() != null);
    }
    
    public void insert(){
        int pos = getCursor().getPosition() - 1;
        switch (getCursor().getSide()) {
            case UP -> {
                for(int i = tiles.length - 1; i > 0;  i--){
                    tiles[i][pos] = tiles[i - 1][pos];
                }

                tiles[0][pos] = front.pull();
            }
            case LEFT -> {
                for(int j = tiles.length - 1; j > 0; j--){
                    tiles[tiles.length - pos - 1][j] = tiles[tiles.length - pos - 1][j - 1];
                }
                
                tiles[tiles.length - pos - 1][0] = front.pull();
            }
            case RIGHT -> {
                for(int j = 0; j < tiles.length - 1; j++){
                    tiles[pos][j] = tiles[pos][j + 1];
                }

                tiles[pos][tiles.length - 1] = front.pull();
            }
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
}
