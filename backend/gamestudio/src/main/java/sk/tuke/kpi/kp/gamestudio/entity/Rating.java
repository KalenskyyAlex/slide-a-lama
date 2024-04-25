package sk.tuke.kpi.kp.gamestudio.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@NamedQuery(name = "Rating.reset",
        query = "DELETE FROM Rating WHERE game = :game")
@NamedQuery(name = "Rating.getRating",
        query = "SELECT rating FROM Rating WHERE player = :player and game = :game")
@NamedQuery(name = "Rating.getAverageRating",
        query = "SELECT AVG(rating) FROM Rating WHERE game = :game")
public class Rating implements Serializable {
    @Id
    @GeneratedValue
    private int ident;

    private String game;
    private int rating;
    private String player;
    private Timestamp ratedOn;

    public Rating(){}

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

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}
