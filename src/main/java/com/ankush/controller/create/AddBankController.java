package com.ankush.controller.create;

import com.ankush.data.entities.Bank;
import com.ankush.data.service.BankService;
import com.ankush.view.AlertNotification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Component
public class AddBankController implements Initializable {
    @FXML private AnchorPane mainPane;
    @FXML private TextField txtBankName;
    @FXML private TextField txtAccountNo;
    @FXML private TextField txtIFSC;
    @FXML private TextField txtBranch;
    @FXML private TextField txtWoner;
    @FXML private TextField txtBalance;
    @FXML private Button btnSave;
    @FXML private Button btnUpdate;
    @FXML private Button btnClear;
    @FXML private Button btnHome;
    @FXML private TableView<Bank> table;
    @FXML private TableColumn<Bank,Integer> colSrNo;
    @FXML private TableColumn<Bank,String> colBankName;
    @FXML private TableColumn<Bank,String> colAccountNo;
    @FXML private TableColumn<Bank,String> colIFSC;
    @FXML private TableColumn<Bank,String> colBranch;
    @FXML private TableColumn<Bank,Float> colBalance;
    @FXML private TableColumn<Bank,String> colWoner;
    @Autowired
    private BankService bankService;
    @Autowired
    private AlertNotification alert;
    private ObservableList<Bank>list = FXCollections.observableArrayList();
    private Integer id;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id=null;
        colSrNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBankName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAccountNo.setCellValueFactory(new PropertyValueFactory<>("accountno"));
        colIFSC.setCellValueFactory(new PropertyValueFactory<>("ifsc"));
        colBranch.setCellValueFactory(new PropertyValueFactory<>("branch"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colWoner.setCellValueFactory(new PropertyValueFactory<>("woner"));
        list.addAll(bankService.getAllBank());
        table.setItems(list);
        btnSave.setOnAction(e->save());
        btnClear.setOnAction(e->clear());
        btnUpdate.setOnAction(e->update());

    }

    private void update() {
        if(table.getSelectionModel().getSelectedItem()==null) return;
        Bank bank = table.getSelectionModel().getSelectedItem();
        txtBankName.setText(bank.getName());
        txtBalance.setText(String.valueOf(bank.getBalance()));
        txtAccountNo.setText(bank.getAccountno());
        txtBranch.setText(bank.getBranch());
        txtWoner.setText(bank.getWoner());
        txtIFSC.setText(bank.getIfsc());
        id=bank.getId();
    }

    private void save() {
        if(!validate()) return;
        Bank bank = Bank.builder()
                .ifsc(txtIFSC.getText())
                .accountno(txtAccountNo.getText())
                .balance(Float.parseFloat(txtBalance.getText()))
                .name(txtBankName.getText())
                .woner(txtWoner.getText())
                .branch(txtBranch.getText())
                .build();
        if(id!=null) bank.setId(id);
        int flag = bankService.save(bank);
        if(flag==1)
        {
            alert.showSuccess("Bank Save Success");
            addInList(bank);
            clear();
        }
        else if(flag==2)
        {
            alert.showSuccess("Bank update Success");
            addInList(bank);
            clear();
        }
        else{
            alert.showError("Error in Saving Bank");
        }
    }

    private void addInList(Bank b) {
        int index=-1;
        for(Bank bank:list)
        {
            if(bank.getId()==b.getId())
            {
                index=list.indexOf(bank);
                break;
            }
        }
        if(index==-1)
        {
            list.add(b);
            table.refresh();
        }
        else {
            list.remove(index);
            list.add(index,b);
            table.refresh();
        }
    }

    private void clear() {
        try {
            System.out.println("Report generate" + createReport("html"));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        txtBankName.setText("");
        txtBalance.setText("");
        txtAccountNo.setText("");
        txtBranch.setText("");
        txtWoner.setText("");
        txtIFSC.setText("");
        id=null;
    }

    private boolean validate()
    {
        if(txtBankName.getText().isEmpty())
        {
            alert.showError("Enter Bank Name");
            txtBankName.requestFocus();
            return false;
        }
        if(txtAccountNo.getText().isEmpty())
        {
            alert.showError("Enter Bank Account No");
            txtAccountNo.requestFocus();
            return false;
        }
        if(txtIFSC.getText().isEmpty())
        {
            txtIFSC.setText("-");
        }
        if(txtBranch.getText().isEmpty())
        {
            txtBranch.setText("-");
        }
        if(txtWoner.getText().isEmpty())
        {
            alert.showError("Enter Woner Name");
            txtWoner.requestFocus();
            return false;
        }
        if(txtBalance.getText().isEmpty())
        {
            alert.showError("Enter Bank Current Balance");
            txtBalance.requestFocus();
            return false;
        }
        return true;
    }
    private String createReport(String formate) throws FileNotFoundException, JRException {
        List<Bank>list = bankService.getAllBank();
        Bank b = bankService.getById(1);
        String path="C:\\Users\\Ankush\\Desktop\\report";
        File file = ResourceUtils.getFile("classpath:report/Bank.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("CreatedBy","Ankush");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,dataSource);
        if(formate.equals("pdf"))
        {
        JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\Bank.pdf");
        }
        if(formate.equalsIgnoreCase("html"))
        {
            JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\Bank.html");

        }
        return "Report Generated "+path;
    }
}
