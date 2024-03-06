package sk.tuke.kpi.kp.slidealama.core;

public class Game {

    private GameState state = GameState.IDLE;

    private Field field = new Field();
    
    private Player player1 = new Player(1);
    private Player player2 = new Player(2);
    private Player currentPlayer = player1;

    public Field getField(){
        return field;
    }

    public Cursor getCursor(){
        return field.getCursor();
    }

    public int getFieldSize(){
        return field.getTiles().length;
    }

    public Tile[][] getTiles(){
        return field.getTiles();
    }


    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
}
