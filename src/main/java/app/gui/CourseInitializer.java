package app.gui;

import framework.view.CourseView;
import interface_adapter.viewmodel.CourseListViewModel;
import interface_adapter.viewmodel.CourseViewModel;

import javax.swing.*;

/**
 * Initialize the course view and its necessary use case objects.
 */
public class CourseInitializer {
    /**
     * Run this initializer.
     */
    public static void run(String username) {

        CourseViewModel viewModel = new CourseViewModel();
        CourseListViewModel listViewModel = new CourseListViewModel();

        SwingUtilities.invokeLater(() -> new CourseView(
                username, viewModel, listViewModel
        ));
    }
}
