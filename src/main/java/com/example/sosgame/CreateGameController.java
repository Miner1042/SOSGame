package com.example.sosgame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateGameController implements Initializable{

    Storage data = Storage.getInstance();

    @FXML
    private Spinner<Integer> boardSize;
    SpinnerValueFactory<Integer> boardSizeSVF
            = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 10, 1);

    @FXML
    private RadioButton bothHuman;
    @FXML
    private RadioButton hvsc;
    @FXML
    private RadioButton cvsh;
    @FXML
    private RadioButton bothComp;
    @FXML
    private ToggleGroup computer;
    @FXML
    private RadioButton simple;
    @FXML
    private RadioButton general;
    @FXML
    private ToggleGroup gameType;
    @FXML
    private Button startButton;

    @FXML
    void startGame(MouseEvent event) throws IOException{
        int tempSize = boardSize.getValue();
        data.setBoardSize(tempSize);

        if(gameType.getSelectedToggle().equals(general)){
            data.setGameType("General Game");
        }else if(gameType.getSelectedToggle().equals(simple)){
            data.setGameType("Simple Game");
        }else{
            //error: default to simple
            data.setGameType("Simple Game");
        }

        if(computer.getSelectedToggle().equals(bothHuman)){
            data.setComputerPlayer(0);
            data.setNpc2(false);
            data.setNpc1(false);
        }else if(computer.getSelectedToggle().equals(hvsc)){
            data.setComputerPlayer(1);
            data.setNpc2(true);
            data.setNpc1(false);
        }else if(computer.getSelectedToggle().equals(bothComp)){
            data.setComputerPlayer(2);
            data.setNpc2(true);
            data.setNpc1(true);
        }else if(computer.getSelectedToggle().equals(cvsh)){
            data.setComputerPlayer(3);
            data.setNpc2(false);
            data.setNpc1(true);
        }else{
            //error. probably just default to 0
            data.setComputerPlayer(0);
            data.setNpc2(false);
            data.setNpc1(false);
        }

        Stage gameStage = (Stage) startButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(SOSGame.class.getResource("SOSGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        gameStage.setTitle("SOS Game");
        gameStage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Setting board size spinner to have 3-10 values
        boardSize.setValueFactory(boardSizeSVF);
    }
}
