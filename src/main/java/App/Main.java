package App;

import App.utilities.themes.DefaultTheme;
import App.utilities.OsIdentifier;
import App.utilities.settings.Settings;

import javax.swing.*;

/**
 * @author Omar
 * @version 1.0
 */

public class Main {

    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();
        OsIdentifier osIdentifier = new OsIdentifier(os);

        SwingUtilities.invokeLater(() -> {
            if (osIdentifier.is_mac()) {
                System.setProperty("apple.awt.application.appearance", "system");
                System.setProperty("apple.laf.useScreenMenuBar", "true");
            }
            // Apply the default theme
            DefaultTheme.default_theme();
            new Settings();
        });
    }

}
