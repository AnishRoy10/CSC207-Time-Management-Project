package framework.view;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import interface_adapter.viewmodel.FriendsListViewModel;
import interface_adapter.controller.FriendsListController;


/**
 * User interface for the active users friends list
 */
public class FriendsListView extends JFrame {
    private final FriendsListViewModel viewModel;
    private final FriendsListController friendsListController;
    private final JList<String>friendsListDisplay;
    private final JTextField usernameField;
    //constructor
    public FriendsListView(FriendsListController controller, FriendsListViewModel viewModel, String username) throws IOException, ClassNotFoundException {
        this.viewModel = viewModel;
        this.friendsListController = controller;
        JFrame mainFrame = new JFrame("Friends");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.pack();
        mainFrame.setSize(450, 500);
        JPanel friendsPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(1, 3));
        friendsListDisplay = new JList<String>();
        friendsPanel.add(new JScrollPane(friendsListDisplay));
        JButton refreshButton = new JButton("Refresh");
        usernameField = new JTextField("add or remove a friend");
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        refreshButton.addActionListener(e -> {
            try {
                refreshFriendsList();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        addButton.addActionListener(e -> {
            try {
                addFriend();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        removeButton.addActionListener(e -> {
            try {
                removeFriend();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        inputPanel.add(refreshButton);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(usernameField);
        mainFrame.add(friendsPanel, BorderLayout.CENTER);
        mainFrame.add(inputPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }
    //refreshes the friends list
    public void refreshFriendsList() throws IOException {
        friendsListController.refreshFriend();
        friendsListDisplay.setModel(viewModel.getDisplayedListModel());
    }
    //adds a friend to the active users friends list given a username
    public void addFriend() throws IOException {
        String name = usernameField.getText();
        usernameField.setText("");
        friendsListController.addFriend(name);
    }
    //removes a friends from the active users friends list given a username
    public void removeFriend() throws IOException, ClassNotFoundException {
        String name = usernameField.getText();
        usernameField.setText("");
        friendsListController.removeFriend(name);
    }
}
