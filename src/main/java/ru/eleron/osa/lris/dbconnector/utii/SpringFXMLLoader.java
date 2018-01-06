package ru.eleron.osa.lris.dbconnector.utii;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SpringFXMLLoader {

    private static final ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext("configSpring.xml");

    public Object load(String url){
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(clazz->APPLICATION_CONTEXT.getBean(clazz));
        loader.setLocation(getClass().getClassLoader().getResource(url));
        try{
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
