package framework.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import app.gui.LoginInitializer;
import app.gui.SignupInitializer;

public class WelcomeView extends JFrame {
    public WelcomeView() {
        setTitle("Welcome");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginInitializer.initializeLogin();
                dispose(); // Close the welcome view
            }
        });

        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.PLAIN, 20));
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignupInitializer.initializeSignup();
                dispose(); // Close the welcome view
            }
        });

        panel.add(loginButton);
        panel.add(signupButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeView welcomeView = new WelcomeView();
            welcomeView.setVisible(true);
        });
    }
}
