package org.example.networking;
public class Client {
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
