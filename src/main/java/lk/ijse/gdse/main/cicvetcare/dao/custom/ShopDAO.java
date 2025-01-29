package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.ShopDto;


import java.util.ArrayList;

public interface ShopDAO extends CrudDAO {
     boolean saveShop(ShopDto shopDto);
     boolean updateShop(ShopDto shopDto);
     boolean deleteShop(String shopId);
     ArrayList<ShopDto> getAllShops();
     String getNextShopId();
}
