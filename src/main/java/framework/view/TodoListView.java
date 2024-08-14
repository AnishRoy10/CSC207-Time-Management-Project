package framework.view;

import com.github.lgooddatepicker.components.DateTimePicker;
import interface_adapter.viewmodel.TodoListViewModel;
import interface_adapter.controller.TodoListController;
import use_case.TodoListUseCases.TaskData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The TodoListView class provides the main user interface for interacting with the to-do list.
 * It allows users to add, view, filter, sort, and remove tasks. This class manages the layout
 * and behavior of the to-do list view, including the input fields, task list display, and control buttons.
 */
public class TodoListView extends JFrame {
    private final TodoListController controller; // Controller for handling user actions
    private final TodoListViewModel viewModel; // ViewModel to provide data to the view
    private final JPanel taskListPanel; // Panel for displaying the list of tasks
    private final JTextField titleField; // Text field for entering the task title
    private final JTextArea descriptionArea; // Text area for entering the task description
    private final DateTimePicker startDatePicker; // Date picker for selecting the task start date
    private final DateTimePicker deadlinePicker; // Date picker for selecting the task deadline
    private final JTextField courseField; // Text field for entering the associated course
    private final JCheckBox showCompletedCheckBox; // Checkbox to filter out completed tasks
    private final JComboBox<String> sortCriteriaComboBox; // ComboBox to select sorting criteria
    private final JCheckBox ascendingCheckBox; // Checkbox to select ascending or descending sort order
    private final String username; // Username of the logged-in user
    private final String courseName; // Course name, if the to-do list is course-specific
    private JFrame parentFrame; // Reference to the parent frame, if any

    /**
     * Constructs the TodoListView with the specified controller, viewModel, and username.
     * This constructor is used for creating a personal to-do list interface.
     *
     * @param controller The controller to handle user actions.
     * @param viewModel  The view model to provide data to the view.
     * @param username   The username of the logged-in user.
     */
    public TodoListView(TodoListController controller, TodoListViewModel viewModel, String username) {
        this(controller, viewModel, username, null);
    }

    /**
     * Constructs the TodoListView with the specified controller, viewModel, username, and courseName.
     * This constructor is used for creating a course-specific to-do list interface.
     *
     * @param controller The controller to handle user actions.
     * @param viewModel  The view model to provide data to the view.
     * @param username   The username of the logged-in user.
     * @param courseName The course name, if applicable.
     */
    public TodoListView(TodoListController controller, TodoListViewModel viewModel, String username, String courseName) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.username = username;
        this.courseName = courseName;

