package sk.tuke.kpi.kp.gamestudio.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sk.tuke.kpi.kp.gamestudio.entity.Score;

import java.util.List;

@Component
public interface ScoreService {
    void addScore(Score score) throws ScoreException;
    List<Score> getTopScores(String game) throws ScoreException;
    void reset(String game) throws ScoreException;
}
