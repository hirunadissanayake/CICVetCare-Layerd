package lk.ijse.gdse.main.cicvetcare.dao.custom.impl;

import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dto.VehicleDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDAOImpl {
    public boolean saveVehicle(VehicleDto vehicleDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Vehicle VALUES(?, ?, ?, ?)",
                vehicleDto.getVehicleId(),
                vehicleDto.getVehicleType(),
                vehicleDto.getLicensePlate(),
                vehicleDto.getDriverId());
    }

    public boolean deleteVehicle(String vehicleId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Vehicle WHERE vehicle_id = ?", vehicleId);
    }

    public ArrayList<VehicleDto> getAllVehicles() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Vehicle");
        ArrayList<VehicleDto> vehicleDtos = new ArrayList<>();

        while (rst.next()) {
            VehicleDto vehicleDto = new VehicleDto(
                    rst.getString("vehicle_id"),
                    rst.getString("vehicle_type"),
                    rst.getString("license_plate"),
                    rst.getString("driver_id")
            );
            vehicleDtos.add(vehicleDto);
        }
        return vehicleDtos;
    }

    public boolean updateVehicle(VehicleDto vehicleDto) throws SQLException {
        return SQLUtil.execute("UPDATE Vehicle SET vehicle_type = ?, license_plate = ?, driver_id = ? WHERE vehicle_id = ?",
                vehicleDto.getVehicleType(),
                vehicleDto.getLicensePlate(),
                vehicleDto.getDriverId(),
                vehicleDto.getVehicleId());
    }

    public String getNextVehicleId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT vehicle_id FROM Vehicle ORDER BY vehicle_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            if (lastId != null && lastId.startsWith("V")) {
                int newIndex = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("V%04d", newIndex);
            }
        }
        return "V0001";
    }
}
