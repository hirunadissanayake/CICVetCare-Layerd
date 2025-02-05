package lk.ijse.gdse.main.cicvetcare.dao.custom.impl;

import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dao.custom.DeliveryDAO;
import lk.ijse.gdse.main.cicvetcare.entity.DeliveryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryDAOImpl implements DeliveryDAO {
    public boolean save(DeliveryEntity deliveryDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Delivery VALUES (?, ?, ?, ?, ?, ?, ?)",
                deliveryDto.getDeliveryId(),
                deliveryDto.getDeliveryDate(),
                deliveryDto.getDeliveryStatus(),
                deliveryDto.getOrderId(),
                deliveryDto.getVehicleId(),
                deliveryDto.getDriverId(),
                deliveryDto.getShopId()
        );
    }

    public boolean delete(String deliveryId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Delivery WHERE delivery_id = ?", deliveryId);

    }

    public boolean update(DeliveryEntity deliveryDto) throws SQLException {
        return SQLUtil.execute("UPDATE Delivery SET delivery_date = ?, delivery_status = ?, order_id = ?, vehicle_id = ?, driver_id = ?, shop_id = ? WHERE delivery_id = ?",
                deliveryDto.getDeliveryDate(),
                deliveryDto.getDeliveryStatus(),
                deliveryDto.getOrderId(),
                deliveryDto.getVehicleId(),
                deliveryDto.getDriverId(),
                deliveryDto.getShopId(),
                deliveryDto.getDeliveryId()
        );
    }

    public ArrayList<DeliveryEntity> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Delivery");
        ArrayList<DeliveryEntity> deliveries = new ArrayList<>();

        while (rst.next()) {
            deliveries.add(new DeliveryEntity(
                    rst.getString("delivery_id"),
                    rst.getDate("delivery_date").toLocalDate(),
                    rst.getString("delivery_status"),
                    rst.getString("order_id"),
                    rst.getString("vehicle_id"),
                    rst.getString("driver_id"),
                    rst.getString("shop_id")
            ));
        }

        return deliveries;
    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT delivery_id FROM Delivery ORDER BY delivery_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString("delivery_id");
            int id = Integer.parseInt(lastId.replace("DEL", ""));
            return String.format("DEL%04d", id + 1);
        }

        return "DEL0001";
    }



}
