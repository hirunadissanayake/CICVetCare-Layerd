package lk.ijse.gdse.main.cicvetcare.bo.custom.impl;

import lk.ijse.gdse.main.cicvetcare.bo.custom.CategoryBO;
import lk.ijse.gdse.main.cicvetcare.dao.DAOFactory;
import lk.ijse.gdse.main.cicvetcare.dao.custom.CategoryDAO;
import lk.ijse.gdse.main.cicvetcare.dto.CategoryDto;
import lk.ijse.gdse.main.cicvetcare.entity.CategoryEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryBOImpl implements CategoryBO {

    CategoryDAO categoryDAO = (CategoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CATEGORY);
    @Override
    public boolean save(CategoryDto dto) throws SQLException {
        return categoryDAO.save(new CategoryEntity(dto.getCategoryId(), dto.getCategoryName()));
    }

    @Override
    public String getNextId() throws SQLException {
        return categoryDAO.getNextId();
    }

    @Override
    public ArrayList<CategoryDto> getAll() throws SQLException {
        ArrayList<CategoryEntity> categoryEntities = categoryDAO.getAll();
        ArrayList<CategoryDto> categoryDtos = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            categoryDtos.add(new CategoryDto(categoryEntity.getCategoryId(), categoryEntity.getCategoryName()));
        }
        return categoryDtos;
    }

    @Override
    public boolean update(CategoryDto dto) throws SQLException {
        return categoryDAO.update(new CategoryEntity(dto.getCategoryId(), dto.getCategoryName()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return categoryDAO.delete(id);
    }
}
