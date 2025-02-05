package lk.ijse.gdse.main.cicvetcare.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.main.cicvetcare.bo.BOFactory;
import lk.ijse.gdse.main.cicvetcare.bo.custom.CustomerBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.CustomerDAO;
import lk.ijse.gdse.main.cicvetcare.dto.CustomerDto;
import lk.ijse.gdse.main.cicvetcare.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse.main.cicvetcare.entity.CustomerEntity;

public class SearchCustomerController {

    @FXML
    private Label TxtContactInfo;

    @FXML
    private Label TxtCustId;

    @FXML
    private Label TxtCustName;

    @FXML
    private TextField TxtSearchContactInfo;

    @FXML
    private Label TxtType;

    @FXML
    private AnchorPane ancSearchCustomer;

    @FXML
    private Button btnSearch;

    CustomerBO customerBo =(CustomerBO) BOFactory.getInstance().getBO(BOFactory.BoType.CUSTOMER);
    @FXML
    void BtnSearchClickOnAction(ActionEvent event) {
        String contact = TxtSearchContactInfo.getText();
        if (!contact.isEmpty()) {
            CustomerEntity customerDto= customerBo.SearchCustomerByContact(contact);
            TxtCustId.setText(contact);
            if (customerDto != null){
                TxtCustId.setText(customerDto.getCustId());
                TxtCustName.setText(customerDto.getCustName());
                TxtType.setText(customerDto.getType());
                TxtContactInfo.setText(customerDto.getContact());
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"Enter valid Email...");
                alert.showAndWait();
            }
        }
    }

}
