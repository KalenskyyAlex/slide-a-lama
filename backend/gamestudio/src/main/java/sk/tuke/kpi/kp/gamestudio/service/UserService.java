package sk.tuke.kpi.kp.gamestudio.service;

import org.springframework.stereotype.Component;
import sk.tuke.kpi.kp.gamestudio.entity.User;


@Component
public interface UserService {
    void addUser(User user) throws UserException;
    User getUser(String game, String username) throws UserException;
    void reset(String game) throws UserException;
}
