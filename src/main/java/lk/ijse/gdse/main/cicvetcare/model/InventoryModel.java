package lk.ijse.gdse.main.cicvetcare.model;

import lk.ijse.gdse.main.cicvetcare.dto.InventoryDto;
import lk.ijse.gdse.main.cicvetcare.dto.OrderItemDto;
import lk.ijse.gdse.main.cicvetcare.dto.ProductDto;
import lk.ijse.gdse.main.cicvetcare.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryModel {
    public static boolean reduceQty(OrderItemDto orderDetailsDto) throws SQLException {
        return CrudUtil.execute("update Inventory set stock_level = stock_level - ? where product_id = ?",
                orderDetailsDto.getQty(),
                orderDetailsDto.getProduct_id()
                );
    }

    public boolean saveInventory(InventoryDto inventoryDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Inventory VALUES(?,?,?,?)",
                inventoryDto.getInventoryId(),
                inventoryDto.getProductId(),
                inventoryDto.getStock(),
                inventoryDto.getLocation()
        );
    }

    public boolean updateInventory(InventoryDto inventoryDto) throws SQLException {
        return CrudUtil.execute("UPDATE Inventory SET product_id = ?, stock_level = ?, location = ? WHERE inventory_id = ?",
                inventoryDto.getProductId(),
                inventoryDto.getStock(),
                inventoryDto.getLocation(),
                inventoryDto.getInventoryId()
        );
    }

    public boolean deleteInventory(String inventoryId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Inventory WHERE inventory_id = ?", inventoryId);
    }

    public ArrayList<InventoryDto> getAllInventory() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Inventory");
        ArrayList<InventoryDto> inventoryDtos = new ArrayList<>();

        while (rst.next()) {
            InventoryDto inventoryDto = new InventoryDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4)
            );
            inventoryDtos.add(inventoryDto);
        }
        return inventoryDtos;
    }

    public String getNextInventoryId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT inventory_id FROM Inventory ORDER BY inventory_id DESC LIMIT 1");

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

    public List<InventoryDto> getLowStockProducts() throws SQLException {
        String query = "SELECT product_id, inventory_id, stock_level, location FROM Inventory WHERE stock_level < 10";
        ResultSet rst = CrudUtil.execute(query);

        ArrayList<InventoryDto> lowStockProducts = new ArrayList<>();
        while (rst.next()) {
            InventoryDto inventoryDto = new InventoryDto(
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
