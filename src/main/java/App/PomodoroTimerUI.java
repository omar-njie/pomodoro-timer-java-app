package App;

import App.scratches.TabbedTimerGUI;
import App.utilities.OsIdentifier;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class PomodoroTimerUI extends JFrame implements ActionListener {

    private final JMenuBar menu_bar = new JMenuBar();
    private final JMenu edit_menu = new JMenu("Edit");
    private final JMenu reset = new JMenu("Reset");
    private final JMenuItem reset_pomodoro = new JMenuItem("Reset Pomodoro");
    private final JMenuItem about = new JMenuItem("About");
    private final JMenuItem themes = new JMenuItem("Themes");
    private final JMenuItem exit = new JMenuItem("Exit");
    OsIdentifier osIdentifier = new OsIdentifier(System.getProperty("os.name").toLowerCase());
    private JPanel main_panel;
    private JTabbedPane main_tabbed_pane;
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

    public PomodoroTimerUI() {
        init();
    }

    public static void start() {

    }

    public static void stop() {

    }

    private void init() {
        this.setTitle("Pomodoro Timer");

        if (osIdentifier.is_mac())
            this.getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);

        this.setContentPane(main_panel);
        this.setSize(553, 380);
        this.setJMenuBar(menu_bar);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);
        this.setLocationRelativeTo(null);


        // Tooltips Texts
        edit_menu.setToolTipText("<html><ul>" + "<li>" + "<h3>Drop Settings Menu ALT+F</h3>" + "</li>" + "<li>" + "<h3>Reload CTRL+R</h3>" + "</li>" + "<li>" + "<h3>Themes CTRL+T</h3>" + "</li>" + "<li>" + "<h3>Exit CTRL+E</h3>" + "</li>" + "</ul></html>");
        about.setToolTipText("<html><h3>Reload settings</h3></html>");
        themes.setToolTipText("<html><h3>Change themes</h3></html>");
        exit.setToolTipText("<html><h3>Exit</h3></html>");

        // shortcuts
        edit_menu.setMnemonic('S');
        about.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        themes.setAccelerator(KeyStroke.getKeyStroke('T', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        exit.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        Font font = new Font("JetBrainsMono Nerd Font", Font.BOLD, 18);

        edit_menu.setFont(new Font("FiraCode Nerd Font", Font.BOLD, 18));
        reset.setFont(new Font("FiraCode Nerd Font", Font.BOLD, 18));
        about.setFont(font);
        exit.setFont(font);
        reset_pomodoro.setFont(font);

        menu_bar.add(edit_menu);
        menu_bar.add(reset);

        edit_menu.add(themes);
        edit_menu.addSeparator();
        edit_menu.add(about);
        edit_menu.addSeparator();
        edit_menu.add(exit);
        reset.add(reset_pomodoro);

        main_tabbed_pane.addTab("Pomodoro", pomodoro_tab);
        main_tabbed_pane.addTab("Short Break", short_break_tab);
        main_tabbed_pane.addTab("Long Break", long_break_tab);

        // TAB1:
        pomodoro_label.setText("Pomodoro");
        timer_label.setText("25:00");
        start_button.setText("Start");
        stop_button.setText("Stop");
        start_button.putClientProperty("JButton.buttonType", "roundRect");
        stop_button.putClientProperty("JButton.buttonType", "roundRect");
        next_button.putClientProperty("JButton.buttonType", "roundRect");
        next_button.setToolTipText("<html><h2>Next</h2></html>");

        // TAB2:
        short_break_label.setText("Short Break");
        short_timer_label.setText("5:00");
        tab2_start_button.setText("Start");
        tab2_stop_button.setText("Stop");
        tab2_start_button.putClientProperty("JButton.buttonType", "roundRect");
        tab2_stop_button.putClientProperty("JButton.buttonType", "roundRect");

        // TAB3:
        long_break_label.setText("Long Break");
        long_timer_label.setText("15:00");
        tab3_start_button.setText("Start");
        tab3_stop_button.setText("Stop");
        tab3_start_button.putClientProperty("JButton.buttonType", "roundRect");
        tab3_stop_button.putClientProperty("JButton.buttonType", "roundRect");

        exit.addActionListener(this);
        reset_pomodoro.addActionListener(this);
        start_button.addActionListener(this);
        stop_button.addActionListener(this);
        tab2_start_button.addActionListener(this);
        tab2_stop_button.addActionListener(this);
        tab3_start_button.addActionListener(this);
        tab3_stop_button.addActionListener(this);
        next_button.addActionListener(this);

        // Create the timer that will update the label
        timer = new Timer(1000, e -> {
            count--;
            int minutes = count / 60;
            int seconds = count % 60;
            if (main_tabbed_pane.getSelectedIndex() == 0) {
                timer_label.setText(String.format("%02d:%02d", minutes, seconds));
            } else if (main_tabbed_pane.getSelectedIndex() == 1) {
                short_timer_label.setText(String.format("%02d:%02d", minutes, seconds));
            } else if (main_tabbed_pane.getSelectedIndex() == 2) {
                long_timer_label.setText(String.format("%02d:%02d", minutes, seconds));
            }
            if (count == 0) {
                timer.stop();
                // JOptionPane.showMessageDialog(PomodoroTimerUI.this, "Timer Finished!");
                // play sound
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/alarm.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                    System.out.println("Sound played successfully! | " + new Date().toString());
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    public void next() {
        int index = main_tabbed_pane.getSelectedIndex();
        int count = main_tabbed_pane.getTabCount();
        main_tabbed_pane.setSelectedIndex((index + 1) % count);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            System.exit(0);
        }

        if (e.getSource() == reset_pomodoro) {
            // reset only the tab that is selected
            if (main_tabbed_pane.getSelectedIndex() == 0)
                timer_label.setText("25:00");
            else if (main_tabbed_pane.getSelectedIndex() == 1)
                short_timer_label.setText("5:00");
            else if (main_tabbed_pane.getSelectedIndex() == 2)
                long_timer_label.setText("15:00");

        }

        if (e.getSource() == start_button) {
            count = 25 * 60;
            timer.start();
        }

        if (e.getSource() == stop_button)
            timer.stop();

        if (e.getSource() == tab2_start_button) {
            count = 1 * 60;
            timer.start();
        }

        if (e.getSource() == tab2_stop_button)
            timer.stop();

        if (e.getSource() == tab3_start_button) {
            count = 15 * 60;
            timer.start();
        }

        if (e.getSource() == tab3_stop_button)
            timer.stop();


        if (e.getSource() == next_button) {
            next();
            System.out.println("Next");
        }
    }


}
