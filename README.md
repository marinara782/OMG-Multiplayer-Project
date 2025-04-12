## Online Multiplayer Game (OMG)

## SENG 300 - W25 Project

Welcome to our software engineering project for **SENG 300 - Winter 2025**. This application is a multi-game platform 
that supports classic games like Checkers, Connect Four, and Tic-Tac-Toe, with features such as authentication, 
matchmaking, a leaderboard system, networking, and a modern GUI.

### 🧩 Key Features

- **Modular game engine** that allows for easy integration of additional games.
- **User authentication** including login, signup, and password recovery.
- **Matchmaking system** with random and skill-based pairing options.
- **Real-time multiplayer** with smooth communication between client and server. 
- **Leaderboard tracking** for player statistics and performance.
- **Integrated games**:
  - Checkers
  - Connect Four
  - Tic-Tac-Toe
- **JavaFX GUI** with responsive design, sound effects, and animations.
- **JUnit test suite** to ensure code reliability and maintainability.

---

### 🛠 Technologies Used

- **Java 17**
- **JavaFX** for graphical user interface
- **Maven** for build automation
- **JUnit 5** for testing

---

### 📁 Project Structure

#### This is a Tree structure diagram that can be generated with command 'Tree /F /A' or Tree /F in command prompt

```

/seng300-w25-project
│   .gitignore
│   git_log.csv
│   Iteration_3_Submission_Table.xlsx
│   mvnw
│   mvnw.cmd
│   Planing.txt
│   pom.xml
│   Project_v1.0.4.pdf
│   README.md
│   team.md
│   temp.txt
│
├───.idea
│   │   .gitignore
│   │   .name
│   │   compiler.xml
│   │   encodings.xml
│   │   jarRepositories.xml
│   │   misc.xml
│   │   vcs.xml
│   │   workspace.xml
│   │
│   └───inspectionProfiles
│           Project_Default.xml
│
├───.mvn
│   └───wrapper
│           maven-wrapper.jar
│           maven-wrapper.properties
│
├───demo
│   │   mvnw
│   │   mvnw.cmd
│   │   pom.xml
│   │
│   ├───.mvn
│   │   └───wrapper
│   │           maven-wrapper.jar
│   │           maven-wrapper.properties
│   │
│   └───src
│       └───main
│           ├───java
│           │   │   module-info.java
│           │   │
│           │   └───org
│           │       └───example
│           │           └───demo
│           │                   HelloApplication.java
│           │                   HelloController.java
│           │
│           └───resources
│               └───org
│                   └───example
│                       └───demo
│                               hello-view.fxml
│
├───docs
│   ├───diagrams
│   │       AuthenticateUseCaseIteration3.jpg
│   │       AuthenticationClassDiagram.png
│   │       AuthenticationIteration3.jpg
│   │       class_diagram_Checkers.png
│   │       Class_Diagram_connect4.png
│   │       class_diagram_Leaderboard.png
│   │       GUI_Class_Diagram.png
│   │       MatchMaking Class Diagram.png
│   │       NetworkingClassDiagram.png
│   │       NetworkingUseCaseDiagram.png
│   │       TicTacToe_Class_Structure.png
│   │
│   ├───planning
│   │       AUTHENTICATION.pdf
│   │       connect4_planning_document.pdf
│   │       GUI_planning_document.pdf
│   │       leaderboard_planning.pdf
│   │       NETWORKING.pdf
│   │       Planning_Documents_-_Team_5_MATCHMAKING.pdf
│   │       ProjectPlanningCheckers.pdf
│   │       TicTacToe_planning_document.pdf
│   │
│   └───UseCaseDescriptions
│           use_cases_descriptions_matchmaking.pdf
│           use_case_descriptions_authentication.pdf
│           use_case_descriptions_checkers.pdf
│           use_case_descriptions_connect4.pdf
│           use_case_descriptions_gui.pdf
│           use_case_descriptions_networking.pdf
│           use_case_descriptions_tictactoe.pdf
│           use_case_description_leaderboard.pdf
│
├───gitlab
│       gitlab_link.txt
│
├───out
│   └───production
│       └───seng300-w25-project
│           └───main
│               └───resources
│                   └───org
│                       └───example
│                           └───gui
│                                   hello-view.fxml
│
├───resources
│   ├───icons
│   │       newIcons
│   │
│   ├───images
│   │       newImages
│   │
│   └───sounds
│           checkersRedMove.mp3
│           connectFourBlue.mp3
│           connectFourRed.mp3
│           ticTacToe1.mp3
│           ticTacToeO.mp3
│           ticTacToeX.mp3
│           win.mp3
│
├───src
│   ├───main
│   │   ├───java
│   │   │   │   module-info.java
│   │   │   │
│   │   │   └───org
│   │   │       └───example
│   │   │           │   Player.java
│   │   │           │
│   │   │           ├───authentication
│   │   │           │       Login.java
│   │   │           │       StatusOptions.java
│   │   │           │       temp.txt
│   │   │           │       User.java
│   │   │           │       UserDatabaseStub.java
│   │   │           │       UserProfile.java
│   │   │           │
│   │   │           ├───game
│   │   │           │   ├───checkers
│   │   │           │   │       CheckersBoard.java
│   │   │           │   │       CheckersGame.java
│   │   │           │   │       CheckersRules.java
│   │   │           │   │
│   │   │           │   ├───connectFour
│   │   │           │   │       ConnectFourBoard.java
│   │   │           │   │       ConnectFourGame.java
│   │   │           │   │       ConnectFourRules.java
│   │   │           │   │
│   │   │           │   └───ticTacToe
│   │   │           │           TicTacToeGame.java
│   │   │           │           TicTacToeRules.java
│   │   │           │
│   │   │           ├───gui
│   │   │           │       ForgetPasswordWindowController.java
│   │   │           │       GameWindow.java
│   │   │           │       HelloApplication.java
│   │   │           │       LeaderBoardController.java
│   │   │           │       LoginWindowController.java
│   │   │           │       MainMenuWindow.java
│   │   │           │       SceneManager.java
│   │   │           │       SignUpWindowController.java
│   │   │           │       UserProfileWindow.java
│   │   │           │       UserProfileWindowController.java
│   │   │           │
│   │   │           ├───leaderboard
│   │   │           │       Leaderboard.java
│   │   │           │       LeaderboardDatabaseStub.java
│   │   │           │
│   │   │           ├───matchmaking
│   │   │           │       Matchmaker.java
│   │   │           │       SkillBasedMatchmaking.java
│   │   │           │
│   │   │           ├───networking
│   │   │           │       bugReport.java
│   │   │           │       Client.java
│   │   │           │       GameSession.java
│   │   │           │       Server.java
│   │   │           │
│   │   │           └───utilities
│   │   │                   ChatManager.java
│   │   │                   GameTimer.java
│   │   │
│   │   └───resources
│   │       │   CheckersIMG.jpg
│   │       │   Connect4IMG.jpg
│   │       │   TicTacToeIMG.jpg
│   │       │
│   │       └───org
│   │           └───example
│   │               └───gui
│   │                   │   ForgetPasswordWindow.fxml
│   │                   │   leaderboardTable.css
│   │                   │   LeaderboardWindow.fxml
│   │                   │   LoginWindow.fxml
│   │                   │   LoginWindow2.fxml
│   │                   │   SignUpWindow.fxml
│   │                   │
│   │                   ├───fxml
│   │                   │       checkers_rules.fxml
│   │                   │       connectfour_rules.fxml
│   │                   │       tictactoe_rules.fxml
│   │                   │       userProfileWindow.fxml
│   │                   │
│   │                   └───styles
│   │                           checkers.css
│   │                           connectfour.css
│   │                           tictactoe.css
│   │                           userprofile.css
│   │
│   └───test
│       └───java
│           └───org
│               └───example
│                   │   PlayerTest.java
│                   │
│                   ├───authentication
│                   │       LoginTest.java
│                   │       UserDatabaseStubTest.java
│                   │       UserProfileTest.java
│                   │       UserTest.java
│                   │
│                   ├───game
│                   │   ├───checkers
│                   │   │       CheckersBoardTest.java
│                   │   │       CheckersGameTest.java
│                   │   │
│                   │   ├───connectFour
│                   │   │       ConnectFourBoardTest.java
│                   │   │       ConnectFourGameTest.java
│                   │   │
│                   │   └───ticTacToe
│                   │           TicTacToeGameTest.java
│                   │
│                   ├───leaderboard
│                   │       LeaderboardDatabaseStubTest.java
│                   │       LeaderboardTest.java
│                   │
│                   ├───matchmaking
│                   │       MatchmakerTest.java
│                   │       SkillBasedMatchMakingTest.java
│                   │
│                   ├───networking
│                   │       ClientTest.java
│                   │       GameSessionTest.java
│                   │       ServerTest.java
│                   │
│                   └───utilities
│                           ChatManagerTest.java
│                           GameTimerTest.java
│
└───target
    ├───classes
    │   │   CheckersIMG.jpg
    │   │   Connect4IMG.jpg
    │   │   module-info.class
    │   │   TicTacToeIMG.jpg
    │   │
    │   └───org
    │       └───example
    │           │   Player.class
    │           │
    │           ├───authentication
    │           │       Login.class
    │           │       StatusOptions.class
    │           │       User.class
    │           │       UserDatabaseStub.class
    │           │       UserProfile.class
    │           │
    │           ├───game
    │           │   ├───checkers
    │           │   │       CheckersBoard.class
    │           │   │       CheckersGame$Move.class
    │           │   │       CheckersGame.class
    │           │   │       CheckersRules.class
    │           │   │
    │           │   ├───connectFour
    │           │   │       CheckersRules.class
    │           │   │       ConnectFourBoard.class
    │           │   │       ConnectFourGame.class
    │           │   │       ConnectFourRules.class
    │           │   │
    │           │   └───ticTacToe
    │           │           TicTacToeGame.class
    │           │           TicTacToeRules.class
    │           │
    │           ├───gui
    │           │   │   ForgetPasswordWindow.fxml
    │           │   │   ForgetPasswordWindowController.class
    │           │   │   GameWindow.class
    │           │   │   hello-view.fxml
    │           │   │   HelloApplication.class
    │           │   │   HelloController.class
    │           │   │   LeaderBoardController$1.class
    │           │   │   LeaderBoardController$2.class
    │           │   │   LeaderBoardController$3.class
    │           │   │   LeaderBoardController.class
    │           │   │   leaderboardTable.css
    │           │   │   LeaderboardWindow.fxml
    │           │   │   LoginWindow.fxml
    │           │   │   LoginWindow2.fxml
    │           │   │   LoginWindowController.class
    │           │   │   MainMenuWindow.class
    │           │   │   SceneManager.class
    │           │   │   SignUpWindow.fxml
    │           │   │   SignUpWindowController.class
    │           │   │   UserProfileWindow.class
    │           │   │   UserProfileWindowController$GameRecord.class
    │           │   │   UserProfileWindowController.class
    │           │   │
    │           │   ├───fxml
    │           │   │       checkers_rules.fxml
    │           │   │       connectfour_rules.fxml
    │           │   │       tictactoe_rules.fxml
    │           │   │       userProfileWindow.fxml
    │           │   │
    │           │   └───styles
    │           │           checkers.css
    │           │           connectfour.css
    │           │           tictactoe.css
    │           │           userprofile.css
    │           │
    │           ├───leaderboard
    │           │       Leaderboard.class
    │           │       LeaderboardDatabaseStub.class
    │           │
    │           ├───matchmaking
    │           │       Matchmaker$GameType.class
    │           │       Matchmaker.class
    │           │       SkillBasedMatchmaking$QueuedPlayer.class
    │           │       SkillBasedMatchmaking.class
    │           │
    │           ├───networking
    │           │       Client.class
    │           │       GameSession.class
    │           │       Server$1.class
    │           │       Server.class
    │           │
    │           └───utilities
    │                   ChatManager$CheckersBot.class
    │                   ChatManager$ConnectFourBot.class
    │                   ChatManager$TicTacToeBot.class
    │                   ChatManager.class
    │                   GameTimer.class
    │
    ├───generated-sources
    │   └───annotations
    ├───generated-test-sources
    │   └───test-annotations
    └───test-classes
        └───org
            └───example
                ├───authentication
                │       LoginTest.class
                │       UserDatabaseStubTest.class
                │       UserProfileTest.class
                │
                ├───game
                │   ├───checkers
                │   │       CheckersBoardTest.class
                │   │       CheckersGameTest.class
                │   │
                │   ├───connectFour
                │   │       ConnectFourBoardTest.class
                │   │       ConnectFourGameTest.class
                │   │
                │   └───ticTacToe
                │           TicTacToeBoardTest.class
                │           TicTacToeGameTest.class
                │
                ├───gui
                │       ForgetPasswordWindowControllerTest.class
                │       GameWindowTest.class
                │       HelloApplicationTest.class
                │       HelloControllerTest.class
                │       LeaderBoardControllerTest.class
                │       LoginWindowControllerTest$FakeUserDatabase.class
                │       LoginWindowControllerTest.class
                │       MainMenuWindowTest.class
                │       SceneManagerTest.class
                │       SignUpWindowControllerTest.class
                │       UserProfileWindowTest.class
                │
                ├───leaderboard
                │       LeaderboardDatabaseStub.class
                │       LeaderboardTest.class
                │
                ├───matchmaking
                │       MatchmakerTest.class
                │       SkillBasedMatchMaking.class
                │
                ├───networking
                │       ClientTest.class
                │       GameSessionTest.class
                │       ServerTest.class
                │
                └───utilities
                        ChatManagerTest.class
                        GameTimerTest.class

```

