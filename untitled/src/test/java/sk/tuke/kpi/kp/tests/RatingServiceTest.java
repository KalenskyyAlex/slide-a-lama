package sk.tuke.kpi.kp.tests;

import org.junit.jupiter.api.*;
import sk.tuke.kpi.kp.slidealama.dao.model.Rating;
import sk.tuke.kpi.kp.slidealama.dao.service.RatingService;
import sk.tuke.kpi.kp.slidealama.dao.service.impl.RatingServiceJDBC;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingServiceTest {

    public static RatingService ratingService = new RatingServiceJDBC();

    @Order(1)
    @Test
    public void testAddNewRatingNotThrowsException(){
        Rating rating = new Rating("A", 5, "B", new Timestamp(new Date().getTime()));

        ratingService.setRating(rating);
    }

    @Order(2)
    @Test
    public void testUpdateOldRating_and_newInstanceNotCreated(){
        Rating rating = new Rating("A", 4, "B", new Timestamp(new Date().getTime()));

        ratingService.setRating(rating);

        Assertions.assertEquals(4, ratingService.getRating("A", "B"));
    }

    @Order(3)
    @Test
    public void testDeleteRating_and_ratingNotFound(){
        ratingService.reset("A");

        Assertions.assertEquals(-1, ratingService.getRating("A", "B")); // not found
    }

    @Order(4)
    @Test
    public void testBatchAddRating_and_correctAverageRating(){
        List<Rating> testBatch = new ArrayList<>();
        testBatch.add(new Rating("A", 1, "B", new Timestamp(new Date().getTime())));
        testBatch.add(new Rating("A", 2, "C", new Timestamp(new Date().getTime())));
        testBatch.add(new Rating("A", 3, "D", new Timestamp(new Date().getTime())));
        testBatch.add(new Rating("A", 4, "E", new Timestamp(new Date().getTime())));
        testBatch.add(new Rating("A", 5, "F", new Timestamp(new Date().getTime())));

        for(Rating rating: testBatch){
            ratingService.setRating(rating);
        }

        Assertions.assertEquals(3, ratingService.getAverageRating("A"));

        ratingService.reset("A");
    }

}
