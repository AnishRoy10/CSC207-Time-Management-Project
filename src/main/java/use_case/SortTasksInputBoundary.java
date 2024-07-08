package use_case;

import java.util.List;

public interface SortTasksInputBoundary {
    List<TaskResponseModel> execute(SortTasksRequestModel requestModel);
}
