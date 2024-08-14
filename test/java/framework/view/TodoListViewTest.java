package framework.view;

import interface_adapter.viewmodel.TodoListViewModel;
import interface_adapter.controller.TodoListController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.TodoListUseCases.TaskData;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.awt.Component;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TodoListViewTest {
    private TodoListController mockController;
    private TodoListViewModel mockViewModel;
    private TestableTodoListView view;

    @BeforeEach
    void setUp() {
        mockController = mock(TodoListController.class);
        mockViewModel = mock(TodoListViewModel.class);
        view = new TestableTodoListView(mockController, mockViewModel, "testUser");

        // Set up mock tasks for the view model
        List<TaskData> tasks = new ArrayList<>();
        tasks.add(new TaskData(UUID.randomUUID(), "testUser", "Test Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), false, "Course 1", null));
        tasks.add(new TaskData(UUID.randomUUID(), "testUser", "Test Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), false, "Course 2", null));
        when(mockViewModel.getTasks()).thenReturn(tasks);
    }

    @Test
    void testAddTask() {
        // Set input fields
        view.getTitleField().setText("New Task");
        view.getDescriptionArea().setText("New Description");
        view.getStartDatePicker().setDateTimeStrict(LocalDateTime.now());
        view.getDeadlinePicker().setDateTimeStrict(LocalDateTime.now().plusDays(3));
        view.getCourseField().setText("New Course");

        // Call the addTask method
        view.callAddTask();

        // Verify that the controller's addTask method was called with the correct parameters
        verify(mockController).addTask(eq("New Task"), eq("New Description"), any(LocalDateTime.class), any(LocalDateTime.class), eq("New Course"), eq("testUser"));
    }

    @Test
    void testRemoveSelectedTask() {
        // Load tasks into the view
        view.callLoadTasks();

        // Mock a selected task card
        TaskCard selectedTaskCard = new TaskCard(mockViewModel.getTasks().get(0));
        selectedTaskCard.setSelected(true);
        view.getTaskListPanel().add(selectedTaskCard);

        // Call the removeSelectedTask method
        view.callRemoveSelectedTask();

        // Verify that the controller's removeTask method was called with the correct parameters
        verify(mockController).removeTask(selectedTaskCard.getTask().getId(), "testUser");
    }

    @Test
    void testFilterTasks() {
        // Set the showCompletedCheckBox to true
        view.getShowCompletedCheckBox().setSelected(true);

        // Call the filterTasks method
        view.callFilterTasks();

        // Verify that the controller's filterTasks method was called with the correct parameters
        verify(mockController).filterTasks(true, "testUser");
    }

    @Test
    void testSortTasks() {
        // Set the sort criteria and order
        view.getSortCriteriaComboBox().setSelectedItem("Title");
        view.getAscendingCheckBox().setSelected(true);

        // Call the sortTasks method
        view.callSortTasks();

        // Verify that the controller's sortTasks method was called with the correct parameters
        verify(mockController).sortTasks("Title", true, "testUser");
    }

    @Test
    void testLoadTasks() {
        // Call the loadTasks method
        view.callLoadTasks();

        // Verify that the task list panel contains the correct number of task cards
        assertEquals(2, view.getTaskListPanel().getComponentCount());
    }

    @Test
    void testDeselectAllTaskCards() {
        // Load tasks into the view
        view.callLoadTasks();

        // Mock a selected task card
        TaskCard selectedTaskCard = new TaskCard(mockViewModel.getTasks().get(0));
        selectedTaskCard.setSelected(true);
        view.getTaskListPanel().add(selectedTaskCard);

        // Call the deselectAllTaskCards method
        view.callDeselectAllTaskCards();

        // Verify that the task card is deselected
        assertFalse(selectedTaskCard.isSelected());
    }
}

