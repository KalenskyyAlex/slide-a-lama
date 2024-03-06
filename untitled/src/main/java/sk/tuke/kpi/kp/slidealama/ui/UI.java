package sk.tuke.kpi.kp.slidealama.ui;

import sk.tuke.kpi.kp.slidealama.core.Field;
import sk.tuke.kpi.kp.slidealama.core.GameState;

public interface UI {

    void init(Field field);

    void render();

    GameState handleInput();
}
