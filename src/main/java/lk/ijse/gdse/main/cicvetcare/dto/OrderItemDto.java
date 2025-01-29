package lk.ijse.gdse.main.cicvetcare.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemDto {
    private String order_id;
    private String product_id;
    private int qty;
    private double price;
}
