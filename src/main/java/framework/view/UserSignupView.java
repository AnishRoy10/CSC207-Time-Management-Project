package framework.view;

import interface_adapter.controller.UserSignupController;
import interface_adapter.viewmodel.UserSignupViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * The UserSignupView class represents the GUI for user signup.
 */
public class UserSignupView extends JFrame {
    private final UserSignupController userSignupController;
    private final UserSignupViewModel userSignupViewModel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel messageLabel;

    /**
     * Constructor for UserSignupView.
     *
     * @param userSignupController The controller for user signup.
     * @param userSignupViewModel The view model for user signup.
     */
    public UserSignupView(UserSignupController userSignupController, UserSignupViewModel userSignupViewModel) {
        this.userSignupController = userSignupController;
        this.userSignupViewModel = userSignupViewModel;

        setTitle("User Signup");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                returnToWelcomeView();
            }
        });
        initComponents();
        setLocationRelativeTo(null); // Center the window
    }

    /**
     * Returns to the welcome view.
     */
    private void returnToWelcomeView() {
        SwingUtilities.invokeLater(() -> {
            WelcomeView welcomeView = new WelcomeView();
            welcomeView.setVisible(true);
        });
    }

    /**
     * Initializes the components of the signup view.
     */
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(45, 45, 45)); // Dark background
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Signup message
        JLabel signupLabel = new JLabel("Create Your Account", JLabel.CENTER);
        signupLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
        signupLabel.setForeground(new Color(210, 210, 210)); // Light text color
        panel.add(signupLabel, BorderLayout.NORTH);

        // Input fields panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);

        initializeFields(inputPanel);
        initializeCheckbox(inputPanel);
        initializeButton(inputPanel);
        initializeMessageLabel(inputPanel);

        panel.add(inputPanel, BorderLayout.CENTER);
        add(panel);
    }

    /**
     * Initializes the text fields for username, password, and confirm password.
     */
    private void initializeFields(JPanel panel) {
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 25)); // Set height to 30
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Ensure it respects the height
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25)); // Set height to 30
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Ensure it respects the height
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(200, 25)); // Set height to 30
        confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Ensure it respects the height

        panel.add(createLabeledComponent("Username:", usernameField));
        panel.add(Box.createVerticalStrut(10)); // Add spacing between components
        panel.add(createLabeledComponent("Password:", passwordField));
        panel.add(Box.createVerticalStrut(10)); // Add spacing between components
        panel.add(createLabeledComponent("Confirm Password:", confirmPasswordField));
        panel.add(Box.createVerticalStrut(10)); // Add spacing between components
    }

    /**
     * Initializes and adds the show password checkbox to the panel.
     *
     * @param panel The panel to add the checkbox to.
     */
    private void initializeCheckbox(JPanel panel) {
        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        styleCheckbox(showPasswordCheckbox);
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passwordField.setEchoChar((char) 0);
                confirmPasswordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
                confirmPasswordField.setEchoChar('*');
            }
        });

        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel.setOpaque(false);
        checkboxPanel.add(showPasswordCheckbox);

        panel.add(checkboxPanel);
        panel.add(Box.createVerticalStrut(10)); // Add spacing between components
    }

    /**
     * Initializes and adds the signup button to the panel.
     *
     * @param panel The panel to add the button to.
     */
    private void initializeButton(JPanel panel) {
        JButton signupButton = new JButton("Sign Up");
        styleButton(signupButton, new Color(60, 63, 65));
        signupButton.setPreferredSize(new Dimension(200, 40)); // Set the preferred size for wider button
        signupButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Ensure it respects the width
        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            userSignupController.signup(username, password, confirmPassword);
            messageLabel.setText(userSignupViewModel.getMessage());
        });

        panel.add(signupButton);
        panel.add(Box.createVerticalStrut(10)); // Add spacing between components
    }

    /**
     * Initializes and adds the message label to the panel.
     *
     * @param panel The panel to add the message label to.
     */
    private void initializeMessageLabel(JPanel panel) {
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(new Color(210, 210, 210));
        panel.add(messageLabel);
    }

    /**
     * Creates a labeled component for the UI.
     *
     * @param labelText the label text
     * @param component the component to be labeled
     * @return the panel containing the labeled component
     */
    private JPanel createLabeledComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        label.setForeground(new Color(210, 210, 210)); // Light text color

        component.setFont(new Font("Helvetica Neue", Font.PLAIN, 18)); // Larger text for entry

        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
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
     * Styles a JCheckBox.
     *
     * @param checkBox The JCheckBox to style.
     */
    private void styleCheckbox(JCheckBox checkBox) {
        checkBox.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        checkBox.setForeground(new Color(210, 210, 210)); // Light text color
        checkBox.setOpaque(false);
    }
}
