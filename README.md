# **Project Workflow and Branching Strategy**

## **1. Team Structure & Responsibilities**

### **A. Documentation & Planning Team (3 People)**
- **Directory:** `/docs`
- **Responsibilities:**
    - Maintaining project timeline (`milestones_and_task_completion_dates.xlsx`)
    - Updating architecture and design diagrams (`class_diagram.png`, `use_case_descriptions.pdf`)

### **B. Authentication & User Management Team (3 People)**
- **Directory:** `/src/authentication`
- **Responsibilities:**
    - Implementing user authentication (`Login.java`, `UserProfile.java`)
    - Managing user data storage (`UserDatabaseStub.java`)

### **C. Game Development Teams (10 People, 3 Sub-Teams)**
Each sub-team develops a specific game.
- **Directory:** `/src/game/[game_name]`
- **Responsibilities:**

[//]: # (    - **Chess Team &#40;2 People&#41;:** `ChessBoard.java`, `ChessPiece.java`, `ChessGame.java`)

[//]: # (    - **Go Team &#40;2 People&#41;:** `GoBoard.java`, `GoStone.java`, `GoGame.java`)
    - **Tic Tac Toe Team (4 People):** `TicTacToeBoard.java`, `TicTacToeGame.java`
    - **Checkers Team (4 People):** `CheckersBoard.java`, `CheckersGame.java`


### for people who have already completed previous CPSC 231, had the connect four game as ASSIGNMENT -1
### (Those who scored 100, please let us know beforehand) this will help boost up the project progress.

    - **Connect Four Team (2 People):** `ConnectFourBoard.java`, `ConnectFourGame.java`
    

### **D. Leaderboard & Matchmaking Team (3 People)**
- **Directories:** `/src/leaderboard` & `/src/matchmaking`
- **Responsibilities:**
    - Implement ranking and score tracking (`Leaderboard.java`, `LeaderboardDatabaseStub.java`)
    - Develop matchmaking logic (`Matchmaker.java`, `SkillBasedMatchmaking.java`)

### **E. Networking Team (3 People)**
- **Directory:** `/src/networking`
- **Responsibilities:**
    - Implement server-client communication (`Server.java`, `Client.java`, `GameSession.java`)

### **F. GUI & Frontend Team (3 People)**
- **Directory:** `/src/gui`
- **Responsibilities:**
    - Create the user interface (`MainMenuWindow.java`, `GameWindow.java`, `UserProfileWindow.java`)

### **G. Testing & Quality Assurance Team (3 People)**
- **Directory:** `/tests`
- **Responsibilities:**
    - Write unit and integration tests (`gameTests`, `leaderboardTests`, `matchmakingTests`, `guiTests`, `networkingTests`, `integrationTests`)
    - Ensure game functionality and quality assurance

---

## **2. Branching Strategy**
Each team works on a **feature branch** before merging into `main`.

### **Branch Naming Convention:**
- `feature/authentication`
- `feature/game-chess`
- `feature/game-go`
- `feature/game-tic-tac-toe`
- `feature/game-connect-four`
- `feature/game-checkers`
- `feature/leaderboard`
- `feature/matchmaking`
- `feature/networking`
- `feature/gui`
- `feature/tests`

---

## **3. Repository Workflow**

### **Step 1: Sync Feature Branch with `main`**
A designated team member ensures the feature branch is **updated with `main`**:
```bash
git checkout feature/authentication
git pull origin main  # Get the latest updates from main
git push origin feature/authentication  # Push the updated branch
```
Now, `feature/authentication` is **in sync** with `main`.

### **Step 2: Developers Pull Updates & Work on Feature Branch**
All team members working on `feature/authentication` must **fetch and pull** the latest changes before making new commits:
```bash
git checkout feature/authentication
git fetch origin  # Get the latest repository changes
git pull origin feature/authentication  # Sync with the updated feature branch
```
Now, developers have the latest version before continuing their work.

### **Step 3: Developers Make Changes & Push**
After making changes, they **commit and push** to the feature branch:
```bash
git add .
git commit -m "Describe your changes"
git push origin feature/authentication  # Push to the feature branch
```

### **Step 4: Merge Feature Branch into `main`**
Once the feature is **stable and tested**, the team merges it into `main`:
```bash
git checkout main
git pull origin main  # Ensure the latest main updates
git merge feature/authentication  # Merge the feature branch
git push origin main  # Update main branch
```

---

## **4. Responsibilities**
**Feature Team Members** → Commit and push only to their **feature branch**  
**Feature Team Lead** → Ensures the **feature branch** is synced with `main` before team members push  
**Merge to `main` Only When Stable** → No direct commits to `main`

---

# Online Multiplayer Game (OMG)

## Project Overview
This project is a comprehensive **multi-game platform**. It supports multiple board games, allowing players to engage 
in games like Chess, Go, Tic Tac Toe, Connect Four, and Checkers. The platform offers user authentication, matchmaking, 
leaderboards, and a rich graphical user interface (GUI) for a seamless gaming experience.

## Project Structure
### This is a Tree structure diagram which can be generated with command 'Tree /F /A' in command prompt
```

/seng300-w25-project
│
├── /docs
│   ├── /planning
│   │   ├── project_task_timeline.pdf
│   │   ├── milestones_and_task_completion_dates.xlsx
│   ├── /diagrams
│   │   ├── class_diagram.png
│   │   ├── use_case_descriptions.pdf
│   │   ├── other_diagrams.pdf
│
├── /src
│   ├── /authentication
│   │   ├── Login.java
│   │   ├── UserProfile.java
│   │   ├── UserDatabaseStub.java
│   ├── /game
│   │   ├── /chess
│   │   │   ├── ChessBoard.java
│   │   │   ├── ChessPiece.java
│   │   │   ├── ChessGame.java
│   │   ├── /go
│   │   │   ├── GoBoard.java
│   │   │   ├── GoStone.java
│   │   │   ├── GoGame.java
│   │   ├── /ticTacToe
│   │   │   ├── TicTacToeBoard.java
│   │   │   ├── TicTacToeGame.java
│   │   ├── /connectFour
│   │   │   ├── ConnectFourBoard.java
│   │   │   ├── ConnectFourGame.java
│   │   ├── /checkers
│   │   │   ├── CheckersBoard.java
│   │   │   ├── CheckersGame.java
│   ├── /leaderboard
│   │   ├── Leaderboard.java
│   │   ├── LeaderboardDatabaseStub.java
│   ├── /matchmaking
│   │   ├── Matchmaker.java
│   │   ├── SkillBasedMatchmaking.java
│   ├── /networking
│   │   ├── Server.java
│   │   ├── Client.java
│   │   ├── GameSession.java
│   ├── /gui
│   │   ├── /mainMenu
│   │   │   ├── MainMenuWindow.java
│   │   ├── /gameplay
│   │   │   ├── GameWindow.java
│   │   ├── /profile
│   │   │   ├── UserProfileWindow.java
│   ├── /utilities
│   │   ├── GameTimer.java
│   │   ├── ChatManager.java
│
├── /tests
│   ├── /gameTests
│   │   ├── ChessGameTest.java
│   │   ├── GoGameTest.java
│   │   ├── TicTacToeGameTest.java
│   │   ├── ConnectFourGameTest.java
│   │   ├── CheckersGameTest.java
│   ├── /leaderboardTests
│   │   ├── LeaderboardTest.java
│   ├── /matchmakingTests
│   │   ├── MatchmakingTest.java
│   ├── /guiTests
│   │   ├── MainMenuTest.java
│   │   ├── GameWindowTest.java
│   │   ├── UserProfileWindowTest.java
│   ├── /networkingTests
│   │   ├── ServerClientCommunicationTest.java
│   ├── /integrationTests
│   │   ├── FullGameFlowTest.java
│
├── /resources
│   ├── /icons
│   │   ├── chessIcon.png
│   │   ├── goIcon.png
│   │   ├── ticTacToeIcon.png
│   │   ├── connectFourIcon.png
│   │   ├── checkersIcon.png
│   ├── /sounds
│   │   ├── gameStart.mp3
│   │   ├── moveSound.mp3
│   │   ├── winSound.mp3
│   ├── /images
│   │   ├── chessBoard.png
│   │   ├── goBoard.png
│   │   ├── ticTacToeBoard.png
│   │   ├── connectFourBoard.png
│   │   ├── checkersBoard.png
│
├── /gitlab
│   ├── gitlab_link.txt
│
├── team.md
├── README.md
├── .gitignore
├── ProjectTreeStructure.txt
├── pom.xml (or build.gradle)
└── /assets (for any other assets like fonts, additional libraries, etc.)


```