### 📄 Documentation

All relevant design documents, reports, and diagrams can be found in the `docs/` directory. This includes:

- System architecture diagrams
- Use case and sequence diagrams
- Milestone reports (`Project_v1.0.4.pdf`, etc.)

---

### 👥 Authors

Team P-16 – Winter 2025 (23 people) 
University of Calgary  
*Full contributor list available in the docs/report.*

---

### **Branching Strategy**

Each team works on an **origin branch** before merging into `main`.

#### 🔀 Branch Naming Convention & Iteration Overview

To maintain an organized development process throughout the lifecycle of this project, we followed a branching strategy structured by iterations. Each branch was named according to the feature or module being developed, and our workflow included collaborative design, documentation, and regular team meetings.

#### 🧩 Iteration 1 (Feb 22 – Mar 9): Feature Development & Architecture
During this phase, development was divided into modular branches,
with team members working in parallel on core game features, UI, networking, and foundational components.
We also prepared key documentation including using case diagrams, class diagrams, and a planning document.

- `NETWORKING` – Multiplayer connection and data transmission logic
- `LEADERBOARD` – Player score tracking and leaderboard system
- `GUI` – Frontend development using JavaFX
- `AUTHENTICATION` – User login/signup and credential verification
- `TICTACTOE` – Game logic for Tic-Tac-Toe
- `MATCHMAKING` – Pairing players for multiplayer sessions
- `<individual_class_name>` – Branches dedicated to each team member’s class and responsibilities

