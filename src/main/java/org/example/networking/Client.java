package org.example.networking;

import org.example.authentication.UserProfile;

public class Client {
    //direct access to these is restricted (they are private) for encapsulation
    //instead we provide controlled access through getters and setters
    private String serverAddress;
    private boolean isConnected = false;
    private UserProfile userProfile;  // user information associated with the client

    // Default constructor initializing connection state
    public Client() {
        this.isConnected = false;
    }

    /**
     * This method simulates a connection to a server. It takes an address and port as parameters.
     *
     * @param address String address of the server to connect to
     * @param port    int port of the server to connect to
     * @return true when connected successfully, false if already connected
     */
    public boolean connect(String address, int port) {
        System.out.println("Attempting to connect to server at " + address + " on port " + port + "...");
        if (!isConnected) {
            //sleep the program for 1 second to simulate a real connection attempt
            //Thread.sleep is in a try/catch block because although an interruption in the fake connection attempt is unlikely, it's good practice in case the thread running Thread.sleep is interrupted by another thread
            try {
                Thread.sleep(1000); // simulate network delay
            } catch (InterruptedException e) {
                System.out.println("Connection attempt was interrupted!");
            }
            this.isConnected = true;
            this.serverAddress = address;  // set the connected server address
            System.out.println("Connected to server successfully!");
            return true;
        } else {
            System.out.println("Already connected!");
            return false;
        }
    }

    // Simulates disconnection from the server
    public void disconnect() {
        System.out.println("Disconnecting from server...");
        if (!isConnected) {
            System.out.println("No connection to disconnect.");
        } else {
            this.isConnected = false;
            System.out.println("Disconnected successfully.");
        }
    }

    // Simulates login request with username and password
    public void login(String username, String password) {
        System.out.println("Attempt to Login...");
        String response_login = Server.processRequest("LOGIN", username, password);
        if (response_login.equals("Successful Login")) {
            System.out.println(response_login);
        } else {
            System.out.println(response_login);
        }
    }

    // Attempts to create a new game session for a given game type
    public void createGame(String game) {
        GameSession session = Server.createGameSession(game);
        if (session != null) {
            session.addPlayer(this);  // add this client to the new session
        }
    }

    // Attempts to join an existing game session
    public void joinGame(GameSession gameSession) {
        boolean game_joined = gameSession.addPlayer(this);
        if (!game_joined) {
            System.out.println("Game Session Not Joined");
        }
    }

    // Sends a move to the game session and simulates a broadcast
    public void sendGameMove(GameSession gameSession, String moveData) {
        String response_move = gameSession.processMove(this, moveData);
        System.out.println(response_move);
        System.out.println("[Simulated Server] " + moveData);
        gameSession.broadcastGameState();
    }

    // Sends a bug report with specified type and user comment
    public void sendBugReport(String type, String comment) {
        bugReport report = new bugReport(type, comment);  // create new bug report
        String response = Server.processBugReport(report);  // send to server stub
        System.out.println(response); // "Bug report received. Thank you!"
    }

    // Notifies game session that game is complete
    public void completeGame(GameSession gameSession) {
        gameSession.completeSession();
    }

    // Sends a chat message in a game session
    public void sendChat(GameSession gameSession, String chatMessage) {
        gameSession.sendChat(chatMessage);
    }

    // Simulated sequence of server communications for testing or demo purposes
    public void CommunicateWithServer() {
        System.out.println("Sending login request...");
        String response = Server.processRequest("LOGIN");
        System.out.println(response);

        System.out.println("Creating game session...");
        response = Server.processRequest("CGS");
        System.out.println(response);

        System.out.println("Joining game session...");
        response = Server.processRequest("JOIN");
        System.out.println(response);

        System.out.println("Sending move...");
        response = Server.processRequest("MOVE");
        System.out.println(response);

        System.out.println("Sending chat message...");
        response = Server.processRequest("CHAT");
        System.out.println(response);

        System.out.println("Sending game complete...");
        response = Server.processRequest("COMPLETE");
        System.out.println(response);
    }
}