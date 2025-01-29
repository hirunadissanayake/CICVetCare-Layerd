package lk.ijse.gdse.main.cicvetcare.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PwdVerifyController {
    @FXML
    public AnchorPane ancVerify;
    @FXML
    public TextField txtCode;
    @FXML
    public Label txtSelectedEmail;

    private static String code = null;
    private static String selectedEmail = "";

    public static void setUserData(String verificationCode, String email) {
        code = verificationCode;
        selectedEmail = email;
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) ancVerify.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource("/view/" + location + ".fxml")))); // Fixed path
        stage.centerOnScreen();
    }

    @FXML
    public void changeEmailOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ForgotPwd");
    }

    @FXML
    public void verifyCodeOnAction(ActionEvent actionEvent) throws IOException {
        if (String.valueOf(code).equals(txtCode.getText().trim())) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ResetPwd.fxml"));
            Parent parent = fxmlLoader.load();

            // Fetch and set data to ResetPasswordController
            ResetPasswordController controller = fxmlLoader.getController();
            controller.setUserData(selectedEmail);

            Stage stage = (Stage) ancVerify.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
        } else {
            new Alert(Alert.AlertType.ERROR, "Wrong Verification Code").show();
        }
    }
}
