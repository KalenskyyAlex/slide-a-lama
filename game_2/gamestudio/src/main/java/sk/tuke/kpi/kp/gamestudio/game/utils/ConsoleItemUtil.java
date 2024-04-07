package sk.tuke.kpi.kp.gamestudio.game.utils;

import sk.tuke.kpi.kp.gamestudio.game.core.Tile;

public class ConsoleItemUtil {
    private enum ConsoleIcons{
        RED(ConsoleColors.RED_BACKGROUND + ConsoleColors.BLACK_BOLD + " sz " + ConsoleColors.RESET),
        GREEN(ConsoleColors.GREEN_BACKGROUND + ConsoleColors.BLACK_BOLD + " dp " + ConsoleColors.RESET),
        YELLOW(ConsoleColors.YELLOW_BACKGROUND + ConsoleColors.BLACK_BOLD + " {} " + ConsoleColors.RESET),
        BLUE(ConsoleColors.BLUE_BACKGROUND + ConsoleColors.BLACK_BOLD + " pd " + ConsoleColors.RESET),
        PURPLE(ConsoleColors.PURPLE_BACKGROUND + ConsoleColors.BLACK_BOLD + " <> " + ConsoleColors.RESET),
        CYAN(ConsoleColors.CYAN_BACKGROUND + ConsoleColors.BLACK_BOLD + " [] " + ConsoleColors.RESET),
        WHITE(ConsoleColors.WHITE_BACKGROUND_BRIGHT + ConsoleColors.BLACK_BOLD + " () " + ConsoleColors.RESET);

        private final String representation;

        ConsoleIcons(String representation){
            this.representation = representation;
        }

        public String getRepresentation() {
            return representation;
        }
    }

    public static String getRepresentation(Tile tile){
        return switch (tile) {
            case RED -> ConsoleIcons.RED.getRepresentation();
            case BLUE -> ConsoleIcons.BLUE.getRepresentation();
            case CYAN -> ConsoleIcons.CYAN.getRepresentation();
            case WHITE -> ConsoleIcons.WHITE.getRepresentation();
            case GREEN -> ConsoleIcons.GREEN.getRepresentation();
            case YELLOW -> ConsoleIcons.YELLOW.getRepresentation();
            case PURPLE -> ConsoleIcons.PURPLE.getRepresentation();
            case EMPTY -> "    ";
        };
    }
}
