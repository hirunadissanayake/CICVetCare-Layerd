package lk.ijse.gdse.main.cicvetcare.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.gdse.main.cicvetcare.dto.InventoryDto;
import lk.ijse.gdse.main.cicvetcare.tm.InventoryTm;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.InventoryDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InventoryController implements Initializable {


    @FXML
    private AnchorPane ancInventory;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<InventoryTm, String> colInventoryId;

    @FXML
    private TableColumn<InventoryTm, String> colProductId;

    @FXML
    private TableColumn<InventoryTm, Integer> colStockLevel;

    @FXML
    private TableColumn<InventoryTm, String> colLocation;

    @FXML
    private Label lblInventoryId;

    @FXML
    private TableView<InventoryTm> tblInventory;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtStockLevel;

    @FXML
    private TextField txtLocation;

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInventoryId.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colStockLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        try{
            refreshPage();
        }catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Load Page").show();
        }

        scheduler.scheduleAtFixedRate(this::checkLowQuantityProducts, 0, 24, TimeUnit.HOURS);

    }

    InventoryDAOImpl inventoryModel = new InventoryDAOImpl();

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String inventoryId = lblInventoryId.getText();
        String productId = txtProductId.getText();
        String location = txtLocation.getText();
        int stockLevel;

        try {
            stockLevel = Integer.parseInt(txtStockLevel.getText());
        } catch (NumberFormatException e) {
            txtStockLevel.setStyle("-fx-border-color: red;");
            return;
        }

        txtProductId.setStyle(txtProductId.getStyle() + " -fx-border-color: blue;");
        txtLocation.setStyle(txtLocation.getStyle() + " -fx-border-color: blue;");
        txtStockLevel.setStyle(txtStockLevel.getStyle() + " -fx-border-color: blue;");


        String productIdPattern = "^P\\d{4}$";


        boolean isValidProId = productId.matches(productIdPattern);


        if (!isValidProId) {
            txtProductId.setStyle("-fx-border-color: red;");
            return;
        }

        InventoryDto inventoryDto = new InventoryDto(inventoryId, productId, stockLevel, location);

        boolean isSaved = inventoryModel.saveInventory(inventoryDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Inventory Added Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Inventory Not Saved").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String inventoryId = lblInventoryId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = inventoryModel.deleteInventory(inventoryId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Inventory Deleted Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Inventory Not Deleted").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String inventoryId = lblInventoryId.getText();
        String productId = txtProductId.getText();
        String location = txtLocation.getText();
        int stockLevel;

        try {
            stockLevel = Integer.parseInt(txtStockLevel.getText());
        } catch (NumberFormatException e) {
            txtStockLevel.setStyle("-fx-border-color: red;");
            return;
        }

        txtProductId.setStyle(txtProductId.getStyle() + " -fx-border-color: blue;");
        txtLocation.setStyle(txtLocation.getStyle() + " -fx-border-color: blue;");
        txtStockLevel.setStyle(txtStockLevel.getStyle() + " -fx-border-color: blue;");


        String productIdPattern = "^P\\d{4}$";


        boolean isValidProId = productId.matches(productIdPattern);

        if (!isValidProId) {
            txtProductId.setStyle("-fx-border-color: red;");
            return;
        }

        InventoryDto inventoryDto = new InventoryDto(inventoryId, productId, stockLevel, location);

        boolean isUpdated = inventoryModel.updateInventory(inventoryDto);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Inventory Updated Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Inventory Not Updated").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void tblInventoryOnAction(MouseEvent event) {
        InventoryTm inventoryTm = tblInventory.getSelectionModel().getSelectedItem();
        if (inventoryTm != null) {
            lblInventoryId.setText(inventoryTm.getInventoryId());
            txtProductId.setText(inventoryTm.getProductId());
            txtStockLevel.setText(String.valueOf(inventoryTm.getStock()));
            txtLocation.setText(inventoryTm.getLocation());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        loadNextInventoryId();
        loadTableData();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtProductId.setText("");
        txtStockLevel.setText("");
        txtLocation.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<InventoryDto> inventoryDtos = inventoryModel.getAllInventory();

        ObservableList<InventoryTm> inventoryTms = FXCollections.observableArrayList();

        for (InventoryDto inventoryDto : inventoryDtos) {
            InventoryTm inventoryTm = new InventoryTm(
                    inventoryDto.getInventoryId(),
                    inventoryDto.getProductId(),
                    inventoryDto.getStock(),
                    inventoryDto.getLocation()
            );
            inventoryTms.add(inventoryTm);
        }
        tblInventory.setItems(inventoryTms);
    }

    private void loadNextInventoryId() throws SQLException {
        String nextId = inventoryModel.getNextInventoryId();
        lblInventoryId.setText(nextId);
    }
    private void checkLowQuantityProducts() {
        Platform.runLater(() -> {
            try {
                List<InventoryDto> lowStockProducts = inventoryModel.getLowStockProducts();
                if (!lowStockProducts.isEmpty()) {
                    displayReminder(lowStockProducts);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    private void displayReminder(List<InventoryDto> lowStockProducts) {
        Stage reminderStage = new Stage();
        reminderStage.initStyle(StageStyle.TRANSPARENT);
        reminderStage.setAlwaysOnTop(true);

        VBox vbox = new VBox(20);
        vbox.setStyle("-fx-background-color: #e66767; -fx-padding: 10;");

        Label title = new Label("Low Stock Alert");
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #1e272e;");

        vbox.getChildren().add(title);
        for (InventoryDto inventoryDto : lowStockProducts) {
            Label productInfo = new Label(inventoryDto.getProductId() + " - Qty: " + inventoryDto.getStock());
            vbox.getChildren().add(productInfo);
        }

        Scene scene = new Scene(vbox);
        reminderStage.setScene(scene);

        // Position the alert in the bottom-right corner
        reminderStage.setX(Screen.getPrimary().getVisualBounds().getMaxX() - 300);
        reminderStage.setY(Screen.getPrimary().getVisualBounds().getMaxY() - 200);

        // Position the alert in the top-right corner
        reminderStage.setX(Screen.getPrimary().getVisualBounds().getMaxX() - 300);
        reminderStage.setY(Screen.getPrimary().getVisualBounds().getMinY() + 20);

        reminderStage.show();

        // Auto-close after 10 seconds
        new Timeline(new KeyFrame(Duration.seconds(10), e -> reminderStage.close())).play();
    }
}
