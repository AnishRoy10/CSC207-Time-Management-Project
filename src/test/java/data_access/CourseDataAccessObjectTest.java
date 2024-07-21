package data_access;

import entity.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CourseDataAccessObjectTest {
    CourseDataAccessObject obj;
    private final String testFilePath = "test_courseCache.txt";

    @BeforeEach
    public void run() throws IOException {
        obj = new CourseDataAccessObject(testFilePath);
    }

    @AfterEach
    void tearDown() {
        // Clean up by deleting the test file after each test
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testReadWriteCourse() {
        Course crs1 = new Course("CSC108", "Intro to Comp. Prog.");
        Course crs2 = new Course("CSC148", "Intro to Comp. Sci.");

        obj.WriteToCache(crs1);
        obj.WriteToCache(crs2);

        Assertions.assertTrue(obj.courseExists("CSC108"));
        Assertions.assertTrue(obj.courseExists("CSC148"));
    }

    @Test
    public void testFetchCourse() {
        Course crs = new Course("CSC207", "Software Design");

        obj.WriteToCache(crs);

        Assertions.assertEquals(obj.findByName("CSC207").getName(), crs.getName());
        Assertions.assertEquals(obj.findByName("CSC207").getDescription(), crs.getDescription());
    }
}

