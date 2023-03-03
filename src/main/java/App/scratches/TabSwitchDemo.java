package App.scratches;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TabSwitchDemo extends JFrame {

    private JTabbedPane tabbedPane;
    private JButton button;

    public TabSwitchDemo() {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("First Tab", new JLabel("This is the first tab"));
        tabbedPane.addTab("Second Tab", new JLabel("This is the second tab"));
        button = new JButton("Switch Tabs");

        button.addActionListener(e -> {
            // Get the current selected index of the tabbed pane
            int index = tabbedPane.getSelectedIndex();

            if (index == 0) {
                tabbedPane.setSelectedIndex(1);
            }
            else if (index == 1) {
                tabbedPane.setSelectedIndex(0);
            }
        });


        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);
        // Add the panel to the frame
        this.add(panel);
        this.pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TabSwitchDemo demo = new TabSwitchDemo();
            demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            demo.setVisible(true);
        });
    }
}
