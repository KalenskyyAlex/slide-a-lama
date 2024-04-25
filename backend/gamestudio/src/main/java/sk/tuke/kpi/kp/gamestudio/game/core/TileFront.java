package sk.tuke.kpi.kp.gamestudio.game.core;

import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class TileFront {

    private final int fixedLength;
    private Queue<Tile> queue;
    private Random generator;
    private Tile[] tiles = Tile.values();


    public TileFront(int length){
        this.fixedLength = length;
        queue = new LinkedList<>();
        generator = new Random();

        for(int i = 0; i < this.fixedLength; i++){
            queue.add(tiles[generator.nextInt(1, tiles.length)]);
        }
    }

    public Tile pull(){
        Tile front = queue.poll();

        queue.add(tiles[generator.nextInt(1, tiles.length)]);

        return front;
    }

    public int getFixedLength(){
        return fixedLength;
    }

    public List<Tile> toList(){
        return queue.stream().toList();
    }
}
