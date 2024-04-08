package com.example.sosgame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void chooseBoardSize5(){
        Board test = new Board();
        test.makeBoard(5, "general");
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                Assertions.assertEquals(0,test.currentBoard[i][j]);
            }
        }
        Assertions.assertEquals("general", test.gameType);
    }

    @Test
    void chooseBoardSize10(){
        Board test = new Board();
        test.makeBoard(10, "simple");
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                Assertions.assertEquals(0,test.currentBoard[i][j]);
            }
        }
        Assertions.assertEquals("simple", test.gameType);
    }

    @Test
    void chooseBoardSize0(){
        Board test = new Board();
        test.makeBoard(0, "simple");
        Assertions.assertEquals(0, test.currentBoard.length);
        Assertions.assertEquals("simple", test.gameType);
    }

    @Test
    void moveS(){
        Board test = new Board();
        test.makeBoard(10, "simple");
        test.makeMove(1, 'S');
        Assertions.assertEquals(1, test.currentBoard[1][0]);
    }

    @Test
    void moveO(){
        Board test = new Board();
        test.makeBoard(10, "simple");
        test.makeMove(1, 'O');
        Assertions.assertEquals(2, test.currentBoard[1][0]);
    }

    @Test
    void moveSinvalid(){
        Board test = new Board();
        test.makeBoard(10, "simple");
        test.makeMove(-1, 'S');
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                Assertions.assertEquals(0,test.currentBoard[i][j]);
            }
        }
    }
}