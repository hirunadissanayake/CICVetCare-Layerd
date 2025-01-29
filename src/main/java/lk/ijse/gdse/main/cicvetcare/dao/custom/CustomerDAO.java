package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.CustomerDto;


import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO {
    boolean saveCustomer(CustomerDto customerDto) ;
    String getNextCustomerId() ;
    ArrayList<CustomerDto> getAllCustomer();
    boolean updateCustomer(CustomerDto customerDto);
    boolean deleteCustomer(String customerId);
    CustomerDto SearchCustomerByContact(String contact);
    ArrayList<String> getAllCustomerIds();
    CustomerDto findById(String selectedCustId);
    ArrayList<String> getCustIds();
}
