package sk.tuke.kpi.kp.gamestudio.service;


import org.springframework.stereotype.Component;
import sk.tuke.kpi.kp.gamestudio.entity.Comment;
import java.util.List;

@Component
public interface CommentService {
    void addComment(Comment comment) throws CommentException;
    List<Comment> getComments(String game) throws CommentException;
    void reset(String game) throws CommentException;
}
