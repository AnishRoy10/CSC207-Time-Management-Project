package framework.view;

import app.gui.LeaderboardInitializer;
import app.gui.TimerExecutable;
import app.gui.TodoListInitializer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class represents the main page of the application, which serves as the central navigation screen.
 * It is displayed to the user after a successful login. The main page provides buttons for accessing
 * various features of the application such as the Calendar, Leaderboard, To-do List, and Timer.
 */
public class MainPageView extends JFrame {

    /**
     * Constructor for MainPageView. Sets up the UI components and layout for the main navigation screen.
     */
    public MainPageView() {
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
        // Additional feature buttons can be added here as needed

        add(navigationPanel, BorderLayout.CENTER);
    }

    /**
     * Adds a navigation button to the specified panel.
     * Each button is configured with text and an action listener that defines its behavior.
     *
     * @param panel The panel to which the button will be added.
     * @param buttonText The text displayed on the button.
     * @param actionListener The action listener attached to the button, defining its behavior.
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
     * Placeholder method to open the Calendar feature.
     * This method should be implemented to open the Calendar view of the application.
     */
    private void openCalendar() {
        // Placeholder implementation
        JOptionPane.showMessageDialog(this, "Calendar feature to be implemented");
    }

    /**
     * Placeholder method to open the Leaderboard feature.
     * This method should be implemented to open the Leaderboard view of the application.
     */
    private void openLeaderboard() {LeaderboardInitializer.main(null);}

    /**
     * Opens the To-do List feature of the application.
     * This method is responsible for initializing and displaying the To-do List view.
     */
    private void openTodoList() {
        TodoListInitializer.main(null);
    }

    /**
     * Placeholder method to open the Timer feature.
     * This method should be implemented to open the Timer view of the application.
     */
    private void openTimer() {
        TimerExecutable.main(null);
    }

//    /**
//     * The main method serves as the entry point for the application.
//     * It creates and displays the MainPageView window, which is the central navigation interface for the user.
//     *
//     * @param args Command line arguments (not used in this application).
//     */
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            MainPageView main = new MainPageView();
//            main.setVisible(true);
//        });
//    }
}