package Entity;

import entity.Course;
import entity.FriendsList;
import entity.User;
import org.junit.Test;

public class FriendsListTest {

    @Test
    public void testUserAddSelf() {
        Course course = new Course("test", "test");
        Course[] courses = new Course[] {course};
        User user1 = new User("user1", "123", null, courses);
        assert user1.getFriends().exportFriendsNames().isEmpty();
        user1.addFriend(user1);
        assert user1.getFriends().exportFriendsNames().isEmpty();
    }
    @Test
    public void testValidFriendsListOperations() {
        Course course = new Course("test", "test");
        Course[] courses = new Course[] {course};
        User user1 = new User("user1", "123", null, courses);
        FriendsList friendsList = new FriendsList(null);
        assert friendsList.exportFriendsNames().isEmpty();
        friendsList.addFriend(user1);
        assert friendsList.exportFriendsNames().size() == 1;
        friendsList.addFriend(user1);
        assert friendsList.exportFriendsNames().size() == 1;
        friendsList.removeFriend(user1);
        assert friendsList.exportFriendsNames().isEmpty();
    }
    @Test
    public void testRemoveNonExistentUser() {
        FriendsList friendsList = new FriendsList(null);
        assert friendsList.exportFriendsNames().isEmpty();
        friendsList.removeFriend("user2");
        assert friendsList.exportFriendsNames().isEmpty();
    }
}
