package use_case.CourseUseCases.ViewCourseUseCase;

import entity.Course;
import repositories.CourseRepository;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

/**
 * Interactor class to the view course use case.
 */
public class ViewCourseUseCase implements ViewCourseInputBoundary {
    /// The output boundary to present to.
    private final ViewCourseOutputBoundary presenter;

    /// The data access object to fetch courses from.
    private final CourseRepository courseDataAccessObject;

    /**
     * Construct a new interactor instance.
     * @param presenter              The output boundary to present to.
     * @param courseDataAccessObject The data access object to fetch courses from.
     */
    public ViewCourseUseCase(ViewCourseOutputBoundary presenter, CourseRepository courseDataAccessObject) {
        this.presenter = presenter;
        this.courseDataAccessObject = courseDataAccessObject;
    }

    @Override
    public void execute(ViewCourseInputData inputData) {
        Course course = courseDataAccessObject.findByName(inputData.getCourseName());

        /// if the course does not exist
        if (course == null) {
            ViewCourseOutputData outputData = new ViewCourseOutputData(
                    false,
                    "Unable to find course with name " + inputData.getCourseName() + "."
            );
            presenter.present(outputData);
            return;
        }

        DefaultListModel<String> model = new DefaultListModel<>();
        model.addAll(List.of(course.getUserNames()));

        ViewCourseOutputData outputData = new ViewCourseOutputData(
                true,
                "Success",
                course.getDescription(),
                model,
                course.getTodoList(),
                course.getDailyLeaderboard(),
                course.getMonthlyLeaderboard(),
                course.getAllTimeLeaderboard()
        );
        presenter.present(outputData);
    }
}
