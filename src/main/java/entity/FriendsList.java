package entity;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FriendsList implements Serializable {
    /**
     * An ArrayList of Users whom a User has added as friends
     */
    // The list of User objects
    private ArrayList<User> friends;

    /**
     * Constructs the users friend list using imported data from the db
     *
     * @param importedFriends An Array of friends imported from the db
     */
    public FriendsList(User[] importedFriends) {
        this.friends = new ArrayList<>();
        if (importedFriends != null) {
            for (int i = 0; i < importedFriends.length; i++) {
                this.friends.add(importedFriends[i]);
            }
        }
    }

    //Add a friend if not already added
    public void addFriend(User user) {
        if (!this.friends.contains(user)) {
            this.friends.add(user);
        }
    }
    public int getSize(){
        return this.friends.size();
    }
    //Given an array of users, return a user by username
    public User findUserByName(String name, User[] users) {
        for (int i = 0; i < users.length; i++) {
            if (Objects.equals(users[i].getUsername(), name)){
                return users[i];
            }
        }
        return null;
    }
    //Remove a friend if in FriendsList
    public void removeFriend(User user) {
        this.friends.remove(user);
    }

    public void removeUserByName(String name) {
        for (int i = 0; i < this.friends.size(); i++) {
            if (this.friends.get(i).getUsername().equals(name)) {
                this.friends.remove(i);
                i--;
            }
        }
    }
    //Convert the FriendsList ArrayList into an Array
    public Object[] exportFriendsList() {
        return this.friends.toArray();

    //Return an arraylist of the usernames of a users friends
    }
    public ArrayList<String> exportFriendsNames() {
        ArrayList<String> friendsnames = new ArrayList<String>();
        for (int i = 0; i < this.friends.size(); i++) {
            friendsnames.add(this.friends.get(i).getUsername());
        }
        return friendsnames;
    }
}
