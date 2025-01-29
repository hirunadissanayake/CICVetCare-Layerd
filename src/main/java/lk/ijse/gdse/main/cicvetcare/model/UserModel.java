package lk.ijse.gdse.main.cicvetcare.model;

import lk.ijse.gdse.main.cicvetcare.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean searchUser(String email, String password) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "select * from User where email = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateUser(String selectedEmail, String password) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "update User set password = ? where email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, selectedEmail);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return true;
            }
        }catch (SQLException e){
            System.out.println("Password Updated Failed");
        }
        return false;
    }
}
