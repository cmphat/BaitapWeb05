package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.entity.Category;

public interface ICategoryDao {

    void insert(Category category);

    void update(Category category);

    void delete(int id);

    Category findById(int id);

    Category findByCategoryname(String name);

    List<Category> findAll();

    List<Category> searchByName(String keyword);

    int count();
}