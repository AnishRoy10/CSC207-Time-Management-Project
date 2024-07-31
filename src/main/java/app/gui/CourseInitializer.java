package app.gui;

import data_access.CourseDataAccessObject;
import data_access.FileCacheUserDataAccessObject;
import framework.view.CourseView;
import interface_adapter.controller.CourseViewController;
import interface_adapter.presenter.CourseListPresenter;
import interface_adapter.presenter.CourseViewPresenter;
import interface_adapter.viewmodel.CourseListViewModel;
import interface_adapter.viewmodel.CourseViewModel;
import repositories.CourseRepository;
import repositories.UserRepository;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesInputBoundary;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesOutputBoundary;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesUseCase;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseInputBoundary;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseOutputBoundary;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseUseCase;

import javax.swing.*;
import java.io.IOException;

/**
 * Initialize the course view and its necessary use case objects.
 */
public class CourseInitializer {
    /**
     * Run this initializer.
     */
    public static void run(String username) {
        try {
            String usersPath = "src/main/java/data_access/userCache.json";
            String coursesPath = "src/main/java/data_access/courseCache.json";

            UserRepository userDataAccessObject = new FileCacheUserDataAccessObject(usersPath);
            CourseRepository courseDataAccessObject = new CourseDataAccessObject(coursesPath);

            CourseViewModel viewModel = new CourseViewModel();
            CourseListViewModel listViewModel = new CourseListViewModel();

            ViewCourseOutputBoundary courseViewPresenter = new CourseViewPresenter(viewModel);
            LoadCoursesOutputBoundary courseListPresenter = new CourseListPresenter(listViewModel);

            ViewCourseInputBoundary courseViewInteractor = new ViewCourseUseCase(
                    courseViewPresenter, courseDataAccessObject
            );

            LoadCoursesInputBoundary courseListInteractor = new LoadCoursesUseCase(
                    courseListPresenter, userDataAccessObject
            );

            CourseViewController courseViewController = new CourseViewController(
                    courseViewInteractor, courseListInteractor
            );

            SwingUtilities.invokeLater(() -> new CourseView(
                    username, courseViewController, viewModel, listViewModel
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
