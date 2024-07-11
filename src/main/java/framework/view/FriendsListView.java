package framework.view;

import javax.swing.*;
import java.awt.*;

import entity.FriendsList;

public class FriendsListView {
    private JFrame mainFrame;
    private JPanel friendsPanel;
    private JList<String>friendsListDisplay;
    private FriendsList friendsList;
    public FriendsListView() {
        mainFrame = new JFrame("Friends");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setSize(1280, 720);
        friendsPanel = new JPanel();
        friendsListDisplay = new JList<String>();
        friendsPanel.add(new JScrollPane(friendsListDisplay));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FriendsListView::new);
    }
}
