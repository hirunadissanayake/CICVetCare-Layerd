package lk.ijse.gdse.main.cicvetcare.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.main.cicvetcare.dto.SupplierDto;
import lk.ijse.gdse.main.cicvetcare.tm.SupplierTm;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.SupplierDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML
    private AnchorPane ancSupplier;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SupplierTm, String> colAddress;

    @FXML
    private TableColumn<SupplierTm, String> colContact;

    @FXML
    private TableColumn<SupplierTm, String> colName;

    @FXML
    private TableColumn<SupplierTm, String> colSupplierId;

    @FXML
    private Label lblSupplierId;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure ?",ButtonType.NO,ButtonType.YES);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = supplierModel.deleteSupplier(supplierId);
            if (isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted Successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Supplier Not Deleted").show();
            }
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String supId = lblSupplierId.getText();
        String supName = txtName.getText();
        String supContact = txtContact.getText();
        String supAddress = txtAddress.getText();

        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");
        txtContact.setStyle(txtContact.getStyle() + " -fx-border-color: blue;");
        txtAddress.setStyle(txtAddress.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String phonePattern = "^(07[0-9]{8}|0[1-9][0-9]{8})$";
        String addressPattern = "^[a-zA-Z0-9_.\\- ]+$";

        boolean isValidName = supName.matches(namePattern);
        boolean isValidContactInfo = supContact.matches(phonePattern);
        boolean isValidAddress = supAddress.matches(addressPattern);

        if (!isValidName){
            txtName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidContactInfo){
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidAddress){
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }

        if (isValidName && isValidContactInfo && isValidAddress){
            SupplierDto supplierDto = new SupplierDto(supId, supName, supContact, supAddress);

            boolean isUpdated  = supplierModel.updateSupplier(supplierDto);
            if (isUpdated){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Supplier Not Updated").show();
            }
        }

    }

    @FXML
    void tblSupplierOnAction(MouseEvent event) {
        SupplierTm supplierTm = tblSupplier.getSelectionModel().getSelectedItem();
        if (supplierTm != null){
            lblSupplierId.setText(supplierTm.getSupplierId());
            txtName.setText(supplierTm.getSupplierName());
            txtContact.setText(supplierTm.getSupplierContact());
            txtAddress.setText(supplierTm.getSupplierAddress());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    SupplierDAOImpl supplierModel = new SupplierDAOImpl();
    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String supId = lblSupplierId.getText();
        String supName = txtName.getText();
        String supContact = txtContact.getText();
        String supAddress = txtAddress.getText();

        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");
        txtContact.setStyle(txtContact.getStyle() + " -fx-border-color: blue;");
        txtAddress.setStyle(txtAddress.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String phonePattern = "^(07[0-9]{8}|0[1-9][0-9]{8})$";
        String addressPattern = "^[a-zA-Z0-9_.\\- ]+$";

        boolean isValidName = supName.matches(namePattern);
        boolean isValidContactInfo = supContact.matches(phonePattern);
        boolean isValidAddress = supAddress.matches(addressPattern);

        if (!isValidName){
            txtName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidContactInfo){
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidAddress){
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }

        if (isValidName && isValidContactInfo && isValidAddress){
            SupplierDto supplierDto = new SupplierDto(supId, supName, supContact, supAddress);

            boolean isSaved  = supplierModel.saveSupplier(supplierDto);
            if (isSaved){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Supplier Added Successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Supplier Not Saved").show();
            }
        }
    }

    private void refreshPage() throws SQLException {
        loadNextSupplierId();
        loadTableData();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtName.setText("");
        txtContact.setText("");
        txtAddress.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<SupplierDto> supplierDtos = supplierModel.getAllSuppliers();

        ObservableList<SupplierTm> supplierTms = FXCollections.observableArrayList();

        for (SupplierDto supplierDto : supplierDtos) {
            SupplierTm supplierTm = new SupplierTm(
                    supplierDto.getSupplierId(),
                    supplierDto.getSupplierName(),
                    supplierDto.getSupplierContact(),
                    supplierDto.getSupplierAddress()
            );
            supplierTms.add(supplierTm);
        }
        tblSupplier.setItems(supplierTms);
    }

    private void loadNextSupplierId() throws SQLException {
        String nextCusId = supplierModel.getNextSupplierId();
        lblSupplierId.setText(nextCusId);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("supplierContact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));

        try{
            refreshPage();
        }catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Load Page").show();
        }

    }
}




