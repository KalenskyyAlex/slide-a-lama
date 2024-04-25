package sk.tuke.kpi.kp.gamestudio.service.impl;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.gamestudio.entity.Rating;
import sk.tuke.kpi.kp.gamestudio.service.RatingException;
import sk.tuke.kpi.kp.gamestudio.service.RatingService;

public class RatingServiceRestClient implements RatingService {

    private final String url = "http://localhost:8080/api/rating";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return restTemplate.getForEntity(url + "/" + game + "/average", Integer.class).getBody().intValue();
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return restTemplate.getForEntity(url + "/" + game + "/" + player, Integer.class).getBody().intValue();
    }

    @Override
    public void reset(String game) throws RatingException {
        restTemplate.delete(url + "/" + game);
    }
}
