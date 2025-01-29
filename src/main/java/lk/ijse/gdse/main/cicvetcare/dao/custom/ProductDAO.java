package lk.ijse.gdse.main.cicvetcare.dao.custom;

import javafx.util.Pair;

import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.InventoryDto;
import lk.ijse.gdse.main.cicvetcare.dto.ProductDto;


import java.util.ArrayList;

public interface ProductDAO extends CrudDAO {
     boolean saveProduct(ProductDto product);
     boolean updateProduct(ProductDto product);
     boolean deleteProduct(String productId);
     ArrayList<ProductDto> getAllProducts();
     String getNextProductId();
     ArrayList<String> getAllProductIds();
     Pair<ProductDto, InventoryDto> findById(String selectedProId);
}
