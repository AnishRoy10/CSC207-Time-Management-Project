package entity;

/*
 * The Course class represents a course that users can join.
 */
public class Course {
    // Name of the course
    private String name;

    // Description of the course
    private String description;

    // Todolist associated with this course
    private TodoList todo;

    /**
     * Create a new Course
     *
     * @param name          The course name
     * @param description   A description of the course
     */
    public Course(String name, String description) {
        this.name = name;
        this.description = description;

        // unsure how we will associate courses and todolists just yet
        this.todo = new TodoList();
    }

    // Getter for the course name
    public String getName() {
        return name;
    }

    // Getter for the course description
    public String getDescription() {
        return description;
    }

    // Setter for the course name
    public void setName(String name) {
        this.name = name;
    }

    // Setter for the course description
    public void setDescription(String description) {
        this.description = description;
    }
}
