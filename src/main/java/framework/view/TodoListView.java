package framework.view;

import com.github.lgooddatepicker.components.DateTimePicker;
import interface_adapter.controller.TodoListController;
import interface_adapter.TodoListViewModel;
import use_case.TaskData;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * View for the to-do list, implementing the main user interface.
 */
public class TodoListView extends JFrame {
    private final TodoListController controller;
    private final TodoListViewModel viewModel;
    private final DefaultListModel<TaskData> taskListModel;
    private final JList<TaskData> taskList;
    private final JTextField titleField;
    private final JTextArea descriptionArea;
    private final DateTimePicker startDatePicker;
    private final DateTimePicker deadlinePicker;
    private final JTextField courseField;
    private final JCheckBox showCompletedCheckBox;
    private final JComboBox<String> sortCriteriaComboBox;
    private final JCheckBox ascendingCheckBox;

    public TodoListView(TodoListController controller, TodoListViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;

        setTitle("Todo List");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Task Adding Panel
        JPanel taskAddingPanel = new JPanel();
        taskAddingPanel.setLayout(new GridLayout(7, 2));

        titleField = new JTextField();
        descriptionArea = new JTextArea();
        startDatePicker = new DateTimePicker();
        deadlinePicker = new DateTimePicker();
        courseField = new JTextField();

        taskAddingPanel.add(new JLabel("Title:"));
        taskAddingPanel.add(titleField);
        taskAddingPanel.add(new JLabel("Description:"));
        taskAddingPanel.add(descriptionArea);
        taskAddingPanel.add(new JLabel("Start Date:"));
        taskAddingPanel.add(startDatePicker);
        taskAddingPanel.add(new JLabel("Deadline:"));
        taskAddingPanel.add(deadlinePicker);
        taskAddingPanel.add(new JLabel("Course:"));
        taskAddingPanel.add(courseField);

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(e -> addTask());
        taskAddingPanel.add(addTaskButton);

        add(taskAddingPanel, BorderLayout.WEST);

        // Task List and Details Panel
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setCellRenderer(new TaskCellRenderer());
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.addListSelectionListener(e -> showTaskDetails(taskList.getSelectedValue()));
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        // Filtering and Sorting Panel
        JPanel filterSortPanel = new JPanel();
        filterSortPanel.setLayout(new GridLayout(2, 2));

        showCompletedCheckBox = new JCheckBox("Show Completed");
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

        add(filterSortPanel, BorderLayout.NORTH);

        // Load tasks initially
        loadTasks();
    }

    private void addTask() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        LocalDateTime startDate = startDatePicker.getDateTimeStrict();
        LocalDateTime deadline = deadlinePicker.getDateTimeStrict();
        String course = courseField.getText();
        controller.addTask(title, description, startDate, deadline, course);
        loadTasks();
    }

    private void completeTask() {
        TaskData selectedTask = taskList.getSelectedValue();
        if (selectedTask != null) {
            controller.completeTask(selectedTask.getId());
            loadTasks();
        }
    }

    private void removeTask() {
        TaskData selectedTask = taskList.getSelectedValue();
        if (selectedTask != null) {
            controller.removeTask(selectedTask.getId());
            loadTasks();
        }
    }

    private void filterTasks() {
        boolean showCompleted = showCompletedCheckBox.isSelected();
        controller.filterTasks(showCompleted);
        loadTasks();
    }

    private void sortTasks() {
        String criterion = (String) sortCriteriaComboBox.getSelectedItem();
        boolean ascending = ascendingCheckBox.isSelected();
        controller.sortTasks(criterion, ascending);
        loadTasks();
    }

    private void showTaskDetails(TaskData task) {
        if (task != null) {
            JOptionPane.showMessageDialog(this, String.format("Title: %s\nDescription: %s\nStart Date: %s\nDeadline: %s\nCourse: %s\nCompleted: %s\nCompletion Date: %s",
                            task.getTitle(), task.getDescription(), task.getStartDate(), task.getDeadline(), task.getCourse(), task.isCompleted() ? "Yes" : "No", task.getCompletionDate() != null ? task.getCompletionDate() : "Not completed"),
                    "Task Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadTasks() {
        taskListModel.clear();
        List<TaskData> tasks = viewModel.getTasks();
        tasks.forEach(taskListModel::addElement);
    }

    private static class TaskCellRenderer extends JPanel implements ListCellRenderer<TaskData> {
        private final JLabel titleLabel;
        private final JLabel courseLabel;
        private final JLabel deadlineLabel;

        public TaskCellRenderer() {
            setLayout(new GridLayout(3, 1));
            titleLabel = new JLabel();
            courseLabel = new JLabel();
            deadlineLabel = new JLabel();
            add(titleLabel);
            add(courseLabel);
            add(deadlineLabel);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends TaskData> list, TaskData value, int index, boolean isSelected, boolean cellHasFocus) {
            titleLabel.setText("Title: " + value.getTitle());
            courseLabel.setText("Course: " + value.getCourse());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            deadlineLabel.setText("Due: " + value.getDeadline().format(formatter));

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }
}
