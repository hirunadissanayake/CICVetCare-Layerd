package lk.ijse.gdse.main.cicvetcare.bo.custom;

import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    boolean save(EmployeeDto dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<EmployeeDto> getAll() throws SQLException;;
    boolean update(EmployeeDto dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;
}