        // Set the title of the window based on whether it is course-specific or not
        setTitle(courseName == null ? "Todo List" : courseName + " Todo List");
        setSize(1200, 700); // Set the default size of the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ensure proper closing behavior
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                if (parentFrame != null) {
                    parentFrame.setVisible(true); // Restore parent frame visibility when this window is closed
                }
            }
        });
        setLayout(new BorderLayout()); // Use BorderLayout for organizing components

        // Set a modern font for all components in the UI
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("CheckBox.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextArea.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));

        // Panel for adding new tasks
        JPanel taskAddingPanel = new JPanel();
        taskAddingPanel.setLayout(new BoxLayout(taskAddingPanel, BoxLayout.Y_AXIS)); // Use vertical box layout
        taskAddingPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding around the panel
        taskAddingPanel.setBackground(Color.WHITE); // Set background color to white

        // Initialize input fields for task details
        titleField = new JTextField();
        descriptionArea = new JTextArea(3, 20);
        startDatePicker = new DateTimePicker();
        deadlinePicker = new DateTimePicker();
        courseField = new JTextField();

        // Add labeled components for task details to the task adding panel
        taskAddingPanel.add(createLabeledComponent("Title:", titleField));
        taskAddingPanel.add(createLabeledComponent("Description:", new JScrollPane(descriptionArea)));
        taskAddingPanel.add(createLabeledComponent("Start Date:", startDatePicker));
        taskAddingPanel.add(createLabeledComponent("Deadline:", deadlinePicker));
        taskAddingPanel.add(createLabeledComponent("Course:", courseField));

        // Button to add a task
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(e -> addTask()); // Add action listener to handle task addition
        taskAddingPanel.add(Box.createVerticalStrut(10)); // Add spacing between components
        taskAddingPanel.add(addTaskButton);

        add(taskAddingPanel, BorderLayout.WEST); // Add the task adding panel to the west side of the layout

        // Panel for displaying the list of tasks
        taskListPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for flexible task arrangement
        JScrollPane taskListScrollPane = new JScrollPane(taskListPanel); // Add scroll functionality to the task list
        taskListScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove borders for a cleaner look
        add(taskListScrollPane, BorderLayout.CENTER); // Add the task list panel to the center of the layout

        // Panel for filtering and sorting tasks
        JPanel filterSortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout for organizing controls
        filterSortPanel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Add padding around the panel
        filterSortPanel.setBackground(Color.WHITE); // Set background color to white

        // Checkbox to hide/show completed tasks
        showCompletedCheckBox = new JCheckBox("Hide Completed");
        showCompletedCheckBox.addActionListener(e -> filterTasks()); // Add action listener to handle filtering
        filterSortPanel.add(showCompletedCheckBox);

        // Dropdown for selecting the sorting criterion
        sortCriteriaComboBox = new JComboBox<>(new String[]{"Title", "Deadline", "Course"});
        filterSortPanel.add(new JLabel("Sort by:")); // Label for the sorting dropdown
        filterSortPanel.add(sortCriteriaComboBox);

        // Checkbox for choosing between ascending and descending sort order
        ascendingCheckBox = new JCheckBox("Ascending");
        filterSortPanel.add(ascendingCheckBox);

        // Button to trigger sorting
        JButton sortTasksButton = new JButton("Sort Tasks");
        sortTasksButton.addActionListener(e -> sortTasks()); // Add action listener to handle sorting
        filterSortPanel.add(sortTasksButton);

        // Button to remove the selected task
        JButton removeTaskButton = new JButton("Remove Task");
        removeTaskButton.addActionListener(e -> removeSelectedTask()); // Add action listener to handle task removal
        filterSortPanel.add(removeTaskButton);

        add(filterSortPanel, BorderLayout.NORTH); // Add the filter and sort panel to the north of the layout

        // Load tasks when the view is initialized
        if (courseName == null) {
            controller.loadTodoList(username); // Load the user's personal to-do list
        } else {
            controller.loadTodoList(username, courseName); // Load the course-specific to-do list
        }
        loadTasks(); // Refresh the task list view
    }

    /**
     * Creates a labeled component for the user interface.
     *
     * @param label     The text to display as the label.
     * @param component The component to be labeled (e.g., text field, date picker).
     * @return A JPanel containing the labeled component, ready to be added to the layout.
     */
    private Component createLabeledComponent(String label, Component component) {
        JPanel panel = new JPanel(new BorderLayout()); // Use BorderLayout for label and component arrangement
        panel.add(new JLabel(label), BorderLayout.NORTH); // Add the label at the top of the panel
        panel.add(component, BorderLayout.CENTER); // Add the component below the label
        panel.setBackground(Color.WHITE); // Set background color to white
        panel.setBorder(new EmptyBorder(5, 0, 10, 0)); // Add padding around the panel
        return panel;
    }

    /**
     * Adds a task to the to-do list using the data entered in the input fields.
     * Validates that all required fields are filled out before proceeding.
     * Displays an error message if validation fails.
     */
    private void addTask() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        LocalDateTime startDate = startDatePicker.getDateTimeStrict();
        LocalDateTime deadline = deadlinePicker.getDateTimeStrict();
        String course = courseField.getText();

        // Validate required fields
        if (title.isEmpty() || description.isEmpty() || startDate == null || deadline == null || course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if validation fails
        }

        // If validation succeeds, add the task to the appropriate to-do list
        if (courseName == null) {
            controller.addTask(title, description, startDate, deadline, course, username); // User's personal to-do list
        } else {
            controller.addTask(title, description, startDate, deadline, course, username, courseName); // Course-specific to-do list
        }
        clearInputFields(); // Clear the input fields after adding the task
        loadTasks(); // Refresh the task list view
    }

    /**
     * Clears the input fields after a task is added or when resetting the form.
     * Resets the text fields, date pickers, and other input components to their default states.
     */
    private void clearInputFields() {
        titleField.setText(""); // Clear the title field
        descriptionArea.setText(""); // Clear the description area
        startDatePicker.clear(); // Clear the start date picker
        deadlinePicker.clear(); // Clear the deadline picker
        courseField.setText(""); // Clear the course field
    }

    /**
     * Marks a task as complete and updates the UI accordingly.
     *
     * @param taskId the ID of the task to complete.
     */
    private void completeTask(UUID taskId) {
        if (courseName == null) {
            controller.toggleTaskCompletion(taskId, username); // User's personal to-do list
        } else {
            controller.toggleTaskCompletion(taskId, username, courseName); // Course-specific to-do list
        }
        updateTaskCardCompletion(taskId, true); // Update the task card's completion status
        taskListPanel.revalidate(); // Refresh the task list panel
        taskListPanel.repaint(); // Refresh the task list panel
        filterTasks(); // Apply the current filter settings
    }

    /**
     * Updates the completion status of a specific task card in the task list.
     *
     * @param taskId       the ID of the task to update.
     * @param isCompleted  whether the task is marked as completed.
     */
    private void updateTaskCardCompletion(UUID taskId, boolean isCompleted) {
        for (Component component : taskListPanel.getComponents()) {
            if (component instanceof TaskCard) {
                TaskCard taskCard = (TaskCard) component;
                if (taskCard.getTask().getId().equals(taskId)) {
                    taskCard.setCompleted(isCompleted);
                }
            }
        }
    }

    /**
     * Removes a task by its ID and updates the task list.
     *
     * @param taskId the ID of the task to remove.
     */
    private void removeTask(UUID taskId) {
        if (courseName == null) {
            controller.removeTask(taskId, username); // User's personal to-do list
        } else {
            controller.removeTask(taskId, username, courseName); // Course-specific to-do list
        }
        loadTasks(); // Refresh the task list after removal
    }

    /**
     * Removes the currently selected task after confirming with the user.
     */
    private void removeSelectedTask() {
        TaskCard selectedTaskCard = getSelectedTaskCard();
        if (selectedTaskCard != null) {
            int confirmed = showConfirmDialog("Are you sure you want to delete the selected task?");
            if (confirmed == JOptionPane.YES_OPTION) {
                removeTask(selectedTaskCard.getTask().getId());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No task selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Gets the currently selected task card.
     *
     * @return the selected TaskCard, or null if no task is selected.
     */
    private TaskCard getSelectedTaskCard() {
        for (Component component : taskListPanel.getComponents()) {
            if (component instanceof TaskCard) {
                TaskCard taskCard = (TaskCard) component;
                if (taskCard.isSelected()) {
                    return taskCard;
                }
            }
        }
        return null;
    }

    /**
     * Filters tasks based on their completion status.
     */
    private void filterTasks() {
        boolean showCompleted = showCompletedCheckBox.isSelected();
        if (courseName == null) {
            controller.filterTasks(showCompleted, username); // User's personal to-do list
        } else {
            controller.filterTasks(showCompleted, username, courseName); // Course-specific to-do list
        }
        loadTasks(); // Refresh the task list after filtering
    }

    /**
     * Sorts tasks based on the selected criterion and order.
     */
    private void sortTasks() {
        String criterion = (String) sortCriteriaComboBox.getSelectedItem();
        boolean ascending = ascendingCheckBox.isSelected();
        if (courseName == null) {
            controller.sortTasks(criterion, ascending, username); // User's personal to-do list
        } else {
            controller.sortTasks(criterion, ascending, username, courseName); // Course-specific to-do list
        }
        loadTasks(); // Refresh the task list after sorting
    }

    /**
     * Loads tasks from the view model and displays them in the task list panel.
     * The task list is updated based on the current filter and sorting criteria.
     */
    public void loadTasks() {
        taskListPanel.removeAll(); // Clear the current task list
        List<TaskData> tasks = viewModel.getTasks();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add each task to the task list panel
        tasks.forEach(task -> {
            TaskCard taskCard = new TaskCard(task);
            taskCard.addCompletionActionListener(e -> {
                completeTask(task.getId());
                loadTasks(); // Refresh the task list after completing a task
            });
            taskCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        deselectAllTaskCards(); // Deselect other tasks
                        taskCard.setSelected(true); // Select the clicked task
                        if (e.getClickCount() == 2) {
                            taskCard.toggleDetails(); // Toggle task details on double-click
                        }
                    }
                }
            });
            taskListPanel.add(taskCard, gbc); // Add the task card to the panel
        });

        // Refresh the task list panel to reflect the updated tasks
        taskListPanel.revalidate();
        taskListPanel.repaint();
    }

    /**
     * Deselects all task cards in the task list panel.
     */
    public void deselectAllTaskCards() {
        for (Component component : taskListPanel.getComponents()) {
            if (component instanceof TaskCard) {
                ((TaskCard) component).setSelected(false); // Deselect each task card
            }
        }
    }

    // Getter methods for testing

    public JTextField getTitleField() {
        return titleField;
    }

    public JTextArea getDescriptionArea() {
        return descriptionArea;
    }

    public DateTimePicker getStartDatePicker() {
        return startDatePicker;
    }

    public DateTimePicker getDeadlinePicker() {
        return deadlinePicker;
    }

    public JTextField getCourseField() {
        return courseField;
    }

    public JCheckBox getShowCompletedCheckBox() {
        return showCompletedCheckBox;
    }

    public JComboBox<String> getSortCriteriaComboBox() {
        return sortCriteriaComboBox;
    }

    public JCheckBox getAscendingCheckBox() {
        return ascendingCheckBox;
    }

    public JPanel getTaskListPanel() {
        return taskListPanel;
    }

    public void callAddTask() {
        addTask();
    }

    public void callRemoveSelectedTask() {
        removeSelectedTask();
    }

    public void callFilterTasks() {
        filterTasks();
    }

    public void callSortTasks() {
        sortTasks();
    }

    public void callLoadTasks() {
        loadTasks();
    }

    public void callDeselectAllTaskCards() {
        deselectAllTaskCards();
    }

    /**
     * Shows a confirmation dialog when attempting to remove a task.
     *
     * @param message The message to display in the dialog.
     * @return The user's response (Yes or No).
     */
    protected int showConfirmDialog(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirm Delete", JOptionPane.YES_NO_OPTION);
    }
}

/**
 * A subclass of TodoListView designed for testing purposes.
 * Overrides methods to simulate user interaction in a controlled environment.
 */
class TestableTodoListView extends TodoListView {
    public TestableTodoListView(TodoListController controller, TodoListViewModel viewModel, String username) {
        super(controller, viewModel, username);
    }

    @Override
    protected int showConfirmDialog(String message) {
        return JOptionPane.YES_OPTION; // Simulate user clicking "Yes"
    }
}
