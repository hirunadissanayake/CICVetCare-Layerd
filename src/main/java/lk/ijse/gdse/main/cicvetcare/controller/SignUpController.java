package lk.ijse.gdse.main.cicvetcare.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.main.cicvetcare.db.DBConnection;
import lk.ijse.gdse.main.cicvetcare.dto.UserDto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {

    public AnchorPane ancSignUp;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public PasswordField txtPassword;

    public void signUpOnAction(ActionEvent actionEvent) {
        try {
            String email = txtEmail.getText().toLowerCase();
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String password = txtPassword.getText().trim(); // Directly use the password for now

            // Input validation
            if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "All fields are required!").show();
                return;
            }

            // Create user
            UserDto createUser = new UserDto(firstName, lastName, email, password); // Use plain password for testing
            boolean isSaved = signup(createUser);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Welcome!").show();
                setUi("/view/LogIn.fxml");
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong. Please try again!").show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Unable to load the next screen!").show();
        }
    }

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("/view/LogIn.fxml");

    }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage) ancSignUp.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource(location))));
        stage.centerOnScreen();
    }
    //================================
    private boolean signup(UserDto user) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        // write a SQl
        String sql ="INSERT INTO User VALUES (?,?,?,?)";
        System.out.println(connection);
        // INSERT INTO user VALUES('hiruna@gmail.com','Hiruna','Sankalpa','1234');
        // create statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getPassword());
        // set sql into the statement and execute
        return statement.executeUpdate()>0; // INSERT, UPDATE, DELETE


    }
}

