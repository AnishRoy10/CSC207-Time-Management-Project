package framework.view;


import data_access.InMemoryTimerDataAccessObject;
import interface_adapter.controller.TimerController;
import interface_adapter.viewmodel.RunningTimerViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RunningTimerView extends JFrame {

    private final TimerController timerController;
    private final RunningTimerViewModel runningTimerViewModel;

    private final JLabel timerLabel;
    private final JButton pauseButton;
    private final JButton returnButton;

    private final Timer actionTimer;

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

    private void updateTimer() {
        timerController.execute_update_timer();
        timerLabel.setText(RunningTimerViewModel.HOURS +
                ":" + RunningTimerViewModel.MINUTES + ":" + RunningTimerViewModel.SECONDS);
        if (RunningTimerViewModel.HOURS.equals("0") &&
        RunningTimerViewModel.MINUTES.equals("0") &&
                RunningTimerViewModel.SECONDS.equals("0")) {
            endTimer();
        }
    }

    private void endTimer() {
        actionTimer.stop();
        Toolkit.getDefaultToolkit().beep();
        returnButton.setVisible(true);
    }

    private void pauseTimer() {

    }

    public void returnPrevious() {

    }
}
