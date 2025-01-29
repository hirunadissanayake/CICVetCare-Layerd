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
import lk.ijse.gdse.main.cicvetcare.dto.PaymentDto;
import lk.ijse.gdse.main.cicvetcare.dto.tm.PaymentTm;
import lk.ijse.gdse.main.cicvetcare.model.PaymentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {


    @FXML
    private AnchorPane ancPayment;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<PaymentTm, Double> colAmount;

    @FXML
    private TableColumn<PaymentTm, String> colDate;

    @FXML
    private TableColumn<PaymentTm, String> colOrderId;

    @FXML
    private TableColumn<PaymentTm, String> colPaymentId;

    @FXML
    private Label lblPaymentId;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtOrderId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        try{
            refreshPage();
        }catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Load Page").show();
        }

    }

    PaymentModel paymentModel = new PaymentModel();

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String paymentId = lblPaymentId.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        String paymentDate = txtDate.getText();
        String orderId = txtOrderId.getText();

        PaymentDto paymentDto = new PaymentDto(paymentId, amount, paymentDate, orderId);

        boolean isSaved = paymentModel.savePayment(paymentDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Payment Added Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Payment Not Added").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String paymentId = lblPaymentId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = paymentModel.deletePayment(paymentId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment Deleted Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment Not Deleted").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String paymentId = lblPaymentId.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        String paymentDate = txtDate.getText();
        String orderId = txtOrderId.getText();

        PaymentDto paymentDto = new PaymentDto(paymentId, amount, paymentDate, orderId);

        boolean isUpdated = paymentModel.updatePayment(paymentDto);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Payment Updated Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Payment Not Updated").show();
        }
    }

    @FXML
    void tblPaymentOnAction(MouseEvent event) {
        PaymentTm paymentTm = tblPayment.getSelectionModel().getSelectedItem();
        if (paymentTm != null) {
            lblPaymentId.setText(paymentTm.getPaymentId());
            txtAmount.setText(String.valueOf(paymentTm.getAmount()));
            txtDate.setText(paymentTm.getPaymentDate());
            txtOrderId.setText(paymentTm.getOrderId());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        loadNextPaymentId();
        loadTableData();

        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtAmount.clear();
        txtDate.clear();
        txtOrderId.clear();
    }

    private void loadTableData() throws SQLException {
        ArrayList<PaymentDto> paymentDtos = paymentModel.getAllPayments();

        ObservableList<PaymentTm> paymentTms = FXCollections.observableArrayList();

        for (PaymentDto paymentDto : paymentDtos) {
            PaymentTm paymentTm = new PaymentTm(
                    paymentDto.getPaymentId(),
                    paymentDto.getAmount(),
                    paymentDto.getPaymentDate(),
                    paymentDto.getOrderId()
            );
            paymentTms.add(paymentTm);
        }
        tblPayment.setItems(paymentTms);
    }

    private void loadNextPaymentId() throws SQLException {
        String nextPaymentId = paymentModel.getNextPaymentId();
        lblPaymentId.setText(nextPaymentId);
    }
}
