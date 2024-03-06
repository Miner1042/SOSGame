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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SOSGameController implements Initializable {
    Storage data = Storage.getInstance();

    ArrayList<Button> array;

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

    @FXML
    void newGame(MouseEvent event) throws IOException{
        Stage gameStage = (Stage) newGameButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(SOSGame.class.getResource("CreateGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        gameStage.setTitle("SOS Game");
        gameStage.setScene(scene);
    }

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
    }

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

    private void handleMouseClickedEvent(Button button){
        setPlayerSymbol(button);

        changeTurn();
    }

    public void setPlayerSymbol(Button button) {

        int buttonIndex = array.indexOf(button);

        //Sets an S or an O in the clicked Cell
        if ((board.getPlayer() && player1.getSelectedToggle().equals(player1S)) || !board.getPlayer() && player2.getSelectedToggle().equals(player2S)) {
            button.setText("S");
            board.makeMove(buttonIndex, 'S');
        }
        else {
            button.setText("O");
            board.makeMove(buttonIndex, '0');
        }

        button.setDisable(true);
        button.setOpacity(1.0);
    }

    private void changeTurn(){
        if(board.getPlayer()){
            board.setPlayer(false);
            gameText.setText("Player 2's Turn");
        }else{
            board.setPlayer(true);
            gameText.setText("Player 1's Turn");
        }
    }
}
