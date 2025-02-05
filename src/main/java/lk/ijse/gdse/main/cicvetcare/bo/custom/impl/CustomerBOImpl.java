package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;

import lk.ijse.gdse.main.cicvetcare.bo.custom.CustomerBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.CustomerDAO;
import lk.ijse.gdse.main.cicvetcare.dto.CustomerDto;
import lk.ijse.gdse.main.cicvetcare.entity.CustomerEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);



    @Override
    public boolean save(CustomerDto dto) throws SQLException {
        return customerDAO.save(new CustomerEntity(dto.getCustId(),dto.getCustName(),dto.getType(),dto.getContact()));
    }

    @Override
    public String getNextId() throws SQLException {
        return customerDAO.getNextId();
    }

    @Override
    public ArrayList<CustomerDto> getAll() throws SQLException {
        ArrayList<CustomerEntity> customerDtos = customerDAO.getAll();
        ArrayList<CustomerDto> dtos = new ArrayList<>();
        for (CustomerEntity customerEntity : customerDtos) {
            dtos.add(new CustomerDto(customerEntity.getCustId(),customerEntity.getCustName(),customerEntity.getType(),customerEntity.getContact()));
        }
        return dtos;
    }

    @Override
    public boolean update(CustomerDto dto) throws SQLException {
        return customerDAO.update(new CustomerEntity(dto.getCustId(),dto.getCustName(),dto.getType(),dto.getContact()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerEntity findById(String selectedCustId) throws SQLException {
        return customerDAO.findById(selectedCustId);
    }

    @Override
    public ArrayList<String> getCustIds() throws SQLException {
        return customerDAO.getCustIds();
    }

    @Override
    public CustomerEntity SearchCustomerByContact(String contact) {
        return customerDAO.SearchCustomerByContact(contact);
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException {
        return customerDAO.getAllCustomerIds();
    }
}
