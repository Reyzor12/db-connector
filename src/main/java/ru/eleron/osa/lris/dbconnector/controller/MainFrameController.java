package ru.eleron.osa.lris.dbconnector.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.dbconnector.bussiness.H2DAO;
import ru.eleron.osa.lris.dbconnector.entity.ConnectorDB;
import ru.eleron.osa.lris.dbconnector.utii.SceneLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Component
public class MainFrameController {

    private Image findDB = new Image("image/findDB.png");
    private Image notFindDB = new Image("image/notFindDB.png");

    public static ConnectorDB connectorDB;

    private Stage stage;

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

    @FXML
    public void checkConnection(){
        if(listViewConnectorDB.getSelectionModel().getSelectedIndex()==-1) return;
        ConnectorDB c = listViewConnectorDB.getSelectionModel().getSelectedItem();
        switch(c.getTypeDB()){
            case 0: {
                String Url = "jdbc:mysql://" +
                        c.getIp() + ":" + c.getPort() + "/" + c.getNameDB() +
                        "?user=" + c.getUser() + "&password=" + c.getPassword();
                try{
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    Connection con = DriverManager.getConnection(Url);
                    imageViewCheckConnection.setImage(findDB);
                    imageViewCheckConnection.setVisible(true);
                    con.close();
                }catch (ClassNotFoundException e) {
                    imageViewCheckConnection.setImage(notFindDB);
                    imageViewCheckConnection.setVisible(true);
                    e.printStackTrace();
                } catch (SQLException e) {
                    imageViewCheckConnection.setImage(notFindDB);
                    imageViewCheckConnection.setVisible(true);
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    imageViewCheckConnection.setImage(notFindDB);
                    imageViewCheckConnection.setVisible(true);
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    imageViewCheckConnection.setImage(notFindDB);
                    imageViewCheckConnection.setVisible(true);
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
                    imageViewCheckConnection.setImage(findDB);
                    imageViewCheckConnection.setVisible(true);
                    con.close();
                }catch (ClassNotFoundException e) {
                    imageViewCheckConnection.setImage(notFindDB);
                    imageViewCheckConnection.setVisible(true);
                    e.printStackTrace();
                } catch (SQLException e) {
                    imageViewCheckConnection.setImage(notFindDB);
                    imageViewCheckConnection.setVisible(true);
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
                    imageViewCheckConnection.setImage(findDB);
                    imageViewCheckConnection.setVisible(true);
                    con.close();
                }catch (ClassNotFoundException e) {
                    imageViewCheckConnection.setImage(notFindDB);
                    imageViewCheckConnection.setVisible(true);
                    e.printStackTrace();
                } catch (SQLException e) {
                    imageViewCheckConnection.setImage(notFindDB);
                    imageViewCheckConnection.setVisible(true);
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    imageViewCheckConnection.setImage(findDB);
                    imageViewCheckConnection.setVisible(true);
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    imageViewCheckConnection.setImage(findDB);
                    imageViewCheckConnection.setVisible(true);
                    e.printStackTrace();
                }
            };break;
            default:break;
        }
    }
}
