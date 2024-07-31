package framework.view;

import entity.Course;
import interface_adapter.viewmodel.CourseListViewModel;
import interface_adapter.viewmodel.CourseViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The course view page lets users visualize the todolist, leaderboards, and other
 * information associated with each course they are a part of.
 */
public class CourseView extends JFrame {
    private final String username;
    private final CourseViewModel viewModel;
    private final CourseListViewModel listViewModel;

    private JPanel leftPanel;
    private JScrollPane courseListScrollPane;
    private JPanel courseListPanel;

    private JPanel rightPanel;
    private JList<String> userList;
    private JLabel memberCountLabel;

    private JPanel mainPanel;
    private JLabel descriptionLabel;

    public CourseView(String username, CourseViewModel viewModel, CourseListViewModel listViewModel) {
        this.username = username;
        this.viewModel = viewModel;
        this.listViewModel = listViewModel;

        this.setTitle("Course View");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(1200, 600);

        this.displayCourseList();
        this.displayMainView();
        this.displayUserList();

        setVisible(true);
    }

    /**
     * Visualizes a course on the course view page.
     *`
     * @param courseName the name of the course to visualize
     */
    private void visualize(String courseName) {
        // TODO: execute view controller
        if (!viewModel.wasSuccessful()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Something went wrong loading " + courseName + ".",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.setTitle(courseName);
        DefaultListModel<String> model = viewModel.getUsernames();

        userList.setModel(model);
        memberCountLabel.setText("Members - " + model.size());
        descriptionLabel.setText(viewModel.getCourseDescription());

        this.leftPanel.setVisible(true);
        this.rightPanel.setVisible(true);
        this.mainPanel.setVisible(true);
    }

    /**
     * Displays the courses a user is part of on the course view page.
     */
    private void displayCourseList() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        courseListPanel = new JPanel();
        courseListPanel.setLayout(new BoxLayout(courseListPanel, BoxLayout.Y_AXIS));
        courseListPanel.setBackground(new Color(220, 220, 220));

        courseListScrollPane = new JScrollPane(courseListPanel);
        courseListScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        courseListScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(5, 0));
        courseListScrollPane.setBorder(BorderFactory.createEmptyBorder());
        courseListScrollPane.setViewportBorder(BorderFactory.createEmptyBorder());

        // TODO: execute list controller
        for (String courseName : listViewModel.getCourses()) {
            addCourseButton(courseName);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 1, 0));
        buttonPanel.setBackground(new Color(220, 220, 220));

        JButton joinCourseButton = new JButton("Join Course");
        JButton leaveCourseButton = new JButton("Leave Course");

        joinCourseButton.setBackground(Color.GRAY);
        leaveCourseButton.setBackground(Color.GRAY);
        joinCourseButton.setForeground(Color.WHITE);
        leaveCourseButton.setForeground(Color.WHITE);
        joinCourseButton.setBorderPainted(false);
        leaveCourseButton.setBorderPainted(false);

        buttonPanel.add(joinCourseButton);
        buttonPanel.add(leaveCourseButton);

        /// TODO: integrate use case listeners here later to join, leave buttons

        leftPanel.add(courseListScrollPane, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(leftPanel, BorderLayout.WEST);
    }

    /**
     * Add a course button to the course list.
     *
     * @param courseName the name of the course to add an entry for
     */
    private void addCourseButton(String courseName) {
        JButton button = new JButton(courseName);
        button.setMaximumSize(new Dimension(courseListScrollPane.getMaximumSize().width, 32));
        button.addActionListener(e -> visualize(courseName));
        button.setBackground(new Color(88, 118, 161));
        button.setForeground(Color.white);
        courseListPanel.add(button);
    }

    /**
     * Displays the main view, including todolist, description, and leaderboards.
     */
    private void displayMainView() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.X_AXIS));

        descriptionLabel = new JLabel("");
        descriptionLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        descriptionLabel.setMaximumSize(new Dimension(mainPanel.getMaximumSize().width, 100));
        descriptionPanel.setLayout(new GridBagLayout());
        descriptionPanel.setBackground(new Color(200, 200, 200));

        descriptionPanel.add(descriptionLabel);
        mainPanel.add(descriptionPanel, BorderLayout.NORTH);
        mainPanel.setVisible(false);

        this.add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Displays all the users in a course on the course view page.
     */
    private void displayUserList() {
        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));

        userList = new JList<>();
        userList.setBorder(new EmptyBorder(5, 5, 5, 5));
        userList.setBackground(new Color(220, 220, 220));

        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(5, 0));
        userScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(5, 0));
        userScrollPane.setBorder(BorderFactory.createEmptyBorder());
        userScrollPane.setViewportBorder(BorderFactory.createEmptyBorder());

        JPanel memberCountPanel = new JPanel();
        memberCountPanel.setLayout(new GridBagLayout());
        memberCountPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        memberCountPanel.setBackground(new Color(220, 220, 220));
        memberCountLabel = new JLabel("");
        memberCountLabel.setForeground(new Color(90, 90, 90));

        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(new Color(220, 220, 220));

        memberCountPanel.add(memberCountLabel);
        userListPanel.add(userScrollPane);

        rightPanel.add(memberCountPanel, BorderLayout.NORTH);
        rightPanel.add(userListPanel, BorderLayout.CENTER);
        rightPanel.setVisible(false);

        this.add(rightPanel, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CourseView(
                "TestUser",
                new CourseViewModel(),
                new CourseListViewModel())
        );
    }
}
