package entity;

/**
 * The Course class represents a course that users can join.
 * This is a barebones implementation to ensure Task.java compiles.
 */
public class Course {
    private String name;

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
