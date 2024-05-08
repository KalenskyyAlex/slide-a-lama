package sk.tuke.kpi.kp.gamestudio.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.gamestudio.entity.Credentials;
import sk.tuke.kpi.kp.gamestudio.game.core.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/game")
@CrossOrigin
public class GameServiceRest {
    List<Game> sessions = new ArrayList<>();

    @PostMapping("/init")
    public int init(@RequestBody Credentials credentials){
        Game game = new Game();
        game.getField().generate();
        game.getPlayer1().setNickname(credentials.getNickname1());
        game.getPlayer2().setNickname(credentials.getNickname2());

        sessions.add(game);

        return sessions.size() - 1;
    }

    @GetMapping("/{id}/field")
    public Field getField(@PathVariable int id){
        Game game = sessions.get(id);
        return game.getField();
    }

    @GetMapping("/{id}/front")
    public List<Tile> getFront(@PathVariable int id){
        Game game = sessions.get(id);
        return game.getField().getFront().toList();
    }

    @PostMapping("/{id}/field")
    public List<MatchResult> insert(@PathVariable int id, @RequestBody Cursor cursor){
        Game game = sessions.get(id);

        game.getField().setCursor(cursor);
        game.getField().insert();

        List<MatchResult> matches = new ArrayList<>();

        while(true){
            MatchResult m = game.getField().checkForMatch();
            game.getField().update(m);
            if(m == null) break;
            matches.add(m);
            game.getCurrentPlayer().setScore(game.getCurrentPlayer().getScore() + m.getScoreMultiplier() * m.getLengthMultiplier());
        }

        if(game.getCurrentPlayer().getId() == 1) game.setCurrentPlayer(game.getPlayer2());
        else game.setCurrentPlayer(game.getPlayer1());

        return matches;
    }

    @GetMapping("/{id}/player1")
    public int getPlayer1Score(@PathVariable int id){
        return sessions.get(id).getPlayer1().getScore();
    }

    @GetMapping("/{id}/player2")
    public int getPlayer2Score(@PathVariable int id){
        return sessions.get(id).getPlayer2().getScore();
    }

    @GetMapping("/{id}/playerCurrent")
    public int getCurrentPlayerId(@PathVariable int id){
        return sessions.get(id).getCurrentPlayer().getId();
    }

    @GetMapping("/{id}/win")
    public boolean isWin(@PathVariable int id){
        double fraction = sessions.get(id).getPlayer1().getScore() / ((double) sessions.get(id).getPlayer2().getScore() + sessions.get(id).getPlayer1().getScore());
        return fraction >= 0.8|| fraction <= 0.2;
    }

    @GetMapping("/{id}/computerHelper")
    public Cursor precalculateMove(@PathVariable int id){
        Field old = sessions.get(id).getField();
        Tile[][] tilesCopy = new Tile[5][5];
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                tilesCopy[i][j] = old.getTiles()[i][j];
            }
        }

        TileFront oldFront = sessions.get(id).getField().getFront();
        TileFront frontCopy = new TileFront(4);
        frontCopy.initQueue(oldFront.toList());

        int maxScore = 0;
        Cursor maxCursor = new Cursor(1, Cursor.Side.UP);
        for(int i = 0; i < 5; i++){
            for(Cursor.Side side : Cursor.Side.values()){
                int score = 0;
                Field copy = new Field();
                copy.setTiles(tilesCopy);
                copy.setFront(frontCopy);
                copy.setCursor(new Cursor(i + 1, side));
                copy.insert();

                while(true){
                    MatchResult m = copy.checkForMatch();
                    copy.update(m);
                    if(m == null) break;
                    score += m.getScoreMultiplier() * m.getLengthMultiplier();
                }

                if (score > maxScore) {
                    maxScore = score;
                    maxCursor = copy.getCursor();
                }
            }
        }

        return maxCursor;
    }
}
