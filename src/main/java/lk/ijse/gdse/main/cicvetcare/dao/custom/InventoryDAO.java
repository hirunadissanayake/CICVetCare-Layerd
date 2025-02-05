package lk.ijse.gdse.main.cicvetcare.dao.custom;

import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dto.InventoryDto;
import lk.ijse.gdse.main.cicvetcare.entity.InventoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface InventoryDAO extends CrudDAO<InventoryEntity> {

    /* boolean saveInventory(InventoryDto inventoryDto);
     boolean updateInventory(InventoryDto inventoryDto);
     boolean delete(String inventoryId)
     ArrayList<InventoryDto> getAllInventory();
     String getNextInventoryId();
     */
     List<InventoryEntity> getLowStockProducts()throws SQLException;

}
