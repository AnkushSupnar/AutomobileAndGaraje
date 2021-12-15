package com.ankush.controller.create;

import com.ankush.config.SpringFXMLLoader;
import com.ankush.config.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class CreateMenuController implements Initializable {
    @Autowired @Lazy
    private StageManager stageManager;
    @Autowired
    SpringFXMLLoader loader;
    @FXML private AnchorPane mainPane;
    @FXML private HBox menuAddCustomer;
    @FXML private HBox menuAddParty;
    @FXML private HBox menuEditBank;
    @FXML private HBox menuEditCustomer;
    @FXML private HBox menuEditParty;
    @FXML private HBox menuViewBank;
    @FXML private HBox menuViewCustomet;
    @FXML private HBox menuViewParty;
    @FXML private HBox menuAddBank;

    private Pane pane;
    private BorderPane rootPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuAddCustomer.setOnMouseClicked(e->{
            pane = loader.getPage("/fxml/create/AddCustomer.fxml");
            rootPane = (BorderPane) mainPane.getParent();
            rootPane.setCenter(pane);
        });
        menuAddBank.setOnMouseClicked(e->{
            pane = loader.getPage("/fxml/create/AddBank.fxml");
            rootPane = (BorderPane) mainPane.getParent();
            rootPane.setCenter(pane);
        });
        menuAddParty.setOnMouseClicked(e->{
            pane = loader.getPage("/fxml/create/PurchaseParty.fxml");
            rootPane = (BorderPane) mainPane.getParent();
            rootPane.setCenter(pane);
        });
    }
}
