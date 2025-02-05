package lk.ijse.gdse.main.cicvetcare.dao.custom.impl;

import javafx.util.Pair;
import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dao.custom.ProductDAO;
import lk.ijse.gdse.main.cicvetcare.entity.InventoryEntity;
import lk.ijse.gdse.main.cicvetcare.entity.ProductEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOImpl implements ProductDAO {


    public boolean save(ProductEntity product) throws SQLException {
        return SQLUtil.execute("INSERT INTO Product VALUES (?, ?, ?, ?)",
                product.getProductId(), product.getProductName(), product.getPrice(), product.getCatId());
    }

    public boolean update(ProductEntity product) throws SQLException {
        return SQLUtil.execute("UPDATE Product SET name=?, price=?, category_id=? WHERE product_id=?",
                product.getProductName(), product.getPrice(), product.getCatId(), product.getProductId());
    }

    public boolean delete(String productId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Product WHERE product_id=?", productId);
    }

    public ArrayList<ProductEntity> getAll() throws SQLException {
        ResultSet result = SQLUtil.execute("SELECT * FROM Product");
        ArrayList<ProductEntity> products = new ArrayList<>();

        while (result.next()) {
            products.add(new ProductEntity(result.getString("product_id"),
                    result.getString("name"),
                    result.getDouble("price"),
                    result.getString("category_id")));
        }
        return products;
    }

    public String getNextId() throws SQLException {
        ResultSet result = SQLUtil.execute("SELECT product_id FROM Product ORDER BY product_id DESC LIMIT 1");

        if (result.next()) {
            String lastId = result.getString(1);
            if (lastId != null && lastId.startsWith("P")) {
                int nextId = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("P%04d", nextId);
            }
        }
        return "P0001";
    }

    public ArrayList<String> getAllProductIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT product_id FROM Product");
        ArrayList<String> productIds = new ArrayList<>();
        while (rst.next()) {
            productIds.add(rst.getString(1));
        }
        return productIds;
    }

    public Pair<ProductEntity, InventoryEntity> findById(String selectedProId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute(
                "SELECT p.product_id, p.name, p.price, p.category_id, " +
                        "i.inventory_id, i.stock_level, i.location " +
                        "FROM Product p " +
                        "JOIN Inventory i ON p.product_id = i.product_id " +
                        "WHERE p.product_id = ?",
                selectedProId
        );
        if (resultSet.next()) {
            ProductEntity productDto = new ProductEntity(
                    resultSet.getString("product_id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getString("category_id")
            );
            InventoryEntity inventoryDto = new InventoryEntity(
                    resultSet.getString("inventory_id"),
                    resultSet.getString("product_id"),
                    resultSet.getInt("stock_level"),
                    resultSet.getString("location")
            );
            return new Pair<>(productDto, inventoryDto); // Assuming a Pair class is available
        }
        return null;
    }


}