🔧 Documentation and Collaboration:
- Use Case Diagrams & Descriptions
- Class Diagram
- Planning Document
- Milestone Tracking
- Issue Boards (GitHub Projects)
- Team Meetings (Twice Weekly)
- Git Logs for Version Control

#### 🔍 Iteration 2 (Mar 9 – Mar 21): Review & Consolidation
This iteration focused on reviewing peer contributions, refining implementation details,
and documenting progress through shared Google Docs.
No new branches were created during this period — collaboration was done directly in shared documents,
allowing the team to align and make improvements based on feedback.

---

### 🔁 Iteration 3 (Mar 22 – Apr 11)

This iteration the team focused
on implementing feedback received from peer reviews in Iteration 2. This phase was centered
on refining existing modules,
enhancing usability, improving performance, and completing final integration tasks across components.

### 🔍 Key Activities for Iteration 3

- Integrated comprehensive feedback received from peer reviews during Iteration 2 to improve system quality and usability.
- Refined core modules, including **Game Logic**, **Graphical User Interface (GUI)**, **Leaderboard**, and **Matchmaking**, enhancing functionality and overall performance.
- Conducted thorough testing and debugging to ensure system stability and address edge cases across all components.
- Updated all relevant project documentation, including:
  - **Use Case Diagrams**
  - **Class Diagrams**
  - **Architecture Diagrams**
