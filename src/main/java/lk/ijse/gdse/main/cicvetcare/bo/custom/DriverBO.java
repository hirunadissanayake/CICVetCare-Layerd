package lk.ijse.gdse.main.cicvetcare.bo.custom;

import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.DriverDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DriverBO extends SuperBO {
    boolean save(DriverDto dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<DriverDto> getAll() throws SQLException;;
    boolean update(DriverDto dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;
}
