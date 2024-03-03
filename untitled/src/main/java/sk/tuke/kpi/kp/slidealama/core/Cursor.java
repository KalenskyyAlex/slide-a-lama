package sk.tuke.kpi.kp.slidealama.core;

public class Cursor {
    public enum Side{
        UP, LEFT, RIGHT
    }

    private Cursor.Side side;
    private int position;

    public Cursor(int position, Cursor.Side side){
        this.position = position;
        this.side = side;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return side;
    }
}
