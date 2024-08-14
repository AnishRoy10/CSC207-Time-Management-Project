package framework.view;

import interface_adapter.controller.UserLoginController;
import interface_adapter.viewmodel.UserLoginViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * The UserLoginView class represents the GUI for user login.
 */
public class UserLoginView extends JFrame {
    private final UserLoginController userLoginController;
    private final UserLoginViewModel userLoginViewModel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public UserLoginView(UserLoginController userLoginController, UserLoginViewModel userLoginViewModel) {
        this.userLoginController = userLoginController;
        this.userLoginViewModel = userLoginViewModel;

        setTitle("User Login");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null); // Center the window
    }

    /**
     * Initializes the components of the login view.
     */
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(45, 45, 45)); // Dark background
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Login message
        JLabel loginLabel = new JLabel("Login to Your Account", JLabel.CENTER);
        loginLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
        loginLabel.setForeground(new Color(210, 210, 210)); // Light text color
        panel.add(loginLabel, BorderLayout.NORTH);

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
     * Initializes the text fields for username and password.
     */
    private void initializeFields(JPanel panel) {
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 40)); // Increase height to 40
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Ensure it respects the height
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 40)); // Increase height to 40
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Ensure it respects the height

        panel.add(createLabeledComponent("Username:", usernameField));
        panel.add(Box.createVerticalStrut(10)); // Add spacing between components
        panel.add(createLabeledComponent("Password:", passwordField));
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
            } else {
                passwordField.setEchoChar('*');
            }
        });

        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel.setOpaque(false);
        checkboxPanel.add(showPasswordCheckbox);

        panel.add(checkboxPanel);
        panel.add(Box.createVerticalStrut(10)); // Add spacing between components
    }

    /**
     * Initializes and adds the login button to the panel.
     *
     * @param panel The panel to add the button to.
     */
    private void initializeButton(JPanel panel) {
        JButton loginButton = new JButton("Login");
        styleButton(loginButton, new Color(60, 63, 65));
        loginButton.setPreferredSize(new Dimension(200, 40)); // Set the preferred size for wider button
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Ensure it respects the width
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            userLoginController.login(username, password);
            messageLabel.setText(userLoginViewModel.getMessage());

            // If login is successful, open MainPageView
            if ("Login successful.".equals(userLoginViewModel.getMessage())) {
                SwingUtilities.invokeLater(() -> {
                    MainPageView mainPage = new MainPageView(username);
                    mainPage.setVisible(true);
                    dispose(); // Close the login view
                });
            }
        });

        panel.add(loginButton);
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
