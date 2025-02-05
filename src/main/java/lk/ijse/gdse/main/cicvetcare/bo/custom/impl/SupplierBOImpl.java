package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;

import lk.ijse.gdse.main.cicvetcare.bo.custom.SupplierBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.SupplierDAO;
import lk.ijse.gdse.main.cicvetcare.dto.SupplierDto;
import lk.ijse.gdse.main.cicvetcare.entity.SupplierEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public boolean save(SupplierDto dto) throws SQLException {
        return supplierDAO.save(new SupplierEntity(dto.getSupplierId(),dto.getSupplierName(),dto.getSupplierAddress(),dto.getSupplierContact()));
    }

    @Override
    public String getNextId() throws SQLException {
        return supplierDAO.getNextId();
    }

    @Override
    public ArrayList<SupplierDto> getAll() throws SQLException {
        ArrayList<SupplierEntity> supplierEntities = supplierDAO.getAll();
        ArrayList<SupplierDto> dtos = new ArrayList<>();
        for (SupplierEntity supplierEntity : supplierEntities) {
            dtos.add(new SupplierDto(supplierEntity.getSupplierId(),supplierEntity.getSupplierName(),supplierEntity.getSupplierAddress(),supplierEntity.getSupplierContact()));
        }
        return dtos;
    }

    @Override
    public boolean update(SupplierDto dto) throws SQLException {
        return supplierDAO.update(new SupplierEntity(dto.getSupplierId(),dto.getSupplierName(),dto.getSupplierAddress(),dto.getSupplierContact()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return supplierDAO.delete(id);
    }
}
