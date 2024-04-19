package com.example.sosgame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SOSGameControllerTest {

    @Test
    void compRandomMoveSWin(){
        Board tempBoard = new Board();
        tempBoard.makeBoard(3, "Simple");
        tempBoard.currentBoard[0][0] = 1;
        tempBoard.currentBoard[2][2] = 1;
        Assertions.assertEquals(4, tempBoard.findWinnerO());
        Assertions.assertEquals(-1, tempBoard.findWinnerS());
    }

    @Test
    void compRandomMoveOWin(){
        Board tempBoard = new Board();
        tempBoard.makeBoard(3, "Simple");
        tempBoard.currentBoard[0][0] = 1;
        tempBoard.currentBoard[1][1] = 2;
        Assertions.assertEquals(-1, tempBoard.findWinnerO());
        Assertions.assertEquals(8, tempBoard.findWinnerS());
    }

    @Test
    void compRandomMoveNoWin(){
        Board tempBoard = new Board();
        tempBoard.makeBoard(3, "Simple");
        Assertions.assertEquals(-1, tempBoard.findWinnerO());
        Assertions.assertEquals(-1, tempBoard.findWinnerS());
    }
}