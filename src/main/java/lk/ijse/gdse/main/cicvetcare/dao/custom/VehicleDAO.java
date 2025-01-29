package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.VehicleDto;


import java.util.ArrayList;

public interface VehicleDAO extends CrudDAO {
     boolean saveVehicle(VehicleDto vehicleDto);
     boolean deleteVehicle(String vehicleId);
     ArrayList<VehicleDto> getAllVehicles();
     boolean updateVehicle(VehicleDto vehicleDto);
     String getNextVehicleId();
}
