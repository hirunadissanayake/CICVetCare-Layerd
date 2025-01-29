package lk.ijse.gdse.main.cicvetcare.dao;

import lk.ijse.gdse.main.cicvetcare.dto.CustomerDto;

import java.util.ArrayList;

public interface CrudDAO{
    boolean save(CustomerDto customerDto) ;
    String getNextId() ;
    ArrayList<CustomerDto> getAllCustomer();
    boolean update(CustomerDto customerDto);
    boolean delete(String customerId);
    //CustomerDto SearchCustomerByContact(String contact);
    ArrayList<String> getAllIds();
    CustomerDto findById(String selectedCustId);
    ArrayList<String> getIds();
}
