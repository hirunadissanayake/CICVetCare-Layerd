package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;

import lk.ijse.gdse.main.cicvetcare.bo.custom.EmployeeBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.EmployeeDAO;
import lk.ijse.gdse.main.cicvetcare.dto.EmployeeDto;
import lk.ijse.gdse.main.cicvetcare.entity.EmployeeEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    private EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public boolean save(EmployeeDto dto) throws SQLException {
        return employeeDAO.save(new EmployeeEntity(dto.getEmployeeId(),dto.getEmployeeName(),dto.getPosition(),dto.getContact()));
    }

    @Override
    public String getNextId() throws SQLException {
        return employeeDAO.getNextId();
    }

    @Override
    public ArrayList<EmployeeDto> getAll() throws SQLException {
        ArrayList<EmployeeDto> dtos = new ArrayList<>();
        ArrayList<EmployeeEntity> employees = employeeDAO.getAll();
        for (EmployeeEntity employee : employees) {
            dtos.add(new EmployeeDto(employee.getEmployeeId(),employee.getEmployeeName(),employee.getPosition(),employee.getContact()));
        }
        return dtos;
    }

    @Override
    public boolean update(EmployeeDto dto) throws SQLException {
        return employeeDAO.update(new EmployeeEntity(dto.getEmployeeId(),dto.getEmployeeName(),dto.getPosition(),dto.getContact()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return employeeDAO.delete(id);
    }
}
