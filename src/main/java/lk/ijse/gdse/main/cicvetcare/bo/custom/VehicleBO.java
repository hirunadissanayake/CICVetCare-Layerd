package lk.ijse.gdse.main.cicvetcare.bo.custom;

import lk.ijse.gdse.main.cicvetcare.bo.SuperBO;
import lk.ijse.gdse.main.cicvetcare.dto.VehicleDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleBO extends SuperBO {
    boolean save(VehicleDto dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<VehicleDto> getAll() throws SQLException;;
    boolean update(VehicleDto dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;
}
