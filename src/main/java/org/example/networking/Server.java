package org.example.networking;

public class Server {
    public String processRequest(String request){
        switch (request){
            case "LOGIN";
                return "Login Successful";
            break;
            case "CGS"
                return "New Game Created";
        }
    }
}
