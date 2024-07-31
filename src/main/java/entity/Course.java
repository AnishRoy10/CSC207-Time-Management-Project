package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Course class represents a course that users can join.
 */
public class Course implements Serializable {
    // Name of this course
    private String name;

    // Description of this course
    private String description;

    // Users in this course
    private ArrayList<User> users;

    // Todolist associated with this course
    private TodoList todo;

    /**
     * Constructs a new Course object.
     *
     * @param name        the course name
     * @param description a description of the course
     */
    public Course(String name, String description) {
        this.name = name;
        this.description = description;
        this.users = new ArrayList<>();
        this.todo = new TodoList();
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
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    /**
     * Attempts to remove the specified user from this course. 
     * Returns a boolean value that indicates whether the attempt was successful.
     * 
     * @param user the user to remove
     * @return     the success value of the method
     */
    public boolean removeUser(User user) {
        return users.remove(user);
    }

    /**
     * Get the usernames of all users in this course.
     * @return an array of strings
     */
    public String[] getUserNames() {
        String[] names = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            names[i] = users.get(i).getUsername();
        }
        return names;
    }


}
