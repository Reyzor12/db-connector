package ru.eleron.osa.lris.dbconnector.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.dbconnector.bussiness.H2DAO;
import ru.eleron.osa.lris.dbconnector.entity.ConnectorDB;
import ru.eleron.osa.lris.dbconnector.utii.SceneLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Component
public class MainFrameController {

    private IntegerProperty intValue = new SimpleIntegerProperty();

    public static ConnectorDB connectorDB;

    private Stage stage;

    private Boolean status = false;

    public void setStatus(Boolean b){
        status = b;
    }

    public Stage getStage(){return stage;}

    public List<Image> imageList = Arrays.asList(new Image("image/1.png"), new Image("image/2.png"), new Image("image/3.png"), new Image("image/4.png"),new Image("image/findDB.png"),new Image("image/notFindDB.png"));

    @Autowired
    private H2DAO h2DAO;

    @FXML
    private ImageView imageViewCheckConnection;

    @FXML
    private Button buttonExit;

    public MainFrameController(){}

    @FXML
    public ListView<ConnectorDB> listViewConnectorDB;

    @FXML
    private Button buttonCheck;

    private List<ConnectorDB> listConnectorDB;

    public void initialize(){

        updateData();
        imageViewCheckConnection.imageProperty().bind(Bindings.createObjectBinding(()->
            imageList.get(intValue.getValue()),intValue));
    }

    @FXML
    public void exit(){
        System.exit(0);
    }

    @FXML
    private void addConnection(){

        if(stage == null) {
            stage = new Stage();
            stage.setResizable(false);
            stage.setHeight(430);
            stage.setWidth(430);
            stage.initModality(Modality.APPLICATION_MODAL);
        }
        SceneLoader.loadScene("view/SetupConnection.fxml",stage);
    }

    @FXML
    public void removeConnection(){
        if(listViewConnectorDB.getSelectionModel().getSelectedIndex() == -1) return;
        h2DAO.removeConnector(listViewConnectorDB.getSelectionModel().getSelectedItem().getName());
        updateData();
    }

    public void updateData(){
        listConnectorDB = h2DAO.getList();
        listViewConnectorDB.setItems(FXCollections.observableArrayList(listConnectorDB));
    }

    @FXML
    public void updateConnector(){
        connectorDB = listViewConnectorDB.getSelectionModel().getSelectedItem();
        addConnection();
    }

    public void checkConnection(ConnectorDB c){

        switch(c.getTypeDB()){
            case 0: {
                String Url = "jdbc:mysql://" +
                        c.getIp() + ":" + c.getPort() + "/" + c.getNameDB() +
                        "?user=" + c.getUser() + "&password=" + c.getPassword();
                try{
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    Connection con = DriverManager.getConnection(Url);
                    setStatus(true);
                    con.close();
                }catch (ClassNotFoundException e) {
                    setStatus(false);
                    e.printStackTrace();
                } catch (SQLException e) {
                    setStatus(false);
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    setStatus(false);
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    setStatus(false);
                    e.printStackTrace();
                }
            };break;
            case 1: {
                String Url = "jdbc:sqlserver://" +
                        c.getIp() + ":" + c.getPort() + ";DatabaseName=" + c.getNameDB() +
                        ";user=" + c.getUser() + ";Password=" + c.getPassword();
                try{
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection con = DriverManager.getConnection(Url);
                    setStatus(true);
                    con.close();
                }catch (ClassNotFoundException e) {
                    setStatus(false);
                    e.printStackTrace();
                } catch (SQLException e) {
                    setStatus(false);
                    e.printStackTrace();
                }
            };break;
            case 2: {
                String Url = "jdbc:postgresql://" +
                        c.getIp() + ":" + c.getPort() + "/" + c.getNameDB() +
                        "?user=" + c.getUser() + "&password=" + c.getPassword()+"&ssl=true";
                try{
                    Class.forName("org.postgresql.Driver").newInstance();
                    Connection con = DriverManager.getConnection(Url);
                    setStatus(true);
                    con.close();
                }catch (ClassNotFoundException e) {
                    setStatus(false);
                    e.printStackTrace();
                } catch (SQLException e) {
                    setStatus(false);
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    setStatus(false);
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    setStatus(false);
                    e.printStackTrace();
                }
            };break;
            default:break;
        }
    }


    @FXML
    public void startConnection(){
        if(listViewConnectorDB.getSelectionModel().getSelectedIndex()==-1) return;
        Thread t = new Thread(new CheckTask(listViewConnectorDB.getSelectionModel().getSelectedItem()));
        t.setDaemon(true);
        t.start();
    }

    private class CheckTask extends Task<Void>{

        ConnectorDB connectorDB;

        CheckTask(ConnectorDB connectorDB){
            this.connectorDB = connectorDB;
        }

        @Override
        protected Void call() throws Exception {
            buttonCheck.setDisable(true);
            imageViewCheckConnection.setVisible(true);
            Thread t = new Thread (new Task(){

                @Override
                protected Object call() throws Exception {
                    checkConnection(connectorDB);
                    return null;
                }
            });
            t.start();
            int i = 0;
            while(t.isAlive()){
                t.join(250);
                i = (i+1)%4;
                intValue.set(i);
            }
            buttonCheck.setDisable(false);
            if(status==true){
                intValue.set(4);
            } else{
                intValue.set(5);
            }
            return null;
        }
    }
}
