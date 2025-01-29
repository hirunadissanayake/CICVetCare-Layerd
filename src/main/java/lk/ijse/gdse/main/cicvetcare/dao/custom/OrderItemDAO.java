package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.OrderItemDto;


import java.util.ArrayList;

public interface OrderItemDAO extends CrudDAO {
     boolean saveOrderDetailsList(ArrayList<OrderItemDto> orderItemDtos);
     boolean saveOrderDetail(OrderItemDto orderDetailsDto);
}
