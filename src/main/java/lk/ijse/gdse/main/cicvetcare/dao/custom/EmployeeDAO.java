package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.EmployeeDto;


import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO {
     boolean saveEmployee(EmployeeDto employeeDto);
     boolean deleteEmployee(String employeeId);
     String getNextEmployeeId();
     ArrayList<EmployeeDto> getAllEmployee();
     boolean updateEmployee(EmployeeDto employeeDto);
}
