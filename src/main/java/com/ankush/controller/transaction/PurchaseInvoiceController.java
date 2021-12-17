package com.ankush.controller.transaction;

import com.ankush.Main;
import com.ankush.common.CommonData;
import com.ankush.config.SpringFXMLLoader;
import com.ankush.config.StageManager;
import com.ankush.data.entities.ItemStock;
import com.ankush.data.entities.PurchaseInvoice;
import com.ankush.data.entities.PurchaseParty;
import com.ankush.data.entities.PurchaseTransaction;
import com.ankush.data.service.ItemService;
import com.ankush.data.service.ItemStockService;
import com.ankush.data.service.PurchasePartyService;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.ResourceBundle;
@Component
public class PurchaseInvoiceController implements Initializable {
    @Autowired @Lazy
    private StageManager stageManager;
    @Autowired
    private SpringFXMLLoader fxmlLoader;
    @FXML private Button btnAdd,btnAddNew,btnClear2,btnHome,btnPrint,btnRemove,btnSave,btnSearchParty,btnUpdate,btnUpdate2,btnView;

    @FXML private ComboBox<String> cmBank;
    @FXML private TextField cmbPaid;
    @FXML private TableView<PurchaseTransaction> tableTr;
    @FXML private TableColumn<PurchaseTransaction,Long> colSrNo;
    @FXML private TableColumn<PurchaseTransaction,Long> colPartNo;
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
    @FXML private TableColumn<PurchaseInvoice,Float> colRemaining;

    @FXML private DatePicker date;
    @FXML private DatePicker dateSearch;

    @FXML private TextField txtAmount, txtGrand,txtInvoice,txtInvoiceSearch;
    @FXML private TextField txtNetTotal,txtOther,txtPartName,txtPartNo,txtPartRate,txtPartyName;
    @FXML private TextField txtPartySearch,txtQuantity,txtTransport;
    @FXML private TextArea txtPartyInfo;

    @Autowired
    private PurchasePartyService partyService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemStockService stockService;
    private  SuggestionProvider<String> partyNameProvider;
    private  SuggestionProvider<String> itemNameProvider;
    private ItemStock stock;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date.setValue(LocalDate.now());
        stock=null;
        partyNameProvider = SuggestionProvider.create(partyService.getAllPartyNames());
        new AutoCompletionTextFieldBinding<>(txtPartyName,partyNameProvider);
        itemNameProvider = SuggestionProvider.create(itemService.getAllItemNames());
        new AutoCompletionTextFieldBinding<>(txtPartName,itemNameProvider);
        btnSearchParty.setOnAction(e->searchParty());
        btnAddNew.setOnAction(e->addNewParty(e));
        txtPartNo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?"))
                    txtPartNo.setText(oldValue);
                else{
                    if(!txtPartNo.getText().isEmpty()) {
                        stock = stockService.findByItem_Partno(Long.valueOf(txtPartNo.getText()));
                        System.out.println(stock);
                        setStock(stock);
                    }}}});
        txtPartNo.setOnAction(e->{
            if(!txtPartNo.getText().isEmpty())
            {
                setStock(stockService.findByItem_Partno(Long.valueOf(txtPartNo.getText())));
                txtPartName.requestFocus();
            }
            txtPartName.requestFocus();
        });
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

}
