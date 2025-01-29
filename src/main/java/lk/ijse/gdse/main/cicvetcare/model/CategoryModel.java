package lk.ijse.gdse.main.cicvetcare.model;

import lk.ijse.gdse.main.cicvetcare.dto.CategoryDto;
import lk.ijse.gdse.main.cicvetcare.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryModel {
    public String getNextCategoryId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select category_id from Category order by category_id desc limit 1");

        if (rst.next()){
            String lastId = rst.getString(1);
            String subString = lastId.substring(3);
            int i = Integer.parseInt(subString);
            int newIndex = i+1;
            return String.format("CAT%03d", newIndex);

        }
        return "CAT001";
    }

    public ArrayList<CategoryDto> getAllCategories() throws SQLException {
        ArrayList<CategoryDto> categoryDtos = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("select * from Category");

        while (rst.next()){
            CategoryDto categoryDto = new CategoryDto(
                    rst.getString(1),
                    rst.getString(2)
            );
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    public boolean saveCategory(CategoryDto categoryDto) throws SQLException {
        return CrudUtil.execute("insert into Category values(?,?)",
                categoryDto.getCategoryId(),
                categoryDto.getCategoryName()
        );
    }

    public boolean updateCategory(CategoryDto categoryDto) throws SQLException {
        return CrudUtil.execute("update Category set name = ? where category_id = ?",
                categoryDto.getCategoryName(),
                categoryDto.getCategoryId()
                );
    }

    public boolean deleteCategory(String catId) throws SQLException {
        return CrudUtil.execute("delete from Category where category_id = ?",catId);
    }
}
