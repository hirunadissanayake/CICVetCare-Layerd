package lk.ijse.gdse.main.cicvetcare.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private String orderId;
    private LocalDate order_date;
    private String order_status;
    private String customer_id;
    private  String employee_id;

    private ArrayList<OrderItemDto> orderItemDtos;
}
