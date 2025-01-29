package lk.ijse.gdse.main.cicvetcare.model;

import lk.ijse.gdse.main.cicvetcare.db.DBConnection;
import lk.ijse.gdse.main.cicvetcare.dto.OrderDto;
import lk.ijse.gdse.main.cicvetcare.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersModel {
    private final OrderItemModel orderItemModel= new OrderItemModel();
    public String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select order_id from Orders order by order_id desc limit 1");

        if (rst.next()){
            String lastId = rst.getString(1);
            String subString = lastId.substring(2);
            int i = Integer.parseInt(subString);
            int newIndex = i +1;
            return String.format("OD%04d", newIndex);
        }
        return "OD0001";
    }

    public boolean saveOrder(OrderDto orderDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isOrderSaved: Saves the order details into the orders table
            boolean isOrderSaved = CrudUtil.execute(
                    "INSERT INTO Orders (order_id, customer_id, order_date) VALUES (?, ?, ?)",
                    orderDto.getOrderId(),
                    orderDto.getCustomer_id(),
                    orderDto.getOrder_date()

            );
            // If the order is saved successfully
            if (isOrderSaved) {
                // @isOrderDetailListSaved: Saves the list of order details
                boolean isOrderDetailListSaved = orderItemModel.saveOrderDetailsList(orderDto.getOrderItemDtos());
                if (isOrderDetailListSaved) {
                    // @commit: Commits the transaction if both order and details are saved successfully
                    connection.commit(); // 2
                    return true;
                }
            }
            // @rollback: Rolls back the transaction if order details saving fails
            connection.rollback(); // 3
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
    }
}
