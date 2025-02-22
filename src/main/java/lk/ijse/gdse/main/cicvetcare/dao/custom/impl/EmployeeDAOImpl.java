package lk.ijse.gdse.main.cicvetcare.dao.custom.impl;

import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dao.custom.EmployeeDAO;
import lk.ijse.gdse.main.cicvetcare.entity.EmployeeEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    public boolean save(EmployeeEntity employeeDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Employee VALUES(?,?,?,?)",
                employeeDto.getEmployeeId(),
                employeeDto.getEmployeeName(),
                employeeDto.getPosition(),
                employeeDto.getContact()

        );
    }

    public boolean delete(String employeeId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Employee WHERE employee_id = ?",employeeId);
    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM Employee ORDER BY employee_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);

            // Ensure lastId is not null and starts with "E" to match the expected format
            if (lastId != null && lastId.startsWith("E")) {
                String subString = lastId.substring(1);  // Remove the "E" prefix
                try {
                    int i = Integer.parseInt(subString);  // Parse remaining part as integer
                    int newIndex = i + 1;
                    return String.format("E%04d", newIndex);  // Return formatted ID
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing last ID: " + lastId);
                }
            }
        }
        // Fallback to "E0001" if no IDs exist or lastId is not in expected format
        return "E0001";
    }

    public ArrayList<EmployeeEntity> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee");
        ArrayList<EmployeeEntity> employeeDtos = new ArrayList<>();

        while (rst.next()) {
            EmployeeEntity employeeDto = new EmployeeEntity(
                    rst.getString("employee_id"),
                    rst.getString("name"),
                    rst.getString("position"),
                    rst.getString("contact_info")

            );
            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }

    public boolean update(EmployeeEntity employeeDto) throws SQLException {
        return SQLUtil.execute("UPDATE Employee SET name=?, position=?, contact_info=? WHERE employee_id=?",

                employeeDto.getEmployeeName(),
                employeeDto.getPosition(),
                employeeDto.getContact(),
                employeeDto.getEmployeeId()
        );

    }
}
