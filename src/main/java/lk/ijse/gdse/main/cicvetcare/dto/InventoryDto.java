package lk.ijse.gdse.main.cicvetcare.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryDto {
    private String inventoryId;
    private String productId;
    private int stock;
    private  String location;
}
