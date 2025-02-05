package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;


import lk.ijse.gdse.main.cicvetcare.bo.custom.OrdersBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.CustomerDAO;
import lk.ijse.gdse.main.cicvetcare.dao.custom.OrderItemDAO;
import lk.ijse.gdse.main.cicvetcare.dao.custom.OrdersDAO;
import lk.ijse.gdse.main.cicvetcare.dao.custom.ProductDAO;
import lk.ijse.gdse.main.cicvetcare.dto.OrderDto;
import lk.ijse.gdse.main.cicvetcare.dto.OrderItemDto;
import lk.ijse.gdse.main.cicvetcare.entity.OrderEntity;
import lk.ijse.gdse.main.cicvetcare.entity.OrderItemEntity;

import java.sql.SQLException;


public class OrdersBOImpl implements OrdersBO {
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDERS);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PRODUCT);
    OrderItemDAO orderItemDAO =(OrderItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDERITEM);



    @Override
    public boolean save(OrderDto dto, OrderItemDto orderItemDto) throws SQLException {
        System.out.println("test3"+dto.getOrderId());
        System.out.println(dto);
        System.out.println(orderItemDto);
        return ordersDAO.saveOrder(
                new OrderItemEntity(orderItemDto.getOrder_id(),orderItemDto.getProduct_id(),orderItemDto.getQty(),orderItemDto.getPrice()).getOrder().getOrder_item(),
                new OrderEntity(dto.getOrderId(),dto.getOrder_date(),dto.getOrder_status(),dto.getCustomer_id(),dto.getEmployee_id()));
    }

    @Override
    public String getNextId() throws SQLException {
        return ordersDAO.getNextId();
    }




}
