package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;

import lk.ijse.gdse.main.cicvetcare.bo.custom.VehicleBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.VehicleDAO;
import lk.ijse.gdse.main.cicvetcare.dto.VehicleDto;
import lk.ijse.gdse.main.cicvetcare.entity.VehicleEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleBOImpl implements VehicleBO {
    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.VEHICLE);

    @Override
    public boolean save(VehicleDto dto) throws SQLException {
        return vehicleDAO.save(new VehicleEntity(dto.getVehicleId(),dto.getVehicleType(),dto.getDriverId(),dto.getLicensePlate()));
    }

    @Override
    public String getNextId() throws SQLException {
        return vehicleDAO.getNextId();
    }

    @Override
    public ArrayList<VehicleDto> getAll() throws SQLException {
        ArrayList<VehicleEntity> vehicles = vehicleDAO.getAll();
        ArrayList<VehicleDto> vehicleDtos = new ArrayList<>();
        for (VehicleEntity vehicle : vehicles) {
            vehicleDtos.add(new VehicleDto(vehicle.getVehicleId(),vehicle.getVehicleType(),vehicle.getDriverId(),vehicle.getLicensePlate()));
        }
        return vehicleDtos;
    }

    @Override
    public boolean update(VehicleDto dto) throws SQLException {
        return vehicleDAO.update(new VehicleEntity(dto.getVehicleId(),dto.getVehicleType(),dto.getDriverId(),dto.getLicensePlate()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return vehicleDAO.delete(id);
    }
}
