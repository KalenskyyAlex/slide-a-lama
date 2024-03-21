package sk.tuke.kpi.kp.slidealama.dao.service;


import sk.tuke.kpi.kp.slidealama.dao.model.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment) throws CommentException;
    List<Comment> getComments(String game) throws CommentException;
    void reset(String game) throws CommentException;
}
