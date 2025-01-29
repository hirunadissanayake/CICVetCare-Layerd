package lk.ijse.gdse.main.cicvetcare.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryTm {
    private String inventoryId;
    private String productId;
    private int stock;
    private  String location;
}
