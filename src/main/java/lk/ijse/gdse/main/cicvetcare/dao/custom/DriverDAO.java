package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.DriverDto;


import java.util.ArrayList;

public interface DriverDAO extends CrudDAO {
     boolean saveDriver(DriverDto driverDto);
     boolean updateDriver(DriverDto driverDto);
     boolean deleteDriver(String driverId);
     ArrayList<DriverDto> getAllDrivers();
     String getNextDriverId();
}
