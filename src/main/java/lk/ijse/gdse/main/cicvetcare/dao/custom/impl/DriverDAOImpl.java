package lk.ijse.gdse.main.cicvetcare.dao.custom.impl;

import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dao.custom.DriverDAO;
import lk.ijse.gdse.main.cicvetcare.entity.DriverEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverDAOImpl implements DriverDAO {
    public boolean save(DriverEntity driverDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Driver VALUES(?,?,?,?)",
                driverDto.getDriverId(),
                driverDto.getDriverName(),
                driverDto.getLicense(),
                driverDto.getDriverContact()
        );
    }

    public boolean update(DriverEntity driverDto) throws SQLException {
        return SQLUtil.execute("UPDATE Driver SET name=?, license = ?, contact_info = ? WHERE driver_id = ?",
                driverDto.getDriverName(),
                driverDto.getLicense(),
                driverDto.getDriverContact(),
                driverDto.getDriverId()
        );
    }

    public boolean delete(String driverId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Driver WHERE driver_id = ?",driverId);
    }

    public ArrayList<DriverEntity> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Driver");
        ArrayList<DriverEntity> driverDtos = new ArrayList<>();

        while (rst.next()) {
            DriverEntity driverDto = new DriverEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            driverDtos.add(driverDto);
        }
        return driverDtos;

    }

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT driver_id FROM Driver ORDER BY driver_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);

            // Ensure lastId is not null and starts with "C" to match the expected format
            if (lastId != null && lastId.startsWith("D")) {
                String subString = lastId.substring(1);  // Remove the "C" prefix
                try {
                    int i = Integer.parseInt(subString);  // Parse remaining part as integer
                    int newIndex = i + 1;
                    return String.format("D%04d", newIndex);  // Return formatted ID
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing last ID: " + lastId);
                }
            }
        }
        // Fallback to "D0001" if no IDs exist or lastId is not in expected format
        return "D0001";
    }
}
