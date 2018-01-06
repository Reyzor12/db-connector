package ru.eleron.osa.lris.dbconnector;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.eleron.osa.lris.dbconnector.utii.SceneLoader;

public class StartApp extends Application {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("configSpring.xml");
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setMinHeight(270);
        primaryStage.setMinWidth(370);
//        primaryStage.setResizable(false);
        SceneLoader.setStage(primaryStage);
        SceneLoader.loadScene("view/MainFrame.fxml");
    }
}
