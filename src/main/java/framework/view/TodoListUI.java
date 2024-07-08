//package framework.view;
//
//import com.github.lgooddatepicker.components.DateTimePicker;
//import use_case.AddTaskUseCase;
//import data_access.TodoListDataAccessObject;
//
//import javax.swing.*;
//import java.awt.*;
//import java.time.LocalDateTime;
//import java.util.List;
//
///**
// * User interface for managing a to-do list.
// */
//public class TodoListUI {
//    private JFrame frame;
//    private JPanel inputPanel;
//    private JPanel taskPanel;
//    private JTextField taskTitleField;
//    private JTextField taskDescriptionField;
//    private JTextField taskCourseField; // Field for entering the course
//    private DateTimePicker startDateTimePicker;
//    private DateTimePicker deadlineDateTimePicker;
//    private JButton addButton;
//    private final TodoListController todoListController;
//    private final TaskPresenter taskPresenter;
//
//    /**
//     * Constructs a new TodoListUI and initializes the user interface components.
//     */
//    public TodoListUI(TodoListController todoListController, TaskPresenter taskPresenter) {
//        this.todoListController = todoListController;
//        this.taskPresenter = taskPresenter;
//
//        // Set up the main frame
//        frame = new JFrame("Todo List");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLayout(new BorderLayout());
//
//        // Create and configure input panels
//        inputPanel = new JPanel(new GridLayout(0, 1));
//        taskPanel = new JPanel();
//        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
//
//        // Initialize UI components for input
//        taskTitleField = new JTextField("Enter task title...");
//        taskDescriptionField = new JTextField("Enter task description (optional)...");
//        taskCourseField = new JTextField("Enter course (optional, max 8 characters)..."); // Course input field
//        startDateTimePicker = new DateTimePicker();
//        deadlineDateTimePicker = new DateTimePicker();
//
//        addButton = new JButton("Add Task");
//        addButton.addActionListener(e -> addTask());
//
//        // Add input components to the input panel
//        inputPanel.add(new JLabel("Task Title:"));
//        inputPanel.add(taskTitleField);
//        inputPanel.add(new JLabel("Task Description:"));
//        inputPanel.add(taskDescriptionField);
//        inputPanel.add(new JLabel("Course:"));
//        inputPanel.add(taskCourseField); // Add course field to the panel
//        inputPanel.add(new JLabel("Start Date and Time:"));
//        inputPanel.add(startDateTimePicker);
//        inputPanel.add(new JLabel("Deadline Date and Time:"));
//        inputPanel.add(deadlineDateTimePicker);
//        inputPanel.add(addButton);
//
//        // Add components to the main frame
//        frame.add(inputPanel, BorderLayout.WEST);
//        frame.add(new JScrollPane(taskPanel), BorderLayout.CENTER);
//
//        frame.pack();
//        frame.setVisible(true);
//
//        // Set the size of the frame
//        frame.setSize(1000, 800);
//
//        // Load and display tasks at startup
//        todoListController.loadTasks();
//    }
//
//    /**
//     * Adds a new task to the to-do list based on user input from the UI components.
//     */
//    private void addTask() {
//        String title = taskTitleField.getText();
//        String description = taskDescriptionField.getText();
//        String courseName = taskCourseField.getText(); // Get course name from input field
//        LocalDateTime startDate = startDateTimePicker.getDateTimePermissive();
//        LocalDateTime deadline = deadlineDateTimePicker.getDateTimePermissive();
//
//        // Validate task input
//        if (title == null || title.trim().isEmpty()) {
//            JOptionPane.showMessageDialog(frame, "Task title cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (startDate == null) {
//            startDate = LocalDateTime.now(); // Set start time to current time if not provided
//        }
//
//        if (deadline != null && deadline.isBefore(startDate)) {
//            JOptionPane.showMessageDialog(frame, "Deadline must be after the start date and time", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (courseName != null && courseName.length() > 8) {
//            JOptionPane.showMessageDialog(frame, "Course name must be 8 characters or less", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Add task to the list using the controller
//        todoListController.addTask(title, description, startDate, deadline, courseName);
//    }
//
//    /**
//     * Refreshes the task list display.
//     *
//     * @param taskResponseModels The list of task response models to display
//     */
//    public void refreshTaskList(List<TaskResponseModel> taskResponseModels) {
//        taskPanel.removeAll();
//        for (TaskResponseModel taskResponseModel : taskResponseModels) {
//            taskPanel.add(new TaskCard(taskResponseModel));
//        }
//        taskPanel.revalidate();
//        taskPanel.repaint();
//    }
//
//    /**
//     * Main method to run the application.
//     *
//     * @param args Command line arguments
//     */
//    public static void main(String[] args) {
//        // Initialize necessary components and start the UI
//        // Ensure the Saves directory exists
//        java.io.File saveDir = new java.io.File("./Saves");
//        if (!saveDir.exists()) {
//            saveDir.mkdirs();
//        }
//
//        // Initialize the necessary components
//        TodoListDataAccessObject todoListDataAccessObject = new TodoListDataAccessObject("./Saves/todoListFile.dat");
//        TaskPresenter taskPresenter = new TaskPresenter();
//        TodoListInputBoundary todoListInputBoundary = new AddTaskUseCase(todoListDataAccessObject, taskPresenter);
//        TodoListController todoListController = new TodoListController(todoListInputBoundary);
//
//        // Start the UI
//        SwingUtilities.invokeLater(() -> new TodoListUI(todoListController, taskPresenter));
//    }
//}