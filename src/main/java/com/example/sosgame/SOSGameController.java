package com.example.sosgame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class SOSGameController implements Initializable {
    Storage data = Storage.getInstance();

    ArrayList<Button> array;

    //ComputerPlayer comp = new ComputerPlayer();

    Board board;

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

    public static final int computerVComputer = 2;
    public static final int computerVHuman = 3;
    public static final int invalidValue = -1;

    //Precondition: The start game button in CreateGame.fxml is clicked
    //Postcondition: SOSGame.fxml is opened
    @FXML
    void newGame(MouseEvent event) throws IOException{
        Stage gameStage = (Stage) newGameButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(SOSGame.class.getResource("CreateGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        gameStage.setTitle("SOS Game");
        gameStage.setScene(scene);
    }

    //Precondition: SOSGame.fxml is opened
    //Postcondition: The board is created, all values initialized, and if the computer has the first move it is made
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initializing the Game Board

        //Setting the initial turn to start with the Blue Player
        gameText.setText("Player 1's Turn");

        //Creating the button array to be filled by (Board Size)^2 many buttons
        array = new ArrayList<>();
        board = new Board();

        //Creating the Game Board
        createBoard(array);

        try{
            FileWriter fw = new FileWriter("gameOutput");
            fw.write("");
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        if(data.getComputerPlayer() == computerVComputer || data.getComputerPlayer() == computerVHuman){
            computerRandomMove();
        }
    }

    //Precondition: An empty array of buttons is passed in
    //Postcondition: the array is populated with enough buttons to fill the current board size
    public void createBoard(ArrayList<Button> array){
        for(int i = 0; i < (data.getBoardSize() * data.getBoardSize()); i++){
            Button temp = new Button();
            array.add(temp);
            GridPane.setConstraints(temp, i % data.getBoardSize(), i / data.getBoardSize());
            gameBoard.getChildren().add(temp);
        }
        array.forEach(this::setupButton);

        board.makeBoard(data.getBoardSize(), data.getGameType());
    }

    //Precondition: A button object is passed in
    //Postcondition: The button's height and width are reset
    private void setupButton(Button button){
        button.setPrefHeight(500.0 / data.getBoardSize());
        button.setPrefWidth(500.0 / data.getBoardSize());
        button.setAlignment(Pos.CENTER);
        button.setStyle("-fx-background-color: transparent;");
        button.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        button.setOnMouseClicked(mouseEvent -> {
            handleMouseClickedEvent(button);
        });
    }

    //Precondition: A button on the GUI is clicked by the user
    //Postcondition: the function is called that will actually modify the data of the game
    private void handleMouseClickedEvent(Button button){
        //add a check for if a point was scored. If so, dont change.
        handleTheButton(button);
    }

    //Precondition: A button is passed in
    //Postcondition: The button cannot be clicked
    public void stopButtons(Button button) {
        button.setDisable(true);
        button.setOpacity(1.0);
    }

    //Precondition: The current button is passed in
    //Postcondition: The chosen letter is placed on the button, the move is made and documented, and returns true if a point was scored.
    public boolean setPlayerSymbol(Button button) {
        if(!board.endGame()) {
            boolean check;
            char letter;
            int buttonIndex = array.indexOf(button);
            button.setDisable(true);
            button.setOpacity(1.0);

            //Sets an S or an O in the clicked Cell
            if ((board.getPlayer() && player1.getSelectedToggle().equals(player1S)) || !board.getPlayer() && player2.getSelectedToggle().equals(player2S)) {
                letter = 'S';
            } else {
                letter = 'O';
            }
            button.setText(String.valueOf(letter));
            board.makeMove(buttonIndex, letter);
            check = board.pointChecker(buttonIndex, letter);
            writeMove("CURRENT MOVE:\nGamemode: " + board.gameType + "\nIndex: " + buttonIndex + "\nCurrent Move: " + letter + "\nMove Counter: " + board.turnCount + "\nPlayer 1: " + board.getPlayer() + "\n");
            //System.out.println("CURRENT MOVE:\nIndex: " + buttonIndex + "\nCurrent Move: S\nMove Counter: " + board.turnCount + "\nPlayer 1: " + board.getPlayer());
            return check;
        }else{
            return false;
        }
    }



    //Precondition: the function is called. It requires no input
    //Postcondition: The current player is alternated to the other player
    private void changeTurn(){
        if(board.getPlayer()){
            board.setPlayer(false);
            gameText.setText("Player 2's Turn");
        }else{
            board.setPlayer(true);
            gameText.setText("Player 1's Turn");
        }
    }

    //Precondition: A GUI button is clicked and passed in, or the computer chooses a button and passes it in
    //Postcondition: The symbol is set and move is made, the game checks if it is over, and if not the computer checks if it should make a move
    private void handleTheButton(Button button){
        if(!setPlayerSymbol(button)){
            changeTurn();
        }
        if(board.endGame()){
            array.forEach(this::stopButtons);
            //display who wins
            if(board.p1score > board.p2score){
                gameText.setText("Player 1 Wins!");
                writeMove("Player 1 Wins!");
            }else if(board.p1score < board.p2score){
                gameText.setText("Player 2 Wins!");
                writeMove("Player 2 Wins!");
            }else{
                gameText.setText("It's a Draw!");
                writeMove("It's a Draw!");
            }
        }
        if(data.getNpc1() && board.getPlayer()){
            computerRandomMove();
        }else if(data.getNpc2() && !board.getPlayer()){
            computerRandomMove();
        }
    }


    //Section for NPC

    //Precondition: The computer decides it should make a move
    //Postcondition: The computer checks if a move can be made to score a point, if so it is made, if not a random move is made
    public void computerRandomMove(){
        int s = board.findWinnerS();
        int o = board.findWinnerO();
        //System.out.println(s + " " + o);
        if(!board.endGame()){
            if(s != invalidValue) {
                getWinningMove(s);
            }else if(o != invalidValue){
                getWinningMove(o);
            }else {
                generateRandomPos();
            }
        }
    }

    //Precondition: A move can be made to score a point
    //Postcondition: the button is "clicked" by the computer
    public void getWinningMove(int index){
        Button currentMove;
        if(board.getPlayer()){
            player1.selectToggle(player1S);
        }else{
            player2.selectToggle(player2S);
        }

        currentMove = array.get(index);
        handleTheButton(currentMove);
    }

    //Precondition: No move can score a point on the current board
    //Postcondition: A random button on the board is selected and "clicked" by the computer
    public void generateRandomPos(){
        Button currentMove;
        int x, y, tempIndex, letter;
        //int counter = 0;
        do{
            Random rand = new Random();
            tempIndex = rand.nextInt(data.getBoardSize() * data.getBoardSize());
            letter = rand.nextInt(2);
            x = tempIndex % data.getBoardSize();
            y = tempIndex / data.getBoardSize();
            //counter++;
            //System.out.println("Counter: " + counter + "\nInt: " + temp + "\n" + board.currentBoard[x][y]);
        }while(board.currentBoard[x][y] != 0);

        currentMove = array.get(tempIndex);

        if(board.getPlayer()){
            if(letter == 0){
                player1.selectToggle(player1S);
            }else{
                player1.selectToggle(player1O);
            }
        }else{
            if(letter == 0){
                player2.selectToggle(player2S);
            }else{
                player2.selectToggle(player2O);
            }
        }

        handleTheButton(currentMove);
    }


    //Precondition: A string that should be recorded is passed in
    //Postcondition: the string is written to the output file
    public void writeMove(String words){
        try{
            FileWriter fw = new FileWriter("gameOutput", true);
            fw.write(words);
            fw.close();
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
