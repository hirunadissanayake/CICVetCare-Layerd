package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.OrderDto;
import lk.ijse.gdse.main.cicvetcare.entity.OrderEntity;
import lk.ijse.gdse.main.cicvetcare.entity.OrderItemEntity;

import java.sql.SQLException;


public interface OrdersDAO extends CrudDAO<OrderEntity> {
     /*String getNextOrderId();
     boolean saveOrder(OrderDto orderDto);*/
     boolean saveOrder(OrderItemEntity orderDto, OrderEntity orderDtos) throws SQLException;

//    boolean saveOrder(OrderEntity order);
}
