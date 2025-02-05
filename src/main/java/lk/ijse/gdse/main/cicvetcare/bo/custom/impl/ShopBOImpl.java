package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;

import lk.ijse.gdse.main.cicvetcare.bo.custom.ShopBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.ShopDAO;
import lk.ijse.gdse.main.cicvetcare.dto.ShopDto;
import lk.ijse.gdse.main.cicvetcare.entity.ShopEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShopBOImpl implements ShopBO {
    ShopDAO shopDAO = (ShopDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SHOP);
    @Override
    public boolean save(ShopDto dto) throws SQLException {
        return shopDAO.save(new ShopEntity(dto.getShopId(),dto.getShopName(),dto.getContactNo(),dto.getLocation(),dto.getCustId()));
    }

    @Override
    public String getNextId() throws SQLException {
        return shopDAO.getNextId();
    }

    @Override
    public ArrayList<ShopDto> getAll() throws SQLException {
        ArrayList<ShopDto> dtos = new ArrayList<>();
        ArrayList<ShopEntity> entities = shopDAO.getAll();
        for (ShopEntity entity : entities) {
            dtos.add(new ShopDto(entity.getShopId(),entity.getShopName(),entity.getContactNo(),entity.getLocation(),entity.getCustId()));
        }
        return dtos;
    }

    @Override
    public boolean update(ShopDto dto) throws SQLException {
        return shopDAO.update(new ShopEntity(dto.getShopId(),dto.getShopName(),dto.getContactNo(),dto.getLocation(),dto.getCustId()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return shopDAO.delete(id);
    }
}
