package lk.ijse.gdse.main.cicvetcare.bo.custom;

import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    boolean save(SupplierDto dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<SupplierDto> getAll() throws SQLException;;
    boolean update(SupplierDto dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;
}
