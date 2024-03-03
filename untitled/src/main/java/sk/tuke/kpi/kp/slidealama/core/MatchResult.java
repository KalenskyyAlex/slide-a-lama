package sk.tuke.kpi.kp.slidealama.core;

public class MatchResult {
    private final int x;
    private final int y;
    private final int orientation;
    private final int length;
    private final int scoreMultiplier;
    private final int lengthMultiplier;

    public MatchResult(int x, int y, int orientation, int length, int scoreMultiplier, int lengthMultiplier) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.length = length;
        this.scoreMultiplier = scoreMultiplier;
        this.lengthMultiplier = lengthMultiplier;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOrientation() {
        return orientation;
    }

    public int getLength() {
        return length;
    }

    public int getScoreMultiplier() {
        return scoreMultiplier;
    }

    public int getLengthMultiplier() {
        return lengthMultiplier;
    }

    @Override
    public String toString() {
        return "MatchResult{" +
                "x=" + x +
                ", y=" + y +
                ", orientation=" + orientation +
                ", length=" + length +
                ", scoreMultiplier=" + scoreMultiplier +
                ", lengthMultiplier=" + lengthMultiplier +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchResult that = (MatchResult) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        if (orientation != that.orientation) return false;
        if (length != that.length) return false;
        if (scoreMultiplier != that.scoreMultiplier) return false;
        return lengthMultiplier == that.lengthMultiplier;
    }
}
