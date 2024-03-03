package sk.tuke.kpi.kp.slidealama.core;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class TileFront {

    private final int fixedLength;
    private Queue<Tile> queue;
    private Random generator;
    private Tile[] tiles = Tile.values();


    public TileFront(int length){
        this.fixedLength = length;
        queue = new PriorityQueue<>();
        generator = new Random();

        for(int i = 0; i < this.fixedLength; i++){
            queue.add(tiles[generator.nextInt(tiles.length)]);
        }
    }

    public Tile pull(){
        Tile front = queue.peek();

        queue.add(tiles[generator.nextInt(tiles.length)]);

        return front;
    }

    public int getFixedLength(){
        return fixedLength;
    }

    public List<Tile> toList(){
        return queue.stream().toList();
    }
}
