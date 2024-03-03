package sk.tuke.kpi.kp.slidealama;

import sk.tuke.kpi.kp.slidealama.core.Field;
import sk.tuke.kpi.kp.slidealama.ui.ConsoleUI;
import sk.tuke.kpi.kp.slidealama.ui.UI;
import sk.tuke.kpi.kp.slidealama.utils.ConsoleColors;



public class Main {
    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    public static void main(String[] args) {
        Field field = new Field();
        field.generate();

        UI ui = new ConsoleUI();
        ui.init(field);
        ui.render();
    }
}