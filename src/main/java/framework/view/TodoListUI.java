package framework.view;

import com.github.lgooddatepicker.components.DateTimePicker;
import entity.TodoList;
import entity.Course;
import interface_adapter.controller.TodoListController;
import interface_adapter.presenter.TaskPresenter;
import use_case.AddTaskUseCase;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/**
 * User interface for managing a to-do list.
 */
public class TodoListUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField taskTitleField;
    private JTextField taskDescriptionField;
    private DateTimePicker startDateTimePicker;
    private DateTimePicker deadlineDateTimePicker;
    private JButton addButton;
    private JTextArea taskListArea;
    private TodoList todoList;
    private AddTaskUseCase addTaskUseCase;
    private TaskPresenter taskPresenter;
    private TodoListController todoListController;
    private Course currentCourse; // Currently selected course for the task

    /**
     * Constructs a new TodoListUI and initializes the user interface components.
     */
    public TodoListUI() {
        // Initialize the domain and use case components
        todoList = new TodoList();
        addTaskUseCase = new AddTaskUseCase(todoList);
        taskPresenter = new TaskPresenter(todoList);
        todoListController = new TodoListController(addTaskUseCase);

        // Set up the main frame
        frame = new JFrame("Todo List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create and configure panels
        panel = new JPanel(new GridLayout(0, 1));
        JPanel taskPanel = new JPanel(new BorderLayout());

        // Initialize UI components
        taskTitleField = new JTextField("Enter task title...");
        taskDescriptionField = new JTextField("Enter task description (optional)...");
        startDateTimePicker = new DateTimePicker();
        deadlineDateTimePicker = new DateTimePicker();

        addButton = new JButton("Add Task");
        addButton.addActionListener(e -> addTask());

        taskListArea = new JTextArea(20, 40);
        taskListArea.setEditable(false);

        // Add components to the task panel
        panel.add(new JLabel("Task Title:"));
        panel.add(taskTitleField);
        panel.add(new JLabel("Task Description:"));
        panel.add(taskDescriptionField);
        panel.add(new JLabel("Start Date and Time:"));
        panel.add(startDateTimePicker);
        panel.add(new JLabel("Deadline Date and Time:"));
        panel.add(deadlineDateTimePicker);
        panel.add(addButton);

        // Add components to the main frame
        frame.add(panel, BorderLayout.WEST);
        taskPanel.add(new JScrollPane(taskListArea), BorderLayout.CENTER);
        frame.add(taskPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Adds a new task to the to-do list based on user input from the UI components.
     */
    private void addTask() {
        String title = taskTitleField.getText();
        String description = taskDescriptionField.getText();
        LocalDateTime startDate = startDateTimePicker.getDateTimePermissive();
        LocalDateTime deadline = deadlineDateTimePicker.getDateTimePermissive();

        // Validate task input
        if (title == null || title.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Task title cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (startDate == null) {
            startDate = LocalDateTime.now(); // Set start time to current time if not provided
        }

        if (deadline != null && deadline.isBefore(startDate)) {
            JOptionPane.showMessageDialog(frame, "Deadline must be after the start date and time", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add task to the list using the controller
        todoListController.addTask(title, description, startDate, deadline, currentCourse);
        taskListArea.setText(taskPresenter.getFormattedTasks());

        // Clear input fields after adding the task
        taskTitleField.setText("");
        taskDescriptionField.setText("");
        startDateTimePicker.clear();
        deadlineDateTimePicker.clear();
    }

    /**
     * Main method to run the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TodoListUI::new);
    }
}
