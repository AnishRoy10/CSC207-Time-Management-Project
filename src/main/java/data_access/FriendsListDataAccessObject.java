package data_access;

import entity.User;
import entity.Course;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendDataAccessInterface;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendsDataAccessInterface;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendDataAccessInterface;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for the friendslist use cases
 */
public class FriendsListDataAccessObject implements AddFriendDataAccessInterface, RefreshFriendsDataAccessInterface, RemoveFriendDataAccessInterface {
    private final SQLDatabaseHelper dbHelper;

    public FriendsListDataAccessObject(SQLDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void writeUser(User user) throws IOException {
        String sql = "INSERT OR REPLACE INTO Users(username, password, friends) VALUES(?,?,?)";
        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, String.join(",", user.getFriends().exportFriendsNames()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IOException("Failed to write user to database", e);
        }
    }

    /**
     * Reads a file and returns the user with the given username if they exist
     * @param username
     * @return
     * @throws IOException
     */
    @Override
    public User loadUser(String username) throws IOException {
        String sql = "SELECT username, password, friends FROM Users WHERE username = ?";
        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String userPassword = rs.getString("password");
                String friends = rs.getString("friends");
                List<User> friendsList = new ArrayList<>();
                if (friends != null && !friends.isEmpty()) {
                    String[] friendUsernames = friends.split(",");
                    for (String friendUsername : friendUsernames) {
                        friendsList.add(new User(friendUsername, null, new User[0], new Course[0])); // Add placeholders for friends
                    }
                }
                return new User(username, userPassword, friendsList.toArray(new User[0]), new Course[0]);
            } else {
                throw new IOException("User not found");
            }
        } catch (SQLException e) {
            throw new IOException("Failed to load user from database", e);
        }
    }

    public void populateFriends(User user) throws IOException {
        String friends = String.join(",", user.getFriends().exportFriendsNames());
        if (friends != null && !friends.isEmpty()) {
            String[] friendUsernames = friends.split(",");
            for (String friendUsername : friendUsernames) {
                User friend = loadUser(friendUsername);
                user.addFriend(friend);
            }
        }
    }
}
