package ru.eleron.osa.lris.dbconnector;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.eleron.osa.lris.dbconnector.utii.SceneLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StartApp extends Application {

    public static Connection connection;

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("configSpring.xml");
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:./test","test","");

        primaryStage.setMinHeight(270);
        primaryStage.setMinWidth(370);
//        primaryStage.setResizable(false);
        SceneLoader.setStage(primaryStage);
        SceneLoader.loadScene("view/MainFrame.fxml");
    }

    public void stop(){
        try {
            if(!connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
