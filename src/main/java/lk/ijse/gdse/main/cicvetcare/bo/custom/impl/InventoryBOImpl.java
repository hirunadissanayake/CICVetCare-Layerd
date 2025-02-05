package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;

import lk.ijse.gdse.main.cicvetcare.bo.custom.InventoryBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.InventoryDAO;
import lk.ijse.gdse.main.cicvetcare.dto.InventoryDto;
import lk.ijse.gdse.main.cicvetcare.entity.InventoryEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryBOImpl implements InventoryBO {
    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.INVENTORY);;


    @Override
    public List<InventoryEntity> getLowStockProducts() throws SQLException {
        return inventoryDAO.getLowStockProducts();
    }

    @Override
    public boolean save(InventoryDto dto) throws SQLException {
        return inventoryDAO.save(new InventoryEntity(dto.getInventoryId(),dto.getProductId(),dto.getStock(), dto.getLocation()));
    }

    @Override
    public String getNextId() throws SQLException {
        return inventoryDAO.getNextId();
    }

    @Override
    public ArrayList<InventoryDto> getAll() throws SQLException {
        ArrayList<InventoryDto> inventoryDtos = new ArrayList<>();
        ArrayList<InventoryEntity> inventoryEntities = inventoryDAO.getAll();
        for (InventoryEntity inventoryEntity : inventoryEntities) {
            inventoryDtos.add(new InventoryDto(inventoryEntity.getInventoryId(),inventoryEntity.getProductId(),inventoryEntity.getStock(),inventoryEntity.getLocation()));
        }
        return inventoryDtos;
    }

    @Override
    public boolean update(InventoryDto dto) throws SQLException {
        return inventoryDAO.update(new InventoryEntity(dto.getInventoryId(),dto.getProductId(),dto.getStock(), dto.getLocation()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return inventoryDAO.delete(id);
    }
}
