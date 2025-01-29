package lk.ijse.gdse.main.cicvetcare.db;

import lk.ijse.gdse.main.cicvetcare.dto.UserDto;
import lk.ijse.gdse.main.cicvetcare.util.security.PasswordManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
    public static ArrayList<UserDto> userTable= new ArrayList();

    static {
        userTable.add(
                new UserDto("Hiruna","Dissanyake",
                        "hiruna@gmail.com",
                        new PasswordManager().encrypt("1234"))
        );
    }

}
//public class DBConnection {
//    private static DBConnection dbConnection;
//    private Connection connection;
//
//    private DBConnection() throws SQLException {
//        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CICVetCare","root","Ijse@1234");
//    }
//    public static DBConnection getInstance() throws SQLException {
//        if (dbConnection == null) {
//            dbConnection = new DBConnection();
//        }
//        return dbConnection;
//    }
//
//    public Connection getConnection() {
//        return connection;
//    }
//}

