package lk.ijse.gdse.main.cicvetcare.dao.custom;

import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dto.InventoryDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface InventoryDAO extends CrudDAO {

     boolean saveInventory(InventoryDto inventoryDto);
     boolean updateInventory(InventoryDto inventoryDto);
     ArrayList<InventoryDto> getAllInventory();
     String getNextInventoryId();
     List<InventoryDto> getLowStockProducts();

}
