package lk.ijse.gdse.main.cicvetcare.bo.custom;

import javafx.util.Pair;
import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.InventoryDto;
import lk.ijse.gdse.main.cicvetcare.dto.ProductDto;
import lk.ijse.gdse.main.cicvetcare.entity.InventoryEntity;
import lk.ijse.gdse.main.cicvetcare.entity.ProductEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductBO extends SuperBO {
    ArrayList<String> getAllProductIds() throws SQLException;
    Pair<ProductDto, InventoryDto> findById(String selectedProId) throws SQLException;

    boolean save(ProductDto dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<ProductDto> getAll() throws SQLException;;
    boolean update(ProductDto dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;

}
