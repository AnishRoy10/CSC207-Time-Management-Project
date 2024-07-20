package framework.view;


import data_access.InMemoryTimerDataAccessObject;
import interface_adapter.viewmodel.RunningTimerViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunningTimerView extends JFrame {

    private final JLabel timerLabel;

    public RunningTimerView() {
        setTitle(RunningTimerViewModel.TITLE_LABEL);
        setSize(1200, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));

        timerLabel = new JLabel(RunningTimerViewModel.HOURS +
                ":" + RunningTimerViewModel.MINUTES + ":" + RunningTimerViewModel.SECONDS);

        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
        timerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        timerPanel.setBackground(Color.WHITE);

        timerPanel.add(timerLabel);
        add(timerPanel, BorderLayout.CENTER);

        ActionListener updateTimer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // update timer use case

            }
        };
        Timer actionTimer = new Timer(100, updateTimer);
        actionTimer.setRepeats(true);
        actionTimer.start();

    }
}
