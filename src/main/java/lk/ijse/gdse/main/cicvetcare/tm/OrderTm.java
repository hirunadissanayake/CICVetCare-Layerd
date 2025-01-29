package lk.ijse.gdse.main.cicvetcare.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderTm {
    private String order_id;
    private LocalDate order_date;
    private String product_id;
    private int qty;
    private double unit_price;
    private double total;
    private Button removeBtn;
}
