package sk.tuke.kpi.kp.slidealama.core;

public class Player {
    private final int id;
    private int score;

    public Player(int id) {
        this.id = id;
        score = 500;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
