package lk.ijse.gdse.main.cicvetcare.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryEntity {
    private String inventoryId;
    private String productId;
    private int stock;
    private  String location;
}
