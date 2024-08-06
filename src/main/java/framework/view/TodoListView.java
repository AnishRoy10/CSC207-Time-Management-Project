package framework.view;

import com.github.lgooddatepicker.components.DateTimePicker;
import interface_adapter.viewmodel.TodoListViewModel;
import interface_adapter.controller.TodoListController;
import use_case.TaskData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * View for the to-do list, implementing the main user interface.
 */
public class TodoListView extends JFrame {
    private final TodoListController controller;
    private final TodoListViewModel viewModel;
    private final JPanel taskListPanel;
    private final JTextField titleField;
    private final JTextArea descriptionArea;
    private final DateTimePicker startDatePicker;
    private final DateTimePicker deadlinePicker;
    private final JTextField courseField;
    private final JCheckBox showCompletedCheckBox;
    private final JComboBox<String> sortCriteriaComboBox;
    private final JCheckBox ascendingCheckBox;
    private final String username;
    private final String courseName;
    private JFrame parentFrame;

    /**
     * Constructs the TodoListView with the specified controller, viewModel, and username.
     * This constructor is used for personal to-do lists.
     *
     * @param controller the controller to handle user actions
     * @param viewModel  the view model to provide data to the view
     * @param username   the username of the logged-in user
     */
    public TodoListView(TodoListController controller, TodoListViewModel viewModel, String username) {
        this(controller, viewModel, username, null);
    }

    /**
     * Constructs the TodoListView with the specified controller, viewModel, username, and courseName.
     * This constructor is used for course-specific to-do lists.
     *
     * @param controller the controller to handle user actions
     * @param viewModel  the view model to provide data to the view
     * @param username   the username of the logged-in user
     * @param courseName the course name, if applicable
     */
    public TodoListView(TodoListController controller, TodoListViewModel viewModel, String username, String courseName) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.username = username;
        this.courseName = courseName;

