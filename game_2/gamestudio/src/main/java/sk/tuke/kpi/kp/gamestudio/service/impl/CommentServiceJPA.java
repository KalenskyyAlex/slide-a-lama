package sk.tuke.kpi.kp.gamestudio.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import sk.tuke.kpi.kp.gamestudio.entity.Comment;
import sk.tuke.kpi.kp.gamestudio.service.CommentException;
import sk.tuke.kpi.kp.gamestudio.service.CommentService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("gamestudio");
    EntityManager entityManager = emf.createEntityManager();

    @Override
    public void addComment(Comment comment) throws CommentException {
        entityManager.getTransaction().begin();
        entityManager.persist(comment);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return entityManager.createNamedQuery("Comment.getComments").setParameter("game", game).getResultList();
    }

    @Override
    public void reset(String game) throws CommentException {
        entityManager.getTransaction().begin();
        entityManager.createNamedQuery("Comment.reset").setParameter("game", game).executeUpdate();
        entityManager.getTransaction().commit();
    }

}
