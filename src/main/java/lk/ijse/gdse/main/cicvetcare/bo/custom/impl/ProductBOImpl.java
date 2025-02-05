package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;

import javafx.util.Pair;
import lk.ijse.gdse.main.cicvetcare.bo.custom.ProductBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.ProductDAO;
import lk.ijse.gdse.main.cicvetcare.dto.InventoryDto;
import lk.ijse.gdse.main.cicvetcare.dto.ProductDto;
import lk.ijse.gdse.main.cicvetcare.entity.InventoryEntity;
import lk.ijse.gdse.main.cicvetcare.entity.ProductEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBOImpl implements ProductBO {
    ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PRODUCT);

    @Override
    public ArrayList<String> getAllProductIds() throws SQLException {
        return productDAO.getAllProductIds();
    }

    @Override
    public Pair<ProductDto, InventoryDto> findById(String selectedProId) throws SQLException {
        Pair<ProductEntity, InventoryEntity> byId = productDAO.findById(selectedProId);
        if (byId != null) {
            ProductEntity productEntity = byId.getKey();
            InventoryEntity inventoryEntity = byId.getValue();

            ProductDto productDto = new ProductDto(
                    productEntity.getProductId(),
                    productEntity.getProductName(),
                    productEntity.getPrice(),
                    productEntity.getCatId()
            );

            InventoryDto inventoryDto = new InventoryDto(
                    inventoryEntity.getInventoryId(),
                    inventoryEntity.getProductId(),
                    inventoryEntity.getStock(),
                    inventoryEntity.getLocation()
            );

            return new Pair<>(productDto, inventoryDto);
        }
        return null;

    }

    @Override
    public boolean save(ProductDto dto) throws SQLException {
        return productDAO.save(new ProductEntity(dto.getProductId(),dto.getProductName(),dto.getPrice(),dto.getCatId()));
    }

    @Override
    public String getNextId() throws SQLException {
        return productDAO.getNextId();
    }

    @Override
    public ArrayList<ProductDto> getAll() throws SQLException {
        ArrayList<ProductEntity> productEntities = productDAO.getAll();
        ArrayList<ProductDto> productDtos = new ArrayList<>();
        for (ProductEntity productEntity : productEntities) {
            productDtos.add(new ProductDto(productEntity.getProductId(),productEntity.getProductName(),productEntity.getPrice(),productEntity.getCatId()));
        }
        return productDtos;
    }

    @Override
    public boolean update(ProductDto dto) throws SQLException {
        return productDAO.update(new ProductEntity(dto.getProductId(),dto.getProductName(),dto.getPrice(),dto.getCatId()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return productDAO.delete(id);
    }
}
