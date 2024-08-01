package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/**
 * The Course class represents a course that users can join.
 */
public class Course implements Serializable {
    /// Name of this course.
    private String name;

    /// Description of this course.
    private String description;

    /// List of users in this course.
    private final List<String> usernames;

    /// Todolist associated with this course.
    private final TodoList todoList;

    /// Daily leaderboard associated with this course.
    private final DailyLeaderboard dailyLeaderboard;

    /// Monthly leaderboard associated with this course.
    private final MonthlyLeaderboard monthlyLeaderboard;

    /// ALl-time leaderboard associated with this course.
    private final AllTimeLeaderboard allTimeLeaderboard;

    /**
     * Constructs a new Course object.
     *
     * @param name        the course name
     * @param description a description of the course
     */
    public Course(String name, String description) {
        this.name = name;
        this.description = description;
        this.usernames = new ArrayList<>();
        this.todoList = new TodoList();
        this.dailyLeaderboard = new DailyLeaderboard(name + " Daily", LocalDate.now());
        this.monthlyLeaderboard = new MonthlyLeaderboard(name + " Monthly", LocalDate.now());
        this.allTimeLeaderboard = new AllTimeLeaderboard(name + " All-time");
    }

    /**
     * Gets the name of this course.
     * 
     * @return the name of this course
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of this course.
     * 
     * @return the description of this course
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the name of this course.
     * 
     * @param name the new name for this course
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description of this course.
     * 
     * @param description the new description for this course
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Adds a new user to this course.
     * 
     * @param user the user to add
     */
    public void addUser(User user) {
        // prevent duplicate users in a course
        if (!usernames.contains(user.getUsername())) {
            usernames.add(user.getUsername());
        }
    }

    /**
     * Attempts to remove the specified user from this course. 
     * Returns a boolean value that indicates whether the attempt was successful.
     * 
     * @param username the name of the user to remove
     * @return         the success value of the method
     */
    public boolean removeUser(String username) {
        return usernames.remove(username);
    }

    /**
     * Get the usernames of all users in this course.
     * @return an array of strings
     */
    public String[] getUserNames() {
        String[] names = new String[usernames.size()];
        for (int i = 0; i < usernames.size(); i++) {
            names[i] = usernames.get(i);
        }
        return names;
    }

    /**
     * Find if a user is in this course by their username.
     * @param username The username of the user to look up.
     * @return         If the user by the username is in this course.
     */
    public boolean containsUser(String username) {
        return usernames.contains(username);
    }

    /**
     * Get this courses todolist.
     * @return The todolist.
     */
    public TodoList getTodoList() {
        return todoList;
    }

    /**
     * Get this courses daily leaderboard.
     * @return The daily leaderboard.
     */
    public Leaderboard getDailyLeaderboard() {
        return dailyLeaderboard;
    }

    /**
     * Get this courses monthly leaderboard.
     * @return The monthly leaderboard.
     */
    public MonthlyLeaderboard getMonthlyLeaderboard() {
        return monthlyLeaderboard;
    }

    /**
     * Get this courses all-time leaderboard.
     * @return The all-time leaderboard.
     */
    public AllTimeLeaderboard getAllTimeLeaderboard() {
        return allTimeLeaderboard;
    }
}
