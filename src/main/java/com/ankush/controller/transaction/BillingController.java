package com.ankush.controller.transaction;

import com.ankush.config.StageManager;
import com.ankush.data.entities.Bill;
import com.ankush.data.entities.Customer;
import com.ankush.data.entities.ItemStock;
import com.ankush.data.entities.Transaction;
import com.ankush.data.service.*;
import com.ankush.view.AlertNotification;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;
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
    @FXML private ComboBox<String> cmbBank;
    @FXML private TableView<Transaction> tableTr;
    @FXML private TableColumn<Transaction, Float> colAmount;
    @FXML private TableColumn<Transaction,Long> colNo;
    @FXML private TableColumn<Transaction,String> colPartName;
    @FXML private TableColumn<Transaction,String> colPartNo;
    @FXML private TableColumn<Transaction,Float> colQty;
    @FXML private TableColumn<Transaction,Float> colRate;
    @FXML private DatePicker date;

    @FXML private TextField txtAmount;
    @FXML private TextField txtBillno,txtOther,txtPaid,txtPartName,txtPartNo,txtQty,txtRate;
    @FXML private TextField txtCustomer,txtGrand,txtNetTotal;
    @FXML private TextArea txtCustomerInfor;

    @FXML private TextField txtSearchBill;
    @FXML private TextField txtSearchCustomer;
    @FXML private DatePicker txtSearchDate;

    @Autowired private ItemStockService stockService;
    @Autowired private ItemService itemService;
    @Autowired private CustomerService customerService;
    @Autowired private BankService bankService;
    @Autowired private AlertNotification alert;
    @Autowired private BillService billService;

    private SuggestionProvider customerNameProvider;
    private SuggestionProvider partnoProvider;
    private SuggestionProvider partNameProvider;
    private Customer customer;
    private ObservableList<Transaction>trList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date.setValue(LocalDate.now());
        customer = null;
        customerNameProvider = SuggestionProvider.create(customerService.getAllCustomerNames());
        new AutoCompletionTextFieldBinding<>(txtCustomer,customerNameProvider);

        partnoProvider = SuggestionProvider.create(itemService.getAllPartNo());
        new AutoCompletionTextFieldBinding<>(txtPartNo,partnoProvider);


        partNameProvider = SuggestionProvider.create(itemService.getAllItemNames());
        new AutoCompletionTextFieldBinding<>(txtPartName,partNameProvider);

        cmbBank.getItems().addAll(bankService.getAllBankNames());
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("partname"));
        colPartNo.setCellValueFactory(new PropertyValueFactory<>("partno"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        tableTr.setItems(trList);

        btnSearch.setOnAction(e->searchCustomer());
        txtRate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtRate.setText(oldValue);
            }});
        txtQty.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtQty.setText(oldValue);
            }});
        txtOther.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtOther.setText(oldValue);
            }});
        txtPaid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtPaid.setText(oldValue);
            }});
        txtPartNo.setOnAction(e->{
            if(txtPartNo.getText().isEmpty() || txtPartNo.getText().equals("")){
                txtPartName.requestFocus();
            }else if(stockService.findByItem_Partno(txtPartNo.getText())!=null)
            setStock(stockService.findByItem_Partno(txtPartNo.getText()));
            txtPartName.requestFocus();
        });
        txtPartName.setOnAction(e->{
            if(txtPartName.getText().isEmpty() || txtPartName.getText().equals(""))
            {
                return;
            }
            else if(stockService.findByItem_Itemname(txtPartName.getText())!=null){
                setStock(stockService.findByItem_Itemname(txtPartName.getText()));
                txtQty.requestFocus();
            }
        });
        txtQty.setOnAction(e->{
            calculateAmount();
            txtAmount.requestFocus();
        });
        txtRate.setOnAction(e->{
            calculateAmount();
            btnAdd.fire();
        });
        txtOther.setOnAction(e->calculateGrandTotal());
        btnAdd.setOnAction(e->add());
        btUpdate.setOnAction(e->update());
        btnRemove.setOnAction(e->remove());
        btnClear.setOnAction(e->clear());
        btnSave.setOnAction(e->save());


    }

    private void save() {
        if(!validateBill())return;
        Bill bill = Bill.builder()
                .bank(bankService.getByName(cmbBank.getValue()))
                .customer(customer)
                .date(date.getValue())
                .grand(Float.parseFloat(txtGrand.getText()))
                .nettotal(Float.parseFloat(txtNetTotal.getText()))
                .other(Float.parseFloat(txtOther.getText()))
                .paid(Float.parseFloat(txtPaid.getText()))
                .build();
        trList.forEach(t->t.setId(null));
        trList.forEach(t->t.setBill(bill));
        bill.setTransactions(trList);
        int flag = billService.save(bill);
        if(flag==1)
        {
            alert.showSuccess("Bill Save Success");
            claerBill();
        }

    }

    private void claerBill() {
        customer=null;
        txtCustomer.setText("");
        txtCustomerInfor.setText("");
        trList.clear();
        txtNetTotal.setText(""+0.0f);
        txtOther.setText(""+0.0f);
        txtGrand.setText(""+0.0f);
        txtPaid.setText("");
        cmbBank.getSelectionModel().clearSelection();
        date.setValue(LocalDate.now());
        txtBillno.setText("");
        clear();
    }

    private boolean validateBill() {
        if(trList.size()==0)
        {
            alert.showError("NO Data to Save");
            return false;
        }
        if(txtCustomerInfor.getText().isEmpty())
        {
            alert.showError("Select Customer Again");
            txtCustomer.requestFocus();
            return false;
        }
        if(customer==null)
        {
            alert.showError("Customer not Found Select Again");
            txtCustomer.requestFocus();
            return false;
        }
        if(cmbBank.getValue()==null || cmbBank.getSelectionModel().getSelectedItem()==null)
        {
            alert.showError("Select Bank");
            cmbBank.requestFocus();
            return false;
        }
        if(txtPaid.getText().isEmpty() || txtPaid.getText().equals(""))
        {
            alert.showError("Enter Paid Amount");
            txtPaid.requestFocus();
            return false;
        }
        return true;
    }

    private void clear() {
        txtPartName.setText("");
        txtPartNo.setText("");
        txtQty.setText(""+0.0f);
        txtRate.setText(""+0.0f);
        txtAmount.setText(""+0.0f);
        txtPartNo.requestFocus();
    }
    private void remove() {
        if(tableTr.getSelectionModel().getSelectedItem()==null) return;
        txtNetTotal.setText(
                String.valueOf(Float.parseFloat(txtNetTotal.getText())-
                        trList.get(tableTr.getSelectionModel().getSelectedIndex()).getAmount())
        );
        trList.remove(tableTr.getSelectionModel().getSelectedIndex());
        tableTr.refresh();
        calculateGrandTotal();
    }

    private void update() {
        if(tableTr.getSelectionModel().getSelectedItem()==null)return;
        Transaction tr = tableTr.getSelectionModel().getSelectedItem();
        txtPartNo.setText(tr.getPartno());
        txtPartName.setText(tr.getPartname());
        txtQty.setText(String.valueOf(tr.getQuantity()));
        txtRate.setText(String.valueOf(tr.getRate()));
        calculateAmount();
    }

    private void add() {
        if(!validate()) return;
       Transaction tr = Transaction.builder()
               .amount(Float.parseFloat(txtAmount.getText()))
               .partname(txtPartName.getText())
               .partno(txtPartNo.getText())
               .quantity(Float.parseFloat(txtQty.getText()))
               .rate(Float.parseFloat(txtRate.getText()))
               .build();
        addInTrList(tr);
        clear();

    }

    private void addInTrList(Transaction tr) {
        int index=-1;
        for(Transaction t:trList)
        {
            if(t.getPartname().equalsIgnoreCase(tr.getPartname()) &&
                    t.getPartno().equalsIgnoreCase(tr.getPartno()) &&
                    t.getRate().longValue()==tr.getRate().longValue()
            )
            {
                index = trList.indexOf(t);
                break;
            }
        }
        if(index==-1)
        {
            tr.setId(Long.valueOf(1));
            trList.add(tr);
            tableTr.refresh();
        }
        else
        {
            tr.setId(Long.valueOf(trList.size()+1));
            trList.get(index).setQuantity(trList.get(index).getQuantity()+tr.getQuantity());
            trList.get(index).setAmount(trList.get(index).getRate()*trList.get(index).getQuantity());
            tableTr.refresh();
        }
        txtNetTotal.setText(
                String.valueOf(Float.parseFloat(txtNetTotal.getText())+tr.getAmount())
        );
        calculateGrandTotal();
    }

    private void calculateGrandTotal() {
        if(txtNetTotal.getText().isEmpty())txtNetTotal.setText(""+0.0f);
        if(txtOther.getText().isEmpty())txtOther.setText(""+0.0f);
        txtGrand.setText(
                String.valueOf(Float.parseFloat(txtNetTotal.getText())+Float.parseFloat(txtOther.getText()))
        );

    }

    private boolean validate() {
        if(txtPartName.getText().isEmpty())
        {
            alert.showError("Enter Item Name");
            txtPartName.requestFocus();
            return false;
        }
        if(txtQty.getText().isEmpty()|| txtQty.getText().equals(""+0.0f))
        {
            alert.showError("Enter Quantity");
            txtQty.requestFocus();
            return false;
        }
        if(txtRate.getText().isEmpty() || txtRate.getText().equals(""+0.0f))
        {
            alert.showError("Enter Part Rate");
            txtRate.requestFocus();
            return false;
        }
        if(txtAmount.getText().isEmpty() || txtAmount.getText().equals(""+0.0f))
        {
            alert.showError("Select Part Again");
            txtPartName.requestFocus();
            return false;
        }
        calculateAmount();
        return true;
    }

    private void setStock(ItemStock stock) {
        if(null!=stock)
        {
            txtPartNo.setText(stock.getItem().getPartno());
            txtPartName.setText(stock.getItem().getItemname());
            txtRate.setText(String.valueOf(stock.getSallingrate()));
            calculateAmount();
        }
    }

    private void calculateAmount() {
        if(txtQty.getText().isEmpty() ||txtQty.getText().equals(""+0.0f)) {
            txtQty.setText("" + 0.0f);
            txtQty.requestFocus();
            return;
        }
        if(txtRate.getText().isEmpty() ||txtRate.getText().equals(""+0.0f)){
            txtRate.setText(""+0.0f);
            txtRate.requestFocus();
            return;
        }
        txtAmount.setText(
                String.valueOf(Float.parseFloat(txtRate.getText())*Float.parseFloat(txtQty.getText()))
        );


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
