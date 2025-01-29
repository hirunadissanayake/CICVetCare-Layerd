package lk.ijse.gdse.main.cicvetcare.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductEntity {
    private String productId;
    private String productName;
    private double price;
    private String catId;
}
