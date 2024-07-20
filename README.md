# CSC207-Time-Management-Project

- [Features](#Quick-Rundown-of-the-Features)
- [How to Run](#How-to-Run-the-Time-Management-Application)
- [To-do List Feature](#To-do-List-Feature)
- [Leaderboard Feature](#Leaderboard-Feature)
- [Timer Feature](#Timer-Feature)
- [Clean Architecture](#Clean-Architecture)

Welcome to the Time Management Project by Team Lebron James. This project is designed to help users manage their time effectively by providing various features such as task management, scheduling, and timers. The project also includes social features such as a friend system and leaderboards that allow you to compete with others. 

## Quick Rundown of the Features
* To-Do List: Create and manage tasks, mark tasks as complete, and filter or sort tasks.
* Calendar: Display tasks on a calendar based on their deadlines.
* Friends: A user can add and remove friends.
* Leaderboards: Compete on leaderboards based on friends, course, or all users.
* Courses: Enroll in courses and have tasks for those courses added to your To-Do list.
* Timer: Use a countdown timer to manage task completion time.

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

## Leaderboard Feature.
The Leaderboard feature allows users to see a leaderboard for time studied and tasks completed. Users can compete with friends and view leaderboards categorized by friends or other users. The leaderboards can be filtered to show daily, monthly, or all-time progress, providing a comprehensive overview of users' achievements and encouraging healthy competition.

## Timer Feature. 
The Timer feature allows users to manage their task completion time efficiently. Users can set a timer for a specific duration to complete their tasks. When the timer finishes, a sound chosen by the user plays. This feature helps users stay focused and complete their tasks within the set time, enhancing productivity.

## Clean Architecture.
As a team, we have tried our very best to make sure that Clean Architecture is followed and enforced by all group members. 
- Packaging by Use Case
   We organized our codebase by use cases to ensure that each feature of the application is encapsulated within its own package. This approach helps in maintaining a clear separation of concerns and makes the   codebase more modular and easier to understand. Each use case has its own input and output boundaries, as well as input and output data models.
- Dependencies Pointing Inwards
   We ensured that our dependencies point inwards, following the Dependency Rule. High-level modules do not depend on low-level modules; both depend on abstractions. We have separated our code into layers, including entities, use cases, interface adapters, and frameworks/drivers.
- User Signup and Login
Implemented with a focus on security and data integrity. Passwords are confirmed during signup, and successful login transitions users to the main navigation page, where each user should have their own personal instance of some features, whilst having access to common grounds like the leaderboard and course features.
- Future Enhancements
We aim to support multiple users, enhance data persistence with MongoDB, and integrate Auth0 for authentication.


