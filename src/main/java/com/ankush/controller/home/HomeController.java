package com.ankush.controller.home;

import com.ankush.config.SpringFXMLLoader;
import com.ankush.config.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class HomeController implements Initializable {
    @Autowired
    @Lazy
    private StageManager stageManager;
    @Autowired
    SpringFXMLLoader fxmlLoader;
    @FXML private BorderPane mainPane;
    @FXML private HBox menuCreate;
    @FXML private HBox menuDashboard;
    @FXML private HBox menuExit;
    @FXML private HBox menuInventary;
    @FXML private HBox menuMaster;
    @FXML private HBox menuReport;
    @FXML private HBox menuSettings;
    @FXML private HBox menuSupport;
    @FXML private HBox menuTransaction;
    @FXML private Text txtUserName;

    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuCreate.setOnMouseClicked(e->{
            pane = fxmlLoader.getPage("/fxml/create/CreateMenu.fxml");
            mainPane.setCenter(pane);
        });
        menuTransaction.setOnMouseClicked(e->{
            pane = fxmlLoader.getPage("/fxml/transaction/TransactionMenu.fxml");
            mainPane.setCenter(pane);
        });
    }
}
