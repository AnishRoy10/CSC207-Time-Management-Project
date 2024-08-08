package framework.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.TodoListUseCases.TaskData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TaskCardTest {
    private TaskCard taskCard;
    private TaskData taskData;

    @BeforeEach
    void setUp() {
        taskData = new TaskData(UUID.randomUUID(), "user1", "Test Task", "Test Description", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), false, "CSC207", null);
        taskCard = new TaskCard(taskData);
    }

    @Test
    void testTaskCardInitialization() {
        assertNotNull(taskCard);
        assertEquals(taskData, taskCard.getTask());
        assertFalse(taskCard.isSelected());
    }

    @Test
    void testAddCompletionActionListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskCard.setCompleted(true);
            }
        };
        taskCard.addCompletionActionListener(actionListener);
        taskCard.getCompletedCheckBox().doClick();
        assertTrue(taskCard.getCompletedCheckBox().isSelected());
    }

    @Test
    void testSetSelected() {
        taskCard.setSelected(true);
        assertTrue(taskCard.isSelected());
        assertEquals(Color.LIGHT_GRAY, taskCard.getBackground());

        taskCard.setSelected(false);
        assertFalse(taskCard.isSelected());
        assertEquals(Color.WHITE, taskCard.getBackground());
    }

    @Test
    void testToggleDetails() {
        assertFalse(taskCard.getDetailsPanel().isVisible());
        taskCard.toggleDetails();
        assertTrue(taskCard.getDetailsPanel().isVisible());
        taskCard.toggleDetails();
        assertFalse(taskCard.getDetailsPanel().isVisible());
    }

    @Test
    void testUpdateCompletionDateLabel() {
        assertEquals("Completion Date: Not completed", taskCard.getCompletionDateLabel().getText());
        taskData.setCompleted(true);
        taskData.setCompletionDate(LocalDateTime.now());
        taskCard.updateCompletionDateLabel();
        assertEquals("Completion Date: " + taskData.getCompletionDate().toString(), taskCard.getCompletionDateLabel().getText());
    }

    @Test
    void testSetCompleted() {
        taskCard.setCompleted(true);
        assertTrue(taskCard.getCompletedCheckBox().isSelected());

        taskCard.setCompleted(false);
        assertFalse(taskCard.getCompletedCheckBox().isSelected());
    }
}
