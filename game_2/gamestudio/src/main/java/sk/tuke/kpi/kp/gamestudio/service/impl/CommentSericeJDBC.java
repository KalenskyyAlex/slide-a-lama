package sk.tuke.kpi.kp.gamestudio.service.impl;

import sk.tuke.kpi.kp.gamestudio.entity.Comment;
import sk.tuke.kpi.kp.gamestudio.service.CommentException;
import sk.tuke.kpi.kp.gamestudio.service.CommentService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class CommentSericeJDBC implements CommentService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String SELECT = "SELECT game, comment, player, commentedOn FROM comment WHERE game = ? ORDER BY commentedOn DESC";
    public static final String DELETE = "DELETE FROM comment WHERE game = ?";
    public static final String INSERT = "INSERT INTO comment (game, comment, player, commentedOn) VALUES (?, ?, ?, ?)";

    @Override
    public void addComment(Comment comment) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, comment.getGame());
            statement.setString(2, comment.getComment());
            statement.setString(3, comment.getPlayer());
            statement.setTimestamp(4, comment.getCommentedOn());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException("Invalid Comment insert for: " + comment.toString(), e);
        }
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                List<Comment> scores = new ArrayList<>();
                while (rs.next()) {
                    scores.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
                }
                return scores;
            }
        } catch (SQLException e) {
            throw new CommentException("Couldn't retrieve any comments for game: " + game, e);
        }
    }

    @Override
    public void reset(String game) throws CommentException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(DELETE);
        ) {
            statement.setString(1, game);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException("Problem deleting comments", e);
        }
    }
}
