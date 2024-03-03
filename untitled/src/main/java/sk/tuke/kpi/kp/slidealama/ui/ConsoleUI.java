package sk.tuke.kpi.kp.slidealama.ui;

import sk.tuke.kpi.kp.slidealama.core.Field;
import sk.tuke.kpi.kp.slidealama.core.Tile;
import sk.tuke.kpi.kp.slidealama.utils.ConsoleColors;
import sk.tuke.kpi.kp.slidealama.utils.ConsoleItemUtil;

import java.util.List;

public class ConsoleUI implements UI {

    Field field;

    private int offset_x = 10;
    private int offset_y = 4;

    public void setOffset_x(int offset_x) {
        this.offset_x = offset_x;
    }

    public void setOffset_y(int offset_y) {
        this.offset_y = offset_y;
    }

    public void init(Field field) {
        this.field = field;
    }

    @Override
    public void render() {
        System.out.print(ConsoleColors.CLEAR_CONSOLE);
        System.out.flush();

        renderField();
        renderFront();
        renderScoreBar();
        renderBox();
        renderHeader();
        renderCursor();

        moveCursor(100, 40);
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
        printHorizontalPattern("════", field.getTiles().length, offset_x + 1, offset_y + 1,"╔", "╗");
        for (Tile[] tiles : field.getTiles()) {
            for (int i = 0; i < offset_x; i++) System.out.print(" ");
            System.out.print("║");
            for (Tile tile : tiles) {
                System.out.print(ConsoleItemUtil.getRepresentation(tile));
            }
            System.out.print("║");
            System.out.println();
        }
        printHorizontalPattern("════", field.getTiles().length, offset_x + 1, offset_y + field.getTiles().length + 2,"╚", "╝");

    }

    private void renderFront() {
        List<Tile> frontList = field.getFront().toList();
        int length = field.getFront().getFixedLength();

        printHorizontalPattern("╓────╖", 1, 2 * offset_x + field.getTiles().length * 4 + 7, offset_y + 1);
        printHorizontalPattern(ConsoleItemUtil.getRepresentation(frontList.get(0)), 1, 2 * offset_x + field.getTiles().length * 4, offset_y + 2,"next → ║", "║");
        printHorizontalPattern("╟────╢", 1, 2 * offset_x + field.getTiles().length * 4 + 7, offset_y + 3);
        for (int i = 1; i < length; i++) {
            printHorizontalPattern(ConsoleItemUtil.getRepresentation(frontList.get(i)), 1, 2 * offset_x + field.getTiles().length * 4 + 7, offset_y + 3 + i,"║", "║");
        }
        printHorizontalPattern("╙────╜", 1, 2 * offset_x + field.getTiles().length * 4 + 7, offset_y + 3 + length);
    }

    private void renderScoreBar(){
        printHorizontalPattern("↓ SCORE BAR ↓", 1, offset_x + 16, offset_y + field.getTiles().length + 4 );
        printHorizontalPattern("─", 40, offset_x + 1, offset_y + field.getTiles().length + 5, "┌" ,"┐");
        printHorizontalPattern(" ", 40, offset_x + 1, offset_y + field.getTiles().length + 6, "│" ,"│");
        printHorizontalPattern("─", 40, offset_x + 1, offset_y + field.getTiles().length + 7, "└" ,"┘");
        printHorizontalPattern("PLAYER 1 ⬏", 1, offset_x + 2, offset_y + field.getTiles().length + 8);
        printHorizontalPattern("⬑ PLAYER 2", 1, offset_x + 32, offset_y + field.getTiles().length + 8);

    }

    private void renderBox(){
        printHorizontalPattern("─", 40 + offset_x * 2, 0, 0, "┌" ,"┐");
        printVerticalPattern("│", 17, 0, 2);
        printVerticalPattern("│", 17, 42 + offset_x * 2, 2);
        printHorizontalPattern("─", 40 + offset_x * 2, 0, 18, "└" ,"┘");
    }

    private void renderCursor(){
        switch (field.getCursor().getSide()){
            case UP:
                moveCursor(offset_x + field.getCursor().getPosition() + 1, offset_y);
                System.out.print("↓");
                break;
            case LEFT:
                moveCursor(offset_x, offset_y + field.getCursor().getPosition() + field.getTiles().length + 1);
                System.out.print("→");
                break;
            case RIGHT:
                moveCursor(offset_x + field.getTiles().length * 4 + 2, offset_y + field.getCursor().getPosition() + 1);
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
        System.out.println(end);
    }

    private void printVerticalPattern(String pattern, int repeat, int col, int row){
        moveCursor(col, row);

        for (int i = 0; i < repeat; i++) {
            moveCursor(col, row + i);
            System.out.print(pattern);
        }
    }

}
