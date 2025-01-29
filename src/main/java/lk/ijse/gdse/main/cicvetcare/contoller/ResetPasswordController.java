package lk.ijse.gdse.main.cicvetcare.contoller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.main.cicvetcare.db.Database;
import lk.ijse.gdse.main.cicvetcare.dto.UserDto;
import lk.ijse.gdse.main.cicvetcare.model.UserModel;
import lk.ijse.gdse.main.cicvetcare.util.security.PasswordManager;

import java.io.IOException;
import java.util.Optional;

public class ResetPasswordController {

    @FXML
    public Button btn;
    @FXML
    public TextField txtPassword;
    @FXML
    public AnchorPane ancResetPw;

    private String selectedEmail = "";

    public void setUserData(String email) {
        this.selectedEmail = email;
        System.out.println("Selected email: " + selectedEmail);
    }

   /* private void setUi(String location) throws IOException {
        Stage stage = (Stage) ancResetPw.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource("/view/" + location + ".fxml")))); // Fixed path
        stage.centerOnScreen();
    }*/

    @FXML
    public void changePasswordOnAction(ActionEvent actionEvent) throws IOException {
        /*Optional<UserDto> selectedUser = Database.userTable.stream()
                .filter(user -> user.getEmail().equals(selectedEmail))
                .findFirst();*/
        String password = txtPassword.getText();
        boolean isUpdate = UserModel.updateUser(selectedEmail, password);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Password updated successfully").show();
            Parent load = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
            ancResetPw.getChildren().clear();
            ancResetPw.getChildren().add(load);
        }else  {
            new Alert(Alert.AlertType.ERROR, "Password not updated").show();
        }
        /*if (selectedUser.isPresent()) {
            selectedUser.get().setPassword(new PasswordManager().encrypt(txtPassword.getText().trim()));
            new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!").show();
            setUi("LoginForm");
        } else {
            new Alert(Alert.AlertType.ERROR, "User not found!").show();
        }*/
    }
}
