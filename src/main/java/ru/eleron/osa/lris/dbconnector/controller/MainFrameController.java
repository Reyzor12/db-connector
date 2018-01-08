package ru.eleron.osa.lris.dbconnector.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.dbconnector.bussiness.H2DAO;
import ru.eleron.osa.lris.dbconnector.entity.ConnectorDB;
import ru.eleron.osa.lris.dbconnector.utii.SceneLoader;

import java.util.List;

@Component
public class MainFrameController {

    private Stage stage;

    @Autowired
    private H2DAO h2DAO;

    @FXML
    private Button buttonExit;

    public MainFrameController(){}

    @FXML
    private ListView<ConnectorDB> listViewConnectorDB;

    @FXML
    private Button buttonCheck;

    private List<ConnectorDB> listConnectorDB;

    public void initialize(){
       /* listConnectorDB = h2DAO.getList();
        if(listConnectorDB != null) listViewConnectorDB.setItems(FXCollections.observableArrayList());*/
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
