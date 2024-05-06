package sk.tuke.kpi.kp.gamestudio.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.gamestudio.entity.Score;
import sk.tuke.kpi.kp.gamestudio.service.ScoreService;
import sk.tuke.kpi.kp.gamestudio.service.impl.ScoreServiceJPA;

import java.util.List;

@RestController
@RequestMapping("/api/score")
@CrossOrigin
public class ScoreServiceRest {

    private final ScoreService scoreService = new ScoreServiceJPA();

    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }

    @DeleteMapping("/{game}")
    public void deleteScore(@PathVariable String game) {
        scoreService.reset(game);
    }

}
