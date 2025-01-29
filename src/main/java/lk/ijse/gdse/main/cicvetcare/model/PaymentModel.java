package lk.ijse.gdse.main.cicvetcare.model;

import lk.ijse.gdse.main.cicvetcare.dto.PaymentDto;
import lk.ijse.gdse.main.cicvetcare.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {

    public boolean savePayment(PaymentDto paymentDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Payment VALUES(?, ?, ?, ?)",
                paymentDto.getPaymentId(),
                paymentDto.getAmount(),
                paymentDto.getPaymentDate(),
                paymentDto.getOrderId()
        );
    }

    public boolean updatePayment(PaymentDto paymentDto) throws SQLException {
        return CrudUtil.execute("UPDATE Payment SET amount = ?, payment_date = ?, order_id = ? WHERE payment_id = ?",
                paymentDto.getAmount(),
                paymentDto.getPaymentDate(),
                paymentDto.getOrderId(),
                paymentDto.getPaymentId()
        );
    }

    public boolean deletePayment(String paymentId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Payment WHERE payment_id = ?", paymentId);
    }

    public ArrayList<PaymentDto> getAllPayments() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Payment");
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();

        while (rst.next()) {
            PaymentDto paymentDto = new PaymentDto(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }

    public String getNextPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT payment_id FROM Payment ORDER BY payment_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            if (lastId != null && lastId.startsWith("PM")) {
                int id = Integer.parseInt(lastId.substring(2));
                return String.format("PM%04d", id + 1);
            }
        }
        return "PM0001";
    }
}
