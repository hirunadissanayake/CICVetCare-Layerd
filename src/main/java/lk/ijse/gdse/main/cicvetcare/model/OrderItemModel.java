package lk.ijse.gdse.main.cicvetcare.model;

import lk.ijse.gdse.main.cicvetcare.dto.OrderItemDto;
import lk.ijse.gdse.main.cicvetcare.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemModel {
    public boolean saveOrderDetailsList(ArrayList<OrderItemDto> orderItemDtos) throws SQLException {
        for (OrderItemDto orderDetailsDto : orderItemDtos) {
            // @isOrderDetailsSaved: Saves the individual order detail
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDto);
            if (!isOrderDetailsSaved) {
                // Return false if saving any order detail fails
                return false;
            }

            // @isItemUpdated: Updates the item quantity in the stock for the corresponding order detail
            boolean isItemUpdated = InventoryModel.reduceQty(orderDetailsDto);
            if (!isItemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        // Return true if all order details are saved and item quantities updated successfully
        return true;
    }

    private boolean saveOrderDetail(OrderItemDto orderDetailsDto) throws SQLException {
        return CrudUtil.execute("insert into Order_Item values(?,?,?,?)",
                orderDetailsDto.getOrder_id(),
                orderDetailsDto.getProduct_id(),
                orderDetailsDto.getQty(),
                orderDetailsDto.getPrice()
        );
    }
}
