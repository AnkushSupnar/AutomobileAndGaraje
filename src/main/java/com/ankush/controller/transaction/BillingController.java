package com.ankush.controller.transaction;

import com.ankush.config.StageManager;
import com.ankush.data.entities.Customer;
import com.ankush.data.service.BankService;
import com.ankush.data.service.CustomerService;
import com.ankush.data.service.ItemService;
import com.ankush.data.service.ItemStockService;
import com.ankush.view.AlertNotification;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
@Component
public class BillingController implements Initializable {
    @Autowired @Lazy
    private StageManager stageManager;
    @FXML private Button btUpdate,btnAdd,btnClear,btnClearBill,btnHome,btnPrint,btnRemove,btnSave,btnSearch;
    @FXML private Button btnUpdateBill;
    @FXML private ComboBox<?> cmbBank;
    @FXML private TableColumn<?, ?> colAmount;
    @FXML private TableColumn<?, ?> colNo;
    @FXML private TableColumn<?, ?> colPartName;
    @FXML private TableColumn<?, ?> colPartNo;
    @FXML private TableColumn<?, ?> colQty;
    @FXML private TableColumn<?, ?> colRate;
    @FXML private DatePicker date;
    @FXML private TableView<?> tableTr;
    @FXML private TextField txtAmount;
    @FXML private TextField txtBillno,txtOther,txtPaid,txtPartName,txtPartNo,txtQty,txtRate;
    @FXML private TextField txtCustomer,txtGrand,txtNetTotal;
    @FXML private TextArea txtCustomerInfor;

    @Autowired private ItemStockService stockService;
    @Autowired private ItemService itemService;
    @Autowired private CustomerService customerService;
    @Autowired private BankService bankService;
    @Autowired private AlertNotification alert;

    private SuggestionProvider customerNameProvider;
    private SuggestionProvider partnoProvider;
    private SuggestionProvider partNameProvider;
    private Customer customer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date.setValue(LocalDate.now());
        customerNameProvider = SuggestionProvider.create(customerService.getAllCustomerNames());
        new AutoCompletionTextFieldBinding<>(txtCustomer,customerNameProvider);

        partnoProvider = SuggestionProvider.create(itemService.getAllPartNo());
        new AutoCompletionTextFieldBinding<>(txtPartNo,partnoProvider);

        partNameProvider = SuggestionProvider.create(itemService.getAllItemNames());
        new AutoCompletionTextFieldBinding<>(txtPartName,partNameProvider);
        btnSearch.setOnAction(e->searchCustomer());

    }

    private void searchCustomer() {
        if(txtCustomer.getText().isEmpty() || txtCustomer.getText().equals(" ")) {
            txtCustomer.requestFocus();
            return;
        }
        customer = customerService.getByCustomerName(txtCustomer.getText());
        txtCustomerInfor.setText(
                "Name: "+customer.getCustomername()+"\n"
                +"Address: "+customer.getAddressline()
                +", Village: "+customer.getVillage()
                +", Taluka: "+customer.getTaluka()
                +",Dist: "+customer.getDistrict()
                +"\nContact: "+customer.getContact()+" , "+customer.getMobile()
        );

    }
}
