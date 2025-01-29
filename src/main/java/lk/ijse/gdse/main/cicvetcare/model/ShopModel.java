package lk.ijse.gdse.main.cicvetcare.model;

import lk.ijse.gdse.main.cicvetcare.dto.ShopDto;
import lk.ijse.gdse.main.cicvetcare.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShopModel {

    public boolean saveShop(ShopDto shopDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Shop VALUES(?, ?, ?, ?, ?)",
                shopDto.getShopId(),
                shopDto.getShopName(),
                shopDto.getContactNo(),
                shopDto.getLocation(),
                shopDto.getCustId()
        );
    }

    public boolean updateShop(ShopDto shopDto) throws SQLException {
        return CrudUtil.execute("UPDATE Shop SET name=?, contact_info=?, location=?, customer_id=? WHERE shop_id=?",
                shopDto.getShopName(),
                shopDto.getContactNo(),
                shopDto.getLocation(),
                shopDto.getCustId(),
                shopDto.getShopId()
        );
    }

    public boolean deleteShop(String shopId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Shop WHERE shop_id=?", shopId);
    }

    public ArrayList<ShopDto> getAllShops() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Shop");
        ArrayList<ShopDto> shopDtos = new ArrayList<>();

        while (rst.next()) {
            ShopDto shopDto = new ShopDto(
                    rst.getString("shop_id"),
                    rst.getString("name"),
                    rst.getString("contact_info"),
                    rst.getString("location"),
                    rst.getString("customer_id")
            );
            shopDtos.add(shopDto);
        }
        return shopDtos;
    }

    public String getNextShopId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT shop_id FROM Shop ORDER BY shop_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            if (lastId != null && lastId.startsWith("SHP")) {
                int newId = Integer.parseInt(lastId.substring(3)) + 1;
                return String.format("SHP%03d", newId);
            }
        }
        return "SHP001";  // Default ID if no records exist
    }
}
