package lk.ijse.gdse.main.cicvetcare.dao.custom.impl;

import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dao.custom.ShopDAO;
import lk.ijse.gdse.main.cicvetcare.entity.ShopEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShopDAOImpl implements ShopDAO {

    public boolean save(ShopEntity shopDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Shop VALUES(?, ?, ?, ?, ?)",
                shopDto.getShopId(),
                shopDto.getShopName(),
                shopDto.getContactNo(),
                shopDto.getLocation(),
                shopDto.getCustId()
        );
    }

    public boolean update(ShopEntity shopDto) throws SQLException {
        return SQLUtil.execute("UPDATE Shop SET name=?, contact_info=?, location=?, customer_id=? WHERE shop_id=?",
                shopDto.getShopName(),
                shopDto.getContactNo(),
                shopDto.getLocation(),
                shopDto.getCustId(),
                shopDto.getShopId()
        );
    }

    public boolean delete(String shopId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Shop WHERE shop_id=?", shopId);
    }

    public ArrayList<ShopEntity> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Shop");
        ArrayList<ShopEntity> shopDtos = new ArrayList<>();

        while (rst.next()) {
            ShopEntity shopDto = new ShopEntity(
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

    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT shop_id FROM Shop ORDER BY shop_id DESC LIMIT 1");

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
