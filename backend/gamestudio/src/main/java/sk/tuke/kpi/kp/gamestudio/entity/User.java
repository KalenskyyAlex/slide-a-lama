package sk.tuke.kpi.kp.gamestudio.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedQuery( name = "User.getUser",
        query = "SELECT u FROM User u WHERE u.game=:game AND u.username = :username")
@NamedQuery( name = "User.reset",
        query = "DELETE FROM User WHERE game=:game")
@Table(name = "users")
public class User implements Serializable {
    private String game;

    private String username;

    private String password;

    @Id
    @GeneratedValue
    private int ident;

    public User() {}

    public User(String game, String username, String password) {
        this.game = game;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "game='" + game + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ident=" + ident +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return ident == user.ident && Objects.equals(game, user.game) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(game);
        result = 31 * result + Objects.hashCode(username);
        result = 31 * result + Objects.hashCode(password);
        result = 31 * result + ident;
        return result;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
