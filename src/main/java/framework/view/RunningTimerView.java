package framework.view;


import interface_adapter.controller.TimerController;
import interface_adapter.viewmodel.RunningTimerViewModel;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * This class represents the page for the timer when it is running.
 * This is displayed after the timer has been set by the user and includes
 * the ability for the user to pause the timer.
 */
public class RunningTimerView extends JFrame {

    private final TimerController timerController;
    private final RunningTimerViewModel runningTimerViewModel;

    private final JLabel timerLabel;
    private final JButton pauseButton;
    private final JButton returnButton;

    private boolean paused = false;

    private final Timer actionTimer;

    /**
     * Constructor for RunningTimerView. Sets up the UI components for this page.
     * @param timerController controller for timer use cases
     * @param runningTimerViewModel view model for RunningTimerView
     */
    public RunningTimerView(TimerController timerController,
                            RunningTimerViewModel runningTimerViewModel) {
        this.timerController = timerController;
        this.runningTimerViewModel = runningTimerViewModel;

        setTitle(RunningTimerViewModel.TITLE_LABEL);
        setSize(1200, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 28));
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));

        timerLabel = new JLabel(RunningTimerViewModel.HOURS +
                ":" + RunningTimerViewModel.MINUTES + ":" + RunningTimerViewModel.SECONDS);

        pauseButton = new JButton(RunningTimerViewModel.PAUSE_LABEL);
        pauseButton.addActionListener(e -> pauseTimer());

        returnButton = new JButton(RunningTimerViewModel.RETURN_LABEL);
        returnButton.addActionListener(e -> returnPrevious());
        returnButton.setVisible(false);

        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
        timerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        timerPanel.setBackground(Color.WHITE);

        timerPanel.add(timerLabel);
        timerPanel.add(pauseButton);
        timerPanel.add(returnButton);
        add(timerPanel, BorderLayout.CENTER);

        ActionListener updateTimer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        };
        actionTimer = new Timer(100, updateTimer);
        actionTimer.setRepeats(true);
        actionTimer.setInitialDelay(100);
        actionTimer.start();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                actionTimer.stop();
            }
        });

    }

    /**
     * Executes the update timer use case. If the timer has ended, this method
     * will call the endTimer() method.
     */
    private void updateTimer() {
        if (!paused) {
            timerController.execute_update_timer();
            timerLabel.setText(RunningTimerViewModel.HOURS +
                    ":" + RunningTimerViewModel.MINUTES + ":" + RunningTimerViewModel.SECONDS);
            if (RunningTimerViewModel.HOURS.equals("0") &&
                    RunningTimerViewModel.MINUTES.equals("0") &&
                    RunningTimerViewModel.SECONDS.equals("0")) {
                endTimer();
            }
        }
    }

    /**
     * Ends the timer by playing a sound to notify the user and allowing
     * the user to return to the main page.
     */
    private void endTimer() {
        actionTimer.stop();
        playSound();
        pauseButton.setVisible(false);
        returnButton.setVisible(true);
    }

    /**
     * Executes the pause timer use case when the user presses the pause button.
     */
    private void pauseTimer() {
        timerController.execute_pause_timer(paused);
        if (runningTimerViewModel.getMessage().equals("Success")) {
            pauseButton.setText(RunningTimerViewModel.PAUSE_LABEL);
            paused = !paused;
        }
    }

    /**
     * Lets the user return to the main page.
     */
    public void returnPrevious() {
        actionTimer.stop();
        dispose();
    }

    /**
     * Plays a sound.
     */
    public void playSound() {
//        Toolkit.getDefaultToolkit().beep();
        Clip clip;
        String fileName = System.getProperty("user.dir") + "\\src\\main\\java\\data_access\\timer_alarm.wav";
        try {
            File file = new File(fileName);
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                // load the sound into memory (a Clip)
                clip = AudioSystem.getClip();
                clip.open(sound);
            }
            else {
                throw new RuntimeException("Sound: file not found: " + fileName);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }

        clip.start();
    }
}
