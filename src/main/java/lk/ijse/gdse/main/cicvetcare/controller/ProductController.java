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
import lk.ijse.gdse.main.cicvetcare.dto.ProductDto;
import lk.ijse.gdse.main.cicvetcare.tm.ProductTm;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.ProductDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductController implements Initializable {


    @FXML
    private AnchorPane ancProduct;

    @FXML
    private Button btnAdd, btnDelete, btnReset, btnUpdate;

    @FXML
    private TableColumn<ProductTm, String> colProductId, colProductName,  colCategoryId;

    @FXML
    private TableColumn<ProductTm,Double> colPrice;

    @FXML
    private Label lblProductId;

    @FXML
    private TableView<ProductTm> tblProduct;

    @FXML
    private TextField txtProductName, txtPrice, txtCategoryId;

    private ProductDAOImpl productModel = new ProductDAOImpl();

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String productId = lblProductId.getText();
        String productName = txtProductName.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String categoryId = txtCategoryId.getText();

        txtProductName.setStyle(txtProductName.getStyle() + " -fx-border-color: blue;");
        txtPrice.setStyle(txtPrice.getStyle() + " -fx-border-color: blue;");
        txtCategoryId.setStyle(txtCategoryId.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String pricePattern = "^[1-9]\\d*(\\.\\d{1,2})?$";
        String categoryPattern = "^CAT\\d{3,}$";

        boolean isValidName =txtProductName.getText().matches(namePattern);
        boolean isValidPrice =txtPrice.getText().matches(pricePattern);
        boolean isValidCatId =txtCategoryId.getText().matches(categoryPattern);

        if (!isValidName){
            txtProductName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidPrice){
            txtPrice.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidCatId){
            txtCategoryId.setStyle("-fx-border-color: red;");
            return;
        }

        ProductDto productDto = new ProductDto(productId, productName, price, categoryId);
        boolean isSaved = productModel.saveProduct(productDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Product Added Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Add Product").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String productId = lblProductId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDeleted = productModel.deleteProduct(productId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Product Deleted Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Delete Product").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String productId = lblProductId.getText();
        String productName = txtProductName.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String categoryId = txtCategoryId.getText();

        txtProductName.setStyle(txtProductName.getStyle() + " -fx-border-color: blue;");
        txtPrice.setStyle(txtPrice.getStyle() + " -fx-border-color: blue;");
        txtCategoryId.setStyle(txtCategoryId.getStyle() + " -fx-border-color: blue;");

        String namePattern = "^[A-Za-z ]+$";
        String pricePattern = "^[1-9]\\d*(\\.\\d{1,2})?$";
        String categoryPattern = "^CAT\\d{3,}$";

        boolean isValidName =txtProductName.getText().matches(namePattern);
        boolean isValidPrice =txtPrice.getText().matches(pricePattern);
        boolean isValidCatId =txtCategoryId.getText().matches(categoryPattern);

        if (!isValidName){
            txtProductName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidPrice){
            txtPrice.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidCatId){
            txtCategoryId.setStyle("-fx-border-color: red;");
            return;
        }

        ProductDto productDto = new ProductDto(productId, productName, price, categoryId);
        boolean isUpdated = productModel.updateProduct(productDto);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Product Updated Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Update Product").show();
        }
    }

    @FXML
    void tblProductOnAction(MouseEvent event) {
        ProductTm selectedProduct = tblProduct.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            lblProductId.setText(selectedProduct.getProductId());
            txtProductName.setText(selectedProduct.getProductName());
            txtPrice.setText(String.valueOf(selectedProduct.getPrice()));
            txtCategoryId.setText(selectedProduct.getCatId());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        loadNextProductId();
        loadTableData();

        txtProductName.clear();
        txtPrice.clear();
        txtCategoryId.clear();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void loadTableData() throws SQLException {
        ArrayList<ProductDto> products = productModel.getAllProducts();
        ObservableList<ProductTm> productTms = FXCollections.observableArrayList();

        for (ProductDto dto : products) {
            productTms.add(new ProductTm(dto.getProductId(), dto.getProductName(), dto.getPrice(), dto.getCatId()));
        }
        tblProduct.setItems(productTms);
    }

    private void loadNextProductId() throws SQLException {
        String nextProductId = productModel.getNextProductId();
        lblProductId.setText(nextProductId);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("catId"));

        try{
            refreshPage();
        }catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Load Page").show();
        }


    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }
}
