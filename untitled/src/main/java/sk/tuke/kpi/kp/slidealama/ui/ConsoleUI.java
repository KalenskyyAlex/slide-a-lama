package sk.tuke.kpi.kp.slidealama.ui;

import sk.tuke.kpi.kp.slidealama.Main;
import sk.tuke.kpi.kp.slidealama.core.Cursor;
import sk.tuke.kpi.kp.slidealama.core.Game;
import sk.tuke.kpi.kp.slidealama.core.GameState;
import sk.tuke.kpi.kp.slidealama.core.Tile;
import sk.tuke.kpi.kp.slidealama.dao.model.Comment;
import sk.tuke.kpi.kp.slidealama.dao.model.Score;
import sk.tuke.kpi.kp.slidealama.dao.service.CommentService;
import sk.tuke.kpi.kp.slidealama.dao.service.RatingService;
import sk.tuke.kpi.kp.slidealama.dao.service.ScoreService;
import sk.tuke.kpi.kp.slidealama.dao.service.impl.CommentSericeJDBC;
import sk.tuke.kpi.kp.slidealama.dao.service.impl.RatingServiceJDBC;
import sk.tuke.kpi.kp.slidealama.dao.service.impl.ScoreServiceJDBC;
import sk.tuke.kpi.kp.slidealama.utils.ConsoleColors;
import sk.tuke.kpi.kp.slidealama.utils.ConsoleItemUtil;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements UI {

    Game game;

    private int offset_x = 10;
    private int offset_y = 4;

    private String getStars(int rating){
        return switch (rating){
            case 1 -> ConsoleColors.RED_BOLD_BRIGHT + "★" + ConsoleColors.RESET;
            case 2 -> ConsoleColors.RED_BOLD + "★★" + ConsoleColors.RESET;
            case 3 -> ConsoleColors.YELLOW_BOLD + "★★★" + ConsoleColors.RESET;
            case 4 -> ConsoleColors.GREEN_BOLD + "★★★★" + ConsoleColors.RESET;
            case 5 -> ConsoleColors.GREEN_BOLD_BRIGHT + "★★★★★" + ConsoleColors.RESET;
            default -> ConsoleColors.BLACK_BRIGHT + "NOT RATED" + ConsoleColors.RESET;
        };
    }

    public void setOffset_x(int offset_x) {
        this.offset_x = offset_x;
    }

    public void setOffset_y(int offset_y) {
        this.offset_y = offset_y;
    }

    public void init(Game game) {
        this.game = game;
    }

    @Override
    public void render() {
        System.out.print(ConsoleColors.CLEAR_CONSOLE);
        System.out.flush();

        if(game.getState() == GameState.START_P1){
            renderStart("Player 1");
        }
        else if (game.getState() == GameState.START_P2) {
            renderStart("Player 2");
        }
        else if (game.getState() == GameState.END){
            renderEnd();
        }
        else if(game.getState() == GameState.PAUSED_SCORE){
            renderTopScores();
        }
        else if(game.getState() == GameState.PAUSED_RATING){
            renderAboutPage();
        }
        else {
            renderField();
            renderFront();
            renderScoreBar();
            renderBox();
            renderHeader();
            renderCursor();
            renderCurrentPlayer();
            renderRules();

            if(game.getState() == GameState.INPUT_ENTERED) inputHint();

            moveCursor(7, 19);
        }
    }

    private void renderEnd() {
        printHorizontalPattern("─", 31, 0, 0, "┌", "┐");
        printVerticalPattern("│", 1, 1, 2);
        printVerticalPattern("│", 1, 33, 2);
        printHorizontalPattern("─", 31, 1, 3, "├", "┤");
        printVerticalPattern("│ >>> ", 1, 1, 4);
        printVerticalPattern("│", 1, 33, 4);
        printHorizontalPattern("─", 31, 1, 5, "└", "┘");

        printHorizontalPattern("◀GAME OVER!▶", 1, 12, 1);
        if(game.getPlayer1().getScore() > game.getPlayer2().getScore()){
            printHorizontalPattern("Player 1 won! Good job! ", 1, 2, 2);
        }
        moveCursor(33, 8);
    }

    private void renderCurrentPlayer() {
        moveCursor(offset_x + 16, offset_y + game.getFieldSize() + 3);

        if (game.getCurrentPlayer().getId() == 1){
            System.out.print(ConsoleColors.BLUE_BRIGHT + "PLAYER 1 TURN" + ConsoleColors.RESET);
        }
        else {
            System.out.print(ConsoleColors.RED + "PLAYER 2 TURN" + ConsoleColors.RESET);
        }

    }

    @Override
    public GameState handleInput() {
        Scanner scanner = new Scanner(System.in);

        if(game.getState() == GameState.START_P1) {
            String nickname1 = scanner.nextLine();
            game.getPlayer1().setNickname(nickname1);

            return GameState.START_P2;
        }
        else if(game.getState() == GameState.START_P2) {
            String nickname2 = scanner.nextLine();
            game.getPlayer2().setNickname(nickname2);

            return GameState.IDLE;
        }
        else if(game.getState() == GameState.END){
            scanner.nextLine();

            return GameState.END;
        }

        String input = scanner.nextLine();
        String processedInput = input.trim().toLowerCase();

        int pos;

        if(game.getState() == GameState.INPUT_ENTERED){
            if(processedInput.isEmpty()) return GameState.INPUT_APPROVED;
        }

        if(processedInput.matches("r(ight)?\\p{javaSpaceChar}[0-9]")){
            pos = Integer.parseInt(processedInput.split(" ")[1]);

            if(pos > game.getFieldSize() || pos <= 0) return GameState.WRONG_INPUT;
            game.getCursor().setPosition(pos);
            game.getCursor().setSide(Cursor.Side.RIGHT);

            return GameState.INPUT_ENTERED;
        }
        else if (processedInput.matches("l(eft)?\\p{javaSpaceChar}\\d+")) {
            pos = Integer.parseInt(processedInput.split(" ")[1]);

            if(pos > game.getFieldSize() || pos <= 0) return GameState.WRONG_INPUT;
            game.getCursor().setPosition(pos);
            game.getCursor().setSide(Cursor.Side.LEFT);

            return GameState.INPUT_ENTERED;
        }
        else if (processedInput.matches("u(p)?\\p{javaSpaceChar}\\d+")) {
            pos = Integer.parseInt(processedInput.split(" ")[1]);

            if(pos > game.getFieldSize() || pos <= 0) return GameState.WRONG_INPUT;
            game.getCursor().setPosition(pos);
            game.getCursor().setSide(Cursor.Side.UP);

            return GameState.INPUT_ENTERED;
        }
        else if (processedInput.matches("e(xit)?")) {
            return GameState.END;
        }
        else if (processedInput.matches("s(core)?")) {
            return GameState.PAUSED_SCORE;
        }
        else if (processedInput.matches("f(eedback)?")){
            return GameState.PAUSED_RATING;
        }

        return GameState.WRONG_INPUT;
    }

    public void renderStart(String player) {
        printHorizontalPattern("─", 31, 0, 0, "┌", "┐");
        printVerticalPattern("│", 1, 1, 2);
        printVerticalPattern("│", 1, 33, 2);
        printHorizontalPattern("─", 31, 1, 3, "├", "┤");
        printVerticalPattern("│ >>> ", 1, 1, 4);
        printVerticalPattern("│", 1, 33, 4);
        printHorizontalPattern("─", 31, 1, 5, "└", "┘");

        printHorizontalPattern("◀WELCOME TO SLIDE-A-LAMA!▶", 1, 5, 1);
        printHorizontalPattern("Enter nickname for " + player + ":", 1, 2, 2);
        moveCursor(7, 4);
    }

    @Override
    public void inputError() {
        moveCursor(50, 19);
        System.out.print(ConsoleColors.RED + "WRONG INPUT" + ConsoleColors.RESET);
        moveCursor(7, 19);
    }

    private void inputHint(){
        moveCursor(41, 19);
        System.out.print(ConsoleColors.GREEN + "PRESS ENTER TO APPLY" + ConsoleColors.RESET);
        moveCursor(7, 19);
    }

    private void renderHeader() {
        moveCursor(offset_x + 14, 1);
        System.out.print("◀ SLIDE A LAMA ▶");
    }

    private void moveCursor(int column, int row) {
        char escCode = 0x1B;
        System.out.print(String.format("%c[%d;%df", escCode, row, column));
    }

    private void renderField() {
        for (int i = 0; i < offset_y; i++) System.out.println();
        for (int i = 0; i < offset_x; i++) System.out.print(" ");
        printHorizontalPattern("════", game.getTiles().length, offset_x + 1, offset_y + 1,"╔", "╗");
        for (Tile[] tiles : game.getTiles()) {
            for (int i = 0; i < offset_x; i++) System.out.print(" ");
            System.out.print("║");
            for (Tile tile : tiles) {
                System.out.print(ConsoleItemUtil.getRepresentation(tile));
            }
            System.out.print("║");
            System.out.println();
        }
        printHorizontalPattern("════", game.getTiles().length, offset_x + 1, offset_y + game.getTiles().length + 2,"╚", "╝");

    }

    private void renderFront() {
        List<Tile> frontList = game.getField().getFront().toList();
        int length = game.getField().getFront().getFixedLength();

        printHorizontalPattern("╓════╖", 1, 2 * offset_x + game.getFieldSize() * 4 + 7, offset_y + 1);
        printHorizontalPattern(ConsoleItemUtil.getRepresentation(frontList.get(0)), 1, 2 * offset_x + game.getFieldSize() * 4, offset_y + 2,"next → ║", "║");
        printHorizontalPattern("╟────╢", 1, 2 * offset_x + game.getFieldSize() * 4 + 7, offset_y + 3);
        for (int i = 1; i < length; i++) {
            printHorizontalPattern(ConsoleItemUtil.getRepresentation(frontList.get(i)), 1, 2 * offset_x + game.getFieldSize() * 4 + 7, offset_y + 3 + i,"║", "║");
        }
        printHorizontalPattern("╙════╜", 1, 2 * offset_x + game.getFieldSize() * 4 + 7, offset_y + 3 + length);
    }

    private void renderScoreBar(){
        double scoreRelation = ((double) game.getPlayer1().getScore()) / (game.getPlayer1().getScore() + game.getPlayer2().getScore());
        int player1BarLength = (int) Math.round(scoreRelation * 40);

        String tmp = Integer.toString(game.getPlayer1().getScore());


        printHorizontalPattern(ConsoleColors.RED_BOLD_BRIGHT + game.getPlayer2().getScore() + ConsoleColors.RESET, 1, offset_x + 44, offset_y + game.getFieldSize() + 6);
        printHorizontalPattern(ConsoleColors.BLUE_BOLD_BRIGHT + game.getPlayer1().getScore() + ConsoleColors.RESET, 1, offset_x - tmp.length(), offset_y + game.getFieldSize() + 6);
        printHorizontalPattern("↓ SCORE BAR ↓", 1, offset_x + 16, offset_y + game.getFieldSize() + 4 );
        printHorizontalPattern("─", 40, offset_x + 1, offset_y + game.getFieldSize() + 5, "┌" ,"┐");
        printHorizontalPattern(ConsoleColors.RED + ConsoleColors.RED_BACKGROUND + " " + ConsoleColors.RESET, 40, offset_x + 1, offset_y + game.getFieldSize() + 6, "│" ,"│");
        printHorizontalPattern(ConsoleColors.BLUE + ConsoleColors.BLUE_BACKGROUND + " " + ConsoleColors.RESET, player1BarLength, offset_x + 2, offset_y + game.getFieldSize() + 6);
        printHorizontalPattern("─", 40, offset_x + 1, offset_y + game.getFieldSize() + 7, "└" ,"┘");
        printHorizontalPattern("⬑ PLAYER 1", 1, offset_x + 2, offset_y + game.getFieldSize() + 8);
        printHorizontalPattern("PLAYER 2 ⬏", 1, offset_x + 32, offset_y + game.getFieldSize() + 8);
        printVerticalPattern("│", 1, offset_x + 10, offset_y + game.getFieldSize() + 5, "┬", "┴");
        printVerticalPattern("│", 1, offset_x + 33, offset_y + game.getFieldSize() + 5, "┬", "┴");
        printVerticalPattern("│", 1, offset_x + 33, offset_y + game.getFieldSize() + 5, "┬", "┴");
    }

    private void renderBox(){
        printHorizontalPattern("─", 40 + offset_x * 2, 0, 0, "┌" ,"┐");
        printVerticalPattern("│", 17, 0, 2);
        printVerticalPattern("│", 17, 42 + offset_x * 2, 2);
        printHorizontalPattern("─", 40 + offset_x * 2, 0, 18, "├" ,"┤");
        printVerticalPattern("│ >>> ", 1, 0, 19);
        printVerticalPattern("│", 1, 42 + offset_x * 2, 19);
        printHorizontalPattern("─", 40 + offset_x * 2, 0, 20, "└" ,"┘");
    }

    private void renderRules(){
        printVerticalPattern("│", 15, 0, 20, "├", "└");
        printVerticalPattern("│", 15 , 42 + offset_x * 2, 20, "┤", "┘");
        printHorizontalPattern("─", 40 + offset_x * 2, 2, 36);
        printHorizontalPattern("RULES", 1, 30, 21);

        moveCursor(6, 23);
        System.out.print("Using input choose where to place new tile (i.e. uP 3 /");
        moveCursor(4, 24);
        System.out.print("LeFt 4). Type e for exit. Place tiles to make a match and");
        moveCursor(4, 25);
        System.out.print("get a score. Different tiles give different score. Longer");
        moveCursor(4, 26);
        System.out.print("match MULTIPLIES score. See below:");
        moveCursor(4, 28);
        System.out.print(ConsoleItemUtil.getRepresentation(Tile.RED) + " - " + Tile.RED.getScoreMultiplier());
        moveCursor(4, 29);
        System.out.print(ConsoleItemUtil.getRepresentation(Tile.GREEN) + " - " + Tile.GREEN.getScoreMultiplier());
        moveCursor(4, 30);
        System.out.print(ConsoleItemUtil.getRepresentation(Tile.YELLOW) + " - " + Tile.YELLOW.getScoreMultiplier());
        moveCursor(4, 31);
        System.out.print(ConsoleItemUtil.getRepresentation(Tile.BLUE) + " - " + Tile.BLUE.getScoreMultiplier());
        moveCursor(4, 32);
        System.out.print(ConsoleItemUtil.getRepresentation(Tile.PURPLE) + " - " + Tile.PURPLE.getScoreMultiplier());
        moveCursor(4, 33);
        System.out.print(ConsoleItemUtil.getRepresentation(Tile.CYAN) + " - " + Tile.CYAN.getScoreMultiplier());
        moveCursor(4, 34);
        System.out.print(ConsoleItemUtil.getRepresentation(Tile.WHITE) + " - " + Tile.WHITE.getScoreMultiplier());

        moveCursor(19, 28);
        System.out.print("Length");
        moveCursor(17, 29);
        System.out.print("multiplier");
        moveCursor(17, 31);
        System.out.print("3 - 1x");
        moveCursor(17, 32);
        System.out.print("4 - 2x");
        moveCursor(17, 33);
        System.out.print("5 - 3x");

        moveCursor(36, 28);
        System.out.print("If your score is multiply");
        moveCursor(34, 29);
        System.out.print("bigger than your opponents'");
        moveCursor(34, 30);
        System.out.print("You'll win (see thresholds");
        moveCursor(34, 31);
        System.out.print("on the scoreboard");
        moveCursor(37, 34);
        System.out.print("Good luck - have fun!");
    }

    private void renderCursor(){
        if (game.getState() != GameState.INPUT_ENTERED && game.getState() != GameState.INPUT_APPROVED) return;
        switch (game.getCursor().getSide()){
            case UP:
                moveCursor(offset_x + (game.getCursor().getPosition()) * 4, offset_y);
                System.out.print("↓");
                break;
            case LEFT:
                moveCursor(offset_x, offset_y + 2 + game.getTiles().length - game.getCursor().getPosition());
                System.out.print("→");
                break;
            case RIGHT:
                moveCursor(offset_x + game.getTiles().length * 4 + 3, offset_y + game.getCursor().getPosition() + 1);
                System.out.print("←");
                break;
        }
    }

    private void printHorizontalPattern(String pattern, int repeat, int col, int row){
        moveCursor(col, row);

        for (int i = 0; i < repeat; i++) {
            System.out.print(pattern);
        }
    }

    private void printHorizontalPattern(String pattern, int repeat, int col, int row, String start, String end){
        moveCursor(col, row);

        System.out.print(start);
        for (int i = 0; i < repeat; i++) {
            System.out.print(pattern);
        }
        System.out.println(end);
    }

    private void printVerticalPattern(String pattern, int repeat, int col, int row, String start, String end){
        moveCursor(col, row);

        System.out.print(start);
        for (int i = 0; i < repeat; i++) {
            moveCursor(col, row + 1 + i);
            System.out.print(pattern);
        }
        moveCursor(col, row + 1 + repeat);
        System.out.println(end);
    }

    private void printVerticalPattern(String pattern, int repeat, int col, int row){
        moveCursor(col, row);

        for (int i = 0; i < repeat; i++) {
            moveCursor(col, row + i);
            System.out.print(pattern);
        }
    }

    private void renderAboutPage(){
        printHorizontalPattern("─", 60, 1, 1, "┌" ,"┐");
        printVerticalPattern("│", 20, 1, 2);
        printVerticalPattern("│", 20, 62, 2);
        printHorizontalPattern("─", 60, 0, 21, "└" ,"┘");

        RatingService ratingService = new RatingServiceJDBC();
        CommentService commentService = new CommentSericeJDBC();

        int avgRating = ratingService.getAverageRating("Slide a Lama");
        int rating = ratingService.getRating("Slide a Lama", game.getCurrentPlayer().getNickname());
        List<Comment> comments = commentService.getComments("Slide a Lama");

        printHorizontalPattern(ConsoleColors.GREEN_BOLD_BRIGHT + "COMMENTS & RATING" + ConsoleColors.RESET, 1, 25, 2);
        printHorizontalPattern(ConsoleColors.GREEN_BOLD_BRIGHT + "YOUR RATING: " + ConsoleColors.RESET + getStars(rating), 1, 4, 3);
        printHorizontalPattern(ConsoleColors.GREEN_BOLD_BRIGHT + "AVERAGE RATING: " + ConsoleColors.RESET + getStars(avgRating), 1, 4, 4);
        printHorizontalPattern(ConsoleColors.GREEN_BOLD_BRIGHT + "3 LAST COMMENTS" + ConsoleColors.RESET, 1, 26, 6);

        for(int i = 0; i < Math.min(3, comments.size()); i++){
            Comment comment = comments.get(i);
            printHorizontalPattern(ConsoleColors.BLUE_BOLD_BRIGHT + comment.getPlayer() + ConsoleColors.RESET, 1, 8, 8 + 3*i);
            printHorizontalPattern(comment.getComment(), 1, 4, 9 + 3*i);
        }
    }

    private void renderTopScores(){
        printHorizontalPattern("─", 60, 1, 1, "┌" ,"┐");
        printVerticalPattern("│", 12, 1, 2);
        printVerticalPattern("│", 12, 62, 2);
        printHorizontalPattern("─", 60, 0, 13, "└" ,"┘");

        printHorizontalPattern(ConsoleColors.YELLOW_BOLD_BRIGHT + "TOP-10 SCORES" + ConsoleColors.RESET, 1, 24, 2);

        ScoreService scoreService = new ScoreServiceJDBC();

        List<Score> topScores = scoreService.getTopScores("Slide a Lama");

        for(int i = 0; i < topScores.size(); i++){
            Score score = topScores.get(i);
            printHorizontalPattern(String.format("%15s", score.getPlayer()) + ": " + String.format("%-15s", score.getPoints()) + " " + score.getPlayedOn(), 1, 2, 3 + i);
        }
    }

}
