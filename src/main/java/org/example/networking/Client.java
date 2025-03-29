package org.example.networking;
public class Client {
    private String serverAddress;
    boolean isConnected = false;
    public boolean Connect(String address){
        System.out.println("Attempting to connect to server at "+ address + "...");
        if(!isConnected){
            //sleep the program for 1 second to simulate a real connection attempt
            //Thread.sleep is in a try/catch block because although an interruption in the fake connection attempt is unlikely, it's good practice in case the thread running Thread.sleep is interrupted by another thread
            try{
                Thread.sleep(1000)
            }catch(InterruptedException e){
                System.out.println("Connection attempt was interrupted!")
            }
            isConnected=true;
            System.out.println("Connected to server successfully!");
        }
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

        System.out.println("Sending disconnect request...");
        response = Server.processRequest("DISCONNECT");
        System.out.println(response);

        System.out.println("Sending game complete...");
        response = Server.processRequest("COMPLETE");
        System.out.println(response);






    }
}
