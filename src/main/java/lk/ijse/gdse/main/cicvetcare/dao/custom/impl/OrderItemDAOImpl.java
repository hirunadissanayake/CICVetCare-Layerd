package lk.ijse.gdse.main.cicvetcare.dao.custom.impl;

import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dao.custom.OrderItemDAO;
import lk.ijse.gdse.main.cicvetcare.entity.OrderItemEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemDAOImpl implements OrderItemDAO {
    public boolean saveOrderDetailsList(OrderItemEntity orderItemDtos) throws SQLException {
            // @isOrderDetailsSaved: Saves the individual order detail
            boolean isOrderDetailsSaved = save(orderItemDtos);
        System.out.println(orderItemDtos);
            if (!isOrderDetailsSaved) {
                // Return false if saving any order detail fails
                return false;
            }

            // @isItemUpdated: Updates the item quantity in the stock for the corresponding order detail
            boolean isItemUpdated = InventoryDAOImpl.reduceQty(orderItemDtos);
            if (!isItemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        // Return true if all order details are saved and item quantities updated successfully
        return true;
    }

     public boolean save(OrderItemEntity orderDetailsDto) throws SQLException {
         try {
             System.out.println(orderDetailsDto);
             return SQLUtil.execute("insert into Order_Item values(?,?,?,?)",
                     orderDetailsDto.getOrder_id(),
                     orderDetailsDto.getProduct_id(),
                     orderDetailsDto.getQty(),
                     orderDetailsDto.getPrice()
             );
         }catch (Exception e){
             e.printStackTrace();
         }
         return false;
    }

    @Override
    public String getNextId() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<OrderItemEntity> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(OrderItemEntity dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }
}
