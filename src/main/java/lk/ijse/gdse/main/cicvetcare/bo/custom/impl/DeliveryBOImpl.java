package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;

import lk.ijse.gdse.main.cicvetcare.bo.custom.DeliveryBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.DeliveryDAO;
import lk.ijse.gdse.main.cicvetcare.dto.DeliveryDto;
import lk.ijse.gdse.main.cicvetcare.entity.DeliveryEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryBOImpl implements DeliveryBO {

    DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.DELIVERY);
    @Override
    public boolean save(DeliveryDto dto) throws SQLException {
        return deliveryDAO.save(new DeliveryEntity(dto.getDeliveryId(),dto.getDeliveryDate(),dto.getDeliveryStatus(),dto.getOrderId(),dto.getVehicleId(),dto.getDriverId(),dto.getShopId()));

    }

    @Override
    public String getNextId() throws SQLException {
        return deliveryDAO.getNextId();
    }

    @Override
    public ArrayList<DeliveryDto> getAll() throws SQLException {
        ArrayList<DeliveryEntity> deliveryEntities = deliveryDAO.getAll();
        ArrayList<DeliveryDto> deliveryDtos = new ArrayList<>();
        for (DeliveryEntity deliveryEntity : deliveryEntities) {
            deliveryDtos.add(new DeliveryDto(deliveryEntity.getDeliveryId(),deliveryEntity.getDeliveryDate(),deliveryEntity.getDeliveryStatus(),deliveryEntity.getOrderId(),deliveryEntity.getVehicleId(),deliveryEntity.getDriverId(),deliveryEntity.getShopId()));
        }
        return deliveryDtos;
    }

    @Override
    public boolean update(DeliveryDto dto) throws SQLException {
        return deliveryDAO.update(new DeliveryEntity(dto.getDeliveryId(),dto.getDeliveryDate(),dto.getDeliveryStatus(),dto.getOrderId(),dto.getVehicleId(),dto.getDriverId(),dto.getShopId()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return deliveryDAO.delete(id);
    }
}
