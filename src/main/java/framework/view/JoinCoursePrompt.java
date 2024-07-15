package framework.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Course;

public class JoinCoursePrompt {
    private Course course;
    private JFrame frame;
    private JPanel infoPanel;
    private JPanel buttonPanel;
    private JLabel infoLabel;
    private JButton confirmButton;
    private JButton cancelButton;

    public JoinCoursePrompt(Course course) {
        this.course = course;

        frame = new JFrame("Confirm Join");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(320, 200);
        
        infoPanel = new JPanel(new GridBagLayout());
        infoLabel = new JLabel("Join " + course.getName() + "?");
        infoLabel.setFont(new Font("ARIAL", Font.PLAIN, 18));
        infoPanel.add(infoLabel);

        buttonPanel = new JPanel(new GridLayout(1, 2));
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("ARIAL", Font.PLAIN, 14));
        confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("ARIAL", Font.PLAIN, 14));
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        frame.add(infoPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
