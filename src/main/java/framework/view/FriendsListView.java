package framework.view;
//TODO add documentation
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import entity.Course;
import entity.FriendsList;
import entity.User;
import interface_adapter.viewmodel.FriendsListViewModel;
import interface_adapter.controller.FriendsListController;

public class FriendsListView extends JFrame {
    private String username;
    private final FriendsListViewModel viewModel;
    private final FriendsListController friendsListController;
    private final JList<String>friendsListDisplay;
    private final JTextField usernameField;

    public FriendsListView(FriendsListController controller, FriendsListViewModel viewModel, String username) throws IOException, ClassNotFoundException {
        this.viewModel = viewModel;
        this.friendsListController = controller;
        JFrame mainFrame = new JFrame("Friends");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.pack();
        mainFrame.setSize(1280, 720);
        JPanel friendsPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(1, 6));
        friendsListDisplay = new JList<String>();
        friendsPanel.add(new JScrollPane(friendsListDisplay));
        JButton refreshButton = new JButton("Refresh");
        usernameField = new JTextField();
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
        inputPanel.add(usernameField);
        mainFrame.add(friendsPanel, BorderLayout.CENTER);
        mainFrame.add(inputPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    public void refreshFriendsList() throws IOException {
        /*DefaultListModel<String> model = new DefaultListModel<>();
        ArrayList<String> friendsUsernames = friendsList.exportFriendsNames();
        for (int i = 0; i < friendsUsernames.size(); i++){
            model.addElement(friendsUsernames.get(i));
        }
        friendsListDisplay.setModel(model);*/
        friendsListController.refreshFriend();
        friendsListDisplay.setModel(viewModel.getDisplayedListModel());
    }

    public void addFriend() throws IOException {
        String name = usernameField.getText();
        friendsListController.addFriend(name);
    }

    public void removeFriend() throws IOException, ClassNotFoundException {
        String name = usernameField.getText();
        friendsListController.removeFriend(name);
    }
}
