# CSC207-Time-Management-Project

- [Features](#Features)
- [How to Run](#How-to-Run-the-Time-Management-Application)
- [To-do List Feature](#To-do-List-Feature)
- [Clean Architecture](#Clean-Architecture)

Welcome to the Time Management Project by Team Lebron James. This project is designed to help users manage their time effectively by providing various features such as task management, scheduling, and timers. The project also includes social features such as a friend system and leaderboards that allow you to compete with others. 

## Features
* To-Do List: The user can create and add tasks to their To-Do list which they can set to complete when they complete the task. Tasks can also be removed and the To-Do list can be filtered.
* Calendar: Displays the tasks a user has on a calendar based on their tasks' deadlines.
* Friends: A user can add and remove friends.
* Leaderboards: Leaderboards based on the progress people have had on their tasks. Leaderboards can be based on friends, course or all users.
* Courses: User can add themselves to a course and tasks for that course will be added to the users To-Do list.
* Timer: A user can set a timer that can be paused and the user can complete a task while the timer counts down. 

# How to Run the Time Management Application
1. Open the Project in IntelliJ IDEA
   - Open IntelliJ IDEA.
   - Select "Open" and navigate to the cloned repository directory.
   - When prompted, choose to open the project as a Maven project and make sure to load the Maven project when prompted.  
2. Run the Application
   - Navigate to src/main/java/app/gui/Main.java in the Project Explorer.
   - Right-click on Main.java and select "Run 'Main.main()'".
3. Using the Application
  - The application will start with the WelcomeView, where you can choose to log in or sign up.
  - Sign Up: Create a new account with a username (3-15 alphanumeric characters) and a password (at least 6 characters, including one letter and one number).
  - Log In: Use the username and password you created to log in.
  - After logging in, you will be brought to the MainPage, which provides navigation to various features like Calendar, Leaderboard, To-do List, Courses, Friends list and Timer.

## To-do List Feature. 
The To-do List feature allows users to manage their tasks efficiently. Below are the functionalities provided by the To-do List:
- **Adding and Removing Tasks**: Users can add new tasks to their To-do list. Each task can have a title, description, deadline, and course code associated to it. Users can also delete tasks from their To-do list.
- **Completing Task**: Users can mark tasks as completed once they finish them. Completed tasks can be hidden if the user desires.
- **Filtering and Sorting Tasks**: Users can hide completed tasks, as well as sort tasks by title, deadline, or course code.

## Clean Architecture.
As a team, we have tried our very best to make sure that Clean Architecture is followed and enforced by all group members. 
- Packaging by Use Case
  We organized our codebase by use cases to ensure that each feature of the application is encapsulated within its own package. This approach helps in maintaining a clear separation of concerns and makes the   codebase more modular and easier to understand. Each use case has its own input and output boundaries, as well as input and output data models.
- Dependencies Pointing Inwards
  