- Produced a final **project explanation video** that outlines the system architecture, design rationale, and core features.
- Maintained a consistent communication schedule through **bi-weekly team meetings**, ensuring smooth collaboration and alignment throughout the iteration.

### 🧑‍💻 Team Members in Iteration 3

**Authentication Team**
- Daman Sodhi (30233872)
- Jayden Wong (30204632)
- Syed Raza Zaidi (30075408)
- Syed Wasef Daiyan (30205727)
- Vihandu Appuhamy (30177972)

**Game Logic Team**
- Abdu Rahmann Ben Issa (30171465)
- Ahmed Azmaine Alvee (30189598)
- Bereket Ware (30180248)
- Hamza Ata (30227411)
- Jacob Baggot (30232092)
- Khan Muhammad Umer (30210150)
- Shazil Khan (30241942)

**GUI Team**
- Bhuvan Gudimetla (30225017)
- Jose Figueredo (30185529)
- Puneet Dhawan (30205304)

**Leaderboard & Matchmaking Team**
- Dilraj Deol (30229680)
- Jerahmeel Ual Alcano (30153076)
- Marena Matavao (30237507)
- Surkhab Mundi (30189028)

**Networking Team**
- Eloisa Castillo (30208515)
- Hassaan Durrani (30213840)
- Sara Moon (30175244)
- Youssif Abdelaziz (30208726)

