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
import lk.ijse.gdse.main.cicvetcare.bo.custom.DeliveryBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.DeliveryDAO;
import lk.ijse.gdse.main.cicvetcare.dto.DeliveryDto;
import lk.ijse.gdse.main.cicvetcare.tm.DeliveryTm;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.DeliveryDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeliveryController implements Initializable {

    @FXML
    private AnchorPane ancDelivery;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<DeliveryTm> tblDelivery;

    @FXML
    private TableColumn<DeliveryTm, String> colDeliveryId;

    @FXML
    private TableColumn<DeliveryTm, LocalDate> colDeliveryDate;

    @FXML
    private TableColumn<DeliveryTm, String> colDeliveryStatus;

    @FXML
    private TableColumn<DeliveryTm, String> colOrderId;

    @FXML
    private TableColumn<DeliveryTm, String> colVehicleId;

    @FXML
    private TableColumn<DeliveryTm, String> colDriverId;

    @FXML
    private TableColumn<DeliveryTm, String> colShopId;

    @FXML
    private Label lblDeliveryId;

    @FXML
    private TextField txtDeliveryDate;

    @FXML
    private ComboBox<String> cmbDeliveryStatus;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtVehicleId;

    @FXML
    private TextField txtDriverId;

    @FXML
    private TextField txtShopId;

     DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getInstance().getBO(BOFactory.BoType.DELIVERY);

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String deliveryId = lblDeliveryId.getText();
        LocalDate deliveryDate = LocalDate.parse(txtDeliveryDate.getText());
        String deliveryStatus = cmbDeliveryStatus.getValue();
        String orderId = txtOrderId.getText();
        String vehicleId = txtVehicleId.getText();
        String driverId = txtDriverId.getText();
        String shopId = txtShopId.getText();

        DeliveryDto deliveryDto = new DeliveryDto(deliveryId, deliveryDate, deliveryStatus, orderId, vehicleId, driverId, shopId);

        boolean isSaved = deliveryBO.save(deliveryDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Delivery Added Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Add Delivery").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String deliveryId = lblDeliveryId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.YES) {
            boolean isDeleted = deliveryBO.delete(deliveryId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Delivery Deleted Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Delete Delivery").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String deliveryId = lblDeliveryId.getText();
        LocalDate deliveryDate = LocalDate.parse(txtDeliveryDate.getText());
        String deliveryStatus = cmbDeliveryStatus.getValue();
        String orderId = txtOrderId.getText();
        String vehicleId = txtVehicleId.getText();
        String driverId = txtDriverId.getText();
        String shopId = txtShopId.getText();

        DeliveryDto deliveryDto = new DeliveryDto(deliveryId, deliveryDate, deliveryStatus, orderId, vehicleId, driverId, shopId);

        boolean isUpdated = deliveryBO.update(deliveryDto);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Delivery Updated Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Update Delivery").show();
        }
    }

    @FXML
    void tblDeliveryOnAction(MouseEvent event) {
        DeliveryTm selectedDelivery = tblDelivery.getSelectionModel().getSelectedItem();
        if (selectedDelivery != null) {
            lblDeliveryId.setText(selectedDelivery.getDeliveryId());
            txtDeliveryDate.setText(selectedDelivery.getDeliveryDate().toString());
            cmbDeliveryStatus.setValue(selectedDelivery.getDeliveryStatus());
            txtOrderId.setText(selectedDelivery.getOrderId());
            txtVehicleId.setText(selectedDelivery.getVehicleId());
            txtDriverId.setText(selectedDelivery.getDriverId());
            txtShopId.setText(selectedDelivery.getShopId());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        loadNextDeliveryId();
        loadTableData();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtDeliveryDate.clear();
        cmbDeliveryStatus.getSelectionModel().clearSelection();
        txtOrderId.clear();
        txtVehicleId.clear();
        txtDriverId.clear();
        txtShopId.clear();
    }

    private void loadTableData() throws SQLException {
        ArrayList<DeliveryDto> deliveries = deliveryBO.getAll();
        ObservableList<DeliveryTm> deliveryTms = FXCollections.observableArrayList();

        for (DeliveryDto delivery : deliveries) {
            deliveryTms.add(new DeliveryTm(
                    delivery.getDeliveryId(),
                    delivery.getDeliveryDate(),
                    delivery.getDeliveryStatus(),
                    delivery.getOrderId(),
                    delivery.getVehicleId(),
                    delivery.getDriverId(),
                    delivery.getShopId()
            ));
        }

        tblDelivery.setItems(deliveryTms);
    }

    private void loadNextDeliveryId() throws SQLException {
        String nextId = deliveryBO.getNextId();
        lblDeliveryId.setText(nextId);
    }

    public void cmbDeliveryStatusOnAction(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDeliveryId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colDeliveryStatus.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colShopId.setCellValueFactory(new PropertyValueFactory<>("shopId"));

        try{
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Delivery Id").show();
        }
        cmbDeliveryStatus.setItems(FXCollections.observableArrayList("Pending", "In Transit", "Delivered","Cancelled"));
        try {
            loadNextDeliveryId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            loadTableData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
