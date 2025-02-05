package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;

import lk.ijse.gdse.main.cicvetcare.bo.custom.PaymentBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.PaymentDAO;
import lk.ijse.gdse.main.cicvetcare.dto.PaymentDto;
import lk.ijse.gdse.main.cicvetcare.entity.OrderEntity;
import lk.ijse.gdse.main.cicvetcare.entity.PaymentEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);
    @Override
    public boolean save(PaymentDto dto) throws SQLException {
        return paymentDAO.save(new PaymentEntity(dto.getPaymentId(),dto.getAmount(),dto.getPaymentDate(),dto.getOrderId()));
    }

    @Override
    public String getNextId() throws SQLException {
        return paymentDAO.getNextId();
    }

    @Override
    public ArrayList<PaymentDto> getAll() throws SQLException {
        ArrayList<PaymentEntity> paymentEntities = paymentDAO.getAll();
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        for (PaymentEntity paymentEntity : paymentEntities) {
            paymentDtos.add(new PaymentDto(paymentEntity.getPaymentId(),paymentEntity.getAmount(),paymentEntity.getPaymentDate(),paymentEntity.getOrderId()));
        }
        return paymentDtos;
    }

    @Override
    public boolean update(PaymentDto dto) throws SQLException {
        return paymentDAO.update(new PaymentEntity(dto.getPaymentId(),dto.getAmount(),dto.getPaymentDate(),dto.getOrderId()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return paymentDAO.delete(id);
    }
}
