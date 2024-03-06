package com.example.sosgame;

public class Storage {

    private static final Storage instance = new Storage();
    private int boardSize, computerPlayer;
    //board size 1-10
    //computer = 0 if both human, 1 if human vs comp, 2 if both comp
    private String gameType;
    private Storage(){};

    public static Storage getInstance(){
        return instance;
    }

    public int getBoardSize(){
        return boardSize;
    }

    public String getGameType() {
        return gameType;
    }

    public int getComputerPlayer(){
        return computerPlayer;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public void setComputerPlayer(int computerPlayer) {
        this.computerPlayer = computerPlayer;
    }
}
