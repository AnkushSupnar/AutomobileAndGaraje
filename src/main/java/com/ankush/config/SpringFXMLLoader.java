package com.ankush.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ResourceBundle;
@Component
public class SpringFXMLLoader {
    private final ResourceBundle resourceBundle;
    private final ApplicationContext context;
    FXMLLoader loader;
    @Autowired
    public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.context = context;
        FXMLLoader loader = new FXMLLoader();
    }

    public Parent load(String fxmlPath) throws IOException {
      //FXMLLoader
              loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean); //Spring now FXML Controller Factory
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }
    public Pane getPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(context::getBean);
            loader.setResources(resourceBundle);
            loader.setLocation(getClass().getResource(fxmlPath));

            return loader.load();
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
}