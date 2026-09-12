package vn.iotstar.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iotstar.entity.Category;

public interface ICategoryService {

    void insert(Category category);

    void update(Category category);

    void delete(int id);

    Category findById(int id);

    Category findByCategoryname(String name);

    List<Category> findAll();

    List<Category> searchByName(String keyword);

    Page<Category> findAll(Pageable pageable);

    Page<Category> searchByName(String keyword, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByNameExceptId(String name, int id);

    long countProductsByCategory(int categoryId);

    int count();
}