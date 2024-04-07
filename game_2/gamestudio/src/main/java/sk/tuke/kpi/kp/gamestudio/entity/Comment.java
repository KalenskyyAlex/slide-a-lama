package sk.tuke.kpi.kp.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@NamedQuery(name = "Comment.reset",
            query = "DELETE FROM Comment WHERE game = :game")
@NamedQuery(name = "Comment.getComments",
            query = "SELECT game, comment, player, commentedOn FROM Comment WHERE game = :game ORDER BY commentedOn DESC")
public class Comment implements Serializable {

    @Id
    @GeneratedValue
    private int ident;

    private String game;
    private String comment;
    private String player;
    private Timestamp commentedOn;

    public Comment(){}

    public Comment(String game, String comment, String player, Timestamp commentedOn){
        this.comment = comment;
        this.player = player;
        this.commentedOn = commentedOn;
        this.game = game;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Timestamp getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Timestamp commentedOn) {
        this.commentedOn = commentedOn;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "game='" + game + '\'' +
                ", content='" + comment + '\'' +
                ", author='" + player + '\'' +
                ", postedAt=" + commentedOn +
                '}';
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment1 = (Comment) o;

        if (!game.equals(comment1.game)) return false;
        if (!comment.equals(comment1.comment)) return false;
        if (!player.equals(comment1.player)) return false;
        return commentedOn.equals(comment1.commentedOn);
    }

    @Override
    public int hashCode() {
        int result = game.hashCode();
        result = 31 * result + comment.hashCode();
        result = 31 * result + player.hashCode();
        result = 31 * result + commentedOn.hashCode();
        return result;
    }

    public int getIdent() { return ident; }
    public void setIdent(int ident) { this.ident = ident; }
}
