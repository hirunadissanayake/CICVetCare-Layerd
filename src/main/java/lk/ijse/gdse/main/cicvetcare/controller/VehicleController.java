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
import lk.ijse.gdse.main.cicvetcare.bo.BOFactory;
import lk.ijse.gdse.main.cicvetcare.bo.custom.VehicleBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.VehicleDAO;
import lk.ijse.gdse.main.cicvetcare.dto.VehicleDto;
import lk.ijse.gdse.main.cicvetcare.tm.VehicleTm;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.VehicleDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VehicleController implements Initializable {

    @FXML
    private AnchorPane ancVehicle;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<VehicleTm, String> colDriverId;

    @FXML
    private TableColumn<VehicleTm, String> colLicense;

    @FXML
    private TableColumn<VehicleTm, String> colType;

    @FXML
    private TableColumn<VehicleTm, String> colVehicleId;

    @FXML
    private Label lblVehicleId;

    @FXML
    private TableView<VehicleTm> tblVehicle;

    @FXML
    private TextField txtDriverId;

    @FXML
    private TextField txtLicense;

    @FXML
    private TextField txtType;

    @FXML
    VehicleBO vehicleBO = (VehicleBO) BOFactory.getInstance().getBO(BOFactory.BoType.VEHICLE);

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String vehicleId = lblVehicleId.getText();
        String vehicleType = txtType.getText();
        String licensePlate = txtLicense.getText();
        String driverId = txtDriverId.getText();

        txtType.setStyle(txtType.getStyle() + " -fx-border-color: blue;");
        txtLicense.setStyle(txtLicense.getStyle() + " -fx-border-color: blue;");
        txtDriverId.setStyle(txtDriverId.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String licensePlatePattern = "^[A-Z]{2}-\\d{4}$|^[A-Z]{2}\\s\\d{4}[A-Z]?$|^[A-Z]{3}\\s\\d{4}$\n";
        String driverIdPattern = "^D\\d{3,}$";

        boolean isValidName = vehicleType.matches(namePattern);
        boolean isValidLicense = licensePlate.matches(licensePlatePattern);
        boolean isValidDriver = driverId.matches(driverIdPattern);

        if (!isValidName){
            txtType.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidLicense){
            txtLicense.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidDriver){
            txtDriverId.setStyle("-fx-border-color: red;");
            return;
        }

        VehicleDto vehicleDto = new VehicleDto(vehicleId, vehicleType, licensePlate, driverId);

        boolean isSaved = vehicleBO.save(vehicleDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Vehicle Added Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Add Vehicle").show();
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String vehicleId = lblVehicleId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> optional = alert.showAndWait();

        if (optional.isPresent() && optional.get() == ButtonType.YES) {
            boolean isDeleted = vehicleBO.delete(vehicleId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Vehicle Deleted Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Delete Vehicle").show();
            }
        }

    }




    private void refreshPage() throws SQLException {
        loadNextVehicleId();
        loadTableData();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtType.clear();
        txtLicense.clear();
        txtDriverId.clear();
    }

    private void loadTableData() throws SQLException {
        ArrayList<VehicleDto> vehicleDtos = vehicleBO.getAll();

        ObservableList<VehicleTm> vehicleTms = FXCollections.observableArrayList();

        for (VehicleDto vehicleDto : vehicleDtos) {
            VehicleTm vehicleTm = new VehicleTm(
                    vehicleDto.getVehicleId(),
                    vehicleDto.getVehicleType(),
                    vehicleDto.getLicensePlate(),
                    vehicleDto.getDriverId()
            );
            vehicleTms.add(vehicleTm);
        }

        tblVehicle.setItems(vehicleTms);
    }


    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String vehicleId = lblVehicleId.getText();
        String vehicleType = txtType.getText();
        String licensePlate = txtLicense.getText();
        String driverId = txtDriverId.getText();

        txtType.setStyle(txtType.getStyle() + " -fx-border-color: blue;");
        txtLicense.setStyle(txtLicense.getStyle() + " -fx-border-color: blue;");
        txtDriverId.setStyle(txtDriverId.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String licensePlatePattern = "^([A-Z]{2}-\\d{4}|[A-Z]{2}\\s\\d{4}|[A-Z]{3}\\s\\d{4}|[A-Z]{2}\\s\\d{4}[A-Z]?)$";
        String driverIdPattern = "^D\\d{3,}$";

        boolean isValidName = vehicleType.matches(namePattern);
        boolean isValidLicense = licensePlate.matches(licensePlatePattern);
        boolean isValidDriver = driverId.matches(driverIdPattern);

        if (!isValidName){
            txtType.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidLicense){
            txtLicense.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidDriver){
            txtDriverId.setStyle("-fx-border-color: red;");
            return;
        }

        VehicleDto vehicleDto = new VehicleDto(vehicleId, vehicleType, licensePlate, driverId);

        boolean isUpdated = vehicleBO.update(vehicleDto);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Vehicle Updated Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Update Vehicle").show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colLicense.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));

        try{
            refreshPage();
        }catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Load Page").show();
        }

    }
    private void loadNextVehicleId() throws SQLException {
        String nextVehicleId = vehicleBO.getNextId();
        lblVehicleId.setText(nextVehicleId);
    }

    public void tblVehicleOnAction(MouseEvent mouseEvent) {
        VehicleTm vehicleTm = tblVehicle.getSelectionModel().getSelectedItem();
        if (vehicleTm != null) {
            lblVehicleId.setText(vehicleTm.getVehicleId());
            txtType.setText(vehicleTm.getVehicleType());
            txtLicense.setText(vehicleTm.getLicensePlate());
            txtDriverId.setText(vehicleTm.getDriverId());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
}
