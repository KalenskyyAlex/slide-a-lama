package sk.tuke.kpi.kp.slidealama;

import sk.tuke.kpi.kp.slidealama.core.Field;
import sk.tuke.kpi.kp.slidealama.core.GameState;
import sk.tuke.kpi.kp.slidealama.ui.ConsoleUI;
import sk.tuke.kpi.kp.slidealama.ui.UI;
import sk.tuke.kpi.kp.slidealama.utils.ConsoleColors;

import java.io.IOException;


public class Main {

    public static void main(String[] args){
        Field field = new Field();
        field.generate();

        UI ui = new ConsoleUI();
        ui.init(field);

        do {
            ui.render();
        } while (ui.handleInput() != GameState.END);
    }

}