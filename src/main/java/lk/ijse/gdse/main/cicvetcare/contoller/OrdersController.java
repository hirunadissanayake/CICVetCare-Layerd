package lk.ijse.gdse.main.cicvetcare.contoller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import lk.ijse.gdse.main.cicvetcare.db.DBConnection;
import lk.ijse.gdse.main.cicvetcare.dto.*;
import lk.ijse.gdse.main.cicvetcare.dto.tm.OrderTm;
import lk.ijse.gdse.main.cicvetcare.model.CustomerModel;
import lk.ijse.gdse.main.cicvetcare.model.OrdersModel;
import lk.ijse.gdse.main.cicvetcare.model.ProductModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private AnchorPane ancOrders;

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnReset;

    @FXML
    private JFXComboBox<String> cmbCustId;

    @FXML
    private JFXComboBox<String> cmbProductId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<OrderDto, Date> colOrderDate;

    @FXML
    private TableColumn<OrderDto, String> colOrderId;

    @FXML
    private TableColumn<OrderDto, String> colProductId;

    @FXML
    private TableColumn<OrderDto, Integer> colQty;

    @FXML
    private TableColumn<OrderDto, Double> colTotal;

    @FXML
    private TableColumn<OrderDto, Double> colUnitPrice;

    @FXML
    private Label lblCatId;

    @FXML
    private Label lblCustName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblProductName;

    @FXML
    private Label lblStockLevel;

    @FXML
    private TableView<OrderTm> tblOrder;

    @FXML
    private TextField txtQty;

     private final ObservableList<OrderTm> orderTms = FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        LocalDate date = LocalDate.parse(lblDate.getText());
        String selectedProductId = cmbProductId.getValue();
        if (selectedProductId == null) {
            new Alert(Alert.AlertType.ERROR, "Please Select Product Id", ButtonType.OK).show();
            return;
        }

        String cartQtyString = txtQty.getText();
        String qtyPattern = "^[0-9]+$";

        if (!cartQtyString.matches(qtyPattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity", ButtonType.OK).show();
            return;
        }

        String productName = lblProductName.getText();
        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(lblStockLevel.getText());
        String catId = lblCatId.getText();

        if (cartQty > qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity", ButtonType.OK).show();
            return;
        }
        txtQty.setText("");
        double unitPrice = Double.parseDouble(lblPrice.getText());
        double total = unitPrice * cartQty;
        for (OrderTm orderTm : orderTms) {
            if (orderTm.getProduct_id().equals(selectedProductId)) {
                int newQty = orderTm.getQty() + cartQty;
                orderTm.setQty(newQty);
                orderTm.setTotal(unitPrice*newQty);
                tblOrder.refresh();
                return;
            }
        }
        Button btn = new Button("Remove");
        OrderTm newOrderTm = new OrderTm(
                orderId,
                date,
                selectedProductId,
                cartQty,
                unitPrice,
                total,
                btn

        );
        btn.setOnAction(actionEvent -> {
            orderTms.remove(newOrderTm);
            tblOrder.refresh();
        });
        orderTms.add(newOrderTm);

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        if (tblOrder.getItems().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Select Products to add Cart", ButtonType.OK).show();
            return;
        }
        if (cmbProductId.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Select Product ID", ButtonType.OK).show();
            return;
        }
        String orderId = lblOrderId.getText();
        LocalDate date = LocalDate.parse(lblDate.getText());
        String customerId = cmbCustId.getValue();

        ArrayList<OrderItemDto> orderItems = new ArrayList<>();
        for (OrderTm orderTm : orderTms) {
            OrderItemDto orderItemDto = new OrderItemDto(
                    orderId,
                    orderTm.getProduct_id(),
                    orderTm.getQty(),
                    orderTm.getUnit_price()
            );
            orderItems.add(orderItemDto);
        }
        String orderStatus = "Pending"; // Example default value
        String employeeId = "E001";     // Replace with actual employee ID logic
        OrderDto orderDto = new OrderDto(
                orderId,
                date,
                orderStatus,
                customerId,
                employeeId,
                orderItems
        );
        boolean isSaved = ordersModel.saveOrder(orderDto);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order Placed", ButtonType.OK).show();
            refreshPage();
        }else{
            new Alert(Alert.AlertType.ERROR, "Order Failed", ButtonType.OK).show();
        }

    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/Reports/OrderDetailsReport.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
//           e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        tblOrder.setItems(orderTms);

        try{
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
    }

    OrdersModel ordersModel = new OrdersModel();
    private void refreshPage() throws SQLException {
        lblOrderId.setText(ordersModel.getNextOrderId());
        lblDate.setText(LocalDate.now().toString());
        loadCustomerId();
        loadProductId();
        cmbCustId.getSelectionModel().clearSelection();
        cmbProductId.getSelectionModel().clearSelection();
        lblProductName.setText("");
        lblStockLevel.setText("");
        lblPrice.setText("");
        lblCatId.setText("");
        lblCustName.setText("");
        txtQty.setText("");

        orderTms.clear();
        tblOrder.refresh();
    }

    ProductModel productModel = new ProductModel();
    private void loadProductId() throws SQLException {
        ArrayList<String> productIds = productModel.getAllProductIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(productIds);
        cmbProductId.setItems(observableList);

    }

    CustomerModel customerModel = new CustomerModel();
    private void loadCustomerId() throws SQLException {
        ArrayList<String> customerIds = customerModel.getCustIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbCustId.setItems(observableList);

    }

    public void cmbCustIdOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedCustId = cmbCustId.getSelectionModel().getSelectedItem();
        CustomerDto customerDto = customerModel.findById(selectedCustId);
        if (customerDto != null) {
            lblCustName.setText(customerDto.getCustName());
        }
    }

    InventoryDto inventoryDto = new InventoryDto();


    public void cmbProductIdOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedProId = cmbProductId.getSelectionModel().getSelectedItem();
        Pair<ProductDto, InventoryDto> result = productModel.findById(selectedProId);

        if (result != null) {
            ProductDto productDto = result.getKey();
            InventoryDto inventoryDto = result.getValue();

            if (productDto != null) {
                lblProductName.setText(productDto.getProductName());
                lblPrice.setText(String.valueOf(productDto.getPrice()));
                lblCatId.setText(productDto.getCatId());
            }
            if (inventoryDto != null) {
                lblStockLevel.setText(String.valueOf(inventoryDto.getStock()));
            }
        }
    }

}
