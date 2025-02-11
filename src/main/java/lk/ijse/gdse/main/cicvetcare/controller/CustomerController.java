package lk.ijse.gdse.main.cicvetcare.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse.main.cicvetcare.bo.BOFactory;
import lk.ijse.gdse.main.cicvetcare.bo.custom.CustomerBO;
import lk.ijse.gdse.main.cicvetcare.dto.CustomerDto;
import lk.ijse.gdse.main.cicvetcare.tm.CustomerTm;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnSend;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CustomerTm, String> colContact;

    @FXML
    private TableColumn<CustomerTm, String> colCustId;

    @FXML
    private TableColumn<CustomerTm, String> colName;

    @FXML
    private TableColumn<CustomerTm, String> colType;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtContact;

    @FXML
    private AnchorPane ancCustomer;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtType;


    CustomerBO customerBo =(CustomerBO) BOFactory.getInstance().getBO(BOFactory.BoType.CUSTOMER);

    public void AddBtnOnClickAction(ActionEvent actionEvent) throws SQLException {
        String custId = lblCustomerId.getText();
        String custName = txtName.getText();
        String custType = txtType.getText();
        String custContact = txtContact.getText();

        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");
        txtType.setStyle(txtType.getStyle() + " -fx-border-color: blue;");
        txtContact.setStyle(txtContact.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidName = custName.matches(namePattern);
        boolean isValidContactInfo = custContact.matches(emailPattern);

        if (!isValidName){
            txtName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidContactInfo){
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }
        if (isValidName && isValidContactInfo){
            CustomerDto customerDto = new CustomerDto(custId, custName, custType, custContact);

            boolean isSaved  = customerBo.save(customerDto);
            if (isSaved){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Customer Not Saved").show();
            }
        }
    }

    public void DeleteBtnOnClickAction(ActionEvent actionEvent) throws SQLException {
        String customerId = lblCustomerId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure ?",ButtonType.NO,ButtonType.YES);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = customerBo.delete(customerId);
            if (isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted Successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Customer Not Deleted").show();
            }
        }
    }

    public void UpdateBtnOnClickAction(ActionEvent actionEvent) throws SQLException {
        String custId = lblCustomerId.getText();
        String custName = txtName.getText();
        String custType = txtType.getText();
        String custContact = txtContact.getText();

        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");
        txtType.setStyle(txtType.getStyle() + " -fx-border-color: blue;");
        txtContact.setStyle(txtContact.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidName = custName.matches(namePattern);
        boolean isValidContactInfo = custContact.matches(emailPattern);

        if (!isValidName){
            txtName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidContactInfo){
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }
        if (isValidName && isValidContactInfo){
            CustomerDto customerDto = new CustomerDto(custId, custName, custType, custContact);

            boolean isUpated  = customerBo.update(customerDto);
            if (isUpated){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated Successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Customer Not Updated").show();
            }
        }
    }

    public void tblCustomerOnClickAction(MouseEvent mouseEvent) {
        CustomerTm customerTm = (CustomerTm) tblCustomer.getSelectionModel().getSelectedItem();
        if (customerTm != null){
            lblCustomerId.setText(customerTm.getCustId());
            txtName.setText(customerTm.getCustName());
            txtType.setText(customerTm.getType());
            txtContact.setText(customerTm.getContact());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try{
            refreshPage();
        }catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Load Page").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextCustomerId();
        loadTableData();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtName.setText("");
        txtType.setText("");
        txtContact.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<CustomerDto> customerDtos = customerBo.getAll();

        ObservableList<CustomerTm> customerTms = FXCollections.observableArrayList();

        for (CustomerDto customerDto : customerDtos) {
            CustomerTm customerTm = new CustomerTm(
                    customerDto.getCustId(),
                    customerDto.getCustName(),
                    customerDto.getType(),
                    customerDto.getContact()
            );
            customerTms.add(customerTm);
        }
        tblCustomer.setItems(customerTms);
    }

    private void loadNextCustomerId() throws SQLException {
        String nextCusId = customerBo.getNextId();
        lblCustomerId.setText(nextCusId);
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }

    public void btnSearchOnClickAction(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SearchCustomer.fxml"));
            Parent load = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Search Customer");
            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);
            stage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Load Page").show();
        }
    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        CustomerTm selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!");
            return;
        }

        try {
            // Load the mail dialog from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustMailSender.fxml"));
            Parent load = loader.load();

            CustMailSenderController sendMailController = loader.getController();

            String email = selectedItem.getContact();
            sendMailController.setCustMail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");
            // stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/mail_icon.png")));

            // Set window as modal
            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }
}
