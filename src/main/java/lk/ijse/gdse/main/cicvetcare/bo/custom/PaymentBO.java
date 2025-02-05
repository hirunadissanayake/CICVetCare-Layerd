package lk.ijse.gdse.main.cicvetcare.bo.custom;

import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.PaymentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    boolean save(PaymentDto dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<PaymentDto> getAll() throws SQLException;;
    boolean update(PaymentDto dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;
}
