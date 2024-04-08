package com.example.sosgame;

public class Storage {

    private static final Storage instance = new Storage();
    private int boardSize, computerPlayer;
    //board size 1-10
    //computer = 0 if both human, 1 if human vs comp, 2 if both comp, 3 if comp vs human
    private String gameType;

    private boolean npc1;
    private boolean npc2;
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
    public boolean getNpc1(){
        return npc1;
    }
    public boolean getNpc2(){
        return npc2;
    }
    public void setNpc1(boolean val) {
        npc1 = val;
    }
    public void setNpc2(boolean val){
        npc2 = val;
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
