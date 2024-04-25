package sk.tuke.kpi.kp.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.kpi.kp.gamestudio.service.ScoreService;
import sk.tuke.kpi.kp.gamestudio.service.impl.ScoreServiceJPA;

@SpringBootApplication
@Configuration
@EntityScan("sk.tuke.gamestudio.entity")
public class GameStudioServer {

    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

}