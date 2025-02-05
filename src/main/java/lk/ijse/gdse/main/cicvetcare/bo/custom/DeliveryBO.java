package lk.ijse.gdse.main.cicvetcare.bo.custom;

import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.DeliveryDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryBO extends SuperBO {
    boolean save(DeliveryDto dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<DeliveryDto> getAll() throws SQLException;;
    boolean update(DeliveryDto dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;
}
