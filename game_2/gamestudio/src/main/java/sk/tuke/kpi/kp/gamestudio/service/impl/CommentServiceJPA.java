package sk.tuke.kpi.kp.gamestudio.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.kpi.kp.gamestudio.entity.Comment;
import sk.tuke.kpi.kp.gamestudio.service.CommentException;
import sk.tuke.kpi.kp.gamestudio.service.CommentService;

import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws CommentException {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return entityManager.createNamedQuery("Comment.getComments").setParameter("game", game).getResultList();
    }

    @Override
    public void reset(String game) throws CommentException {
        entityManager.createNamedQuery("Comment.reset").setParameter("game", game).executeUpdate();
    }

}
