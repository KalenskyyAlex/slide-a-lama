package sk.tuke.kpi.kp.tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.slidealama.core.Field;
import sk.tuke.kpi.kp.slidealama.core.MatchResult;
import sk.tuke.kpi.kp.slidealama.core.Tile;


public class MatchTest {

    @Test
    public void checkFieldWith_MATCH_5_V(){
        Tile[][] field = {
                {Tile.BLUE, Tile.BLUE, Tile.CYAN, Tile.BLUE, Tile.RED},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.GREEN},
                {Tile.BLUE, Tile.GREEN, Tile.PURPLE, Tile.PURPLE, Tile.PURPLE},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.GREEN},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.GREEN}
        };

        Field f = new Field();
        f.setTiles(field);

        MatchResult matchResult = f.checkForMatch();

        MatchResult valid = new MatchResult(0, 0, 1, 5, 40, 3);

        Assertions.assertEquals(matchResult, valid);
    }

    @Test
    public void checkFieldWith_MATCH_5_H(){
        Tile[][] field = {
                {Tile.BLUE, Tile.BLUE, Tile.CYAN, Tile.BLUE, Tile.RED},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.GREEN},
                {Tile.GREEN, Tile.GREEN, Tile.GREEN, Tile.GREEN, Tile.GREEN},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.RED},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.GREEN}
        };

        Field f = new Field();
        f.setTiles(field);

        MatchResult matchResult = f.checkForMatch();

        MatchResult valid = new MatchResult(0, 2, 0, 5, 20, 3);

        Assertions.assertEquals(matchResult, valid);
    }

    @Test
    public void checkFieldWith_MATCH_4_V(){
        Tile[][] field = {
                {Tile.BLUE, Tile.RED, Tile.CYAN, Tile.RED, Tile.RED},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.GREEN},
                {Tile.GREEN, Tile.GREEN, Tile.YELLOW, Tile.GREEN, Tile.RED},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.GREEN},
                {Tile.BLUE, Tile.CYAN, Tile.YELLOW, Tile.YELLOW, Tile.GREEN}
        };

        Field f = new Field();
        f.setTiles(field);

        MatchResult matchResult = f.checkForMatch();

        MatchResult valid = new MatchResult(2, 1, 1, 4, 30, 2);

        Assertions.assertEquals(matchResult, valid);
    }

    @Test
    public void checkFieldWith_MATCH_4_H(){
        Tile[][] field = {
                {Tile.BLUE, Tile.RED, Tile.RED, Tile.RED, Tile.RED},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.GREEN},
                {Tile.GREEN, Tile.GREEN, Tile.PURPLE, Tile.GREEN, Tile.RED},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.GREEN},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.GREEN}
        };

        Field f = new Field();
        f.setTiles(field);

        MatchResult matchResult = f.checkForMatch();

        MatchResult valid = new MatchResult(1, 0, 0, 4, 10, 2);

        Assertions.assertEquals(matchResult, valid);
    }

    @Test
    public void checkFieldWith_MATCH_3_V(){
        Tile[][] field = {
                {Tile.BLUE, Tile.RED, Tile.CYAN, Tile.RED, Tile.RED},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.RED},
                {Tile.GREEN, Tile.GREEN, Tile.YELLOW, Tile.GREEN, Tile.RED},
                {Tile.BLUE, Tile.RED, Tile.PURPLE, Tile.BLUE, Tile.GREEN},
                {Tile.BLUE, Tile.CYAN, Tile.YELLOW, Tile.YELLOW, Tile.GREEN}
        };

        Field f = new Field();
        f.setTiles(field);

        MatchResult matchResult = f.checkForMatch();

        MatchResult valid = new MatchResult(4, 0, 1, 3, 10, 1);

        Assertions.assertEquals(matchResult, valid);
    }

    @Test
    public void checkFieldWith_MATCH_3_H(){
        Tile[][] field = {
                {Tile.BLUE, Tile.RED, Tile.CYAN, Tile.RED, Tile.RED},
                {Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.BLUE, Tile.GREEN},
                {Tile.GREEN, Tile.GREEN, Tile.YELLOW, Tile.GREEN, Tile.RED},
                {Tile.BLUE, Tile.RED, Tile.GREEN, Tile.BLUE, Tile.GREEN},
                {Tile.BLUE, Tile.RED, Tile.CYAN, Tile.CYAN, Tile.CYAN}
        };

        Field f = new Field();
        f.setTiles(field);

        MatchResult matchResult = f.checkForMatch();

        MatchResult valid = new MatchResult(2, 4, 0, 3, 100, 1);

        Assertions.assertEquals(matchResult, valid);
    }

}
