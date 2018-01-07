package ru.eleron.osa.lris.dbconnector.utii;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneLoader {


    private static final SpringFXMLLoader loader = new SpringFXMLLoader();

    public static void loadScene(String url,Stage stage){
        if(stage == null) return;
        Parent root = (Parent) loader.load(url);
        stage.setScene(new Scene(root));
        stage.show();
    }

}
