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