package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.DeliveryDto;


import java.util.ArrayList;

public interface DeliveryDAO extends CrudDAO {
    boolean saveDelivery(DeliveryDto deliveryDto);
    boolean deleteDelivery(String deliveryId);
    boolean updateDelivery(DeliveryDto deliveryDto);
    ArrayList<DeliveryDto> getAllDeliveries();
    String getNextDeliveryId();
}
