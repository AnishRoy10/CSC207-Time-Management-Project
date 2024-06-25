package framework.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;
import entity.TodoList;
import entity.Task;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/**
 * The TodoListUI class provides a graphical user interface for managing a to-do list.
 * It allows users to add tasks with descriptions, start dates, and deadlines using LGoodDatePicker components.
 */
public class TodoListUI {
    private JFrame frame;
    private JPanel panel;
    private DateTimePicker startDateTimePicker;
    private DateTimePicker deadlineDateTimePicker;
    private JTextField taskDescriptionField;
    private JButton addButton;
    private JTextArea taskListArea;
    private TodoList todoList;

    /**
     * Constructs a new TodoListUI and initializes the user interface components.
     */
    public TodoListUI() {
        // Initialize the TodoList
        todoList = new TodoList();

        // Set up the main frame
        frame = new JFrame("Todo List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel with a grid layout
        panel = new JPanel(new GridLayout(0, 1));

        // Initialize UI components
        taskDescriptionField = new JTextField("Enter task description...");
        startDateTimePicker = new DateTimePicker();
        deadlineDateTimePicker = new DateTimePicker();

        addButton = new JButton("Add Task");
        addButton.addActionListener(e -> addTask());

        taskListArea = new JTextArea(10, 30);
        taskListArea.setEditable(false);

        // Add components to the panel
        panel.add(new JLabel("Task Description:"));
        panel.add(taskDescriptionField);
        panel.add(new JLabel("Start Date and Time:"));
        panel.add(startDateTimePicker);
        panel.add(new JLabel("Deadline Date and Time:"));
        panel.add(deadlineDateTimePicker);
        panel.add(addButton);
        panel.add(new JScrollPane(taskListArea));

        // Add panel to the frame
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Adds a new task to the to-do list based on user input from the UI components.
     */
    private void addTask() {
        // Get task details from UI components
        String description = taskDescriptionField.getText();
        LocalDateTime startDate = startDateTimePicker.getDateTimePermissive();
        LocalDateTime deadline = deadlineDateTimePicker.getDateTimePermissive();

        // Validate input
        if (description == null || description.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Task description cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (startDate == null) {
            JOptionPane.showMessageDialog(frame, "Start date and time cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (deadline == null) {
            JOptionPane.showMessageDialog(frame, "Deadline date and time cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new task and add it to the TodoList
        Task task = new Task(description, startDate, deadline, null);
        todoList.addTask(task);

        // Update the task list display
        taskListArea.setText(todoList.toString());

        // Clear input fields
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
