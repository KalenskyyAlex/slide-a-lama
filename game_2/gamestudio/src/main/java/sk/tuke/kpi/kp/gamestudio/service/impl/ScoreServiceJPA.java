package sk.tuke.kpi.kp.gamestudio.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.transaction.annotation.Transactional;
import sk.tuke.kpi.kp.gamestudio.entity.Score;
import sk.tuke.kpi.kp.gamestudio.service.ScoreException;
import sk.tuke.kpi.kp.gamestudio.service.ScoreService;

import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("gamestudio");
    EntityManager entityManager = emf.createEntityManager();

    @Override
    public void addScore(Score score) throws ScoreException {
        entityManager.getTransaction().begin();
        entityManager.persist(score);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Score> getTopScores(String game) throws ScoreException {
        return entityManager.createNamedQuery("Score.getTopScores")
                .setParameter("game", game).setMaxResults(10).getResultList();
    }

    @Override
    public void reset(String game) {
        entityManager.createNamedQuery("Score.resetScores").setParameter("game", game).executeUpdate();
    }
}