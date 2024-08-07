package framework.view;

import interface_adapter.controller.TimerController;
import interface_adapter.viewmodel.SetTimerViewModel;
import interface_adapter.viewmodel.RunningTimerViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * This class represents the page allowing the user to set a timer.
 */
public class SetTimerView extends JFrame{
    public final String viewName = "set timer";

    private final SetTimerViewModel setTimerViewModel;
    private final RunningTimerViewModel runningTimerViewModel;
    private final TimerController timerController;

    private final JTextField hoursInputField;
    private final JTextField minutesInputField;
    private final JTextField secondsInputField;

    private final JButton setTimerButton;

    /**
     * Constructor for SetTimerView. Sets up the UI components for this page.
     * @param timerController controller for timer use cases
     * @param setTimerViewModel view model for SetTimerView
     * @param runningTimerViewModel view model for RunningTimerView
     */
    public SetTimerView(TimerController timerController,
                        SetTimerViewModel setTimerViewModel,
                        RunningTimerViewModel runningTimerViewModel) {
        this.timerController = timerController;
        this.setTimerViewModel = setTimerViewModel;
        this.runningTimerViewModel = runningTimerViewModel;

        setTitle(SetTimerViewModel.TITLE_LABEL);
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

    /**
     * Creates a labeled component for the UI.
     *
     * @param label     the label text
     * @param component the component to be labeled
     * @return the panel containing the labeled component
     */
    private Component createLabeledComponent(String label, Component component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 0, 10, 0));
        return panel;
    }

    /**
     * Executes the set timer use case using the text from the input fields.
     */
    private void setTimer() {
        String hours = hoursInputField.getText();
        String minutes = minutesInputField.getText();
        String seconds = secondsInputField.getText();
        timerController.execute_set_timer(hours, minutes, seconds);
        System.out.println(runningTimerViewModel.getMessage());

        if ("Success".equals(runningTimerViewModel.getMessage())) {
            SwingUtilities.invokeLater(() -> {
                RunningTimerView runningTimerView = new RunningTimerView(
                        timerController, runningTimerViewModel);
                runningTimerView.setVisible(true);
                dispose();
            });
        } else if ("Invalid Input".equals(runningTimerViewModel.getMessage())) {
            JOptionPane.showMessageDialog(this, "Invalid Input.");
        }
        clearInputFields();
    }

    /**
     * Clears all input fields.
     */
    private void clearInputFields() {
        hoursInputField.setText("");
        minutesInputField.setText("");
        secondsInputField.setText("");
    }


}
