package lk.ijse.gdse.main.cicvetcare.dao.custom;



import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.entity.CustomerEntity;


import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<CustomerEntity> {
   // boolean saveCustomer(CustomerDto customerDto) ;
    //String getNextCustomerId() ;
    //ArrayList<CustomerDto> getAllCustomer();
    //boolean updateCustomer(CustomerDto customerDto);
    //boolean deleteCustomer(String customerId);
   CustomerEntity SearchCustomerByContact(String contact);
    ArrayList<String> getAllCustomerIds() throws SQLException;
    CustomerEntity findById(String selectedCustId) throws SQLException;
    ArrayList<String> getCustIds() throws SQLException;
}
