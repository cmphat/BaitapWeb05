package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.dao.impl.CategoryDao;
import vn.iotstar.entity.Category;
import vn.iotstar.service.ICategoryService;

public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryDao cateDao = new CategoryDao();

    @Override
    public void insert(Category category) {

        Category old = findByCategoryname(category.getCategoryname());

        if (old == null) {
            cateDao.insert(category);
        }
    }

    @Override
    public void update(Category category) {

        Category old = findById(category.getCategoryid());

        if (old != null) {
            cateDao.update(category);
        }
    }

    @Override
    public void delete(int id) {
        cateDao.delete(id);
    }

    @Override
    public Category findById(int id) {
        return cateDao.findById(id);
    }

    @Override
    public Category findByCategoryname(String name) {
        return cateDao.findByCategoryname(name);
    }

    @Override
    public List<Category> findAll() {
        return cateDao.findAll();
    }

    @Override
    public List<Category> searchByName(String keyword) {
        return cateDao.searchByName(keyword);
    }

    @Override
    public int count() {
        return cateDao.count();
    }
}