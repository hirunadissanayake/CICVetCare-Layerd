package lk.ijse.gdse.main.cicvetcare.bo.custom;

import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.OrderDto;
import lk.ijse.gdse.main.cicvetcare.dto.OrderItemDto;

import java.sql.SQLException;
import java.util.ArrayList;


public interface OrdersBO extends SuperBO {
    boolean save(OrderDto dto, OrderItemDto orderItemDto) throws SQLException;
    String getNextId() throws SQLException;;



}
