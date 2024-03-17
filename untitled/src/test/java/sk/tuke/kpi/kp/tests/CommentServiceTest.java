package sk.tuke.kpi.kp.tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.slidealama.dao.model.Comment;
import sk.tuke.kpi.kp.slidealama.dao.service.CommentService;
import sk.tuke.kpi.kp.slidealama.dao.service.impl.CommentSericeJDBC;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTest {

    public static CommentService commentService = new CommentSericeJDBC();

    @Order(1)
    @Test
    public void testAddNewCommentNotThrowsAnException(){
        commentService.addComment(new Comment("Slide a Lama", "Nice game", "Alex", new Timestamp(new Date().getTime())));
    }

    @Order(2)
    @Test
    public void testCommentAddedSuccessfully(){
        List<Comment> results = commentService.getComments("Slide a Lama");

        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals(results.size(), 1);
        Assertions.assertEquals(results.get(0).getComment(), "Nice game");
        Assertions.assertEquals(results.get(0).getGame(), "Slide a Lama");
        Assertions.assertEquals(results.get(0).getPlayer(), "Alex");
    }

    @Order(3)
    @Test
    public void testCommentsDelete(){
        commentService.reset();

        Assertions.assertEquals(commentService.getComments("Slide a Lama").size(), 0);
    }

    @Test
    @Order(4)
    public void testBatchAddComments(){
        List<Comment> testBatch = new ArrayList<>();
        testBatch.add(new Comment("A", "1", "1", new Timestamp(new Date().getTime())));
        testBatch.add(new Comment("A", "2", "2", new Timestamp(new Date().getTime())));
        testBatch.add(new Comment("A", "3", "3", new Timestamp(new Date().getTime())));
        testBatch.add(new Comment("A", "4", "4", new Timestamp(new Date().getTime())));
        testBatch.add(new Comment("A", "5", "5", new Timestamp(new Date().getTime())));

        for(Comment comment: testBatch){
            commentService.addComment(comment);
        }

        List<Comment> results = commentService.getComments("A");

        Assertions.assertEquals(results, testBatch);

        commentService.reset();
    }

}
