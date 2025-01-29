package lk.ijse.gdse.main.cicvetcare.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {
    private String paymentId;
    private double amount;
    private String paymentDate;
    private String orderId;

}
