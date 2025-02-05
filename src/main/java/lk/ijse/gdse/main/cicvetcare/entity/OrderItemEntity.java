package lk.ijse.gdse.main.cicvetcare.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemEntity {
    private String order_id;
    private String product_id;
    private int qty;
    private double price;

    private OrderEntity order;

    public OrderItemEntity(String orderId, String productId, int qty, double price) {
    }
}
