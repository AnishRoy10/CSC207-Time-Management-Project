package framework.view;
//TODO make the code work for the FriendsList class
//TODO add documentation
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
    private ArrayList<String> friendsList; //Temporary data structure

    public FriendsListView() {
        mainFrame = new JFrame("Friends");
        friendsList = new ArrayList<>();
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
        addButton.addActionListener(e -> AddFriend());
        inputPanel.add(refreshButton);
        inputPanel.add(addButton);
        mainFrame.add(friendsPanel, BorderLayout.CENTER);
        mainFrame.add(inputPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    public void refreshFriendsList(){
        DefaultListModel<String> model = new DefaultListModel<>();
        ArrayList<String> friendsUsernames = friendsList;
        for (int i = 0; i < friendsUsernames.size(); i++){
            model.addElement(friendsUsernames.get(i));
        }
        friendsListDisplay.setModel(model);
    }

    public void AddFriend(){
        friendsList.add("User1");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FriendsListView::new);
    }
}
