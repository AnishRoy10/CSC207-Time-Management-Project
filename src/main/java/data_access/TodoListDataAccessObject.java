package data_access;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entity.TodoList;
import repositories.TodoListRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Data access object for TodoList, using Gson for JSON serialization.
 */
public class TodoListDataAccessObject implements TodoListRepository {
    private static final String DIRECTORY_PATH = "saves";
    private static final String FILE_PATH = DIRECTORY_PATH + "/todo_list.json";
    private final Gson gson;

    public TodoListDataAccessObject() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();
    }

    /**
     * Loads the to-do list from a JSON file.
     *
     * @return The loaded to-do list.
     */
    @Override
    public TodoList loadTodoList() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type todoListType = new TypeToken<TodoList>() {}.getType();
            return gson.fromJson(reader, todoListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new TodoList();
        }
    }

    /**
     * Saves the to-do list to a JSON file.
     *
     * @param todoList The to-do list to be saved.
     */
    @Override
    public void saveTodoList(TodoList todoList) {
        try {
            File directory = new File(DIRECTORY_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                gson.toJson(todoList, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
