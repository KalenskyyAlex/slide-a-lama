package sk.tuke.kpi.kp.slidealama.dao.service;


import sk.tuke.kpi.kp.slidealama.dao.model.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score) throws ScoreException;
    List<Score> getTopScores(String game) throws ScoreException;
    void reset() throws ScoreException;
}
