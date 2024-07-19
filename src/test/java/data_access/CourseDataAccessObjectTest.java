package data_access;

import entity.Course;
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

        try {
            obj.WriteToCache(crs1);
            obj.WriteToCache(crs2);
        } catch (IOException e) {
            fail("(testReadWriteCourse) The DAO was unable to write a course to the file.");
        }

        assertTrue(obj.courseExists("CSC108"));
        assertTrue(obj.courseExists("CSC148"));
    }

    @Test
    public void testFetchCourse() {
        Course crs = new Course("CSC207", "Software Design");

        try {
            obj.WriteToCache(crs);
        } catch (IOException e) {
            fail("(testFetchCourse) The DAO was unable to write a course to the file.");
        }

        assertEquals(obj.findByName("CSC207").getName(), crs.getName());
        assertEquals(obj.findByName("CSC207").getDescription(), crs.getDescription());
    }
}

