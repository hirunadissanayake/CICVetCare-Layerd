package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.PaymentDto;


import java.util.ArrayList;

public interface PaymentDAO extends CrudDAO {
     boolean savePayment(PaymentDto paymentDto);
     boolean updatePayment(PaymentDto paymentDto);
     boolean deletePayment(String paymentId);
     ArrayList<PaymentDto> getAllPayments();
     String getNextPaymentId();
}
