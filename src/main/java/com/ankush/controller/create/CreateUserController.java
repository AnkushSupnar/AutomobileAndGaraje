package com.ankush.controller.create;

import com.ankush.data.entities.User;
import com.ankush.data.service.UserService;
import com.ankush.view.AlertNotification;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class CreateUserController implements Initializable {
    @FXML private MFXButton btnCreate;
    @FXML private MFXButton btnCancel;
    @FXML private AnchorPane mainPane;
    @FXML private MFXPasswordField txtPassword;
    @FXML private MFXPasswordField txtRepassword;
    @FXML private MFXTextField txtUsername;

    @Autowired
    private UserService userService;
    @Autowired
    private AlertNotification alert;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnCreate.setOnAction(e->create());
        btnCancel.setOnAction(e->clear());
    }

    private void create() {
        System.out.println("Clicked on Add");
        if(!validate()) return;
        User user = User.builder()
                .username(txtUsername.getText().trim())
                .password(txtPassword.getText().trim())
                .build();
        if(userService.saveUser(user)==1)
        {
            alert.showSuccess("User Saved Success");
            clear();
        }
    }

    private void clear() {
        txtUsername.clear();

        //txtUsername.setText("");
        txtPassword.clear();
        txtRepassword.clear();
    }

    private boolean validate() {
        if(txtUsername.getText().isEmpty())
        {
            alert.showError("Enter User Name!!!");
            txtUsername.requestFocus();
            return false;
        }
        if(userService.getByUsername(txtUsername.getText().trim())!=null)
        {
            alert.showError("User Name Already Exist!!! Choose another one!!!");
            txtUsername.requestFocus();
            return false;
        }
        if(txtPassword.getText().isEmpty())
        {
            alert.showError("Enter Password !!!!");
            txtPassword.requestFocus();
            return false;
        }
        if(txtRepassword.getText().isEmpty())
        {
            alert.showError("Enter Re-Password !!!");
            txtRepassword.requestFocus();
            return false;
        }
        if(!txtPassword.getText().equals(txtRepassword.getText()))
        {
            alert.showError("Both Password Must be Same");
            txtRepassword.requestFocus();
            return false;
        }
        return true;
    }
}
