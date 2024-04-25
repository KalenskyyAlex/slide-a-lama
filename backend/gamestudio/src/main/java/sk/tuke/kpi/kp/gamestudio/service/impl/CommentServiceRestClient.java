package sk.tuke.kpi.kp.gamestudio.service.impl;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.gamestudio.entity.Comment;
import sk.tuke.kpi.kp.gamestudio.service.CommentException;
import sk.tuke.kpi.kp.gamestudio.service.CommentService;

import java.util.Arrays;
import java.util.List;

public class CommentServiceRestClient implements CommentService {

    private final String url = "http://localhost:8080/api/comment";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addComment(Comment comment) throws CommentException {
        restTemplate.postForEntity(url, comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Comment[].class).getBody());
    }

    @Override
    public void reset(String game) throws CommentException {
        restTemplate.delete(url + "/" + game);
    }
}
