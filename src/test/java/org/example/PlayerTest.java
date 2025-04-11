package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("testUser");
    }

    @Test
    void updateTotalWins() {
        player.setCheckerWins(2);
        player.setTictactoeWins(3);
        player.setConnect4Wins(1);
        player.updateTotalWins();
        assertEquals(6, player.getTotalWins());
    }

    @Test
    void updateTotalLosses() {
        player.setCheckerLosses(2);
        player.setTictactoeLosses(1);
        player.setConnect4Losses(2);
        player.updateTotalLosses();
        assertEquals(5, player.getTotalLosses());
    }

    @Test
    void updateTotalDraws() {
        player.setTictactoeDraws(1);
        player.setConnect4Draws(3);
        player.updateTotalDraws();
        assertEquals(4, player.getTotalDraws());
    }

    @Test
    void updateWinPercentage() {
        player.setCheckerWins(2);
        player.setCheckerLosses(2);
        player.setTictactoeWins(1);
        player.setConnect4Wins(1);
        player.updateWinPercentage();
        assertEquals(4.0/6.0, player.getWinPercentage());
    }

    @Test
    void setCheckerWins() {
        player.setCheckerWins(5);
        assertEquals(5, player.getCheckerWins());
    }

    @Test
    void setCheckerLosses() {
        player.setCheckerLosses(2);
        assertEquals(2, player.getCheckerLosses());
    }

    @Test
    void setTictactoeWins() {
        player.setTictactoeWins(4);
        assertEquals(4, player.getTictactoeWins());
    }

    @Test
    void setTictactoeLosses() {
        player.setTictactoeLosses(3);
        assertEquals(3, player.getTictactoeLosses());
    }

    @Test
    void setTictactoeDraws() {
        player.setTictactoeDraws(2);
        assertEquals(2, player.getTictactoeDraws());
    }

    @Test
    void setConnect4Wins() {
        player.setConnect4Wins(6);
        assertEquals(6, player.getConnect4Wins());
    }

    @Test
    void setConnect4Losses() {
        player.setConnect4Losses(1);
        assertEquals(1, player.getConnect4Losses());
    }

    @Test
    void setConnect4Draws() {
        player.setConnect4Draws(3);
        assertEquals(3, player.getConnect4Draws());
    }

    @Test
    void getUsername() {
        assertEquals("testUser", player.getUsername());
    }

    @Test
    void setUsername() {
        player.setUsername("newUser");
        assertEquals("newUser", player.getUsername());
    }

    @Test
    void getCheckerWins() {
        player.setCheckerWins(7);
        assertEquals(7, player.getCheckerWins());
    }

    @Test
    void getCheckerLosses() {
        player.setCheckerLosses(4);
        assertEquals(4, player.getCheckerLosses());
    }

    @Test
    void getCheckersWinPercentage() {
        player.setCheckerWins(3);
        player.setCheckerLosses(2);
        assertEquals(3.0/5.0, player.getCheckersWinPercentage());
    }

    @Test
    void getTictactoeWins() {
        player.setTictactoeWins(2);
        assertEquals(2, player.getTictactoeWins());
    }

    @Test
    void getTictactoeLosses() {
        player.setTictactoeLosses(1);
        assertEquals(1, player.getTictactoeLosses());
    }

    @Test
    void getTictactoeDraws() {
        player.setTictactoeDraws(5);
        assertEquals(5, player.getTictactoeDraws());
    }

    @Test
    void getTicTacToeWinPercentage() {
        player.setTictactoeWins(4);
        player.setTictactoeLosses(2);
        assertEquals(4.0/6.0, player.getTicTacToeWinPercentage());
    }

    @Test
    void getConnect4Wins() {
        player.setConnect4Wins(2);
        assertEquals(2, player.getConnect4Wins());
    }

    @Test
    void getConnect4Losses() {
        player.setConnect4Losses(3);
        assertEquals(3, player.getConnect4Losses());
    }

    @Test
    void getConnect4Draws() {
        player.setConnect4Draws(1);
        assertEquals(1, player.getConnect4Draws());
    }

    @Test
    void getConnect4WinPercentage() {
        player.setConnect4Wins(3);
        player.setConnect4Losses(1);
        assertEquals(3.0/4.0, player.getConnect4WinPercentage());
    }

    @Test
    void getTotalWins() {
        player.setCheckerWins(1);
        player.setConnect4Wins(3);
        player.setTictactoeWins(2);
        assertEquals(6, player.getTotalWins());
    }

    @Test
    void getTotalLosses() {
        player.setCheckerLosses(1);
        player.setTictactoeLosses(1);
        player.setConnect4Losses(2);
        assertEquals(4, player.getTotalLosses());
    }

    @Test
    void getWinPercentage() {
        player.setCheckerWins(1);
        player.setTictactoeWins(1);
        player.setConnect4Wins(1);
        player.setCheckerLosses(1);
        player.setTictactoeLosses(1);
        player.setConnect4Losses(0);
        player.setTictactoeDraws(1);
        player.setConnect4Draws(1);
        assertEquals(3.0 / 7.0, player.getWinPercentage());
    }

    @Test
    void getTotalDraws() {
        player.setTictactoeDraws(3);
        player.setConnect4Draws(2);
        assertEquals(5, player.getTotalDraws());
    }

    @Test
    void updateCheckerWins() {
        player.updateCheckerWins();
        assertEquals(1, player.getCheckerWins());
    }

    @Test
    void updateCheckerLosses() {
        player.updateCheckerLosses();
        assertEquals(1, player.getCheckerLosses());
    }

    @Test
    void updateTicTacToeWins() {
        player.updateTicTacToeWins();
        assertEquals(1, player.getTictactoeWins());
    }

    @Test
    void updateTictactoeLosses() {
        player.updateTictactoeLosses();
        assertEquals(1, player.getTictactoeLosses());
    }

    @Test
    void updateTictactoeDraws() {
        player.updateTictactoeDraws();
        assertEquals(1, player.getTictactoeDraws());
    }

    @Test
    void updateConnect4Wins() {
        player.updateConnect4Wins();
        assertEquals(1, player.getConnect4Wins());
    }

    @Test
    void updateConnect4Losses() {
        player.updateConnect4Losses();
        assertEquals(1, player.getConnect4Losses());
    }

    @Test
    void updateConnect4Draws() {
        player.updateConnect4Draws();
        assertEquals(1, player.getConnect4Draws());
    }
}
