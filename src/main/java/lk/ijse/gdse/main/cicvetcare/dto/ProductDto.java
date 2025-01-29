package lk.ijse.gdse.main.cicvetcare.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
    private String productId;
    private String productName;
    private double price;
    private String catId;
}
