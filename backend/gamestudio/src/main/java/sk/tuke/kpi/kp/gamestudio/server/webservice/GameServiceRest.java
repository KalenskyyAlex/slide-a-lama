package sk.tuke.kpi.kp.gamestudio.server.webservice;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.gamestudio.entity.Credentials;
import sk.tuke.kpi.kp.gamestudio.game.core.Cursor;
import sk.tuke.kpi.kp.gamestudio.game.core.Field;
import sk.tuke.kpi.kp.gamestudio.game.core.Game;

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

    @PostMapping("/{id}/field")
    public void insert(@PathVariable int id, @RequestBody Cursor cursor){
        Game game = sessions.get(id);

        game.getField().setCursor(cursor);
        game.getField().insert();
    }
}
