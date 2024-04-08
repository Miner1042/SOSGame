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
        if((buttonIndex > -1 && buttonIndex < (boardSize * boardSize))) {
            int x = buttonIndex % boardSize;
            int y = buttonIndex / boardSize;

            if(currentBoard[x][y] == 0){
                if (symbol == 'S') {
                    boardVal = 1;
                    currentBoard[x][y] = boardVal;
                    //System.out.println(x + " " + y);
                } else {
                    boardVal = 2;
                    currentBoard[x][y] = boardVal;
                    //System.out.println(x + " " + y);
                }
            }

            //increment turn counter
            turnCount += 1;
        }else{
            System.out.println("Invalid space attempted");
        }
    }

    public boolean pointChecker(int buttonIndex, char letter){
        int score = 0;
        if(buttonIndex > -1 && buttonIndex < (boardSize * boardSize)) {
            int x = buttonIndex % boardSize;
            int y = buttonIndex / boardSize;

            //check which check should be provided
            if(letter == 'S'){
                //checks all 8 angles around the S
                //checks west
                if(validIndex(x-1, y) && currentBoard[x-1][y] == 2){
                    if(validIndex(x-2, y) && currentBoard[x-2][y] == 1){
                        score++;
                    }
                }
                //checks east
                if(validIndex(x+1, y) && currentBoard[x+1][y] == 2){
                    if(validIndex(x+2, y) && currentBoard[x+2][y] == 1){
                        score++;
                    }
                }
                //checks north
                if(validIndex(x, y-1) && currentBoard[x][y-1] == 2){
                    if(validIndex(x, y-2) && currentBoard[x][y-2] == 1){
                        score++;
                    }
                }
                //checks south
                if(validIndex(x, y+1) && currentBoard[x][y+1] == 2){
                    if(validIndex(x, y+2) && currentBoard[x][y+2] == 1){
                        score++;
                    }
                }
                //checks northwest
                if(validIndex(x-1, y-1) && currentBoard[x-1][y-1] == 2){
                    if(validIndex(x-2, y-2) && currentBoard[x-2][y-2] == 1){
                        score++;
                    }
                }
                //checks northeast
                if(validIndex(x+1, y-1) && currentBoard[x+1][y-1] == 2){
                    if(validIndex(x+2, y-2) && currentBoard[x+2][y-2] == 1){
                        score++;
                    }
                }
                //checks southwest
                if(validIndex(x-1, y+1) && currentBoard[x-1][y+1] == 2){
                    if(validIndex(x-2, y+2) && currentBoard[x-2][y+2] == 1){
                        score++;
                    }
                }
                //checks southeast
                if(validIndex(x+1, y+1) && currentBoard[x+1][y+1] == 2){
                    if(validIndex(x+2, y+2) && currentBoard[x+2][y+2] == 1){
                        score++;
                    }
                }
            }else{
                //checks around the O
                //if you think about the current position as the center of a circle and north as 1, that'll help make sense of what i say next
                // 8 1 2
                // 7 U 3
                // 6 5 4
                //checking 7 and 3
                if(validIndex(x-1, y) && validIndex(x+1, y) && currentBoard[x-1][y] == 1 && currentBoard[x+1][y] == 1){
                    score++;
                }
                //checking 8 and 4
                if(validIndex(x-1, y-1) && validIndex(x+1, y+1) && currentBoard[x-1][y-1] == 1 && currentBoard[x+1][y+1] == 1){
                    score++;
                }
                //checking 1 and 5
                if(validIndex(x, y-1) && validIndex(x, y+1) && currentBoard[x][y-1] == 1 && currentBoard[x][y+1] == 1){
                    score++;
                }
                //checking 2 and 6
                if(validIndex(x+1, y-1) && validIndex(x-1, y+1) && currentBoard[x+1][y-1] == 1 && currentBoard[x-1][y+1] == 1){
                    score++;
                }
            }
            if(score > 0){
                if(player1){
                    p1score += score;
                }else{
                    p2score += score;
                }
                return true;
            }
        }
        return false;
    }

    private boolean validIndex(int xpos, int ypos){
        if(xpos < 0 || xpos >= boardSize) {
            return false;
        }else if(ypos < 0 || ypos >= boardSize){
            return false;
        }
        return true;
    }

    public boolean endGame(){
        if(gameType.equals("Simple Game")){
            //simple game end
            //System.out.println("End simple game");
            return (p1score > 0 || p2score > 0) || (turnCount == (boardSize * boardSize));
        }else {
            //general game end
            if (turnCount == (boardSize * boardSize)) {
                System.out.println("End general game");
                return true;
            } else {
                return false;
            }
        }
    }

    public int findWinnerS(){
        int x, y;
        for(int i = 0; i < boardSize*boardSize; i++){
            x = i % boardSize;
            y = i / boardSize;
            //check the S options
            if(validIndex(x-1, y) && currentBoard[x-1][y] == 2){
                if(validIndex(x-2, y) && currentBoard[x-2][y] == 1){
                    if(currentBoard[x][y] == 0){
                        return i;
                    }
                }
            }
            //checks east
            if(validIndex(x+1, y) && currentBoard[x+1][y] == 2){
                if(validIndex(x+2, y) && currentBoard[x+2][y] == 1){
                    if(currentBoard[x][y] == 0){
                        return i;
                    }
                }
            }
            //checks north
            if(validIndex(x, y-1) && currentBoard[x][y-1] == 2){
                if(validIndex(x, y-2) && currentBoard[x][y-2] == 1){
                    if(currentBoard[x][y] == 0){
                        return i;
                    }
                }
            }
            //checks south
            if(validIndex(x, y+1) && currentBoard[x][y+1] == 2){
                if(validIndex(x, y+2) && currentBoard[x][y+2] == 1){
                    if(currentBoard[x][y] == 0){
                        return i;
                    }
                }
            }
            //checks northwest
            if(validIndex(x-1, y-1) && currentBoard[x-1][y-1] == 2){
                if(validIndex(x-2, y-2) && currentBoard[x-2][y-2] == 1){
                    if(currentBoard[x][y] == 0){
                        return i;
                    }
                }
            }
            //checks northeast
            if(validIndex(x+1, y-1) && currentBoard[x+1][y-1] == 2){
                if(validIndex(x+2, y-2) && currentBoard[x+2][y-2] == 1){
                    if(currentBoard[x][y] == 0){
                        return i;
                    }
                }
            }
            //checks southwest
            if(validIndex(x-1, y+1) && currentBoard[x-1][y+1] == 2){
                if(validIndex(x-2, y+2) && currentBoard[x-2][y+2] == 1){
                    if(currentBoard[x][y] == 0){
                        return i;
                    }
                }
            }
            //checks southeast
            if(validIndex(x+1, y+1) && currentBoard[x+1][y+1] == 2){
                if(validIndex(x+2, y+2) && currentBoard[x+2][y+2] == 1){
                    if(currentBoard[x][y] == 0){
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public int findWinnerO(){
        int x, y;
        for(int i = 0; i < boardSize*boardSize; i++){
            x = i % boardSize;
            y = i / boardSize;
            //check the O options
            //checks around the O
            //if you think about the current position as the center of a circle and north as 1, that'll help make sense of what i say next
            // 8 1 2
            // 7 U 3
            // 6 5 4
            //checking 7 and 3
            if(validIndex(x-1, y) && validIndex(x+1, y) && currentBoard[x-1][y] == 1 && currentBoard[x+1][y] == 1){
                if(currentBoard[x][y] == 0){
                    return i;
                }
            }
            //checking 8 and 4
            if(validIndex(x-1, y-1) && validIndex(x+1, y+1) && currentBoard[x-1][y-1] == 1 && currentBoard[x+1][y+1] == 1){
                if(currentBoard[x][y] == 0){
                    return i;
                }
            }
            //checking 1 and 5
            if(validIndex(x, y-1) && validIndex(x, y+1) && currentBoard[x][y-1] == 1 && currentBoard[x][y+1] == 1){
                if(currentBoard[x][y] == 0){
                    return i;
                }
            }
            //checking 2 and 6
            if(validIndex(x+1, y-1) && validIndex(x-1, y+1) && currentBoard[x+1][y-1] == 1 && currentBoard[x-1][y+1] == 1){
                if(currentBoard[x][y] == 0){
                    return i;
                }
            }
        }
        return -1;
    }
}
