package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.entity.OrderItemEntity;


import java.sql.SQLException;

public interface OrderItemDAO extends CrudDAO<OrderItemEntity> {
     boolean saveOrderDetailsList(OrderItemEntity orderItemDtos) throws SQLException;
     /*boolean saveOrderDetail(OrderItemDto orderDetailsDto);*/
}
