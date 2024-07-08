package framework.view;

import com.github.lgooddatepicker.components.DateTimePicker;
import interface_adapter.controller.TodoListController;
import interface_adapter.TodoListViewModel;
import use_case.TaskData;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class TodoListView extends JFrame {
    private final TodoListController controller;
    private final TodoListViewModel viewModel;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;

    public TodoListView(TodoListController controller, TodoListViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        initializeUI();
        loadTasks();
    }

    private void initializeUI() {
        setTitle("Todo List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JTextField titleField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        DateTimePicker startDatePicker = new DateTimePicker();
        DateTimePicker deadlinePicker = new DateTimePicker();
        JTextField courseField = new JTextField(20);
        JButton addButton = new JButton("Add Task");
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        JButton completeButton = new JButton("Complete Task");
        JButton removeButton = new JButton("Remove Task");

        // Set layout and add components
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Start Date:"));
        inputPanel.add(startDatePicker);
        inputPanel.add(new JLabel("Deadline:"));
        inputPanel.add(deadlinePicker);
        inputPanel.add(new JLabel("Course:"));
        inputPanel.add(courseField);
        inputPanel.add(addButton);
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(completeButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add button listeners
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            LocalDateTime startDate = startDatePicker.getDateTimePermissive();
            LocalDateTime deadline = deadlinePicker.getDateTimePermissive();
            String course = courseField.getText();
            controller.addTask(title, description, startDate, deadline, course);
            loadTasks();
        });

        completeButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                TaskData task = viewModel.getTasks().get(selectedIndex);
                controller.completeTask(task.getId());
                loadTasks();
            }
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                TaskData task = viewModel.getTasks().get(selectedIndex);
                controller.removeTask(task.getId());
                loadTasks();
            }
        });
    }

    private void loadTasks() {
        controller.loadTodoList();
        List<TaskData> tasks = viewModel.getTasks();
        taskListModel.clear();
        for (TaskData task : tasks) {
            taskListModel.addElement(task.getTitle() + " - " + (task.isCompleted() ? "Completed" : "Pending"));
        }
    }
}
