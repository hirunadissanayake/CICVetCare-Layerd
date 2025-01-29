package lk.ijse.gdse.main.cicvetcare.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerEntity {
    private String custId;
    private String custName;
    private String type;
    private String contact;
}
