package com.example.sosgame;

public class Board {
    int[][] currentBoard;

    int gameState = 0; //0: ongoing, 1: tied, 2: p1 win, 3: p2 win
    int turnCount = 0;
    int boardSize; //int X int board size
    String gameType; //general or simple

    boolean player1; //true if player 1 turn, false if player2 turn

    int p1score = 0;
    int p2score = 0;

    public boolean getPlayer(){
        return player1;
    }

    public void setPlayer(boolean in){
        player1 = in;
    }

    public void makeBoard(int boardSize, String gameType){
        this.boardSize = boardSize;
        this.gameType = gameType;
        player1 = true;

        currentBoard = new int[boardSize][boardSize];
        for(int y = 0; y < boardSize; y++){
            for(int x = 0; x < boardSize; x++){
                currentBoard[0][0] = 0;
            }
        }
    }

    public void makeMove(int buttonIndex, char symbol){


        int boardVal; //1 represents S, 2 represents O

        //prevents invalid button index
        if(buttonIndex > -1 && buttonIndex < (boardSize * boardSize)) {
            int x = buttonIndex % boardSize;
            int y = buttonIndex / boardSize;

            if (symbol == 'S') {
                boardVal = 1;
                currentBoard[x][y] = boardVal;
            } else {
                boardVal = 2;
                currentBoard[x][y] = boardVal;
            }

            //increment turn counter
            turnCount += 1;
        }else{
            System.out.println("Invalid space attempted");
        }
    }
}
