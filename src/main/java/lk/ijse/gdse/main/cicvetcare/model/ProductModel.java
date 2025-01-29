package lk.ijse.gdse.main.cicvetcare.model;

import javafx.util.Pair;
import lk.ijse.gdse.main.cicvetcare.dto.InventoryDto;
import lk.ijse.gdse.main.cicvetcare.dto.OrderItemDto;
import lk.ijse.gdse.main.cicvetcare.dto.ProductDto;
import lk.ijse.gdse.main.cicvetcare.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductModel {


    public boolean saveProduct(ProductDto product) throws SQLException {
        return CrudUtil.execute("INSERT INTO Product VALUES (?, ?, ?, ?)",
                product.getProductId(), product.getProductName(), product.getPrice(), product.getCatId());
    }

    public boolean updateProduct(ProductDto product) throws SQLException {
        return CrudUtil.execute("UPDATE Product SET name=?, price=?, category_id=? WHERE product_id=?",
                product.getProductName(), product.getPrice(), product.getCatId(), product.getProductId());
    }

    public boolean deleteProduct(String productId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Product WHERE product_id=?", productId);
    }

    public ArrayList<ProductDto> getAllProducts() throws SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Product");
        ArrayList<ProductDto> products = new ArrayList<>();

        while (result.next()) {
            products.add(new ProductDto(result.getString("product_id"),
                    result.getString("name"),
                    result.getDouble("price"),
                    result.getString("category_id")));
        }
        return products;
    }

    public String getNextProductId() throws SQLException {
        ResultSet result = CrudUtil.execute("SELECT product_id FROM Product ORDER BY product_id DESC LIMIT 1");

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
        ResultSet rst = CrudUtil.execute("SELECT product_id FROM Product");
        ArrayList<String> productIds = new ArrayList<>();
        while (rst.next()) {
            productIds.add(rst.getString(1));
        }
        return productIds;
    }

    public Pair<ProductDto, InventoryDto> findById(String selectedProId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT p.product_id, p.name, p.price, p.category_id, " +
                        "i.inventory_id, i.stock_level, i.location " +
                        "FROM Product p " +
                        "JOIN Inventory i ON p.product_id = i.product_id " +
                        "WHERE p.product_id = ?",
                selectedProId
        );
        if (resultSet.next()) {
            ProductDto productDto = new ProductDto(
                    resultSet.getString("product_id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getString("category_id")
            );
            InventoryDto inventoryDto = new InventoryDto(
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