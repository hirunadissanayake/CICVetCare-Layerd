package lk.ijse.gdse.main.cicvetcare.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentTm {
    private String paymentId;
    private double amount;
    private String paymentDate;
    private String orderId;
}
