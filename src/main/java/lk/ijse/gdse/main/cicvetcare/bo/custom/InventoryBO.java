package lk.ijse.gdse.main.cicvetcare.bo.custom;

import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.InventoryDto;
import lk.ijse.gdse.main.cicvetcare.entity.InventoryEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface InventoryBO extends SuperBO {
    List<InventoryEntity> getLowStockProducts()throws SQLException;

    boolean save(InventoryDto dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<InventoryDto> getAll() throws SQLException;;
    boolean update(InventoryDto dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;

}
