package com.ankush.controller.home;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoginController implements Initializable {

    @FXML private MFXButton btnLogin;
    @FXML private MFXButton btnPassword;
    @FXML private AnchorPane mainPane;
    @FXML private MFXPasswordField txtPassword;
    @FXML private MFXTextField txtUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
