package sk.tuke.kpi.kp.gamestudio.service.impl;

import sk.tuke.kpi.kp.gamestudio.entity.Rating;
import sk.tuke.kpi.kp.gamestudio.service.RatingException;
import sk.tuke.kpi.kp.gamestudio.service.RatingService;

import java.sql.*;

@Deprecated
public class RatingServiceJDBC implements RatingService {

    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String INSERT = "INSERT INTO rating (player, game, rating, ratedOn) VALUES (?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE rating SET rating = ?, ratedOn = ? WHERE player = ? and game = ?";
    public static final String AVG_RATING = "SELECT AVG(rating) FROM rating WHERE game = ?";
    public static final String DELETE = "DELETE FROM rating WHERE game = ?";
    public static final String CONFILCT = "SELECT rating FROM rating WHERE player = ? and game = ?";


    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement;

            if(getRating(rating.getGame(), rating.getPlayer()) == -1){
                statement = connection.prepareStatement(INSERT);

                statement.setString(1, rating.getPlayer());
                statement.setString(2, rating.getGame());
                statement.setInt(3, rating.getRating());
                statement.setTimestamp(4, rating.getRatedOn());

                statement.executeUpdate();
            }
            else {
                statement = connection.prepareStatement(UPDATE);

                statement.setInt(1, rating.getRating());
                statement.setTimestamp(2, rating.getRatedOn());
                statement.setString(3, rating.getPlayer());
                statement.setString(4, rating.getGame());

                statement.executeUpdate();
            }

            statement.close();
        } catch (SQLException e) {
            throw new RatingException("Invalid Rating insert/update for: " + rating.toString(), e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(AVG_RATING)) {

            statement.setString(1, game);

            ResultSet results = statement.executeQuery();
            if(!results.next()) throw new SQLException("Empty set");

            return (int) results.getFloat(1);
        } catch (SQLException e) {
            throw new RatingException("Unable to get average rating for game: " + game, e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(CONFILCT)) {

            statement.setString(1, player);
            statement.setString(2, game);

            ResultSet results = statement.executeQuery();
            if(!results.next()) return -1;

            return results.getInt(1);
        } catch (SQLException e) {
            throw new RatingException("Unable to get rating for player: " + player + " in game: " + game, e);
        }
    }

    @Override
    public void reset(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(DELETE);
        ) {
            statement.setString(1, game);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RatingException("Problem deleting ratings", e);
        }
    }
}
