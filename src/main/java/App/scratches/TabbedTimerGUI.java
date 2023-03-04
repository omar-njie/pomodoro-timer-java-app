package App.scratches;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TabbedTimerGUI extends JFrame implements ActionListener {
    private JLabel pomodoroLabel;
    private JLabel shortBreakLabel;
    private JLabel longBreakLabel;
    private JButton pomodoroStartButton;
    private JButton pomodoroStopButton;
    private JButton shortBreakStartButton;
    private JButton shortBreakStopButton;
    private JButton longBreakStartButton;
    private JButton longBreakStopButton;
    private JButton nextButton;
    private Timer timer;
    private int count;

    public TabbedTimerGUI() {
        super("Tabbed Timer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane);

        // Create the first tab for the pomodoro timer
        JPanel pomodoroPanel = new JPanel(new FlowLayout());
        tabbedPane.addTab("Pomodoro", pomodoroPanel);

        pomodoroLabel = new JLabel("25:00");
        pomodoroLabel.setFont(new Font("Serif", Font.BOLD, 30));
        pomodoroPanel.add(pomodoroLabel);

        pomodoroStartButton = new JButton("Start");
        pomodoroStartButton.addActionListener(this);
        pomodoroPanel.add(pomodoroStartButton);

        pomodoroStopButton = new JButton("Stop");
        pomodoroStopButton.addActionListener(this);
        pomodoroPanel.add(pomodoroStopButton);

        // Create the second tab for the short break timer
        JPanel shortBreakPanel = new JPanel(new FlowLayout());
        tabbedPane.addTab("Short Break", shortBreakPanel);

        shortBreakLabel = new JLabel("05:00");
        shortBreakLabel.setFont(new Font("Serif", Font.BOLD, 30));
        shortBreakPanel.add(shortBreakLabel);

        shortBreakStartButton = new JButton("Start");
        shortBreakStartButton.addActionListener(this);
        shortBreakPanel.add(shortBreakStartButton);

        shortBreakStopButton = new JButton("Stop");
        shortBreakStopButton.addActionListener(this);
        shortBreakPanel.add(shortBreakStopButton);

        // Create the third tab for the long break timer
        JPanel longBreakPanel = new JPanel(new FlowLayout());
        tabbedPane.addTab("Long Break", longBreakPanel);

        longBreakLabel = new JLabel("15:00");
        longBreakLabel.setFont(new Font("Serif", Font.BOLD, 30));
        longBreakPanel.add(longBreakLabel);

        longBreakStartButton = new JButton("Start");
        longBreakStartButton.addActionListener(this);
        longBreakPanel.add(longBreakStartButton);

        longBreakStopButton = new JButton("Stop");
        longBreakStopButton.addActionListener(this);
        longBreakPanel.add(longBreakStopButton);

        // Create the next button to cycle between the tabs
        nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            int count = tabbedPane.getTabCount();
            tabbedPane.setSelectedIndex((index + 1) % count);
        });
        getContentPane().add(nextButton, BorderLayout.SOUTH);

        // Create the timer that will update the label
        timer = new Timer(1000, e -> {
            count--;
            int minutes = count / 60;
            int seconds = count % 60;
            if (tabbedPane.getSelectedIndex() == 0) {
                pomodoroLabel.setText(String.format("%02d:%02d", minutes, seconds));
            } else if (tabbedPane.getSelectedIndex() == 1) {
                shortBreakLabel.setText(String.format("%02d:%02d", minutes, seconds));
            } else if (tabbedPane.getSelectedIndex() == 2) {
                longBreakLabel.setText(String.format("%02d:%02d", minutes, seconds));
            }
            if (count == 0) {
                timer.stop();
                JOptionPane.showMessageDialog(TabbedTimerGUI.this, "Timer Finished!");
            }
        });

    }
    public void actionPerformed (ActionEvent e){
        if (e.getSource() == pomodoroStartButton) {
            count = 25 * 60;
            timer.start();
        } else if (e.getSource() == pomodoroStopButton) {
            timer.stop();
            pomodoroLabel.setText("25:00");
        } else if (e.getSource() == shortBreakStartButton) {
            count = 5 * 60;
            timer.start();
        } else if (e.getSource() == shortBreakStopButton) {
            timer.stop();
            shortBreakLabel.setText("05:00");
        } else if (e.getSource() == longBreakStartButton) {
            count = 15 * 60;
            timer.start();
        } else if (e.getSource() == longBreakStopButton) {
            timer.stop();
            longBreakLabel.setText("15:00");
        }
    }
    public static void main (String[]args){
        TabbedTimerGUI gui = new TabbedTimerGUI();
        gui.setVisible(true);
    }
}
