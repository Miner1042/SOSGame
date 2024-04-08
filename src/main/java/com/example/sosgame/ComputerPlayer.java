package com.example.sosgame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;
public class ComputerPlayer {

    Storage data = Storage.getInstance();

    @FXML
    private RadioButton player1S;

    @FXML
    private RadioButton player1O;

    @FXML
    private ToggleGroup player1;

    @FXML
    private RadioButton player2S;

    @FXML
    private RadioButton player2O;

    @FXML
    private ToggleGroup player2;

    @FXML
    private GridPane gameBoard;

    @FXML
    private Button newGameButton;

    @FXML
    private Text gameText;



    public void computerRandomMove(Board thisBoard, ArrayList<Button> arr){
        //this gets us a random move
        int x, y, temp;
        do{
            Random rand = new Random();
            temp = rand.nextInt(data.getBoardSize() * data.getBoardSize());
            x = temp % data.getBoardSize();
            y = temp / data.getBoardSize();
        }while(thisBoard.currentBoard[x][y] != 0);

        Button currentMove = arr.get(temp);

        if(!setPlayerSymbol(currentMove, thisBoard, arr)){
            changeTurn(thisBoard, arr);
        }else{
            computerRandomMove(thisBoard, arr);
        }

        if(thisBoard.endGame()){
            arr.forEach(this::stopButtons);
            //display who wins
            if(thisBoard.p1score > thisBoard.p2score){
                gameText.setText("Player 1 Wins!");
            }else if(thisBoard.p1score < thisBoard.p2score){
                gameText.setText("Player 2 Wins!");
            }else{
                gameText.setText("It's a Draw!");
            }
        }
    }

    public void stopButtons(Button button) {
        button.setDisable(true);
        button.setOpacity(1.0);
    }

    public boolean setPlayerSymbol(Button button, Board thisBoard, ArrayList<Button> arr) {
        boolean check;
        int buttonIndex = arr.indexOf(button);
        button.setDisable(true);
        button.setOpacity(1.0);

        //Sets an S or an O in the clicked Cell
        if ((thisBoard.getPlayer() && player1.getSelectedToggle().equals(player1S)) || !thisBoard.getPlayer() && player2.getSelectedToggle().equals(player2S)) {
            button.setText("S");
            thisBoard.makeMove(buttonIndex, 'S');
            check = thisBoard.pointChecker(buttonIndex, 'S');
        }
        else {
            button.setText("O");
            thisBoard.makeMove(buttonIndex, 'O');
            check = thisBoard.pointChecker(buttonIndex, 'O');
        }
        return check;
    }

    private void changeTurn(Board thisBoard, ArrayList<Button> arr){
        if(thisBoard.getPlayer()){
            thisBoard.setPlayer(false);
            gameText.setText("Player 2's Turn");
            if(data.getNpc2()){
                //make a robot move
                //change turn again
            }
        }else{
            thisBoard.setPlayer(true);
            gameText.setText("Player 1's Turn");
            if(data.getNpc1()){
                //make robot move
                //change turn again
            }else{

            }
        }
    }
}
