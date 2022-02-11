package com.ankush.controller.create;

import com.ankush.data.entities.Customer;
import com.ankush.data.service.CustomerService;
import com.ankush.view.AlertNotification;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddCustomerController implements Initializable {
    @FXML private TextField txtCustomerName;
    @FXML private TextField txtAddresline;
    @FXML private TextField txtCity;
    @FXML private TextField txtTaluka;
    @FXML private TextField txtDistrict;
    @FXML private TextField txtcontact;
    @FXML private TextField txtMobile;
    @FXML private Button btnSave;
    @FXML private Button btnUpdate;
    @FXML private Button btnClear;
    @FXML private Button btnHome;
    @FXML private TableView<Customer> table;
    @FXML private TableColumn<Customer,Long> colSr;
    @FXML private TableColumn<Customer,String> colName;
    @FXML private TableColumn<Customer,String> colContact;
    @FXML private TableColumn<Customer,String> colmobile;
    @FXML private TableColumn<Customer,String> colAddress;
    @Autowired private CustomerService service;
    @Autowired private AlertNotification alert;
    private Long id;
    private ObservableList<Customer>list= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id=null;
        colSr.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customername"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colmobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colAddress.setCellValueFactory(
                cellData->new SimpleStringProperty(
                        cellData.getValue().getAddressline()+" , "
                        +cellData.getValue().getVillage()+" , "
                        +cellData.getValue().getTaluka()+" , "
                        +cellData.getValue().getDistrict()
                        ));
        list.addAll(service.getAllCustomer());
        table.setItems(list);
        btnSave.setOnAction(e->save());
        btnUpdate.setOnAction(e->update());
        btnClear.setOnAction(e->clear());

    }

    private void clear() {
        txtDistrict.setText("");
        txtTaluka.setText("");
        txtCity.setText("");
        txtcontact.setText("");
        txtCustomerName.setText("");
        txtMobile.setText("");
        txtAddresline.setText("");
        id=null;
    }

    private void update() {
        if(table.getSelectionModel().getSelectedItem()==null)
            return;
        Customer customer = service.getById(table.getSelectionModel().getSelectedItem().getId());
        txtDistrict.setText(customer.getDistrict());
        txtTaluka.setText(customer.getTaluka());
        txtCity.setText(customer.getVillage());
        txtcontact.setText(customer.getContact());
        txtCustomerName.setText(customer.getCustomername());
        txtMobile.setText(customer.getMobile());
        txtAddresline.setText(customer.getAddressline());
        id=customer.getId();
    }

    private void save() {
        if(!validate()) return;
        Customer customer = Customer.builder()
                .contact(txtcontact.getText())
                .customername(txtCustomerName.getText())
                .addressline(txtAddresline.getText())
                .district(txtDistrict.getText())
                .mobile(txtMobile.getText())
                .taluka(txtTaluka.getText())
                .village(txtCity.getText())
                .id(null)
                .build();
    if(id!=null) customer.setId(id);
    int flag = service.saveCustomer(customer);
    if(flag==1) {
        alert.showSuccess("Customer Saved Success");
        addInList(customer);
    }
    else
    {
        alert.showSuccess("Customer Update Success");
        addInList(customer);
    }
        clear();
    }
    void addInList(Customer customer)
    {
        int flag=-1;
        for(Customer cust:list)
        {
            if(customer.getId()==cust.getId())
            {
                flag = list.indexOf(cust);
                break;
            }
        }
        if(flag==-1)
        {
            System.out.println("Found new");
            list.add(customer);
            table.refresh();
        }
        else
        {
            System.out.println("Found old");
            list.remove(flag);
            list.add(flag,customer);
            table.refresh();
        }
    }

    private boolean validate()
    {
        try {
            if(txtCustomerName.getText().isEmpty())
            {
                alert.showError("Enter Customer Name");
                txtCustomerName.requestFocus();
                return false;
            }
            if(txtAddresline.getText().isEmpty())
            {
                txtAddresline.setText(""+1);
            }
            if(txtcontact.getText().isEmpty())
            {
                alert.showError("Enter Customer Contact no");
                txtcontact.requestFocus();
                return false;
            }
            if(txtMobile.getText().isEmpty())
            {
                txtMobile.setText(""+0);
            }
            if(txtCity.getText().isEmpty())
            {
                alert.showError("Enter Customer City or Village Name");
                txtCity.requestFocus();
                return false;
            }
            if(txtTaluka.getText().isEmpty())
            {
                alert.showError("Enter Customer Taluka Name");
                txtTaluka.requestFocus();
                return false;
            }
            if(txtDistrict.getText().isEmpty())
            {
                alert.showError("Enter Customer District Name");
                txtDistrict.requestFocus();
                return false;
            }

            return true;
        }catch(Exception e)
        {
            alert.showError("Error in Validating Customer Data");
            return false;
        }
    }
}
