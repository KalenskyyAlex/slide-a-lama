package sk.tuke.kpi.kp.gamestudio.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.gamestudio.entity.Comment;
import sk.tuke.kpi.kp.gamestudio.entity.Rating;
import sk.tuke.kpi.kp.gamestudio.service.CommentService;
import sk.tuke.kpi.kp.gamestudio.service.RatingService;
import sk.tuke.kpi.kp.gamestudio.service.impl.CommentServiceJPA;
import sk.tuke.kpi.kp.gamestudio.service.impl.RatingServiceJPA;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    private final RatingService ratingService = new RatingServiceJPA();

    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player) {
        return ratingService.getRating(game, player);
    }

    @GetMapping("/{game}/average")
    public int getRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

    @PostMapping
    public void addScore(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }

    @DeleteMapping("/{game}")
    public void deleteScore(@PathVariable String game) {
        ratingService.reset(game);
    }

}
