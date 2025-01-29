package lk.ijse.gdse.main.cicvetcare.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentEntity {
    private String paymentId;
    private double amount;
    private String paymentDate;
    private String orderId;

}
