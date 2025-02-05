package lk.ijse.gdse.main.cicvetcare.bo.custom;

import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.CustomerDto;
import lk.ijse.gdse.main.cicvetcare.entity.CustomerEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

    boolean save(CustomerDto dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<CustomerDto> getAll() throws SQLException;;
    boolean update(CustomerDto dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;

    CustomerEntity findById(String selectedCustId) throws SQLException;
    ArrayList<String> getCustIds() throws SQLException;
    CustomerEntity SearchCustomerByContact(String contact);
    ArrayList<String> getAllCustomerIds() throws SQLException;

}
