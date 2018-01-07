package ru.eleron.osa.lris.dbconnector.controller;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class SetupConnectionController {

    @FXML
    private ChoiceBox<String> choiceBoxListDB;

    @FXML
    private TextField textFieldPort;

    private List<String> listDB;

    private String pattern_name = "\\d{0,5}";
    private Pattern valiedValue = Pattern.compile(pattern_name);

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
    }
}
