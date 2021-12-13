package com.ankush.controller.create;

import com.ankush.data.entities.PurchaseParty;
import com.ankush.data.service.PurchasePartyService;
import com.ankush.view.AlertNotification;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.net.URL;
import java.util.ResourceBundle;
@Controller
public class PurchasePartyController implements Initializable {
    @FXML private MFXButton btnClear;
    @FXML private MFXButton btnExit;
    @FXML private MFXButton btnSave;
    @FXML private MFXButton btnUpdate;
    @FXML private TableView<PurchaseParty> table;
    @FXML private TableColumn<PurchaseParty,String> colAddress;
    @FXML private TableColumn<PurchaseParty,String> colContact;
    @FXML private TableColumn<PurchaseParty,String> colGst;
    @FXML private TableColumn<PurchaseParty,String> colName;
    @FXML private TableColumn<PurchaseParty,String> colPan;
    @FXML private TableColumn<PurchaseParty,String> colSrno;
    @FXML private AnchorPane mainPane;
    @FXML private MFXTextField txtAddress;
    @FXML private MFXTextField txtContact;
    @FXML private MFXTextField txtGst;
    @FXML private MFXTextField txtName;
    @FXML private MFXTextField txtPan;

    @FXML private MFXTextField txtSearchParty;
    @FXML private MFXButton btnSearch;
    @FXML private MFXButton btnShow;
    @Autowired
    private PurchasePartyService service;
    @Autowired
    private AlertNotification alert;
    private ObservableList<PurchaseParty>list = FXCollections.observableArrayList();
    Integer id;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id = null;
        TextFields.bindAutoCompletion(txtSearchParty,service.getAllPartyNames());
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colGst.setCellValueFactory(new PropertyValueFactory<>("gst"));
        colPan.setCellValueFactory(new PropertyValueFactory<>("pan"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSrno.setCellValueFactory(new PropertyValueFactory<>("id"));
        table.setItems(list);
        btnSave.setOnAction(e->save());
        btnUpdate.setOnAction(e->update());
        btnClear.setOnAction(e->clear());
        btnShow.setOnAction(e->showAll());
        btnSearch.setOnAction(e->search());
    }

    private void search() {
        if(txtSearchParty.getText().isEmpty()) txtSearchParty.requestFocus();
        list.clear();
        if(service.getPartyByName(txtSearchParty.getText())!=null)
        {
            list.addAll(service.getPartyByName(txtSearchParty.getText()));
        }
    }

    private void showAll() {
        list.clear();
        list.addAll(service.getAllPurchaseParty());
    }

    private void save() {
        if(!validate()) return;
        PurchaseParty party = PurchaseParty.builder()
                .address(txtAddress.getText())
                .contact(txtContact.getText())
                .gst(txtGst.getText())
                .pan(txtPan.getText())
                .name(txtName.getText())
                .build();
        if(id!=null)
            party.setId(id);
        int flag = service.savePurchaseParty(party);
        if(flag==1)
        {
            alert.showSuccess("Purchase Party Saved Success");
            addInList(party);
            clear();

        }
        else if(flag==2)
        {
            alert.showSuccess("Purchase party Updated Success");
            addInList(party);
            clear();
        }
    }
    private boolean validate() {
        if(txtName.getText().isEmpty())
        {
            alert.showError("Enter Party Name");
            txtName.requestFocus();
            return false;
        }
        if(txtAddress.getText().isEmpty())
        {
            alert.showError("Enter Party Address");
            txtAddress.requestFocus();
            return false;
        }
        if(txtContact.getText().isEmpty())
        {
            alert.showError("Enter Party Contact No");
            txtContact.requestFocus();
            return false;
        }
        if(txtPan.getText().isEmpty())
            txtPan.setText("-");
        if(txtGst.getText().isEmpty())
            txtGst.setText("-");
        return true;
    }
    private void addInList(PurchaseParty party){
        int index=-1;
        for(PurchaseParty p:list)
        {
            if(p.getId().equals(party.getId())) {
                index = list.indexOf(p);
                break;
            }
        }
        if(index==-1)
        {
            list.add(party);
            table.refresh();
        }
        else
        {
            list.remove(index);
            list.add(index,party);
            table.refresh();
        }
    }
    private void update() {
        if(table.getSelectionModel().getSelectedItem()==null)return;
        PurchaseParty party = table.getSelectionModel().getSelectedItem();
        txtName.setText(party.getName());
        txtAddress.setText(party.getAddress());
        txtContact.setText(party.getContact());
        txtPan.setText(party.getPan());
        txtGst.setText(party.getGst());
        id=party.getId();
    }
    private void clear() {
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtPan.setText("");
        txtGst.setText("");
        id=null;
    }
}
