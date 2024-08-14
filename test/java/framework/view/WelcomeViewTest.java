package framework.view;

import app.gui.LoginInitializer;
import app.gui.SignupInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class WelcomeViewTest {
    private WelcomeView welcomeView;

    @BeforeEach
    void setUp() {
        welcomeView = new WelcomeView();
    }

    @Test
    void testWelcomeViewInitialization() {
        // Check that the title is set correctly
        assertEquals("Welcome", welcomeView.getTitle());

        // Check that the size is set correctly
        assertEquals(550, welcomeView.getWidth());
        assertEquals(450, welcomeView.getHeight());

        // Check that the default close operation is set correctly
        assertEquals(JFrame.EXIT_ON_CLOSE, welcomeView.getDefaultCloseOperation());

        // Check that the panel is added to the frame
        assertNotNull(welcomeView.getContentPane());
    }

    @Test
    void testLoginButtonAction() {
        JButton loginButton = welcomeView.getLoginButton();
        assertNotNull(loginButton);

        // Simulate button click by calling the action listener directly
        for (var actionListener : loginButton.getActionListeners()) {
            actionListener.actionPerformed(new ActionEvent(loginButton, ActionEvent.ACTION_PERFORMED, null));
        }

        // Here we can't verify the static method call, but we can check if the frame is disposed
        assertFalse(welcomeView.isDisplayable());
    }

    @Test
    void testSignupButtonAction() {
        JButton signupButton = welcomeView.getSignupButton();
        assertNotNull(signupButton);

        // Simulate button click by calling the action listener directly
        for (var actionListener : signupButton.getActionListeners()) {
            actionListener.actionPerformed(new ActionEvent(signupButton, ActionEvent.ACTION_PERFORMED, null));
        }

        // Here we can't verify the static method call, but we can check if the frame is disposed
        assertFalse(welcomeView.isDisplayable());
    }
}
