package com.ankush.controller.transaction;

import com.ankush.config.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class TransactionMenuController implements Initializable {
    @Autowired @Lazy
    private StageManager stageManager;
    @FXML private HBox MenuPaymentReceived;
    @FXML private AnchorPane mainPane;
    @FXML private HBox menuDailyBilling;
    @FXML private HBox menuPayInvoice;
    @FXML private HBox menuPurchaseInvoice;
    @FXML private HBox menuViewBills;
    @FXML private HBox menuViewInvoice;
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
