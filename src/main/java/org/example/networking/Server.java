package org.example.networking;

public class Server {
    public String processRequest(String request){
        switch (request){
            case "LOGIN";
                return "Login Successful";
            break;
            case "CONNECT"
                return "Connection Successful";
        }
    }
}
