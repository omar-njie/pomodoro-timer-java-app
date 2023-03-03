package App;

import App.scratches.StopWatch;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanContrastIJTheme;

import javax.swing.*;

public class TimerExample {

    public static void main(String[] args) {
        dark_mode();
        new StopWatch();
    }

    public static void dark_mode() {
        try {
            UIManager.setLookAndFeel(new FlatMaterialDeepOceanContrastIJTheme());
        } catch (Exception e) {
            System.err.println("Couldn't set look and feel, using default");
        }
    }
}
