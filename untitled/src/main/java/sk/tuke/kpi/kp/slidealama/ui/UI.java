package sk.tuke.kpi.kp.slidealama.ui;

import sk.tuke.kpi.kp.slidealama.core.Game;
import sk.tuke.kpi.kp.slidealama.core.GameState;

public interface UI {

    void init(Game game);

    void render();

    GameState handleInput();

    void inputError();
}
