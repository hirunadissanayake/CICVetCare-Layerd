package lk.ijse.gdse.main.cicvetcare.dao.custom.impl;

import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dao.custom.InventoryDAO;
import lk.ijse.gdse.main.cicvetcare.entity.InventoryEntity;
import lk.ijse.gdse.main.cicvetcare.entity.OrderItemEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAOImpl implements InventoryDAO {
    public static boolean reduceQty(OrderItemEntity orderDetailsDto){
        try {
            return SQLUtil.execute("update Inventory set stock_level = stock_level - ? where product_id = ?",
                    orderDetailsDto.getQty(),
                    orderDetailsDto.getProduct_id()
            );
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean save(InventoryEntity inventoryDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Inventory VALUES(?,?,?,?)",
                inventoryDto.getInventoryId(),
                inventoryDto.getProductId(),
                inventoryDto.getStock(),
                inventoryDto.getLocation()
        );
    }

    public boolean update(InventoryEntity inventoryDto) throws SQLException {
        return SQLUtil.execute("UPDATE Inventory SET product_id = ?, stock_level = ?, location = ? WHERE inventory_id = ?",
                inventoryDto.getProductId(),
                inventoryDto.getStock(),
                inventoryDto.getLocation(),
                inventoryDto.getInventoryId()
        );
    }

    public boolean delete(String inventoryId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Inventory WHERE inventory_id = ?", inventoryId);
    }

    public ArrayList<InventoryEntity> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Inventory");
        ArrayList<InventoryEntity> inventoryDtos = new ArrayList<>();

        while (rst.next()) {
            InventoryEntity inventoryDto = new InventoryEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4)
            );
            inventoryDtos.add(inventoryDto);
        }
        return inventoryDtos;
    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT inventory_id FROM Inventory ORDER BY inventory_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);

            if (lastId != null && lastId.startsWith("I")) {
                String subString = lastId.substring(1);
                try {
                    int i = Integer.parseInt(subString);
                    int newIndex = i + 1;
                    return String.format("I%04d", newIndex);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing last ID: " + lastId);
                }
            }
        }
        return "I0001";
    }

    public List<InventoryEntity> getLowStockProducts() throws SQLException {
        String query = "SELECT product_id, inventory_id, stock_level, location FROM Inventory WHERE stock_level < 10";
        ResultSet rst = SQLUtil.execute(query);

        ArrayList<InventoryEntity> lowStockProducts = new ArrayList<>();
        while (rst.next()) {
            InventoryEntity inventoryDto = new InventoryEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4)
            );
            lowStockProducts.add(inventoryDto);
        }
        return lowStockProducts;
    }
}
