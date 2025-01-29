package lk.ijse.gdse.main.cicvetcare.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryEntity {
    private String deliveryId;
    private LocalDate deliveryDate;
    private String deliveryStatus;
    private String orderId;
    private String vehicleId;
    private String driverId;
    private String shopId;

}
