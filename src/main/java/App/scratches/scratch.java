package App.scratches;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanContrastIJTheme;

import javax.swing.*;
import java.awt.*;

class Scratch {

    private JFrame frame;

    public Scratch() {
        initialize();
    }

    private void initialize() {

        frame = new JFrame();
        frame.setBounds(100, 100, 592, 387);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frame.getContentPane().add(tabbedPane);

        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("New tab", null, panel_1, null);

        JLabel lblNewLabel = new JLabel("Pomodoro");
        panel_1.add(lblNewLabel);

        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("New tab", null, panel_2, null);

        JPanel panel_3 = new JPanel();
        tabbedPane.addTab("New tab", null, panel_3, null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                dark_mode();
                Scratch window = new Scratch();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void dark_mode() {
        try {
            UIManager.setLookAndFeel(new FlatMaterialDeepOceanContrastIJTheme());
        } catch (Exception e) {
            System.err.println("Couldn't set look and feel");
        }
    }
}