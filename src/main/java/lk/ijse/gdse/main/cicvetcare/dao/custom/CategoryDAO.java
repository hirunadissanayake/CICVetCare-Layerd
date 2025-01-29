package lk.ijse.gdse.main.cicvetcare.dao.custom;


import lk.ijse.gdse.main.cicvetcare.dao.CrudDAO;
import lk.ijse.gdse.main.cicvetcare.dto.CategoryDto;


import java.util.ArrayList;

public interface CategoryDAO extends CrudDAO {

    String getNextCategoryId();
    ArrayList<CategoryDto> getAllCategories();
    boolean saveCategory(CategoryDto categoryDto);
    boolean updateCategory(CategoryDto categoryDto);
    boolean deleteCategory(String catId);
}
