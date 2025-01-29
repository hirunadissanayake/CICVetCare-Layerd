package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;

public interface UserDAO extends CrudDAO {
      boolean searchUser(String email, String password);
      boolean updateUser(String selectedEmail, String password);
}
