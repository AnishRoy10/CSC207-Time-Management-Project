package app.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Main class that serves as the central navigation screen for the application.
 */
public class MainPage extends JFrame {

    public MainPage() {
        setTitle("Time Management Application");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Navigation Panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add buttons for each feature
        addNavigationButton(navigationPanel, "Calendar", e -> openCalendar());
        addNavigationButton(navigationPanel, "Leaderboard", e -> openLeaderboard());
        addNavigationButton(navigationPanel, "To-do List", e -> openTodoList());
        addNavigationButton(navigationPanel, "Timer", e -> openTimer());
        // Add more buttons here as needed

        add(navigationPanel, BorderLayout.CENTER);
    }

    /**
     * Adds a button to the navigation panel.
     *
     * @param panel The panel to add the button to.
     * @param buttonText The text for the button.
     * @param actionListener The action listener for the button.
     */
    private void addNavigationButton(JPanel panel, String buttonText, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        button.addActionListener(actionListener);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set uniform size for all buttons
        Dimension buttonSize = new Dimension(225, 50);
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setMinimumSize(buttonSize);

        // Add some spacing between buttons
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        panel.add(Box.createHorizontalStrut(10));
    }


    /**
     * Opens the Calendar feature.
     */
    private void openCalendar() {
        // Code to open Calendar feature
        // Placeholder for now
        JOptionPane.showMessageDialog(this, "Calendar feature to be implemented");
    }


    /**
     * Opens the Leaderboard feature.
     */
    private void openLeaderboard() {
        // Code to open Leaderboard feature
        // Placeholder for now
        JOptionPane.showMessageDialog(this, "Leaderboard feature to be implemented");
    }


    /**
     * Opens the To-do List feature.
     */
    private void openTodoList() {
        TodoListInitializer.main(null);
    }

    /**
     * Opens the Timer feature.
     */
    private void openTimer() {
        // Code to open Timer feature
        // Placeholder for now
        JOptionPane.showMessageDialog(this, "Timer feature to be implemented");
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage main = new MainPage();
            main.setVisible(true);
        });
    }
}
