package sk.tuke.kpi.kp.slidealama.core;

public enum Tile {

    EMPTY(0), RED(10), GREEN(20), YELLOW(30), BLUE(40), PURPLE(70), CYAN(100), WHITE(150);

    private final int scoreMultiplier;

    Tile(int scoreMultiplier){
        this.scoreMultiplier = scoreMultiplier;
    }

    public int getScoreMultiplier(){
        return scoreMultiplier;
    }

}
