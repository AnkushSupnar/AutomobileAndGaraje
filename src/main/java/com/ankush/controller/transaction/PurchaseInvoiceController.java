package com.ankush.controller.transaction;

import com.ankush.Main;
import com.ankush.common.CommonData;
import com.ankush.config.SpringFXMLLoader;
import com.ankush.config.StageManager;
import com.ankush.data.entities.*;
import com.ankush.data.service.*;
import com.ankush.view.AlertNotification;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
@Component
public class PurchaseInvoiceController implements Initializable {
    @Autowired @Lazy
    private StageManager stageManager;
    @Autowired
    private SpringFXMLLoader fxmlLoader;
    @FXML private Button btnAdd,btnAddNew,btnClear2,btnClear,btnHome,btnPrint,btnRemove,btnSave,btnSearchParty,btnUpdate,btnUpdate2,btnView;

    @FXML private ComboBox<String> cmbBank;
    @FXML private TextField txtPaid;
    @FXML private TableView<PurchaseTransaction> tableTr;
    @FXML private TableColumn<PurchaseTransaction,Long> colSrNo;
    @FXML private TableColumn<PurchaseTransaction,String> colPartNo;
    @FXML private TableColumn<PurchaseTransaction,String> colPartName;
    @FXML private TableColumn<PurchaseTransaction,Float> colRate;
    @FXML private TableColumn<PurchaseTransaction,Float> colQty;
    @FXML private TableColumn<PurchaseTransaction,Float> colAmount;

    @FXML private TableView<PurchaseInvoice> tableBill;
    @FXML private TableColumn<PurchaseInvoice,Long> colSrNo2;
    @FXML private TableColumn<PurchaseInvoice,String> colPartyName;
    @FXML private TableColumn<PurchaseInvoice,Float> colAmount2;
    @FXML private TableColumn<PurchaseInvoice, LocalDate> colDate;
    @FXML private TableColumn<PurchaseInvoice,String> colInvoiceNo;
    @FXML private TableColumn<PurchaseInvoice,Float> colPaid;
    @FXML private TableColumn<PurchaseInvoice,Number> colRemaining;

    @FXML private DatePicker date;
    @FXML private DatePicker dateSearch;

    @FXML private TextField txtAmount, txtGrand,txtInvoice,txtInvoiceSearch;
    @FXML private TextField txtNetTotal,txtOther,txtPartName,txtPartNo,txtPartRate,txtPartyName;
    @FXML private TextField txtPartySearch,txtQuantity,txtTransport;
    @FXML private TextArea txtPartyInfo;
    @Autowired private PurchasePartyService partyService;
    @Autowired private ItemService itemService;
    @Autowired private ItemStockService stockService;
    @Autowired private AlertNotification alert;
    @Autowired private BankService bankService;
    @Autowired private PurchaseInvoiceService purchaseService;

    private  SuggestionProvider<String> partyNameProvider;
    private  SuggestionProvider<String> itemNameProvider;
    private  SuggestionProvider<String> partnoProvider;
    private ItemStock stock;
    private ObservableList<PurchaseTransaction>trList = FXCollections.observableArrayList();
    private ObservableList<PurchaseInvoice>billList = FXCollections.observableArrayList();
    private Long id;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date.setValue(LocalDate.now());
        id=null;
        stock=null;
        cmbBank.getItems().addAll(bankService.getAllBankNames());
        partyNameProvider = SuggestionProvider.create(partyService.getAllPartyNames());
        new AutoCompletionTextFieldBinding<>(txtPartyName,partyNameProvider);
        itemNameProvider = SuggestionProvider.create(itemService.getAllItemNames());
        new AutoCompletionTextFieldBinding<>(txtPartName,itemNameProvider);
        partnoProvider = SuggestionProvider.create(itemService.getAllPartNo());
        new AutoCompletionTextFieldBinding<>(txtPartNo,partnoProvider);

        colSrNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartNo.setCellValueFactory(new PropertyValueFactory<>("partno"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("partname"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableTr.setItems(trList);


        colSrNo2.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartyName.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getParty().getName()));
        colAmount2.setCellValueFactory(new PropertyValueFactory<>("grandtotal"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colInvoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoiceno"));
        colPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        colRemaining.setCellValueFactory(cellData->new SimpleFloatProperty((cellData.getValue().getGrandtotal()-cellData.getValue().getPaid())));
        billList.addAll(purchaseService.getInvoiceByDate(date.getValue()));
        tableBill.setItems(billList);

        btnSearchParty.setOnAction(e->searchParty());
        btnAddNew.setOnAction(e->addNewParty(e));

        txtPartNo.setOnAction(e->{
            if(!txtPartNo.getText().isEmpty())
            {
                //partyNameProvider.clearSuggestions();
                partyNameProvider.addPossibleSuggestions(itemService.findByPartnoLike(txtPartNo.getText()));
            }
            else
            {

            }
            txtPartName.requestFocus();
        });
        txtPartName.setOnAction(e->{
            if(txtPartNo.getText().isEmpty())
            {
                setStock(stockService.findByItem_Itemname(txtPartName.getText()));
            }
            else{
                setStock(stockService.findByItem_Partno(txtPartNo.getText()));
            }
        });
        txtPartRate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtPartRate.setText(oldValue);
            }});
        txtPartRate.setOnAction(e->{
            if(!txtQuantity.getText().isEmpty() && !txtPartRate.getText().isEmpty())
            {
                txtAmount.setText(
                        String.valueOf(Float.parseFloat(txtQuantity.getText())*Float.parseFloat(txtPartRate.getText())));
            }
            txtQuantity.requestFocus();
        });
        txtQuantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtQuantity.setText(oldValue);
            }});
        txtQuantity.setOnAction(e->{
            if(!txtQuantity.getText().isEmpty() && !txtPartRate.getText().isEmpty())
            {
                txtAmount.setText(
                        String.valueOf(Float.parseFloat(txtQuantity.getText())*Float.parseFloat(txtPartRate.getText()))
                );
                txtAmount.requestFocus();
            }
        });
        txtAmount.setOnAction(e->btnAdd.requestFocus());
        txtTransport.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtTransport.setText(oldValue);
                else{
                    if(!txtTransport.getText().isEmpty()) calculateGrandTotal();
                }
            }});
        txtTransport.setOnAction(e->{
            if(txtTransport.getText().isEmpty())txtTransport.setText(""+0.0f);
            else
                calculateGrandTotal();
        });
        txtOther.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtOther.setText(oldValue);
                else{
                    if(!txtOther.getText().isEmpty()) calculateGrandTotal();
                }
            }});
        txtOther.setOnAction(e->{
            if(txtOther.getText().isEmpty())txtOther.setText(""+0.0f);
            else
                calculateGrandTotal();
        });
        btnAdd.setOnAction(e->add());
        btnUpdate.setOnAction(e->update());
        btnRemove.setOnAction(e->remove());
        btnClear.setOnAction(e->clear());

        btnSave.setOnAction(e->save());
        btnUpdate2.setOnAction(e->updateBill());
        btnClear2.setOnAction(e->clearBill());

    }
    private void clear()  {
        txtPartNo.setText("");
        txtPartName.setText("");
        txtPartRate.setText("");
        txtQuantity.setText("");
        txtAmount.setText(""+0.0f);
    }
    private void remove() {
        txtNetTotal.setText(
                String.valueOf(Float.parseFloat(txtNetTotal.getText())-
                        tableTr.getSelectionModel().getSelectedItem().getAmount())
        );
        calculateGrandTotal();
        trList.remove(tableTr.getSelectionModel().getSelectedIndex());
        tableTr.refresh();
    }
    private void update() {
        if(tableTr.getSelectionModel().getSelectedItem()==null) return;
        PurchaseTransaction tr =tableTr.getSelectionModel().getSelectedItem();
        txtPartNo.setText(tr.getPartno());
        txtPartName.setText(tr.getPartname());
        txtPartRate.setText(String.valueOf(tr.getRate()));
        txtQuantity.setText(String.valueOf(tr.getQuantity()));
        txtAmount.setText(String.valueOf(tr.getAmount()));
    }
    private void add() {
        if(!validate())return;
        txtAmount.setText(
                String.valueOf(Float.parseFloat(txtQuantity.getText())*Float.parseFloat(txtPartRate.getText()))
        );
        PurchaseTransaction tr = PurchaseTransaction.builder()
                .amount(Float.parseFloat(txtAmount.getText()))
                .partno(txtPartNo.getText())
                .partname(txtPartName.getText())
                .quantity(Float.parseFloat(txtQuantity.getText()))
                .rate(Float.parseFloat(txtPartRate.getText()))
                .build();
        addInTrList(tr);
        clear();
    }
    private void addInTrList(PurchaseTransaction tr) {
        try {
            int index = -1;
           for(PurchaseTransaction pt:trList)
           {
               if(pt.getPartno().equalsIgnoreCase(tr.getPartno())&&
               pt.getPartname().equalsIgnoreCase(tr.getPartname()) &&
               pt.getRate().longValue()==tr.getRate().longValue())
               {
                   System.out.println("Found");
                   index=trList.indexOf(pt);
                   break;
               }
           }
           if(index==-1){
               tr.setId((long) (trList.size()+1));
               trList.add(tr);
           }
           else{
               trList.get(index).setQuantity(trList.get(index).getQuantity()+tr.getQuantity());
               trList.get(index).setAmount(trList.get(index).getQuantity()*trList.get(index).getRate());
               tableTr.refresh();
           }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        txtNetTotal.setText(String.valueOf(Float.parseFloat(txtNetTotal.getText())+tr.getAmount()));
        calculateGrandTotal();
    }
    private boolean validate() {
        if(txtPartName.getText().isEmpty())
        {
            alert.showError("Enter Part Name");
            txtPartName.requestFocus();
            return false;
        }
        if(txtPartNo.getText().isEmpty())
        {
           txtPartNo.setText("-");
        }
        if(txtPartRate.getText().isEmpty())
        {
            alert.showError("Enter Part Rate");
            txtPartRate.requestFocus();
            return false;
        }
        if(txtQuantity.getText().isEmpty())
        {
            alert.showError("Enter Quantity");
            txtQuantity.requestFocus();
            return false;
        }
        if(null!=stockService.findByItem_Partno(txtPartNo.getText()) &&
                !stockService.findByItem_Partno(txtPartNo.getText()).getItem().getItemname().equals(txtPartName.getText()))
        {
            alert.showError("This part no is available to another Part");
            txtPartNo.requestFocus();
            return false;
        }

        if(txtAmount.getText().equals(""+0.0f))
        {
            alert.showError("Amount Must be greater than 0");
            return false;
        }

        return true;
    }
    private void setStock(ItemStock stock) {
        if(null!=stock) {
            txtPartName.setText(stock.getItem().getItemname());
            txtPartRate.setText(String.valueOf(stock.getPurchaserate()));
        }
    }
    private void searchParty() {
        if(txtPartyName.getText().isEmpty()) {
            txtPartyName.requestFocus();
            return ;
        }
       List< PurchaseParty> party = partyService.getPartyByName(txtPartyName.getText());
        if(party.size()>0)
        {
            txtPartyInfo.setText("Name: "+party.get(0).getName()+"\nAddress: "+party.get(0).getAddress()+"\nContact no: "+party.get(0).getContact());
        }
        else {
            txtPartyInfo.setText("");
            txtPartyName.requestFocus();
        }
    }
    private void addNewParty(ActionEvent e) {
       // stageManager.showAddNewParty(e,"/fxml/create/PurchaseParty.fxml");
        Stage stage = new Stage();
        Parent rootNode = null;
        try {
            rootNode = stageManager.getFxmlLoader().load("/fxml/create/PurchaseParty.fxml");
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
            stage.setScene(new Scene(rootNode));
            stage.setTitle("My modal window");
            stage.setTitle("My modal window");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) e.getSource()).getScene().getWindow());
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    partyNameProvider.clearSuggestions();
                    partyNameProvider.addPossibleSuggestions(partyService.getAllPartyNames());
                }
            });
        } catch (Exception exception) {
           // logAndExit("Unable to load FXML view" + filePath, exception);
        }


    }
    private void calculateGrandTotal(){
        if(txtTransport.getText().isEmpty()) txtTransport.setText(""+0.0f);
        if(txtOther.getText().isEmpty())txtOther.setText(""+0.0f);
        txtGrand.setText(
                String.valueOf(Float.parseFloat(txtNetTotal.getText())+
                        Float.parseFloat(txtTransport.getText())+
                        Float.parseFloat(txtOther.getText()))
        );
    }

    private void save() {
        if(!validateBill())return;
        PurchaseInvoice invoice = PurchaseInvoice.builder()
                .date(date.getValue())
                .bank(bankService.getByName(cmbBank.getValue()))
                .invoiceno(txtInvoice.getText())
                .party(partyService.getPartyByName(txtPartyName.getText()).get(0))
                .grandtotal(Float.parseFloat(txtGrand.getText()))
                .nettotal(Float.parseFloat(txtNetTotal.getText()))
                .other(Float.parseFloat(txtOther.getText()))
                .paid(Float.parseFloat(txtPaid.getText()))
                .transport(Float.parseFloat(txtTransport.getText())
                ).build();

        if(id!=null){
            invoice.setId(id);

        }
       trList.forEach(t->t.setId(null));
       trList.forEach(t->t.setInvoice(invoice));
        invoice.setPurchaseTransactions(trList);
        int flag=purchaseService.saveInvoice(invoice);
        invoice.getPurchaseTransactions().forEach(t-> System.out.println(t));
        if(flag==1)
        {
            addInStock(invoice);
            alert.showSuccess("Invoice Saved Success");
            addInBillList(invoice);
            clearBill();
        }
        else if(flag==2)
        {
            alert.showSuccess("Invoice Update Success");
            addInBillList(invoice);
            clearBill();
        }
        else {
            alert.showError("Error in Saving Invoice");
        }


    }

    private void addInStock(PurchaseInvoice invoice) {
        for(PurchaseTransaction t:invoice.getPurchaseTransactions())
        {
            Item item = Item.builder()
                    .partno(t.getPartno())
                    .itemname(t.getPartname()).build();
            itemService.saveItem(item);
            ItemStock stock = ItemStock.builder()
                    .item(item)
                    .quantity(t.getQuantity())
                    .purchaserate(t.getRate())
                    .sallingrate(0.0f)
                    .build();
            stockService.saveItemStock(stock);
        }
    }

    private void addInBillList(PurchaseInvoice invoice)
    {
        billList.clear();
        billList.addAll(purchaseService.getInvoiceByDate(LocalDate.now()));
        tableBill.refresh();
    }

    private void updateBill() {
        if(tableBill.getSelectionModel().getSelectedItem()==null) return;
        PurchaseInvoice invoice =
                purchaseService.getInvoiceById(tableBill.getSelectionModel().getSelectedItem().getId()).get();
        System.out.println("To Edit="+invoice);
        id=invoice.getId();
        txtInvoice.setText(invoice.getInvoiceno());
        txtPartyName.setText(invoice.getParty().getName());
        searchParty();
        trList.clear();
        invoice.getPurchaseTransactions().forEach(t->addInTrList(t));
        txtGrand.setText(String.valueOf(invoice.getGrandtotal()));
        txtOther.setText(String.valueOf(invoice.getOther()));
        txtTransport.setText(String.valueOf(invoice.getTransport()));
        txtPaid.setText(String.valueOf(invoice.getPaid()));
        cmbBank.setValue(invoice.getBank().getName());
        calculateGrandTotal();
    }
    private void clearBill()
    {
        id=null;
        txtInvoice.setText("");
        txtPartyName.setText("");
        txtPartyInfo.setText("");
        date.setValue(LocalDate.now());
        trList.clear();
        txtNetTotal.setText(""+0.0f);
        txtOther.setText(""+0.0f);
        txtTransport.setText(""+0.0f);
        txtGrand.setText(""+0.0f);
        txtPaid.setText(""+0.0f);
        cmbBank.getSelectionModel().clearSelection();
        clear();
    }
    private boolean validateBill() {
        if(txtPartyInfo.getText().isEmpty())
        {
            alert.showError("Enter Party");
            txtPartyName.requestFocus();
            return false;
        }
        if(trList.size()==0)
        {
            alert.showError("No Data To Save ");
            return false;
        }
        if(txtPaid.getText().isEmpty())
        {
            alert.showError("Enter Paid Amount");
            txtPaid.requestFocus();
            return false;
        }
        if(cmbBank.getValue()==null)
        {
            alert.showError("Select Bank Paid From");
            cmbBank.requestFocus();
            return false;
        }
        return true;
    }
}
