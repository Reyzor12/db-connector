package ru.eleron.osa.lris.dbconnector.controller;

import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class SetupConnectionController {

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

    }

    @FXML
    public void exit(){
        ((Stage)choiceBoxListDB.getScene().getWindow()).close();
    }
    @FXML
    public void addConnectorDB(){
        if()
    }

    public Boolean check(){
        if()
        return false;
    }
}
