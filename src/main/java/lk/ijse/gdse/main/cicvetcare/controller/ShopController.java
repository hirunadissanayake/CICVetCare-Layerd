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
import lk.ijse.gdse.main.cicvetcare.dto.ShopDto;
import lk.ijse.gdse.main.cicvetcare.tm.ShopTm;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.ShopDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShopController implements Initializable {

    @FXML
    private AnchorPane ancShop;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbCustId;


    @FXML
    private TableColumn<ShopTm, String> colContactInfo;

    @FXML
    private TableColumn<ShopTm, String> colLocation;

    @FXML
    private TableColumn<ShopTm, String> colName;

    @FXML
    private TableColumn<ShopTm, String> colShopId;

    @FXML
    private TableColumn<ShopTm, String> colCustomerId;

    @FXML
    private Label lblShopId;

    @FXML
    private TableView<ShopTm> tblShop;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtContactInfo;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtCustomerId;

    private ShopDAOImpl shopModel = new ShopDAOImpl();

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String shopId = lblShopId.getText();
        String name = txtName.getText();
        String contactInfo = txtContactInfo.getText();
        String location = txtLocation.getText();
        String customerId = cmbCustId.getSelectionModel().getSelectedItem();

        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");
        txtContactInfo.setStyle(txtContactInfo.getStyle() + " -fx-border-color: blue;");
        txtLocation.setStyle(txtLocation.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String phonePattern = "^(07[0-9]{8}|0[1-9][0-9]{8})$";
        String addressPattern = "^[a-zA-Z0-9_.\\- ]+$";

        boolean isValidName =txtName.getText().matches(namePattern);
        boolean isValidContact =txtContactInfo.getText().matches(phonePattern);
        boolean isValidLocation =txtLocation.getText().matches(addressPattern);

        if (!isValidName){
            txtName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidContact){
            txtContactInfo.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidLocation){
            txtLocation.setStyle("-fx-border-color: red;");
            return;
        }
        if (name.isEmpty() || contactInfo.isEmpty() || location.isEmpty() || customerId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
            return;
        }

        ShopDto shopDto = new ShopDto(shopId, name, contactInfo, location, customerId);

        boolean isSaved = shopModel.saveShop(shopDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Shop Added Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Shop Not Saved").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String shopId = lblShopId.getText();
        String name = txtName.getText();
        String contactInfo = txtContactInfo.getText();
        String location = txtLocation.getText();
        String customerId = cmbCustId.getSelectionModel().getSelectedItem();

        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");
        txtContactInfo.setStyle(txtContactInfo.getStyle() + " -fx-border-color: blue;");
        txtLocation.setStyle(txtLocation.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String phonePattern = "^(07[0-9]{8}|0[1-9][0-9]{8})$";
        String addressPattern = "^[a-zA-Z0-9_.\\- ]+$";

        boolean isValidName =txtName.getText().matches(namePattern);
        boolean isValidContact =txtContactInfo.getText().matches(phonePattern);
        boolean isValidLocation =txtLocation.getText().matches(addressPattern);

        if (!isValidName){
            txtName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidContact){
            txtContactInfo.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidLocation){
            txtLocation.setStyle("-fx-border-color: red;");
            return;
        }
        if (name.isEmpty() || contactInfo.isEmpty() || location.isEmpty() || customerId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
            return;
        }

        ShopDto shopDto = new ShopDto(shopId, name, contactInfo, location, customerId);

        boolean isUpdated = shopModel.updateShop(shopDto);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Shop Updated Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Shop Not Updated").show();
        }
    }

    @FXML
    void tblShopOnAction(MouseEvent event) {
        ShopTm shopTm = tblShop.getSelectionModel().getSelectedItem();
        if (shopTm != null) {
            lblShopId.setText(shopTm.getShopId());
            txtName.setText(shopTm.getShopName());
            txtContactInfo.setText(shopTm.getContactNo());
            txtLocation.setText(shopTm.getLocation());
            cmbCustId.setValue(shopTm.getCustId());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        loadNextShopId();
        loadTableData();
        loadCustomerIds();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtName.setText("");
        txtContactInfo.setText("");
        txtLocation.setText("");
        cmbCustId.getSelectionModel().clearSelection();
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerModel.getAllCustomerIds();
        ObservableList<String> customerIdObservableList = FXCollections.observableArrayList();
        customerIdObservableList.addAll(customerIds);
        cmbCustId.setItems(customerIdObservableList);
    }

    private void loadTableData() throws SQLException {
        ArrayList<ShopDto> shopDtos = shopModel.getAllShops();

        ObservableList<ShopTm> shopTms = FXCollections.observableArrayList();

        for (ShopDto shopDto : shopDtos) {
            ShopTm shopTm = new ShopTm(
                    shopDto.getShopId(),
                    shopDto.getShopName(),
                    shopDto.getContactNo(),
                    shopDto.getLocation(),
                    shopDto.getCustId()
            );
            shopTms.add(shopTm);
        }
        tblShop.setItems(shopTms);
    }

    private void loadNextShopId() throws SQLException {
        String nextShopId = shopModel.getNextShopId();
        lblShopId.setText(nextShopId);
    }
    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colShopId.setCellValueFactory(new PropertyValueFactory<>("shopId"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colName.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        colContactInfo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustId"));

        try{
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Shop Id").show();
        }
    }

    public void btnDeleteOnAtion(ActionEvent actionEvent) throws SQLException {
        String shopId = lblShopId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = shopModel.deleteShop(shopId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Shop Deleted Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Shop Not Deleted").show();
            }
        }
    }

    CustomerDAOImpl customerModel = new CustomerDAOImpl();
    public void cmbBoxCustIdOnClickAction(ActionEvent actionEvent) {

    }
}
