package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.OrderDto;



public interface OrdersDAO extends CrudDAO {
     String getNextOrderId();
     boolean saveOrder(OrderDto orderDto);
}
