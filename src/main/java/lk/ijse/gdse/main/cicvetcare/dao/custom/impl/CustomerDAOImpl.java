package lk.ijse.gdse.main.cicvetcare.dao.custom.impl;

import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dto.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl {
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Customer VALUES(?,?,?,?)",
                customerDto.getCustId(),
                customerDto.getCustName(),
                customerDto.getType(),
                customerDto.getContact()
                );

    }

    public String getNextCustomerId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT customer_id FROM Customer ORDER BY customer_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);

            // Ensure lastId is not null and starts with "C" to match the expected format
            if (lastId != null && lastId.startsWith("C")) {
                String subString = lastId.substring(1);  // Remove the "C" prefix
                try {
                    int i = Integer.parseInt(subString);  // Parse remaining part as integer
                    int newIndex = i + 1;
                    return String.format("C%04d", newIndex);  // Return formatted ID
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing last ID: " + lastId);
                }
            }
        }
        // Fallback to "C0001" if no IDs exist or lastId is not in expected format
        return "C0001";
    }


    public ArrayList<CustomerDto> getAllCustomer() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();

        while (rst.next()) {
            CustomerDto customerDto = new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }

    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        return SQLUtil.execute("UPDATE Customer SET name=?, type = ?, contact_info = ? WHERE customer_id = ?",
                customerDto.getCustName(),
                customerDto.getType(),
                customerDto.getContact(),
                customerDto.getCustId()
                );
    }

    public boolean deleteCustomer(String customerId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Customer WHERE customer_id = ?",customerId);
    }

    public CustomerDto SearchCustomerByContact(String contact) {
        try{
            ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE contact_info = ?",contact);
            if (rst.next()){
                return new CustomerDto(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4)
                );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select customer_id from Customer where type ='Wholesale'");
        ArrayList<String> customerIds = new ArrayList<>();
        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }
        return customerIds;
    }

    public CustomerDto findById(String selectedCustId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Customer WHERE customer_id = ?",selectedCustId);
        if (resultSet.next()) {
            return new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public ArrayList<String> getCustIds() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT customer_id FROM Customer");
        ArrayList<String> custIds = new ArrayList<>();
        while (resultSet.next()){
            custIds.add(resultSet.getString(1));
        }
        return custIds;
    }
}
