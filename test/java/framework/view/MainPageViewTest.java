package framework.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MainPageViewTest {
    private MainPageView mainPageView;
    private String username = "testUser";

    @BeforeEach
    public void setUp() {
        mainPageView = new MainPageView(username);
    }

    @Test
    public void testMainPageViewTitle() {
        assertEquals("Time Management Application", mainPageView.getTitle());
    }

    @Test
    public void testWelcomeLabel() {
        JLabel welcomeLabel = findLabelByText(mainPageView, "Welcome, " + username + "!");
        assertEquals("Welcome, " + username + "!", welcomeLabel.getText());
    }

    /**
     * Utility method to find a JLabel by its text within a container.
     */
    private JLabel findLabelByText(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel && text.equals(((JLabel) component).getText())) {
                return (JLabel) component;
            } else if (component instanceof Container) {
                JLabel result = findLabelByText((Container) component, text);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * Utility method to find a JButton by its text within a container.
     */
    private JButton findButtonByText(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton && text.equals(((JButton) component).getText())) {
                return (JButton) component;
            } else if (component instanceof Container) {
                JButton result = findButtonByText((Container) component, text);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
