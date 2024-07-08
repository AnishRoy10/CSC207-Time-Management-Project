package framework.view;

import com.github.lgooddatepicker.components.DateTimePicker;
import entity.TodoList;
import entity.Task;
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
    private JPanel inputPanel;
    private JPanel taskPanel;
    private JTextField taskTitleField;
    private JTextField taskDescriptionField;
    private JTextField taskCourseField; // Field for entering the course
    private DateTimePicker startDateTimePicker;
    private DateTimePicker deadlineDateTimePicker;
    private JButton addButton;
    private TodoList todoList;
    private AddTaskUseCase addTaskUseCase;
    private TaskPresenter taskPresenter;
    private TodoListController todoListController;

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

        // Create and configure input panels
        inputPanel = new JPanel(new GridLayout(0, 1));
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));

        // Initialize UI components for input
        taskTitleField = new JTextField("Enter task title...");
        taskDescriptionField = new JTextField("Enter task description (optional)...");
        taskCourseField = new JTextField("Enter course (optional, max 8 characters)..."); // Course input field
        startDateTimePicker = new DateTimePicker();
        deadlineDateTimePicker = new DateTimePicker();

        addButton = new JButton("Add Task");
        addButton.addActionListener(e -> addTask());

        // Add input components to the input panel
        inputPanel.add(new JLabel("Task Title:"));
        inputPanel.add(taskTitleField);
        inputPanel.add(new JLabel("Task Description:"));
        inputPanel.add(taskDescriptionField);
        inputPanel.add(new JLabel("Course:"));
        inputPanel.add(taskCourseField); // Add course field to the panel
        inputPanel.add(new JLabel("Start Date and Time:"));
        inputPanel.add(startDateTimePicker);
        inputPanel.add(new JLabel("Deadline Date and Time:"));
        inputPanel.add(deadlineDateTimePicker);
        inputPanel.add(addButton);

        // Add components to the main frame
        frame.add(inputPanel, BorderLayout.WEST);
        frame.add(new JScrollPane(taskPanel), BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

        // Set the size of the frame
        frame.setSize(1000, 800);
    }

    /**
     * Adds a new task to the to-do list based on user input from the UI components.
     */
    private void addTask() {
        String title = taskTitleField.getText();
        String description = taskDescriptionField.getText();
        String courseName = taskCourseField.getText(); // Get course name from input field
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

        if (courseName != null && courseName.length() > 8) {
            JOptionPane.showMessageDialog(frame, "Course name must be 8 characters or less", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add task to the list using the controller
        todoListController.addTask(title, description, startDate, deadline, courseName);
        refreshTaskList();

        // Clear input fields after adding the task
        taskTitleField.setText("");
        taskDescriptionField.setText("");
        taskCourseField.setText(""); // Clear course field
        startDateTimePicker.clear();
        deadlineDateTimePicker.clear();
    }

    /**
     * Refreshes the task list display.
     */
    private void refreshTaskList() {
        taskPanel.removeAll();
        for (Task task : todoList.getTasks()) {
            taskPanel.add(new TaskCard(task));
        }
        taskPanel.revalidate();
        taskPanel.repaint();
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
