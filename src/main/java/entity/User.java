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
    private Timer timer; // A timer set by the user
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

    /**
    * Get this users username.
    * 
    * @return the username of this user
    */
    public String getUsername() {
        return username;
    }

    /**
    * Change this users username.
    * 
    * @param username the new username for this user
    */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
    * Add a friend to this user.
    * 
    * @param user the new friend for this user
    */
    public void addFriend(User user) {
        this.friends.addFriend(user);
    }
    
    // Getter for the password
    public String getPassword() {
        return password;
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

    /**
    * Attempt to remove a friend from this user.
    * 
    * @param user the friend to remove
    */
    public void removeFriend(User user) {
        this.friends.removeFriend(user);
    }

    /**
    * Add a task to this users todolist.
    * 
    * @param task the task to add
    */
    public void addTask(Task task) {
        this.todo.addTask(task);
    }

    /**
    * Remove a task from this users todolist.
    * 
    * @param task the task to remove
    */
    public void removeTask(Task task) {
        this.todo.removeTask(task);
    }

    /**
    * Place this user in a new course.
    * 
    * @param course the course to put this user in
    */
    public void addCourse(Course course) {
        course.addUser(this);
        if (!this.courses.contains(course)) {
            this.courses.add(course);
        }
    }
    
    // Getter for the courses
    public List<Course> getCourses() {
        return courses;
    }

    // Getter for score
    public int getScore() {
        return score;
    }

    // Setter for score
    public void setScore(int score) {
        this.score = score;
    }

    // Setter for timer
    public void addTimer(Timer timer) {
        this.timer = timer;
    }

    // Hash the password (to be properly done later. SHA-256 perhaps?)
    private String hashPassword(String password) {
        return password;
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
    
    /**
    * Attempts to remove this user from the target course.
    * Returns whether the removal was successful.
    * 
    * @param course the course to remove this user from
    * @return       success value of the method
    */
    public boolean removeCourse(Course course) {
        if (this.courses.contains(course)) {
            return course.removeUser(this);
        }
        return false;
    }
}
