package App.scratches;

import App.utilities.OsIdentifier;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PomodoroTimerUI2 extends JFrame implements ActionListener {

    private static final Font MENU_FONT = new Font("FiraCode Nerd Font", Font.BOLD, 18);
    private static final Font BUTTON_FONT = new Font("JetBrainsMono Nerd Font", Font.BOLD, 18);
    private final JMenuBar MENU_BAR = new JMenuBar();
    private final JMenu EDIT_MENU = new JMenu("Edit");
    private final JMenu RESET_MENU = new JMenu("Reset");
    private final JMenuItem RESET_POMODORO = new JMenuItem("Reset Pomodoro");
    private final JMenuItem ABOUT = new JMenuItem("About");
    private final JMenuItem THEMES = new JMenuItem("Themes");
    private final JMenuItem EXIT = new JMenuItem("Exit");
    private final OsIdentifier OS_IDENTIFIER = new OsIdentifier(System.getProperty("os.name").toLowerCase());
    private JPanel pomodoro_tab;
    private JPanel short_break_tab;
    private JPanel long_break_tab;
    private JLabel pomodoro_label;
    private JLabel timer_label;
    private JButton start_button;
    private JButton next_button;
    private JLabel short_break_label;
    private JButton tab2_start_button;
    private JLabel short_timer_label;
    private JButton stop_button;
    private JButton tab2_stop_button;
    private JLabel long_break_label;
    private JLabel long_timer_label;
    private JButton tab3_start_button;
    private JButton tab3_stop_button;
    private Timer timer;
    private int count;

    public PomodoroTimerUI2() {
        init();
    }

    /**
     * The main method to start the application
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PomodoroTimerUI2().setVisible(true));
    }

    private void init() {
        setTitle("Pomodoro Timer");

    // if (OS_IDENTIFIER.is_mac()) {
    //     getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
    // }

    setSize(553, 380);
    setJMenuBar(MENU_BAR);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    setResizable(true);
    setLocationRelativeTo(null);

    // Create main panel and tabbed pane
    JPanel main_panel = new JPanel();
    JTabbedPane main_tabbed_pane = new JTabbedPane();
    main_panel.setLayout(new BorderLayout());
    main_panel.add(main_tabbed_pane, BorderLayout.CENTER);

    // Add tabs to the tabbed pane
    pomodoro_tab = new JPanel();
    short_break_tab = new JPanel();
    long_break_tab = new JPanel();
    main_tabbed_pane.addTab("Pomodoro", pomodoro_tab);
    main_tabbed_pane.addTab("Short Break", short_break_tab);
    main_tabbed_pane.addTab("Long Break", long_break_tab);

    // Create menus
    MENU_BAR.add(EDIT_MENU);
    MENU_BAR.add(RESET_MENU);
    EDIT_MENU.setFont(MENU_FONT);
    RESET_MENU.setFont(MENU_FONT);

    // Add menu items
    THEMES.setFont(MENU_FONT);
    ABOUT.setFont(BUTTON_FONT);
    EXIT.setFont(BUTTON_FONT);
    RESET_POMODORO.setFont(BUTTON_FONT);
    RESET_MENU.add(RESET_POMODORO);
    EDIT_MENU.addSeparator();
    EDIT_MENU.add(THEMES);
    EDIT_MENU.addSeparator();
    EDIT_MENU.add(ABOUT);
    EDIT_MENU.addSeparator();
    EDIT_MENU.add(EXIT);

    // Create JLabels for the pomodoro tab
    pomodoro_label = new JLabel("Pomodoro");
    timer_label = new JLabel("00:00");
    start_button = new JButton("Start");
    next_button = new JButton("Next");

    // Add the JLabels and JButtons to the pomodoro_tab panel
    pomodoro_tab.setLayout(new GridLayout(3, 1));
    pomodoro_tab.add(pomodoro_label);
    pomodoro_tab.add(timer_label);
    pomodoro_tab.add(start_button);
    pomodoro_tab.add(next_button);

    // Set fonts
    pomodoro_label.setFont(BUTTON_FONT);
    timer_label.setFont(BUTTON_FONT);
    start_button.setFont(BUTTON_FONT);
    next_button.setFont(BUTTON_FONT);

    add(main_panel);

        // Set action listeners
        start_button.addActionListener(this);
        stop_button.addActionListener(this);
        tab2_start_button.addActionListener(this);
        tab2_stop_button.addActionListener(this);
        tab3_start_button.addActionListener(this);
        tab3_stop_button.addActionListener(this);
        next_button.addActionListener(this);
        EXIT.addActionListener(this);
        ABOUT.addActionListener(this);
        THEMES.addActionListener(this);
        RESET_POMODORO.addActionListener(this);

        // Create the timer that will update the label
        timer = new Timer(1000, e -> {
            count--;
            String min = String.format("%02d", count / 60);
            String sec = String.format("%02d", count % 60);
            timer_label.setText(min + ":" + sec);

            // If the countdown timer reaches 0, play a sound, and display the next timer
            if (count <= 0) {
                timer.stop();
                playSound();
                nextTimer();
            }
        });
    }

    private void playSound() {
        try {
            File soundFile = new File("resources/bell.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void nextTimer() {
        if (pomodoro_tab.isVisible()) {
            if (pomodoro_label.getText().equals("Pomodoro")) {
                pomodoro_label.setText("Short Break");
                timer_label.setText("05:00");
                count = 5 * 60;
            } else {
                pomodoro_label.setText("Pomodoro");
                timer_label.setText("25:00");
                count = 25 * 60;
            }
        } else if (short_break_tab.isVisible()) {
            pomodoro_label.setText("Pomodoro");
            timer_label.setText("25:00");
            count = 25 * 60;
        } else {
            pomodoro_label.setText("Pomodoro");
            timer_label.setText("25:00");
            count = 25 * 60;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == EXIT) {
            System.exit(0);
        } else if (e.getSource() == RESET_POMODORO) {
            timer.stop();
            pomodoro_label.setText("Pomodoro");
            timer_label.setText("25:00");
            count = 25 * 60;
        } else if (e.getSource() == start_button) {
            timer.start();
            start_button.setEnabled(false);
            stop_button.setEnabled(true);
            next_button.setEnabled(false);
        } else if (e.getSource() == stop_button) {
            timer.stop();
            start_button.setEnabled(true);
            stop_button.setEnabled(false);
            next_button.setEnabled(true);
        } else if (e.getSource() == tab2_start_button) {
            timer.stop();
            short_break_label.setText("Short Break");
            short_timer_label.setText("05:00");
            count = 5 * 60;
            timer_label.setText("05:00");
            pomodoro_tab.setVisible(false);
            short_break_tab.setVisible(true);
            long_break_tab.setVisible(false);
            start_button.setEnabled(true);
            stop_button.setEnabled(false);
            next_button.setEnabled(false);
            tab2_start_button.setEnabled(false);
            tab2_stop_button.setEnabled(true);
            tab3_start_button.setEnabled(false);
            tab3_stop_button.setEnabled(false);
        } else if (e.getSource() == tab2_stop_button) {
            timer.stop();
            start_button.setEnabled(true);
            stop_button.setEnabled(false);
            next_button.setEnabled(true);
            tab2_start_button.setEnabled(true);
            tab2_stop_button.setEnabled(false);
        } else if (e.getSource() == tab3_start_button) {
            timer.stop();
            long_break_label.setText("Long Break");
            long_timer_label.setText("15:00");
            count = 15 * 60;
            timer_label.setText("15:00");
            pomodoro_tab.setVisible(false);
            short_break_tab.setVisible(false);
            long_break_tab.setVisible(true);
            start_button.setEnabled(true);
            stop_button.setEnabled(false);
            next_button.setEnabled(false);
            tab2_start_button.setEnabled(false);
            tab2_stop_button.setEnabled(false);
            tab3_start_button.setEnabled(false);
            tab3_stop_button.setEnabled(true);
        } else if (e.getSource() == tab3_stop_button) {
            timer.stop();
            pomodoro_label.setText("Pomodoro");
            timer_label.setText("25:00");
            count = 25 * 60;
            start_button.setEnabled(true);
            stop_button.setEnabled(false);
            next_button.setEnabled(true);
            tab2_start_button.setEnabled(true);
            tab2_stop_button.setEnabled(false);
            tab3_start_button.setEnabled(true);
            tab3_stop_button.setEnabled(false);
        }
    }
}

