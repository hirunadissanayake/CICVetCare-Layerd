package lk.ijse.gdse.main.cicvetcare.dao;



import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T>{
    boolean save(T dto) throws SQLException;
    String getNextId() throws SQLException;;
    ArrayList<T> getAll() throws SQLException;;
    boolean update(T dto) throws SQLException;;
    boolean delete(String id) throws SQLException;;

}
