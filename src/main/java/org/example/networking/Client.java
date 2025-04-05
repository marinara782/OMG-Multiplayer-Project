package org.example.networking;
public class Client {
    //direct access to these is restricted (they are private) for encapsulation
    //instead we provide controlled access through getters and setters
    private String serverAddress;
    private boolean isConnected = false;
    private UserProfile userProfile;


    public Client(){
        this.isConnected = false;
    }
    public boolean connect(String address, int port){
        System.out.println("Attempting to connect to server at "+ address +" on port " + port + "...");
        if(!isConnected){
            //sleep the program for 1 second to simulate a real connection attempt
            //Thread.sleep is in a try/catch block because although an interruption in the fake connection attempt is unlikely, it's good practice in case the thread running Thread.sleep is interrupted by another thread
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.out.println("Connection attempt was interrupted!");
            }
            this.isConnected=true;
            this.serverAddress = address;
            System.out.println("Connected to server successfully!");
            return true;
        }else{
            System.out.println("Already connected!");
            return false;
        }
    }

    public void disconnect(){
        System.out.println("Disconnecting from server...");
        if(!isConnected){
            System.out.println("No connection to disconnect.");
        }else{
            this.isConnected = false;
            System.out.println("Disconnected successfully.");
        }
    }

    public void sendGameMove(String moveData){
        //check if move is valid by calling game logic system
        //if move is valid, update game state and send to all players
    }



    //everything below this may be removed later
    public void CommunicateWithServer(){
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
