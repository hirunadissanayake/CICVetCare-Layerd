module lk.ijse.gdse.main.cicvetcare {
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires com.jfoenix;
    requires javafx.controls;
    requires net.sf.jasperreports.core;
    requires java.mail;
    requires jbcrypt;

    opens lk.ijse.gdse.main.cicvetcare.dto.tm to javafx.base;
    opens lk.ijse.gdse.main.cicvetcare.contoller to javafx.fxml;
    exports lk.ijse.gdse.main.cicvetcare;
    opens lk.ijse.gdse.main.cicvetcare.controller to javafx.fxml;
}