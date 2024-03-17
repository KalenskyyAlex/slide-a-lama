package sk.tuke.kpi.kp.slidealama.dao.model;


import java.sql.Timestamp;

public class Rating {
    private String game;
    private int rating;
    private String player;
    private Timestamp ratedOn;

    public Rating(String game, int rating, String player, Timestamp ratedOn) {
        this.game = game;
        this.rating = rating;
        this.player = player;
        this.ratedOn = ratedOn;
    }

    public Timestamp getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Timestamp ratedOn) {
        this.ratedOn = ratedOn;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "game='" + game + '\'' +
                ", stars=" + rating +
                ", author='" + player + '\'' +
                ", ratedAt=" + ratedOn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating1 = (Rating) o;

        if (rating != rating1.rating) return false;
        if (!game.equals(rating1.game)) return false;
        if (!player.equals(rating1.player)) return false;
        return ratedOn.equals(rating1.ratedOn);
    }

    @Override
    public int hashCode() {
        int result = game.hashCode();
        result = 31 * result + rating;
        result = 31 * result + player.hashCode();
        result = 31 * result + ratedOn.hashCode();
        return result;
    }
}
