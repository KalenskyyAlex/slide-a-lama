package sk.tuke.kpi.kp.gamestudio.game.ui;

import sk.tuke.kpi.kp.gamestudio.game.core.Game;
import sk.tuke.kpi.kp.gamestudio.game.core.GameState;

public interface UI {

    void init(Game game);

    void render();

    GameState handleInput();

    void inputError();
}
