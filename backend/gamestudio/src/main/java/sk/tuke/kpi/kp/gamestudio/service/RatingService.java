package sk.tuke.kpi.kp.gamestudio.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sk.tuke.kpi.kp.gamestudio.entity.Rating;

@Component
public interface RatingService {
    void setRating(Rating rating) throws RatingException;
    int getAverageRating(String game) throws RatingException;
    int getRating(String game, String player) throws RatingException;
    void reset(String game) throws RatingException;
}
