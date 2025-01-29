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
import lk.ijse.gdse.main.cicvetcare.dto.DriverDto;
import lk.ijse.gdse.main.cicvetcare.tm.DriverTm;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.DriverDAOImpl;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DriverController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colLicense.setCellValueFactory(new PropertyValueFactory<>("license"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("driverContact"));

        try{
            refreshPage();
        }catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Load Page").show();
        }

    }

    @FXML
    private AnchorPane ancDriver;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<DriverTm, String> colContact;

    @FXML
    private TableColumn<DriverTm, String> colDriverId;

    @FXML
    private TableColumn<DriverTm, String> colLicense;

    @FXML
    private TableColumn<DriverTm, String> colName;

    @FXML
    private Label lblDriverId;

    @FXML
    private TableView<DriverTm> tblDriver;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtLicense;

    @FXML
    private TextField txtName;

    @FXML
    private DriverDAOImpl driverModel = new DriverDAOImpl();
    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String driverId = lblDriverId.getText();
        String driverName = txtName.getText();
        String license = txtLicense.getText();
        String contact = txtContact.getText();

        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");
        txtLicense.setStyle(txtLicense.getStyle() + " -fx-border-color: blue;");
        txtContact.setStyle(txtContact.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String phonePattern = "^(07[0-9]{8}|0[1-9][0-9]{8})$";
        String licensePattern = "^[A-Z]{1}[0-9]{7,8}$";

        boolean isValidName = driverName.matches(namePattern);
        boolean isValidContactInfo = contact.matches(phonePattern);
        boolean isValidLicense = license.matches(licensePattern);

        if (!isValidName){
            txtName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidContactInfo){
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidLicense){
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }

        if (isValidName && isValidContactInfo && isValidLicense){
            DriverDto driverDto = new DriverDto(driverId, driverName, license, contact);

            boolean isSaved  = driverModel.saveDriver(driverDto);
            if (isSaved){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Driver Added Successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Driver Not Saved").show();
            }
        }

    }

    private void refreshPage() throws SQLException {
        loadNextDriverId();
        loadTableData();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtName.setText("");
        txtLicense.setText("");
        txtContact.setText("");

    }

    private void loadTableData() throws SQLException {
        ArrayList<DriverDto> driverDtos = driverModel.getAllDrivers();

        ObservableList<DriverTm> driverTms = FXCollections.observableArrayList();

        for (DriverDto driverDto  : driverDtos) {
            DriverTm driverTm = new DriverTm(
                    driverDto.getDriverId(),
                    driverDto.getDriverName(),
                    driverDto.getLicense(),
                    driverDto.getDriverContact()
            );
            driverTms.add(driverTm);
        }
        tblDriver.setItems(driverTms);
    }

    private void loadNextDriverId() throws SQLException {
        String nextCusId = driverModel.getNextDriverId();
        lblDriverId.setText(nextCusId);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String driverId = lblDriverId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure ?",ButtonType.NO,ButtonType.YES);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = driverModel.deleteDriver(driverId);
            if (isDeleted){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Driver Deleted Successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Driver Not Deleted").show();
            }
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String driverId = lblDriverId.getText();
        String driverName = txtName.getText();
        String license = txtLicense.getText();
        String contact = txtContact.getText();

        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");
        txtLicense.setStyle(txtLicense.getStyle() + " -fx-border-color: blue;");
        txtContact.setStyle(txtContact.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String phonePattern = "^(07[0-9]{8}|0[1-9][0-9]{8})$";
        String licensePattern = "^[A-Z]{1}[0-9]{7,8}$";

        boolean isValidName = driverName.matches(namePattern);
        boolean isValidContactInfo = contact.matches(phonePattern);
        boolean isValidLicense = license.matches(licensePattern);

        if (!isValidName){
            txtName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidContactInfo){
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidLicense){
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }

        if (isValidName && isValidContactInfo && isValidLicense){
            DriverDto driverDto = new DriverDto(driverId, driverName, license, contact);

            boolean isUpdated  = driverModel.updateDriver(driverDto);
            if (isUpdated){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Driver Updated Successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Driver Not Updated").show();
            }
        }

    }

    @FXML
    void tblDriverOnAction(MouseEvent event) {
        DriverTm driverTm = tblDriver.getSelectionModel().getSelectedItem();
        if (driverTm != null){
            lblDriverId.setText(driverTm.getDriverId());
            txtName.setText(driverTm.getDriverName());
            txtLicense.setText(driverTm.getLicense());
            txtContact.setText(driverTm.getDriverContact());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }

    }

}
