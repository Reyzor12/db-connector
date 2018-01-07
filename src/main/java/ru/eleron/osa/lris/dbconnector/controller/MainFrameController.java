package ru.eleron.osa.lris.dbconnector.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.dbconnector.utii.SceneLoader;

@Component
public class MainFrameController {

    private Stage stage;

    @FXML
    private Button buttonExit;

    public MainFrameController(){}



    @FXML
    private Button buttonCheck;

    public void initialize(){

    }

    @FXML
    public void exit(){
        System.exit(0);
    }

    @FXML
    private void addConnection(){

        if(stage == null) {
            stage = new Stage();
        }
        SceneLoader.loadScene("view/SetupConnection.fxml",stage);
    }

}
