package lk.ijse.gdse.main.cicvetcare.dao.custom;

import javafx.util.Pair;

import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.entity.InventoryEntity;
import lk.ijse.gdse.main.cicvetcare.entity.ProductEntity;


import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO extends CrudDAO<ProductEntity> {
     /*boolean saveProduct(ProductDto product);
     boolean updateProduct(ProductDto product);
     boolean deleteProduct(String productId);
     ArrayList<ProductDto> getAllProducts();
     String getNextProductId();*/
     ArrayList<String> getAllProductIds() throws SQLException;
    Pair<ProductEntity, InventoryEntity> findById(String selectedProId) throws SQLException;
}
