package lk.ijse.gdse.main.cicvetcare.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoard2Controller implements Initializable {

    @FXML
    private AnchorPane ancLoading;

    @FXML
    private JFXButton btnCategory;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDelivery;

    @FXML
    private JFXButton btnDriver;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnInventory;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnProduct;

    @FXML
    private JFXButton btnShop;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnVehicle;

    @FXML
    private AnchorPane mainAnc;

    @FXML
    void btnCategoryOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/CategoryView.fxml");
    }

    @FXML
    void btnCustomerOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/CustomerView.fxml");
    }

    @FXML
    void btnDeliveryOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/DeliveryView.fxml");
    }

    @FXML
    void btnDriverOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/DriverView.fxml");
    }

    @FXML
    void btnEmployeeOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/EmployeeView.fxml");
    }

    @FXML
    void btnInventoryOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/InventoryView.fxml");
    }

    @FXML
    void btnOrdersOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/OrdersView.fxml");
    }

    @FXML
    void btnPaymentOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/PaymentView.fxml");
    }

    @FXML
    void btnProductOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/ProductView.fxml");
    }

    @FXML
    void btnShopOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/ShopView.fxml");
    }

    @FXML
    void btnSupplierOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/SupplierView.fxml");
    }

    @FXML
    void btnVehicleOnClickAction(ActionEvent event) throws IOException {
        navigateTo("/view/VehicleView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            navigateTo("/view/CustomerView.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void navigateTo(String fxmlPath) throws IOException {
        try{
            ancLoading.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(fxmlPath));
            ancLoading.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"failed to load Page").show();
        }
    }
}
