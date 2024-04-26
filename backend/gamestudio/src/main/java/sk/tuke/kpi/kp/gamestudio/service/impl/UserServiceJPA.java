package sk.tuke.kpi.kp.gamestudio.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import sk.tuke.kpi.kp.gamestudio.entity.User;
import sk.tuke.kpi.kp.gamestudio.service.UserException;
import sk.tuke.kpi.kp.gamestudio.service.UserService;

public class UserServiceJPA implements UserService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("gamestudio");
    EntityManager entityManager = emf.createEntityManager();

    @Override
    public void addUser(User user) throws UserException {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public User getUser(String game, String username) throws UserException {
        try{
            return (User) entityManager.createNamedQuery("User.getUser").setParameter("game", game).setParameter("username", username).getSingleResult();
        }
        catch (NoResultException e){
            return new User(game, "NOT FOUND", "NOT FOUND");
        }
    }

    @Override
    public void reset(String game) throws UserException {
        entityManager.getTransaction().begin();
        entityManager.createNamedQuery("User.reset").setParameter("game", game).executeUpdate();
        entityManager.getTransaction().commit();
    }

}
