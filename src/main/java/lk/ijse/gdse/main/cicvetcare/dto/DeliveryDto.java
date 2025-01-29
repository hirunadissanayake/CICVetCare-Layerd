package lk.ijse.gdse.main.cicvetcare.dto;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryDto {
    private String deliveryId;
    private LocalDate deliveryDate;
    private String deliveryStatus;
    private String orderId;
    private String vehicleId;
    private String driverId;
    private String shopId;

}
