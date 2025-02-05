package lk.ijse.gdse.main.cicvetcare.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.main.cicvetcare.bo.BOFactory;
import lk.ijse.gdse.main.cicvetcare.bo.custom.EmployeeBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.EmployeeDAO;
import lk.ijse.gdse.main.cicvetcare.dto.EmployeeDto;
import lk.ijse.gdse.main.cicvetcare.tm.EmployeeTm;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.EmployeeDAOImpl;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private AnchorPane ancEmployee;

    @FXML
    private Button btnAddEmpolyee;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnDeleteEmployee;

    @FXML
    private Button btnUpdateEmployee;

    @FXML
    private TableColumn<EmployeeTm, String> colContactInfo;

    @FXML
    private TableColumn<EmployeeTm, String> colEmployeeId;

    @FXML
    private TableColumn<EmployeeTm, String> colName;

    @FXML
    private TableColumn<EmployeeTm, String> colPosition;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPosition;

    @FXML
    private Button btnReset;

    @FXML
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BoType.EMPLOYEE);
    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();
        String employeeName = txtName.getText();
        String position = txtPosition.getText();
        String contact = txtContact.getText();

        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");
        txtPosition.setStyle(txtPosition.getStyle() + " -fx-border-color: blue;");
        txtContact.setStyle(txtContact.getStyle() + " -fx-border-color: blue;");


        // Validation (example pattern for phone number and name)
        String namePattern = "^[A-Za-z ]+$";
        String contactPattern = "^[0-9]{10}$";
        String emailPattern = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidName = employeeName.matches(namePattern);
        boolean isValidContact = contact.matches(emailPattern);

        if (!isValidName) {
            txtName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidContact) {
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }

        EmployeeDto employeeDto = new EmployeeDto(employeeId, employeeName, position, contact);

        boolean isSaved = employeeBO.save(employeeDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Employee Added Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Employee Not Saved").show();
        }
    }



    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDeleted = employeeBO.delete(employeeId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Deleted").show();
            }
        }

    }

    @FXML
    void btnUpdateEmployeeOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();
        String employeeName = txtName.getText();
        String position = txtPosition.getText();
        String contact = txtContact.getText();

        txtName.setStyle(txtName.getStyle() + " -fx-border-color: blue;");
        txtPosition.setStyle(txtPosition.getStyle() + " -fx-border-color: blue;");
        txtContact.setStyle(txtContact.getStyle() + " -fx-border-color: blue;");


        // Validation (example pattern for phone number and name)
        String namePattern = "^[A-Za-z ]+$";
        String contactPattern = "^[0-9]{10}$";
        String emailPattern = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidName = employeeName.matches(namePattern);
        boolean isValidContact = contact.matches(emailPattern);

        if (!isValidName) {
            txtName.setStyle("-fx-border-color: red;");
            return;
        }
        if (!isValidContact) {
            txtContact.setStyle("-fx-border-color: red;");
            return;
        }

        EmployeeDto employeeDto = new EmployeeDto(employeeId, employeeName, position, contact);

        boolean isUpadated = employeeBO.update(employeeDto);
        if (isUpadated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Employee Not Updates").show();
        }
    }
    public void btnDashboardOnAction(ActionEvent actionEvent) {
        try{
            ancEmployee.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DashBoardView.fxml"));
            ancEmployee.getChildren().add(anchorPane);
        }catch(IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Load Page").show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colContactInfo.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Load Page").show();
        }

    }
    private void refreshPage() throws SQLException {
        loadNextEmployeeId();
        loadTableData();

        btnAddEmpolyee.setDisable(false);
        btnDeleteEmployee.setDisable(true);
        btnUpdateEmployee.setDisable(true);

        txtName.setText("");
        txtPosition.setText("");
        txtContact.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<EmployeeDto > employeeDtos = employeeBO.getAll();

       ObservableList<EmployeeTm> employeeTms = FXCollections.observableArrayList();

       for (EmployeeDto employeeDto : employeeDtos) {
           EmployeeTm employeeTm = new EmployeeTm(
                   employeeDto.getEmployeeId(),
                   employeeDto.getEmployeeName(),
                   employeeDto.getPosition(),
                   employeeDto.getContact()

           );
           employeeTms.add(employeeTm);
       }
        tblEmployee.setItems(employeeTms);
    }

    private void loadNextEmployeeId() throws SQLException {
        String nextEmployeeId = employeeBO.getNextId();
        lblEmployeeId.setText(nextEmployeeId);
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }

    public void tblEmployeeOnClickAction(MouseEvent mouseEvent) {
        EmployeeTm employeeTm = (EmployeeTm) tblEmployee.getSelectionModel().getSelectedItem();
        if (employeeTm != null) {
            lblEmployeeId.setText(employeeTm.getEmployeeId());
            txtName.setText(employeeTm.getEmployeeName());
            txtPosition.setText(employeeTm.getPosition());
            txtContact.setText(employeeTm.getContact());

            btnAddEmpolyee.setDisable(true);
            btnDeleteEmployee.setDisable(false);
            btnUpdateEmployee.setDisable(false);
        }

    }
}
