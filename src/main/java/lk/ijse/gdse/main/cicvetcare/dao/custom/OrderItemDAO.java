package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.OrderItemDto;
import lk.ijse.gdse.main.cicvetcare.entity.OrderEntity;
import lk.ijse.gdse.main.cicvetcare.entity.OrderItemEntity;


import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderItemDAO extends CrudDAO<OrderItemEntity> {
     boolean saveOrderDetailsList(OrderItemEntity orderItemDtos) throws SQLException;
     /*boolean saveOrderDetail(OrderItemDto orderDetailsDto);*/
}
