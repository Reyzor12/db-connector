package ru.eleron.osa.lris.dbconnector;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.eleron.osa.lris.dbconnector.utii.SceneLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StartApp extends Application {

    public static Connection connection;

    public static final String CREATE_IF_NOT_EXISTS =
            "CREATE TABLE IF NOT EXISTS CONNECTIONS(" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "TYPE VARCHAR(255)," +
                    "IP VARCHAR(255)," +
                    "PORT VARCHAR(255)," +
                    "NAMEDB VARCHAR(255)," +
                    "USER VARCHAR(255)," +
                    "PASSWORD VARCHAR(255))";

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("configSpring.xml");
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:./test","test","");
        initBD();

        primaryStage.setMinHeight(270);
        primaryStage.setMinWidth(370);
        primaryStage.setOnCloseRequest(e-> Platform.exit());
//        primaryStage.setResizable(false);

        SceneLoader.loadScene("view/MainFrame.fxml",primaryStage);
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

    public void initBD(){
        if(connection != null){
            try {
                connection.createStatement().execute(CREATE_IF_NOT_EXISTS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
