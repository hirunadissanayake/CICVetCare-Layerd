package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;

import lk.ijse.gdse.main.cicvetcare.bo.custom.DriverBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.DriverDAO;
import lk.ijse.gdse.main.cicvetcare.dto.DriverDto;
import lk.ijse.gdse.main.cicvetcare.entity.DriverEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class DriverBOImpl implements DriverBO {
     DriverDAO driverDAO = (DriverDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.DRIVER);
    @Override
    public boolean save(DriverDto dto) throws SQLException {
        return driverDAO.save(new DriverEntity(dto.getDriverId(),dto.getDriverName(),dto.getDriverContact(),dto.getLicense()));
    }

    @Override
    public String getNextId() throws SQLException {
        return driverDAO.getNextId();
    }

    @Override
    public ArrayList<DriverDto> getAll() throws SQLException {
        ArrayList<DriverEntity> driverEntities = driverDAO.getAll();
        ArrayList<DriverDto> driverDtos = new ArrayList<>();
        for (DriverEntity driverEntity : driverEntities) {
            driverDtos.add(new DriverDto(driverEntity.getDriverId(),driverEntity.getDriverName(),driverEntity.getDriverContact(),driverEntity.getLicense()));
        }
        return driverDtos;
    }

    @Override
    public boolean update(DriverDto dto) throws SQLException {
        return driverDAO.update(new DriverEntity(dto.getDriverId(),dto.getDriverName(),dto.getDriverContact(),dto.getLicense()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return driverDAO.delete(id);
    }
}
