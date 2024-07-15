package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * The User class represents a user of the program.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L; // Add serial version UID for serialization compatibility

    private String username; // The username of this user
    private String password; // Hashed password for the user
    private final FriendsList friends; // Friends list of this user
    private final List<Course> courses; // Courses this user is in
    private final TodoList todo; // To-do list associated with this user
    private int score; // The users score
    /**
     * Constructs a new User object.
     *
     * @param username The username for this user
     * @param password The password for this user
     * @param friends  Friends for this user
     * @param courses  Courses this user is in
     */
    public User(String username, String password, User[] friends, Course[] courses) {
        this.username = username;
        this.password = hashPassword(password); // Hash the password
        this.friends = new FriendsList(friends);
        this.courses = new ArrayList<>(Arrays.asList(courses));
        this.todo = new TodoList();
        this.score = 0;
    }

    // Getter for the username
    public String getUsername() {
        return username;
    }

    // Setter for the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Setter for the password
    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    // Verify the provided password
    public boolean verifyPassword(String password) {
        return this.password.equals(hashPassword(password));
    }

    // Getter for the friends list
    public FriendsList getFriends() {
        return friends;
    }

    // Add a friend to the friends list
    public void addFriend(User user) {
        this.friends.addFriend(user);
    }

    // Remove a friend from the friends list
    public void removeFriend(User user) {
        this.friends.removeFriend(user);
    }

    // Add a task to the to-do list
    public void addTask(Task task) {
        this.todo.addTask(task);
    }

    // Remove a task from the to-do list
    public void removeTask(Task task) {
        this.todo.removeTask(task);
    }

    // Getter for the courses
    public List<Course> getCourses() {
        return courses;
    }

    // Add a course to the user's courses
    public void addCourse(Course course) {
        course.addUser(this);
        if (!this.courses.contains(course)) {
            this.courses.add(course);
        }
    }

    // Remove a course from the user's courses
    public boolean removeCourse(Course course) {
        if (this.courses.contains(course)) {
            return course.removeUser(this);
        }
        return false;
    }

    // Getter for score
    public int getScore() {
        return score;
    }

    // Setter for score
    public void setScore(int score) {
        this.score = score;
    }

    // Hash the password (to be properly done later. SHA-256 perhaps?)
    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
