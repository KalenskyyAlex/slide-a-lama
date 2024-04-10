package sk.tuke.kpi.kp.gamestudio.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.gamestudio.entity.Comment;
import sk.tuke.kpi.kp.gamestudio.service.CommentService;
import sk.tuke.kpi.kp.gamestudio.service.impl.CommentServiceJPA;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {

    private final CommentService commentService = new CommentServiceJPA();

    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game) {
        return commentService.getComments(game);
    }

    @PostMapping
    public void addScore(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }

    @DeleteMapping("/{game}")
    public void deleteScore(@PathVariable String game) {
        commentService.reset(game);
    }

}
