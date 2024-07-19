package framework.view;
//TODO add documentation and CA classes/interfaces
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import entity.Course;
import entity.FriendsList;
import entity.User;
import interface_adapter.controller.FriendsListController;

public class FriendsListView {
    private FriendsListController controller;
    private JFrame mainFrame;
    private JPanel friendsPanel;
    private JPanel inputPanel;
    private JList<String>friendsListDisplay;
    private JButton refreshButton;
    private JButton addButton;
    private FriendsList friendsList; //Temporary data structure

    public FriendsListView(FriendsListController controller) {
        this.controller = controller;
        mainFrame = new JFrame("Friends");
        friendsList = new FriendsList(null);
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
        /*DefaultListModel<String> model = new DefaultListModel<>();
        ArrayList<String> friendsUsernames = friendsList.exportFriendsNames();
        for (int i = 0; i < friendsUsernames.size(); i++){
            model.addElement(friendsUsernames.get(i));
        }
        friendsListDisplay.setModel(model);
         */
    }

    public void AddFriend(){
        User[] empty = new User[1];
        Course[] empty2 = new Course[1];
        for (int i = 0; i < 100; i++) {
            User dummy = new User("User", empty, empty2);
            friendsList.addFriend(dummy);
        }
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(FriendsListView::new);
    }*/
}
