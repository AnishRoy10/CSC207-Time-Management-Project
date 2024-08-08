package data_access;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.*;
import repositories.CourseRepository;
import data_access.serializers.*;

public class CourseDataAccessObject implements CourseRepository {
    private final SQLDatabaseHelper dbHelper;
    private final Gson gson;

    public CourseDataAccessObject(SQLDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(Course.class, new CourseDeserializer())
                .registerTypeAdapter(AllTimeLeaderboard.class, new AllTimeLeaderboardDeserializer())
                .registerTypeAdapter(DailyLeaderboard.class, new DailyLeaderboardDeserializer())
                .registerTypeAdapter(MonthlyLeaderboard.class, new MonthlyLeaderboardDeserializer())
                .setPrettyPrinting()
                .create();
    }

    @Override
    public Map<String, Course> ReadFromCache() throws IOException {
        Map<String, Course> courses = new HashMap<>();
        String sql = "SELECT * FROM Courses";

        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                String usernames = rs.getString("usernames");
                String todoListJson = rs.getString("todoList");
                String dailyLeaderboardJson = rs.getString("dailyLeaderboard");
                String monthlyLeaderboardJson = rs.getString("monthlyLeaderboard");
                String allTimeLeaderboardJson = rs.getString("allTimeLeaderboard");

                Course course = new Course(name, description);
                if (!usernames.isEmpty()) {
                    course.setUsernames(Arrays.asList(usernames.split(",")));
                }
                course.setTodoList(gson.fromJson(todoListJson, TodoList.class));
                course.setDailyLeaderboard(gson.fromJson(dailyLeaderboardJson, DailyLeaderboard.class));
                course.setMonthlyLeaderboard(gson.fromJson(monthlyLeaderboardJson, MonthlyLeaderboard.class));
                course.setAllTimeLeaderboard(gson.fromJson(allTimeLeaderboardJson, AllTimeLeaderboard.class));

                courses.put(name, course);
            }
        } catch (SQLException e) {
            throw new IOException("Database error", e);
        }

        System.out.println("Courses read from cache: " + courses);
        return courses;
    }

    @Override
    public boolean WriteToCache(Course course) {
        String sql = "INSERT OR REPLACE INTO Courses (name, description, usernames, todoList, dailyLeaderboard, monthlyLeaderboard, allTimeLeaderboard) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getName());
            pstmt.setString(2, course.getDescription());
            pstmt.setString(3, String.join(",", course.getUserNames()));
            pstmt.setString(4, gson.toJson(course.getTodoList()));
            pstmt.setString(5, gson.toJson(course.getDailyLeaderboard()));
            pstmt.setString(6, gson.toJson(course.getMonthlyLeaderboard()));
            pstmt.setString(7, gson.toJson(course.getAllTimeLeaderboard()));

            pstmt.executeUpdate();
            System.out.println("Courses written to cache: " + course.getName());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean courseExists(String courseName) {
        String sql = "SELECT name FROM Courses WHERE name = ?";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseName);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Course findByName(String courseName) {
        String sql = "SELECT * FROM Courses WHERE name = ?";

        try (Connection conn = dbHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                String usernames = rs.getString("usernames");
                String todoListJson = rs.getString("todoList");
                String dailyLeaderboardJson = rs.getString("dailyLeaderboard");
                String monthlyLeaderboardJson = rs.getString("monthlyLeaderboard");
                String allTimeLeaderboardJson = rs.getString("allTimeLeaderboard");

                Course course = new Course(name, description);
                if (!usernames.isEmpty()) {
                    course.setUsernames(Arrays.asList(usernames.split(",")));
                }
                course.setTodoList(gson.fromJson(todoListJson, TodoList.class));
                course.setDailyLeaderboard(gson.fromJson(dailyLeaderboardJson, DailyLeaderboard.class));
                course.setMonthlyLeaderboard(gson.fromJson(monthlyLeaderboardJson, MonthlyLeaderboard.class));
                course.setAllTimeLeaderboard(gson.fromJson(allTimeLeaderboardJson, AllTimeLeaderboard.class));

                return course;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public boolean addToCourse(String courseName, User user) {
        Course course = findByName(courseName);
        if (course == null) {
            return false;
        }

        course.addUser(user);
        return WriteToCache(course);
    }

    @Override
    public boolean addTask(String courseName, Task task) {
        Course course = findByName(courseName);
        if (course == null) {
            return false;
        }

        course.getTodoList().addTask(task);
        return WriteToCache(course);
    }
}
