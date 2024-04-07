package sk.tuke.kpi.kp.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import sk.tuke.kpi.kp.gamestudio.entity.Score;
import sk.tuke.kpi.kp.gamestudio.game.core.Game;
import sk.tuke.kpi.kp.gamestudio.game.core.GameState;
import sk.tuke.kpi.kp.gamestudio.game.core.MatchResult;
import sk.tuke.kpi.kp.gamestudio.game.ui.ConsoleUI;
import sk.tuke.kpi.kp.gamestudio.game.ui.UI;
import sk.tuke.kpi.kp.gamestudio.service.ScoreService;
import sk.tuke.kpi.kp.gamestudio.service.impl.ScoreServiceJDBC;
import sk.tuke.kpi.kp.gamestudio.service.impl.ScoreServiceJPA;

import java.util.Date;

@SpringBootApplication
@EntityScan("sk.tuke.kpi.kp.gamestudio.entity")
public class GamestudioApplication {

	private static boolean winCondition(Game game){
		double fraction = game.getPlayer1().getScore() / ((double) game.getPlayer2().getScore() + game.getPlayer1().getScore());
		return fraction >= 0.8|| fraction <= 0.2;
	}

	public static void main(String[] args) {
		SpringApplication.run(GamestudioApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			Game game = new Game();
			game.getField().generate();

			UI ui = new ConsoleUI();
			ui.init(game);

			game.setState(GameState.START_P1);
			ui.render();
			ui.handleInput();
			game.setState(GameState.START_P2);
			ui.render();
			ui.handleInput();
			game.setState(GameState.IDLE);

			GameState state = game.getState();

			do {
				game.setState(state);
				ui.render();

				if(state == GameState.WRONG_INPUT) ui.inputError();
				else if (state == GameState.INPUT_APPROVED){
					game.setState(GameState.IDLE);
					game.getField().insert();

					while(true){
						MatchResult m = game.getField().checkForMatch();
						game.getField().update(m);
						if(m == null) break;
						game.getCurrentPlayer().setScore(game.getCurrentPlayer().getScore() + m.getScoreMultiplier() * m.getLengthMultiplier());
					}
					if(game.getCurrentPlayer().getId() == 1) game.setCurrentPlayer(game.getPlayer2());
					else game.setCurrentPlayer(game.getPlayer1());
				}

				if(winCondition(game) || state == GameState.FORCE_WIN){
					ScoreService service = new ScoreServiceJPA();

					Score score1 = new Score("Slide a Lama", game.getPlayer1().getNickname(), game.getPlayer1().getScore(), new Date());
					Score score2 = new Score("Slide a Lama", game.getPlayer2().getNickname(), game.getPlayer2().getScore(), new Date());

					service.addScore(score1);
					service.addScore(score2);

					ui.render();
					game.setState(GameState.END);
				}
			} while ((state = ui.handleInput()) != GameState.END);

			ui.render();
		};
	}
}
