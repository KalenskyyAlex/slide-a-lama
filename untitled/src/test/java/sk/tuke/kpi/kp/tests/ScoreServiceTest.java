package sk.tuke.kpi.kp.tests;

import org.junit.jupiter.api.*;
import sk.tuke.kpi.kp.slidealama.dao.model.Score;
import sk.tuke.kpi.kp.slidealama.dao.service.ScoreService;
import sk.tuke.kpi.kp.slidealama.dao.service.impl.ScoreServiceJDBC;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScoreServiceTest {

    public static ScoreService scoreService = new ScoreServiceJDBC();

    @Order(1)
    @Test
    public void testAddNewScoreNotThrowsAnException(){
        Score score = new Score("A", "Player 1", 123, new Date());

        scoreService.addScore(score);
    }

    @Order(2)
    @Test
    public void testScoreAddedSuccessfully(){
        List<Score> results = scoreService.getTopScores("A");

        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals(results.size(), 1);
        Assertions.assertEquals(results.get(0).getPlayer(), "Player 1");
        Assertions.assertEquals(results.get(0).getGame(), "A");
        Assertions.assertEquals(results.get(0).getPoints(), 123);
    }

    @Order(3)
    @Test
    public void testDeletingScores(){
        scoreService.reset("A");

        Assertions.assertTrue(scoreService.getTopScores("A").isEmpty());
    }

    @Order(4)
    @Test
    public void testBatchAddScores_and_CheckOrder(){
        List<Score> testBatch = new ArrayList<>();

        testBatch.add(new Score("A", "Player 1", 123, new Date()));
        testBatch.add(new Score("A", "Player 3", 999, new Date()));
        testBatch.add(new Score("A", "Player 2", 12, new Date()));
        testBatch.add(new Score("A", "Player 4", 1243, new Date()));
        testBatch.add(new Score("A", "Player 5", 128, new Date()));

        for(Score score: testBatch){
            scoreService.addScore(score);
        }

        List<Score> results = scoreService.getTopScores("A");

        class ScoreComparator implements Comparator<Score>{
            @Override
            public int compare(Score o1, Score o2) {
                return o2.getPoints() - o1.getPoints();
            }
        }

        System.out.print(results);

        Assertions.assertEquals(testBatch.stream().sorted(new ScoreComparator()).toList(), results);

        scoreService.reset("A");
    }


}
