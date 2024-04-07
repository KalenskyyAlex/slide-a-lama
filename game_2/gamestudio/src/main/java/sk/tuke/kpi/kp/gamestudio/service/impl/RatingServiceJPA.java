package sk.tuke.kpi.kp.gamestudio.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.kpi.kp.gamestudio.entity.Rating;
import sk.tuke.kpi.kp.gamestudio.service.RatingException;
import sk.tuke.kpi.kp.gamestudio.service.RatingService;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.merge(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return ((Number) entityManager.createNamedQuery("Rating.getAverageRating").setParameter("game", game).getSingleResult()).intValue();
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return ((Number) entityManager.createNamedQuery("Rating.getRating").setParameter("game", game).setParameter("player", player).getSingleResult()).intValue();
    }

    @Override
    public void reset(String game) throws RatingException {
        entityManager.createNamedQuery("Rating.reset").setParameter("game", game).executeUpdate();
    }

}
