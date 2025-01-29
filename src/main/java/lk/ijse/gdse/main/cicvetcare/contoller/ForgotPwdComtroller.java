package lk.ijse.gdse.main.cicvetcare.contoller;

import javafx.event.ActionEvent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse.main.cicvetcare.util.security.VerificationCodeGenerator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class ForgotPwdComtroller {
    public AnchorPane ancForgetPW;
    public TextField txtEmail;
    @FXML
    private Button btn;


    public void sendCodeOnAction(ActionEvent actionEvent) {
        try {
            // Generate the verification code
            String verificationCode = new VerificationCodeGenerator().getCode(5);

            // Print the verification code to the terminal
            System.out.println("Verification Code: " + verificationCode);

            String fromEmail = "hiruna@gmail.com";
            String toEmail = txtEmail.getText();
            String host = "127.0.0.1";

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.port", "587");
            // => node=> nodemailer, (sendGrid, twilio)
            Session session = Session.getDefaultInstance(properties);

            //-------------------------
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(fromEmail));
            mimeMessage.setSubject("Verification Code");
            mimeMessage.setText("Verification Code : " + verificationCode);
            PwdVerifyController verify=new PwdVerifyController();
            //verify.setUserData(Integer.parseInt(verificationCode),toEmail);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            PwdVerifyController.setUserData(String.valueOf(verificationCode),toEmail);

            // Uncomment this to send the email
            // Transport.send(mimeMessage);
            System.out.println("Email preparation completed!");

            //=======================>

            Stage window = (Stage) btn.getScene().getWindow();
            window.close();
            Parent load = FXMLLoader.load(getClass().getResource("/view/PwdVerify.fxml"));
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        /* Uncomment and adjust this block if needed:
        Parent parent = fxmlLoader.load();
        PwdVerifyController controller = fxmlLoader.getController();
        controller.setUserData(verificationCode, txtEmail.getText());
        Stage stage = (Stage) ancForgetPW.getScene().getWindow();
        stage.setScene(new Scene(parent));
        */

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void backToLoginPage(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) btn.getScene().getWindow();
        stage.close();
        Parent load = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        Scene scene = new Scene(load);
        Stage parentStage = new Stage();
        parentStage.setScene(scene);
        parentStage.show();
    }



}
