package com.ankush.controller.home;

import com.ankush.config.StageManager;
import com.ankush.data.entities.User;
import com.ankush.data.service.UserService;
import com.ankush.view.AlertNotification;
import com.ankush.view.FxmlView;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoginController implements Initializable {

    @Autowired @Lazy
    private StageManager stageManager;
    @FXML private MFXButton btnLogin;
    @FXML private MFXButton btnPassword;
    @FXML private AnchorPane mainPane;
    @FXML private MFXPasswordField txtPassword;
    @FXML private MFXTextField txtUsername;
    @FXML private Hyperlink linkCreateUser;

    @Autowired
    private UserService userService;
    @Autowired
    private AlertNotification alert;
    private SuggestionProvider<String>usernameProvider;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(userService.getAllUserNames().isEmpty())
        {
            linkCreateUser.setVisible(true);
        }
        usernameProvider = SuggestionProvider.create(userService.getAllUserNames());
        TextFields.bindAutoCompletion(txtUsername,usernameProvider);
        linkCreateUser.setOnAction(e->stageManager.switchScene(FxmlView.ADDUSER));
        btnLogin.setOnAction(e->login());

    }

    private void login() {
        if(txtUsername.getText().isEmpty()){
        alert.showError("Enter User Name");
        txtUsername.requestFocus();
        return;
        }
        if(txtPassword.getText().isEmpty())
        {
            alert.showError("Enter Password");
            txtPassword.requestFocus();
            return;
        }
        User user = userService.getByUsername(txtUsername.getText());
        if(null==user){
            alert.showError("Enter Correct User Name!!!");
            txtUsername.requestFocus();
            return;
        }else
        if(!user.getPassword().equals(txtPassword.getText()))
        {
            alert.showError("Enter Correct Password!!!");
            txtPassword.requestFocus();
            return;
        }else{
            alert.showSuccess("Login Success\n Welcome "+user.getUsername());
            stageManager.switchScene(FxmlView.HOME);
        }

    }
}