---

#### 🌱 Branches Created:
- `Gamelogic_iteration3`
- `GUI_iteration3`
- `Iteration3`
- `LeaderandMatch_iteration3`

These branches reflect the modular refinement efforts by various subgroups within the team. Although the exact team size per branch varies, the whole class collaborated across modules during this final implementation phase.

> Note: Not all contributions during Iteration 3 were divided evenly among members, but each team member contributed to code refinement, debugging, and final documentation.

---

### **Repository Workflow**

#### **Step 1: Sync Feature Branch with `main`**

A designated team member ensures the feature branch is **updated with `main`**:

```bash 
git checkout origin/authentication
git pull origin main 
git push origin origin/authentication  
```

Now, `origin/authentication` is **in sync** with `main`.

#### **Step 2: Developers Pull Updates & Work on origin Branch**

All team members working on `origin/authentication` must **fetch and pull** the latest changes before making new commits:

```bash
git checkout origin/authentication
git fetch origin  
git pull origin origin/authentication  
```

Now, developers have the latest version before continuing their work.

#### **Step 3: Developers Make Changes & Push**

After making changes, they **commit and push** to the origin branch:

```bash
git add .
git commit -m "Describe your changes"
git push origin origin/authentication 
```

#### **Step 4: Merge origin Branch into `main`**

Once the origin is **stable and tested**, the team merges it into `main`:

```bash
git checkout main
git pull origin main 
git merge origin/authentication 
git push origin main 
```

---

### **Responsibilities**

**origin Team Members** → Commit and push only to their **origin branch**  
**origin Team Lead** → Ensures the **origin branch** is synced with `main` before team members push  
**Merge to `main` Only When Stable** → No direct commits to `main`

---


### 📜 License

This project is developed for academic purposes and is not licensed for commercial distribution.

---