        setTitle(courseName == null ? "Todo List" : courseName + " Todo List");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                if (parentFrame != null) {
                    parentFrame.setVisible(true);
                }
            }
        });
        setLayout(new BorderLayout());

        // Set a modern font for the entire application
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("CheckBox.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextArea.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));

        // Task Adding Panel
        JPanel taskAddingPanel = new JPanel();
        taskAddingPanel.setLayout(new BoxLayout(taskAddingPanel, BoxLayout.Y_AXIS));
        taskAddingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        taskAddingPanel.setBackground(Color.WHITE);

        titleField = new JTextField();
        descriptionArea = new JTextArea(3, 20);
        startDatePicker = new DateTimePicker();
        deadlinePicker = new DateTimePicker();
        courseField = new JTextField();

        taskAddingPanel.add(createLabeledComponent("Title:", titleField));
        taskAddingPanel.add(createLabeledComponent("Description:", new JScrollPane(descriptionArea)));
        taskAddingPanel.add(createLabeledComponent("Start Date:", startDatePicker));
        taskAddingPanel.add(createLabeledComponent("Deadline:", deadlinePicker));
        taskAddingPanel.add(createLabeledComponent("Course:", courseField));

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(e -> addTask());
        taskAddingPanel.add(Box.createVerticalStrut(10));
        taskAddingPanel.add(addTaskButton);

        add(taskAddingPanel, BorderLayout.WEST);

        // Task List Panel
        taskListPanel = new JPanel(new GridBagLayout());
        JScrollPane taskListScrollPane = new JScrollPane(taskListPanel);
        taskListScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(taskListScrollPane, BorderLayout.CENTER);

        // Filtering and Sorting Panel
        JPanel filterSortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterSortPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        filterSortPanel.setBackground(Color.WHITE);

        showCompletedCheckBox = new JCheckBox("Hide Completed");
        showCompletedCheckBox.addActionListener(e -> filterTasks());
        filterSortPanel.add(showCompletedCheckBox);

        sortCriteriaComboBox = new JComboBox<>(new String[]{"Title", "Deadline", "Course"});
        filterSortPanel.add(new JLabel("Sort by:"));
        filterSortPanel.add(sortCriteriaComboBox);

        ascendingCheckBox = new JCheckBox("Ascending");
        filterSortPanel.add(ascendingCheckBox);

        JButton sortTasksButton = new JButton("Sort Tasks");
        sortTasksButton.addActionListener(e -> sortTasks());
        filterSortPanel.add(sortTasksButton);

        JButton removeTaskButton = new JButton("Remove Task");
        removeTaskButton.addActionListener(e -> removeSelectedTask());
        filterSortPanel.add(removeTaskButton);

        add(filterSortPanel, BorderLayout.NORTH);

        // Ensure tasks are loaded initially
        if (courseName == null) {
            controller.loadTodoList(username); // Load user's personal to-do list
        } else {
            controller.loadTodoList(username, courseName); // Load course-specific to-do list
        }
        loadTasks();
    }

    /**
     * Creates a labeled component for the UI.
     *
     * @param label     the label text
     * @param component the component to be labeled
     * @return the panel containing the labeled component
     */
    private Component createLabeledComponent(String label, Component component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 0, 10, 0));
        return panel;
    }

    /**
     * Adds a task using the data from the input fields.
     */
    private void addTask() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        LocalDateTime startDate = startDatePicker.getDateTimeStrict();
        LocalDateTime deadline = deadlinePicker.getDateTimeStrict();
        String course = courseField.getText();
        if (courseName == null) {
            controller.addTask(title, description, startDate, deadline, course, username); // User's personal to-do list
        } else {
            controller.addTask(title, description, startDate, deadline, course, username, courseName); // Course-specific to-do list
        }
        clearInputFields();
        loadTasks();
    }

    /**
     * Clears the input fields.
     */
    private void clearInputFields() {
        titleField.setText("");
        descriptionArea.setText("");
        startDatePicker.clear();
        deadlinePicker.clear();
        courseField.setText("");
    }

    /**
     * Marks a task as complete.
     *
     * @param taskId the ID of the task to complete
     */
    private void completeTask(UUID taskId) {
        if (courseName == null) {
            controller.toggleTaskCompletion(taskId, username); // User's personal to-do list
        } else {
            controller.toggleTaskCompletion(taskId, username, courseName); // Course-specific to-do list
        }
        updateTaskCardCompletion(taskId, true); // Update the task card's checkbox
        taskListPanel.revalidate(); // Refresh the task list panel
        taskListPanel.repaint(); // Refresh the task list panel
        filterTasks();
        filterTasks();
    }

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
     * Removes a task by its ID.
     *
     * @param taskId the ID of the task to remove
     */
    private void removeTask(UUID taskId) {
        if (courseName == null) {
            controller.removeTask(taskId, username); // User's personal to-do list
        } else {
            controller.removeTask(taskId, username, courseName); // Course-specific to-do list
        }
        loadTasks();
    }

    /**
     * Removes the currently selected task after confirmation.
     */
    private void removeSelectedTask() {
        TaskCard selectedTaskCard = getSelectedTaskCard();
        if (selectedTaskCard != null) {
            int confirmed = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
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
     * @return the selected TaskCard, or null if no task is selected
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
     * Filters tasks based on the completed status.
     */
    private void filterTasks() {
        boolean showCompleted = showCompletedCheckBox.isSelected();
        if (courseName == null) {
            controller.filterTasks(showCompleted, username); // User's personal to-do list
        } else {
            controller.filterTasks(showCompleted, username, courseName); // Course-specific to-do list
        }
        loadTasks();
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
        loadTasks();
    }

    private void loadTasks() {
        taskListPanel.removeAll();
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
                loadTasks();
            });
            taskCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        deselectAllTaskCards();
                        taskCard.setSelected(true);
                        if (e.getClickCount() == 2) {
                            taskCard.toggleDetails();
                        }
                    }
                }
            });
            taskListPanel.add(taskCard, gbc);
        });

        // Refresh the task list panel
        taskListPanel.revalidate();
        taskListPanel.repaint();
    }

    /**
     * Deselects all task cards in the task list panel.
     */
    private void deselectAllTaskCards() {
        for (Component component : taskListPanel.getComponents()) {
            if (component instanceof TaskCard) {
                ((TaskCard) component).setSelected(false);
            }
        }
    }
}
