package lk.ijse.gdse.main.cicvetcare.bo.custom;

import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.ShopDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ShopBO extends SuperBO {
    boolean save(ShopDto dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<ShopDto> getAll() throws SQLException;;
    boolean update(ShopDto dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;
}
