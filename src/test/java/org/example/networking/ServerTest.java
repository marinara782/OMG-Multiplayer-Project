package org.example.networking;
package org.example.networking;

import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    //instantiate server object
    private Server server;

    //set up-- make a server object
    @BeforeEach
    void SetUp(){
        //before each test, make a new server object
        server = new Server();
    }

    @AfterEach
    void takeDown(){
        //delete file
        File playerFile = new File("players.json");
        if (playerFile.exists()){
            playerFile.delete();
        }
    }

    @Test
    void TestStart(){
        //enter port number 80 as parameter
        server.start(80);
        //create a game session
        GameSession gameSession = server.createGameSession("test");

    }

}
