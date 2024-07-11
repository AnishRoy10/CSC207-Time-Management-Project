package framework.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import entity.FriendsList;
import entity.User;

public class FriendsListView {
    private JFrame mainFrame;
    private JPanel friendsPanel;
    private JPanel inputPanel;
    private JList<String>friendsListDisplay;
    private JButton refreshButton;
    private JButton addButton;
    private FriendsList friendsList;

    public FriendsListView() {
        mainFrame = new JFrame("Friends");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.pack();
        mainFrame.setSize(1280, 720);
        friendsPanel = new JPanel(new BorderLayout());
        inputPanel = new JPanel(new GridLayout(1, 4));
        friendsListDisplay = new JList<String>();
        friendsPanel.add(new JScrollPane(friendsListDisplay));
        refreshButton = new JButton("Refresh");
        addButton = new JButton("Add");
        refreshButton.addActionListener(e -> refreshFriendsList());
        inputPanel.add(refreshButton);
        inputPanel.add(addButton);
        mainFrame.add(friendsPanel, BorderLayout.CENTER);
        mainFrame.add(inputPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    public void refreshFriendsList(){
        DefaultListModel<String> model = new DefaultListModel<>();
        ArrayList<String> friendsUsernames = friendsList.exportFriendsNames();
        for (int i = 0; i < friendsUsernames.size(); i++){
            model.addElement(friendsUsernames.get(i));
        }
        System.out.println(friendsList.exportFriendsNames());
        friendsListDisplay.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FriendsListView::new);
    }
}
