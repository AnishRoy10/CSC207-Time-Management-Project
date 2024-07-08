package data_access;

import entity.TodoList;

import java.io.*;

/**
 * TodoListDataAccessObject provides methods to save and load a TodoList to and from a file.
 */
public class TodoListDataAccessObject {

    private String filePath;

    /**
     * Constructs a TodoListDataAccessObject with the specified file path.
     *
     * @param filePath The path of the file where the to-do list will be saved and loaded.
     */
    public TodoListDataAccessObject(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given to-do list to the file.
     *
     * @param todoList The to-do list to save.
     * @throws IOException If an I/O error occurs while saving the to-do list.
     */
    public void saveTodoList(TodoList todoList) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(todoList);
        }
    }

    /**
     * Loads the to-do list from the file.
     *
     * @return The loaded to-do list.
     * @throws IOException            If an I/O error occurs while loading the to-do list.
     * @throws ClassNotFoundException If the class of the loaded object cannot be found.
     */
    public TodoList loadTodoList() throws IOException, ClassNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            // Return a new TodoList if the file does not exist
            return new TodoList();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (TodoList) ois.readObject();
        }
    }
}
