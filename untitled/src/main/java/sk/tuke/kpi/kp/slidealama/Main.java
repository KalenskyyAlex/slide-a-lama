package sk.tuke.kpi.kp.slidealama;

import sk.tuke.kpi.kp.slidealama.core.Game;
import sk.tuke.kpi.kp.slidealama.core.GameState;
import sk.tuke.kpi.kp.slidealama.core.MatchResult;
import sk.tuke.kpi.kp.slidealama.ui.ConsoleUI;
import sk.tuke.kpi.kp.slidealama.ui.UI;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.getField().generate();

        UI ui = new ConsoleUI();
        ui.init(game);

        GameState state = game.getState();

        do {
            game.setState(state);
            ui.render();

            if(state == GameState.WRONG_INPUT) ui.inputError();
            else if (state == GameState.INPUT_APPROVED){
                game.setState(GameState.IDLE);
                game.getField().insert();
                MatchResult m = game.getField().checkForMatch();
                game.getField().update(m);
                if(m != null) game.getCurrentPlayer().setScore(game.getCurrentPlayer().getScore() + m.getScoreMultiplier() * m.getLengthMultiplier());
                if(game.getCurrentPlayer().getId() == 1) game.setCurrentPlayer(game.getPlayer2());
                else game.setCurrentPlayer(game.getPlayer1());
            }
        } while ((state = ui.handleInput()) != GameState.END);
    }

}