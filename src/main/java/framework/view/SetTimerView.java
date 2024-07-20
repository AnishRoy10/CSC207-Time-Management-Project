package framework.view;

import interface_adapter.controller.TimerController;
import interface_adapter.viewmodel.SetTimerViewModel;
import interface_adapter.viewmodel.RunningTimerViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SetTimerView extends JFrame{
    public final String viewName = "set timer";

    private final SetTimerViewModel setTimerViewModel;
    private final RunningTimerViewModel runningTimerViewModel;
    private final TimerController timerController;

    private final JTextField hoursInputField;
    private final JTextField minutesInputField;
    private final JTextField secondsInputField;

    private final JButton setTimerButton;

    public SetTimerView(TimerController setTimerController,
                        SetTimerViewModel setTimerViewModel,
                        RunningTimerViewModel runningTimerViewModel) {
        this.timerController = setTimerController;
        this.setTimerViewModel = setTimerViewModel;
        this.runningTimerViewModel = runningTimerViewModel;

        setTitle(SetTimerViewModel.TITLE_LABEL);
        setSize(1200, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));

        hoursInputField = new JTextField(5);
        minutesInputField = new JTextField(5);
        secondsInputField = new JTextField(5);

        setTimerButton = new JButton(SetTimerViewModel.SET_TIMER_BUTTON_LABEL);
        setTimerButton.addActionListener(e -> setTimer());

        JPanel setTimerPanel = new JPanel();
        setTimerPanel.setLayout(new BoxLayout(setTimerPanel, BoxLayout.Y_AXIS));
        setTimerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setTimerPanel.setBackground(Color.WHITE);

        setTimerPanel.add(createLabeledComponent(
                SetTimerViewModel.HOURS_LABEL, hoursInputField));
        setTimerPanel.add(createLabeledComponent(
                SetTimerViewModel.MINUTES_LABEL, minutesInputField));
        setTimerPanel.add(createLabeledComponent(
                SetTimerViewModel.SECONDS_LABEL, secondsInputField));
        setTimerPanel.add(setTimerButton);

        add(setTimerPanel, BorderLayout.CENTER);
    }

    private Component createLabeledComponent(String label, Component component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 0, 10, 0));
        return panel;
    }

    private void setTimer() {
        String hours = hoursInputField.getText();
        String minutes = minutesInputField.getText();
        String seconds = secondsInputField.getText();
        timerController.execute_set_timer(hours, minutes, seconds);

        if ("Success".equals(runningTimerViewModel.getMessage())) {
            SwingUtilities.invokeLater(() -> {
                RunningTimerView runningTimerView = new RunningTimerView(
                        timerController, runningTimerViewModel);
                runningTimerView.setVisible(true);
                dispose();
            });
        }
        clearInputFields();
    }

    private void clearInputFields() {
        hoursInputField.setText("");
        minutesInputField.setText("");
        secondsInputField.setText("");
    }


}
