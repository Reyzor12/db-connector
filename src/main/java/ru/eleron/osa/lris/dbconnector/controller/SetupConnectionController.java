package ru.eleron.osa.lris.dbconnector.controller;

import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.dbconnector.bussiness.H2DAO;
import ru.eleron.osa.lris.dbconnector.entity.ConnectorDB;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class SetupConnectionController {

    @Autowired
    private MainFrameController mainFrameController;

    @Autowired
    private H2DAO h2DAO;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldNameDB;

    @FXML
    private TextField textFieldUser;

    @FXML
    private PasswordField passwordFieldPassword;

    @FXML
    private ChoiceBox<String> choiceBoxListDB;

    @FXML
    private TextField textFieldPort;

    @FXML
    private TextField textFieldIp;

    private List<String> listDB;

    private String pattern_name = "\\d{0,5}";
    private Pattern valiedValue = Pattern.compile(pattern_name);
    private static final String IPADDRESS_PATTERN =
            "(^(([1]\\d\\d?|2[0-4]\\d|25[0-5]))|()|[123456789]?\\d)\\." +
                    "(([1]\\d\\d?|2[0-4]\\d|25[0-5])|()|[123456789]?\\d)\\." +
                    "(([1]\\d\\d?|2[0-4]\\d|25[0-5])|()|[123456789]?\\d)\\." +
                    "(([1]\\d\\d?|2[0-4]\\d|25[0-5])||()|[123456789]?\\d)$";
    private Pattern validIp = Pattern.compile(IPADDRESS_PATTERN);
    private static final String IP_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    private Pattern validIpNet = Pattern.compile(IP_PATTERN);

    public void initialize(){
        listDB = Arrays.asList("MySQL","MSSQL","PostgreSQL");
        choiceBoxListDB.setItems(FXCollections.observableArrayList(listDB));
        textFieldPort.textProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(valiedValue.matcher(newValue).matches()||newValue.isEmpty()){
                        ((StringProperty)observable).setValue(newValue);
                    }else{
                        ((StringProperty)observable).setValue(oldValue);
                    }
                })
        );
        textFieldIp.setText("0.0.0.0");
        textFieldIp.textProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    if(validIp.matcher(newValue).matches()||newValue.isEmpty()){
                        ((StringProperty)observable).setValue(newValue);
                    }else{
                        ((StringProperty)observable).setValue(oldValue);
                    }
                })
        );
        if(MainFrameController.connectorDB != null){
            ConnectorDB c = MainFrameController.connectorDB;
            textFieldName.setText(c.getName());
            choiceBoxListDB.getSelectionModel().select(c.getTypeDB());
            textFieldIp.setText(c.getIp());
            textFieldPort.setText(c.getPort());
            textFieldNameDB.setText(c.getNameDB());
            textFieldUser.setText(c.getUser());
            passwordFieldPassword.setText(c.getPassword());
        }
    }

    @FXML
    public void exit(){
        ((Stage)choiceBoxListDB.getScene().getWindow()).close();
    }
    @FXML
    public void addConnectorDB(){
        if (check()) {
            if (MainFrameController.connectorDB == null) {
                ConnectorDB connectorDB = new ConnectorDB(
                        textFieldName.getText(),
                        choiceBoxListDB.getSelectionModel().getSelectedIndex(),
                        textFieldIp.getText(),
                        textFieldPort.getText(),
                        textFieldNameDB.getText(),
                        textFieldUser.getText(),
                        passwordFieldPassword.getText());
                h2DAO.addConnector(connectorDB);
            } else {
                ConnectorDB c = MainFrameController.connectorDB;
                c.setName(textFieldName.getText());
                c.setTypeDB(choiceBoxListDB.getSelectionModel().getSelectedIndex());
                c.setIp(textFieldIp.getText());
                c.setPort(textFieldPort.getText());
                c.setNameDB(textFieldNameDB.getText());
                c.setUser(textFieldUser.getText());
                c.setPassword(passwordFieldPassword.getText());
                h2DAO.updateConnector(c);
                MainFrameController.connectorDB = null;
            }
            mainFrameController.updateData();
            exit();
        }
    }

    public Boolean check(){
        if(
                textFieldName.getText().equals("")||
                choiceBoxListDB.getSelectionModel().getSelectedIndex()==-1||
                !validIpNet.matcher(textFieldIp.getText()).matches()||
                textFieldPort.getText().equals("")||
                textFieldNameDB.getText().equals("")||
                textFieldUser.getText().equals("")||
                passwordFieldPassword.getText().equals("")) return false;

        return true;
    }
}
