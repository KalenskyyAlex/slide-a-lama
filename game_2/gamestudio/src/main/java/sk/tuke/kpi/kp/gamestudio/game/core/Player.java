package sk.tuke.kpi.kp.gamestudio.game.core;

public class Player {
    private final int id;
    private int score;
    private String nickname;

    public Player(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
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


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
