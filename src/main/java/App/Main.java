package App;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanContrastIJTheme;

import javax.swing.*;

/**
 * @author Omar
 * This is the main class of the application.
 * It is responsible for initializing the GUI and starting the application.
 */

public class Main {

    public static void main(String[] args) {
        dark_mode();
        new Thread(PomodoroTimer::new).start();
    }

    public static void dark_mode() {
        try {
            UIManager.setLookAndFeel(new FlatMaterialDeepOceanContrastIJTheme());
        } catch (Exception e) {
            System.err.println("Couldn't set look and feel, using default");
        }
    }
}
