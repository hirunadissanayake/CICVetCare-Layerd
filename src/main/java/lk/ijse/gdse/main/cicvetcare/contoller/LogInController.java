package lk.ijse.gdse.main.cicvetcare.contoller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.main.cicvetcare.db.Database;
import lk.ijse.gdse.main.cicvetcare.dto.UserDto;
import lk.ijse.gdse.main.cicvetcare.model.UserModel;
import lk.ijse.gdse.main.cicvetcare.util.security.PasswordManager;

import java.io.IOException;
import java.util.Optional;

public class LogInController {

    @FXML
    private Button BtnCreateAcc;

    @FXML
    private Hyperlink BtnForgotPwd;

    @FXML
    private Button BtnLogIN;

    @FXML
    private AnchorPane LogInAnc;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void createAnAccountOnAction(ActionEvent event) throws IOException {
        setUi(event, "SignUpView");
    }

    @FXML
    void forgotPasswordOnAction(ActionEvent event) throws IOException {
        setUi(event, "ForgotPwd");
    }

    @FXML
    void loginOnAction(ActionEvent event) throws IOException {
        String email = txtEmail.getText().toLowerCase().trim();
        String password = txtPassword.getText().trim();

        // Validate user input
        if (email.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Email and Password cannot be empty!").show();
            return;
        }
        if (!email.matches("[^@]+@[^.]+\\..+")) {
            new Alert(Alert.AlertType.WARNING, "Invalid email format!").show();
            return;
        }
        boolean isAvailable = UserModel.searchUser(email, password);
        if (isAvailable) {
            LogInAnc.getChildren().clear();
            Parent load = FXMLLoader.load(getClass().getResource("/view/Dashboard2.fxml"));
            LogInAnc.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.WARNING, String.format("User not found (%s)", email)).show();
        }
        // Authenticate user
        Optional<UserDto> selectedUser = Database.userTable.stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst();

       /* if (selectedUser.isPresent()) {
            if (new PasswordManager().checkPassword(password, selectedUser.get().getPassword())) {
                setUi(event, "Dashboard2");
            } else {
                new Alert(Alert.AlertType.ERROR, "Wrong Password!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, String.format("User not found (%s)", email)).show();
        }*/
    }

    private void setUi(ActionEvent event, String location) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/" + location + ".fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(anchorPane));
        currentStage.show();
    }
}
