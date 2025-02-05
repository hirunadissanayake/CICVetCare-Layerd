package lk.ijse.gdse.main.cicvetcare.dao.custom.impl;

import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dao.custom.PaymentDAO;
import lk.ijse.gdse.main.cicvetcare.entity.PaymentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

    public boolean save(PaymentEntity paymentDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Payment VALUES(?, ?, ?, ?)",
                paymentDto.getPaymentId(),
                paymentDto.getAmount(),
                paymentDto.getPaymentDate(),
                paymentDto.getOrderId()
        );
    }

    public boolean update(PaymentEntity paymentDto) throws SQLException {
        return SQLUtil.execute("UPDATE Payment SET amount = ?, payment_date = ?, order_id = ? WHERE payment_id = ?",
                paymentDto.getAmount(),
                paymentDto.getPaymentDate(),
                paymentDto.getOrderId(),
                paymentDto.getPaymentId()
        );
    }

    public boolean delete(String paymentId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Payment WHERE payment_id = ?", paymentId);
    }

    public ArrayList<PaymentEntity> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Payment");
        ArrayList<PaymentEntity> paymentDtos = new ArrayList<>();

        while (rst.next()) {
            PaymentEntity paymentDto = new PaymentEntity(
                    rst.getString(1),
                    rst.getDouble(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT payment_id FROM Payment ORDER BY payment_id DESC LIMIT 1");

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
