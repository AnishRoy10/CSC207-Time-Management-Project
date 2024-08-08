package framework.view;

import app.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class represents the main page of the application, which serves as the central navigation screen.
 * It is displayed to the user after a successful login. The main page provides buttons for accessing
 * various features of the application such as the Calendar, Leaderboard, To-do List, and Timer.
 */
public class MainPageView extends JFrame {
    private final String username;

    /**
     * Constructor for MainPageView. Sets up the UI components and layout for the main navigation screen.
     */
    public MainPageView(String username) {
        this.username = username;
        setTitle("Time Management Application");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the window

        // Navigation Panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        navigationPanel.setBackground(new Color(245, 245, 245));

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(60, 63, 65));
        navigationPanel.add(welcomeLabel);
        navigationPanel.add(Box.createVerticalStrut(20)); // Add spacing after the welcome message

        // Add buttons for each feature
        addNavigationButtonWithDescription(navigationPanel, "Calendar", "View and manage your events", e -> openCalendar());
        addNavigationButtonWithDescription(navigationPanel, "Leaderboard", "See where you stand", e -> openLeaderboard());
        addNavigationButtonWithDescription(navigationPanel, "To-do List", "Manage your tasks", e -> openTodoList());
        addNavigationButtonWithDescription(navigationPanel, "Timer", "Track your time", e -> openTimer());
        addNavigationButtonWithDescription(navigationPanel, "Friends", "Manage your friends", e -> openFriendsList());
        addNavigationButtonWithDescription(navigationPanel, "Courses", "View your courses", e -> openCourseView());

        add(navigationPanel, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(245, 245, 245));
        JLabel footerLabel = new JLabel("© 2024 Time Management Application");
        footerLabel.setFont(new Font("Helvetica Neue", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(150, 150, 150));
        footerPanel.add(footerLabel);

        add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds a navigation button with a description to the specified panel.
     * Each button is configured with text, a description, and an action listener that defines its behavior.
     *
     * @param panel The panel to which the button will be added.
     * @param buttonText The text displayed on the button.
     * @param description The brief description displayed next to the button.
     * @param actionListener The action listener attached to the button, defining its behavior.
     */
    private void addNavigationButtonWithDescription(JPanel panel, String buttonText, String description, ActionListener actionListener) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setOpaque(false);

        JButton button = new JButton(buttonText);
        button.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        button.setBackground(new Color(220, 220, 220));
        button.setForeground(new Color(60, 63, 65));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        button.addActionListener(actionListener);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel(description, JLabel.CENTER);
        descriptionLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(100, 100, 100));

        buttonPanel.add(button, BorderLayout.CENTER);
        buttonPanel.add(descriptionLabel, BorderLayout.SOUTH);

        // Add some spacing between buttons
        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(15));
    }

    /**
     * Placeholder method to open the Calendar feature.
     * This method should be implemented to open the Calendar view of the application.
     */
    private void openCalendar() {CalendarInitializer.initializeCalendar(username);}

    /**
     * Placeholder method to open the Leaderboard feature.
     * This method should be implemented to open the Leaderboard view of the application.
     */
    private void openLeaderboard() {LeaderboardInitializer.LeaderboardInitializer();}

    /**
     * Opens the To-do List feature of the application.
     * This method is responsible for initializing and displaying the To-do List view.
     */
    private void openTodoList() {
        TodoListInitializer.initializeTodoList(username);
    }

    /**
     * Placeholder method to open the Timer feature.
     * This method should be implemented to open the Timer view of the application.
     */
    private void openTimer() {
        TimerInitializer.initializeTimer();
    }

    /**
     * Opens the friends list feature of the application
     */
    private void openFriendsList() {
        FriendsListInitializer.InitializeFriendsList(username);
    }

    /**
     * Visualize the course view.
     */
    private void openCourseView() {
        CourseInitializer.initializeView(username);
    }
}
