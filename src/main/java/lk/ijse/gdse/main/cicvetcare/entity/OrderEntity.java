package lk.ijse.gdse.main.cicvetcare.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEntity {
    private String orderId;
    private LocalDate order_date;
    private String order_status;
    private String customer_id;
    private  String employee_id;
    private OrderItemEntity order_item;

    public OrderEntity(String orderId, LocalDate order_date, String order_status, String customer_id, String employee_id) {
        this.orderId = orderId;
        this.order_date = order_date;
        this.order_status = order_status;
        this.customer_id = customer_id;
        this.employee_id = employee_id;

    }

}
