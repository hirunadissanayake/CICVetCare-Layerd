package lk.ijse.gdse.main.cicvetcare.model;

import lk.ijse.gdse.main.cicvetcare.dto.DeliveryDto;
import lk.ijse.gdse.main.cicvetcare.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryModel {
    public boolean saveDelivery(DeliveryDto deliveryDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Delivery VALUES (?, ?, ?, ?, ?, ?, ?)",
                deliveryDto.getDeliveryId(),
                deliveryDto.getDeliveryDate(),
                deliveryDto.getDeliveryStatus(),
                deliveryDto.getOrderId(),
                deliveryDto.getVehicleId(),
                deliveryDto.getDriverId(),
                deliveryDto.getShopId()
        );
    }

    public boolean deleteDelivery(String deliveryId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Delivery WHERE delivery_id = ?", deliveryId);

    }

    public boolean updateDelivery(DeliveryDto deliveryDto) throws SQLException {
        return CrudUtil.execute("UPDATE Delivery SET delivery_date = ?, delivery_status = ?, order_id = ?, vehicle_id = ?, driver_id = ?, shop_id = ? WHERE delivery_id = ?",
                deliveryDto.getDeliveryDate(),
                deliveryDto.getDeliveryStatus(),
                deliveryDto.getOrderId(),
                deliveryDto.getVehicleId(),
                deliveryDto.getDriverId(),
                deliveryDto.getShopId(),
                deliveryDto.getDeliveryId()
        );
    }

    public ArrayList<DeliveryDto> getAllDeliveries() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Delivery");
        ArrayList<DeliveryDto> deliveries = new ArrayList<>();

        while (rst.next()) {
            deliveries.add(new DeliveryDto(
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

    public String getNextDeliveryId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT delivery_id FROM Delivery ORDER BY delivery_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString("delivery_id");
            int id = Integer.parseInt(lastId.replace("DEL", ""));
            return String.format("DEL%04d", id + 1);
        }

        return "DEL0001";
    }
}
