package lk.ijse.gdse.main.cicvetcare.bo.custom;

import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.CategoryDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryBO extends SuperBO {
    boolean save(CategoryDto dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<CategoryDto> getAll() throws SQLException;;
    boolean update(CategoryDto dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;
}
