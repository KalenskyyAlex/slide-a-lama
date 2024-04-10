package sk.tuke.kpi.kp.gamestudio.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import org.springframework.transaction.annotation.Transactional;
import sk.tuke.kpi.kp.gamestudio.entity.Rating;
import sk.tuke.kpi.kp.gamestudio.service.RatingException;
import sk.tuke.kpi.kp.gamestudio.service.RatingService;

@Transactional
public class RatingServiceJPA implements RatingService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("gamestudio");
    EntityManager entityManager = emf.createEntityManager();

    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.getTransaction().begin();
        entityManager.merge(rating);
        entityManager.getTransaction().commit();
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return ((Number) entityManager.createNamedQuery("Rating.getAverageRating").setParameter("game", game).getSingleResult()).intValue();
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            return ((Number) entityManager.createNamedQuery("Rating.getRating").setParameter("game", game).setParameter("player", player).getSingleResult()).intValue();
        }
        catch (NoResultException e) {
            return -1;
        }
    }

    @Override
    public void reset(String game) throws RatingException {
        entityManager.createNamedQuery("Rating.reset").setParameter("game", game).executeUpdate();
    }

}
