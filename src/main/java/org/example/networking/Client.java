package org.example.networking;
public class Client {
    public void CommunicateWithServer(){
        System.out.println("Sending login request...");
        String response = server.CommunicateWithServer("LOGIN");
        System.out.println(response);

        System.out.println("Creating game session...");
        String response = server.CommunicateWithServer("CGS");
        System.out.println(response)
    }
}
