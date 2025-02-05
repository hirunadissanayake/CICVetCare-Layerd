package lk.ijse.gdse.main.cicvetcare.dao.custom.impl;

import lk.ijse.gdse.main.cicvetcare.dao.SQLUtil;
import lk.ijse.gdse.main.cicvetcare.dao.custom.CategoryDAO;
import lk.ijse.gdse.main.cicvetcare.entity.CategoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAOImpl implements CategoryDAO {
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select category_id from Category order by category_id desc limit 1");

        if (rst.next()){
            String lastId = rst.getString(1);
            String subString = lastId.substring(3);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("CAT%03d", newIndex);

        }
        return "CAT001";
    }

    public ArrayList<CategoryEntity> getAll() throws SQLException {
        ArrayList<CategoryEntity> categoryDtos = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("select * from Category");

        while (rst.next()){
            CategoryEntity categoryDto = new CategoryEntity(
                    rst.getString(1),
                    rst.getString(2)
            );
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    public boolean save(CategoryEntity categoryDto) throws SQLException {
        return SQLUtil.execute("insert into Category values(?,?)",
                categoryDto.getCategoryId(),
                categoryDto.getCategoryName()
        );
    }

    public boolean update(CategoryEntity categoryDto) throws SQLException {
        return SQLUtil.execute("update Category set name = ? where category_id = ?",
                categoryDto.getCategoryName(),
                categoryDto.getCategoryId()
                );
    }

    public boolean delete(String catId) throws SQLException {
        return SQLUtil.execute("delete from Category where category_id = ?",catId);
    }
}
