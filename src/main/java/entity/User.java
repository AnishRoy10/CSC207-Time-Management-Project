package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code User} class represents a user in the application. Each user has a username, password,
 * a list of friends, courses they are enrolled in, a to-do list, a score, a timer, and a calendar.
 * This class provides methods to manage the user's friends, tasks, courses, and events.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L; // Serial version UID for serialization compatibility

    private String username; // The username of this user
    private String password; // Hashed password for the user
    private final FriendsList friends; // Friends list of this user
    private final List<String> courses; // Courses this user is in
    private final TodoList todo; // To-do list associated with this user
    private int score; // The user's score
    private Timer timer; // A timer set by the user
    private int aNumber; // An additional integer attribute
    private Calendar calendar; // The user's calendar

    /**
     * Constructs a new {@code User} object with the specified username, password, friends, and courses.
     *
     * @param username The username for this user.
     * @param password The password for this user (will be hashed).
     * @param friends  An array of {@code User} objects representing the friends of this user.
     * @param courses  An array of {@code Course} objects representing the courses this user is enrolled in.
     */
    public User(String username, String password, User[] friends, Course[] courses) {
        this.username = username;
        this.password = hashPassword(password); // Hash the password
        this.friends = new FriendsList(friends);
        this.courses = new ArrayList<>();
        this.todo = new TodoList();
        this.score = 0;
        this.calendar = new Calendar();
        this.aNumber = 5;
        for (Course course : courses) {
            this.courses.add(course.getName());
        }
    }

    /**
     * Gets the username of this user.
     *
     * @return The username of this user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for this user.
     *
     * @param username The new username for this user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Adds a friend to this user's friends list.
     *
     * @param user The user to add as a friend.
     */
    public void addFriend(User user) {
        if (!(user.getUsername().equals(username))) {
            this.friends.addFriend(user);
        }
    }

    /**
     * Gets the hashed password of this user.
     *
     * @return The hashed password of this user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a new password for this user. The password will be hashed before being stored.
     *
     * @param password The new password to set.
     */
    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    /**
     * Verifies the provided password by comparing its hash with the stored hashed password.
     *
     * @param password The password to verify.
     * @return {@code true} if the password is correct, {@code false} otherwise.
     */
    public boolean verifyPassword(String password) {
        return this.password.equals(hashPassword(password));
    }

    /**
     * Gets the friends list of this user.
     *
     * @return The {@code FriendsList} of this user.
     */
    public FriendsList getFriends() {
        return friends;
    }

    /**
     * Removes a friend from this user's friends list.
     *
     * @param user The user to remove from the friends list.
     */
    public void removeFriend(User user) {
        this.friends.removeFriend(user);
    }

    /**
     * Adds a task to this user's to-do list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        this.todo.addTask(task);
    }

    /**
     * Removes a task from this user's to-do list.
     *
     * @param task The task to remove.
     */
    public void removeTask(Task task) {
        this.todo.removeTask(task);
    }

    /**
     * Enrolls this user in a new course and adds the course to the user's course list.
     *
     * @param course The course to enroll the user in.
     */
    public void addCourse(Course course) {
        course.addUser(this);
        if (!this.courses.contains(course.getName())) {
            this.courses.add(course.getName());
        }
    }

    /**
     * Gets the list of courses this user is enrolled in.
     *
     * @return A list of course names this user is enrolled in.
     */
    public List<String> getCourses() {
        return courses;
    }

    /**
     * Gets the score of this user.
     *
     * @return The score of this user.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score for this user.
     *
     * @param score The new score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the timer associated with this user.
     *
     * @return The {@code Timer} associated with this user.
     */
    public Timer getTimer() {
        return this.timer;
    }

    /**
     * Sets a timer for this user.
     *
     * @param timer The {@code Timer} to set.
     */
    public void addTimer(Timer timer) {
        this.timer = timer;
    }

    /**
     * Gets the to-do list associated with this user.
     *
     * @return The {@code TodoList} associated with this user.
     */
    public TodoList getTodoList() {
        return todo;
    }

    /**
     * Gets the calendar associated with this user.
     *
     * @return The {@code Calendar} associated with this user.
     */
    public Calendar getCalendar() {
        return this.calendar;
    }

    /**
     * Gets all events in the user's calendar.
     *
     * @return A list of {@code CalendarEvent} in the user's calendar.
     */
    public List<CalendarEvent> getEvents() {
        return this.calendar.getAllEvents();
    }

    /**
     * Adds an event to the user's calendar.
     *
     * @param event The {@code CalendarEvent} to add.
     */
    public void addEvent(CalendarEvent event) {
        this.calendar.addEvent(event);
    }

    /**
     * Removes an event from the user's calendar.
     *
     * @param event The {@code CalendarEvent} to remove.
     */
    public void removeEvent(CalendarEvent event) {
        this.calendar.removeEvent(event);
    }

    /**
     * Hashes the provided password. This method currently returns the password as-is and should
     * be replaced with a proper hashing algorithm (e.g., SHA-256) in production.
     *
     * @param password The password to hash.
     * @return The hashed password.
     */
    private String hashPassword(String password) {
        return password; // Placeholder for hashing
    }

    /**
     * Determines whether this user is equal to another object. Two users are considered equal if they
     * have the same username.
     *
     * @param o The object to compare with.
     * @return {@code true} if this user is equal to the specified object, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    /**
     * Returns the hash code for this user based on the username.
     *
     * @return The hash code for this user.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    /**
     * Removes this user from the specified course by name.
     *
     * @param courseName The name of the course to remove this user from.
     * @return {@code true} if the user was successfully removed from the course, {@code false} otherwise.
     */
    public boolean removeCourse(String courseName) {
        return this.courses.remove(courseName);
    }

    /**
     * Gets the value of the {@code aNumber} attribute.
     *
     * @return The value of {@code aNumber}.
     */
    public int getANumber() {
        return aNumber;
    }

    /**
     * Sets the value of the {@code aNumber} attribute.
     *
     * @param aNumber The new value for {@code aNumber}.
     */
    public void setANumber(int aNumber) {
        this.aNumber = aNumber;
    }
}
