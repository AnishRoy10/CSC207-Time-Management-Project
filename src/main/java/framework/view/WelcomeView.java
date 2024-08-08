package framework.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import app.gui.LoginInitializer;
import app.gui.SignupInitializer;

/**
 * The WelcomeView class represents the initial screen of the application,
 * offering options for the user to either log in or sign up.
 * This view serves as the entry point for users, guiding them to the authentication process.
 */
public class WelcomeView extends JFrame {
    private JButton loginButton;
    private JButton signupButton;

    /**
     * Constructor for WelcomeView. Sets up the UI components and layout for the welcome screen.
     */
    public WelcomeView() {
        setTitle("Welcome");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null); // Center the window
    }

    /**
     * Initializes the components of the WelcomeView.
     * This method sets up the buttons for login and sign up, along with their action listeners.
     */
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(45, 45, 45)); // Dark background
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to the Time-Management Application!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 21));
        welcomeLabel.setForeground(new Color(210, 210, 210)); // Light text color
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        buttonsPanel.setOpaque(false);

        loginButton = new JButton("Login");
        styleButton(loginButton, new Color(60, 63, 65));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginInitializer.initializeLogin();
                dispose(); // Close the welcome view
            }
        });

        signupButton = new JButton("Sign Up");
        styleButton(signupButton, new Color(60, 63, 65));
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignupInitializer.initializeSignup();
                dispose(); // Close the welcome view
            }
        });

        buttonsPanel.add(loginButton);
        buttonsPanel.add(signupButton);

        panel.add(buttonsPanel, BorderLayout.CENTER);

        // Footer message
        JLabel footerLabel = new JLabel("Please login or sign up to continue", JLabel.CENTER);
        footerLabel.setFont(new Font("Helvetica Neue", Font.ITALIC, 14));
        footerLabel.setForeground(new Color(150, 150, 150)); // Muted text color
        panel.add(footerLabel, BorderLayout.SOUTH);

        add(panel);
    }

    /**
     * Styles a JButton with the given background color.
     *
     * @param button The JButton to style.
     * @param color  The background color to set.
     */
    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        button.setBackground(color);
        button.setForeground(new Color(210, 210, 210));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    /**
     * Getter for the login button.
     *
     * @return the login button.
     */
    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * Getter for the signup button.
     *
     * @return the signup button.
     */
    public JButton getSignupButton() {
        return signupButton;
    }
}
